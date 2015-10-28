package fr.ujf.m2pgi.REST.Resources;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;
import java.util.Map;

import javax.ejb.EJB;
import javax.ws.rs.*;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

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
	public Response uploadFile(MultipartFormDataInput input, @PathParam("id") long id) {

		String fileName = "";
		//String newName = UUID.randomUUID().toString();

		Map<String, List<InputPart>> formParts = input.getFormDataMap();

		List<InputPart> inPart = formParts.get("file");

		for (InputPart inputPart : inPart) {
			 try {
				// Retrieve headers, read the Content-Disposition header to obtain the original name of the file
				MultivaluedMap<String, String> headers = inputPart.getHeaders();
				fileName = parseFileName(headers);

				// Handle the body of that part with an InputStream
				InputStream istream = inputPart.getBody(InputStream.class,null);

				fileService.saveFile(istream,"/tmp/" + fileName);

			  } catch (IOException e) {
				  e.printStackTrace();
			  }
		}

		PhotoDTO photo = new PhotoDTO();
		photo.setLocation("/tmp/" + fileName);
		photo.setName(fileName);
		photo.setPrice(2.0f);
		photo.setDescription("Description!");
		photo.setSellerID(id);
		PhotoDTO created = photoService.createPhoto(photo);
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
}
