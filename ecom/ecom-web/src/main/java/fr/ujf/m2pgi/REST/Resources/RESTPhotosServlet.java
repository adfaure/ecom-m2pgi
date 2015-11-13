package fr.ujf.m2pgi.REST.Resources;

import java.io.*;
import java.lang.reflect.Field;
import java.text.DecimalFormat;
import java.util.List;
import java.util.Map;

import javax.ejb.EJB;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import fr.ujf.m2pgi.REST.Security.PrincipalUser;
import fr.ujf.m2pgi.REST.Security.SecurityAnnotations.Allow;
import fr.ujf.m2pgi.REST.Security.SecurityAnnotations.AllowAll;
import fr.ujf.m2pgi.facades.FacadePhoto;
import org.apache.james.mime4j.message.BodyPart;
import org.apache.james.mime4j.message.SingleBody;
import org.jboss.resteasy.plugins.providers.multipart.InputPart;
import org.jboss.resteasy.plugins.providers.multipart.MultipartFormDataInput;

import fr.ujf.m2pgi.database.DTO.PhotoDTO;
import fr.ujf.m2pgi.database.Service.FileService;
import fr.ujf.m2pgi.database.Service.MemberService;
import fr.ujf.m2pgi.database.Service.PhotoService;
import fr.ujf.m2pgi.elasticsearch.PhotoDocument;
import fr.ujf.m2pgi.elasticsearch.PhotoServiceES;
import fr.ujf.m2pgi.elasticsearch.SearchResult;

/**
 * Created by AZOUZI Marwen 23/10/15
 */
@Path("/photos")
public class RESTPhotosServlet {

	@EJB
	private FacadePhoto facadePhoto;

	@EJB
	private MemberService memberService;

	@EJB
	private PhotoServiceES photoServiceES;

	@Context
	private HttpServletRequest httpServletRequest;

	@GET
	@Path("/")
	@Produces("application/json")
	@AllowAll
	public Response getAllPhotos() {
		List<PhotoDTO> photos = facadePhoto.getAllPhotos();
		return Response.ok(photos).build();
	}

	@GET
	@Path("/search")
	@Produces("application/json")
	@AllowAll
	public Response getAllPhotosES() {
		SearchResult photos = photoServiceES.getAllPhotos();
		return Response.ok(photos).build();
	}

	@GET
	@Path("/search/{text}")
	@Produces("application/json")
	@AllowAll
	public Response searchPhotosES(@PathParam("text") String text) {
		SearchResult photos = photoServiceES.searchPhotos(text);
		return Response.ok(photos).build();
	}

	@GET
	@Path("/id/{id:[1-9][0-9]*}")
	@Produces("application/json")
	public Response getPhotoByID(@PathParam("id") Long id) {
		PhotoDTO photo =  facadePhoto.getPhotoById(id);
		return Response.ok(photo).build();
	}

	@GET
	@Path("/user/id/{id:[1-9][0-9]*}")
	@Produces("application/json")
	public Response getUserPhotos(@PathParam("id") Long id) {
		List<PhotoDTO> photos = facadePhoto.getUserPhotos(id);
		return Response.ok(photos).build();
	}

	@GET
	@Path("/user/login/{login}")
	@Produces("application/json")
	public Response getUserPhotos(@PathParam("login") String login) {
		List<PhotoDTO> photos = facadePhoto.getUserPhotos(login);
		return Response.ok(photos).build();
	}

	@DELETE
	@Path("/delete/{id:[1-9][0-9]*}")
	@Produces("application/json")
	public Response deletePhotoByID(@PathParam("id") Long id) {
		PhotoDTO photo =  facadePhoto.deletePhoto(id);
		return Response.ok(photo).build();
	}

	@POST
	@Path("/upload/seller/{id:[1-9][0-9]*}")
	@Consumes("multipart/form-data")//@Consumes(MediaType.MULTIPART_FORM_DATA)
	@Produces("application/json")
	@Allow(groups="sellers")
	public Response uploadFile(MultipartFormDataInput input, @PathParam("id") long id) {
		HttpSession session = httpServletRequest.getSession();
		DecimalFormat df = new DecimalFormat("###.##"); //FIXME maybe generalise this
		PrincipalUser user = (PrincipalUser) session.getAttribute("principal");

		if(user.getUser().getMemberID() != id) {
			return Response.status(403).build();
		}

		String fileName    = "";
		String description = "";
		float  price = -1;
		Map<String, List<InputPart>> formParts = input.getFormDataMap();

		List<InputPart> inPart = formParts.get("file");
		if(formParts.containsKey("description")) {
			description = getTextFromPart(formParts.get("description"));
		}

		try {
			InputStream istream = null;
			if(!formParts.containsKey("description")) {
				return Response.status(Status.BAD_REQUEST).build();
			} else {
				String priceString = getTextFromPart(formParts.get("price"));
				price = Float.parseFloat(priceString);
			}

			for (InputPart inputPart : inPart) {

				// Retrieve headers, read the Content-Disposition header to obtain the original name of the file
				MultivaluedMap<String, String> headers = inputPart.getHeaders();
				fileName = parseFileName(headers);
				// Handle the body of that part with an InputStream
				istream = inputPart.getBody(InputStream.class,null);
			}


			PhotoDTO photo = new PhotoDTO();
			photo.setName(fileName);
			photo.setPrice(price);
			photo.setDescription(description);
			photo.setSellerID(id);
			PhotoDTO created = facadePhoto.savePhoto(istream, photo);
			if (created == null) return Response.status(Status.BAD_REQUEST).entity("La photo n'a pas été enregistrée !").build();
			return Response.status(Status.CREATED).entity(created).build();

		} catch (IOException e) {
			e.printStackTrace();
			return Response.status(Status.BAD_REQUEST).build();
		} catch (NumberFormatException nb) {
			nb.printStackTrace();
			return Response.status(Status.BAD_REQUEST).build();
		}
	}
	
	@GET
	@Path("/count")
	@Produces("application/json")
	@Allow(groups = "admin")
	public Response getPhotoCount() {
		Long pCount = facadePhoto.getPhotoCount();
		return Response.ok(pCount).build();
	}

	// Parse Content-Disposition header to get the original file name.
	private String parseFileName(MultivaluedMap<String, String> headers) {

		String[] contentDispositionHeader = headers.getFirst("Content-Disposition").split(";");
		for (String name : contentDispositionHeader) {
			if ((name.trim().startsWith("filename"))) {
				String[] tmp = name.split("=");
				String fileName = tmp[1].trim().replaceAll("\"","");
				return fileName;
			}
		}
		return "randomName";
	}

	private String getTextFromPart(List<InputPart> part) {
		String res = null;
		for (InputPart inputPart : part) {
			try {
				res = inputPart.getBodyAsString();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return res;
	}
}
