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
import org.apache.james.mime4j.message.BodyPart;
import org.apache.james.mime4j.message.SingleBody;
import org.jboss.resteasy.plugins.providers.multipart.InputPart;
import org.jboss.resteasy.plugins.providers.multipart.MultipartFormDataInput;

import fr.ujf.m2pgi.database.DTO.PhotoDTO;
import fr.ujf.m2pgi.database.Service.FileService;
import fr.ujf.m2pgi.database.Service.MemberService;
import fr.ujf.m2pgi.database.Service.PhotoService;

/**
 * Created by AZOUZI Marwen 23/10/15
 */
@Path("/photos")
public class RESTPhotosServlet {

	@EJB
	private PhotoService photoService;

	@EJB
	private MemberService memberService;

	@EJB
	private FileService fileService;

	@Context
	private HttpServletRequest httpServletRequest;

	@GET
	@Path("/")
	@Produces("application/json")
	public Response getAllPhotos() {
		List<PhotoDTO> photos = photoService.getAllPhotos();
		return Response.ok(photos).build();
	}

	@GET
	@Path("/id/{id:[1-9][0-9]*}")
	@Produces("application/json")
	public Response getPhotoByID(@PathParam("id") Long id) {
		PhotoDTO photo =  photoService.getPhotoById(id);
		return Response.ok(photo).build();
	}

	@GET
	@Path("/user/id/{id:[1-9][0-9]*}")
	@Produces("application/json")



	public Response getUserPhotos(@PathParam("id") Long id) {
		List<PhotoDTO> photos = photoService.getUserPhotos(id);
		return Response.ok(photos).build();
	}

	@GET
	@Path("/user/login/{login}")
	@Produces("application/json")
	public Response getUserPhotos(@PathParam("login") String login) {
		List<PhotoDTO> photos = photoService.getUserPhotos(login);
		return Response.ok(photos).build();
	}

	@DELETE
	@Path("/delete/{id:[1-9][0-9]*}")
	@Produces("application/json")
	public Response deletePhotoByID(@PathParam("id") Long id) {
		PhotoDTO photo =  photoService.deletePhoto(id);
		return Response.ok(photo).build();
	}

	@POST
	@Path("/upload/seller/{id:[1-9][0-9]*}")
	@Consumes("multipart/form-data")//@Consumes(MediaType.MULTIPART_FORM_DATA)
	@Produces("application/json")
	@Allow(groups="seller")
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
				InputStream istream = inputPart.getBody(InputStream.class,null);
				fileService.saveFile(istream,"/tmp/" + fileName);

			}

		} catch (IOException e) {
			e.printStackTrace();
		} catch (NumberFormatException nb) {
			nb.printStackTrace();
			return Response.status(Status.BAD_REQUEST).build();
		}

		PhotoDTO photo = new PhotoDTO();
		photo.setLocation("/tmp/" + fileName);
		photo.setName(fileName);
		photo.setPrice(price);
		photo.setDescription(description);
		photo.setSellerID(id);
		PhotoDTO created = photoService.createPhoto(photo);
		if (created == null) return Response.status(Status.BAD_REQUEST).entity("La photo n'a pas été enregistrée !").build();
		return Response.status(Status.CREATED).entity(created).build();
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
