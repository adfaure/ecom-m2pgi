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
import fr.ujf.m2pgi.database.Service.PhotoService;

/**
 * Created by AZOUZI Marwen 23/10/15
 */
@Path("/photo")
public class RESTPhotosServlet {
	
	@EJB
	private PhotoService photoService;
	
	@GET
	@Path("/id/{id}")
	@Produces("application/json")
	public Response getPhotoByID(@PathParam("id") String id) {
		PhotoDTO photo =  photoService.getPhotoById(Long.parseLong(id, 10));
		return Response.ok(photo).build();
	}
	
	/*@DELETE
	@Path("/id/{id}")
	@Produces("application/json")
	public Response deletePhotoByID(@PathParam("id") String id) {
		// ...
		return Response.ok();
	}*/
		
	@POST
	@Path("/upload/seller/{id}")
	@Consumes("multipart/form-data")
	@Produces("application/json")
	public Response uploadFile(MultipartFormDataInput input, @PathParam("id") String id) {

		String fileName = "";

		Map<String, List<InputPart>> formParts = input.getFormDataMap();

		List<InputPart> inPart = formParts.get("file");

		for (InputPart inputPart : inPart) {
			 try {
				// Retrieve headers, read the Content-Disposition header to obtain the original name of the file
				MultivaluedMap<String, String> headers = inputPart.getHeaders();
				fileName = parseFileName(headers);

				// Handle the body of that part with an InputStream
				InputStream istream = inputPart.getBody(InputStream.class,null);

				saveFile(istream,"/tmp/" + fileName);

			  } catch (IOException e) {
				  e.printStackTrace();
			  }
		}
		
		String output = "File saved to server location : /tmp/" + fileName;
		
		PhotoDTO photo = new PhotoDTO();
		photo.setPhotoId(0);
		photo.setLocation("/tmp/" + fileName);
		photo.setName(fileName);
		photo.setPrice(2.0f);
		photo.setDescription("Une photo !");
		photo.setSellerID(Long.parseLong(id));
		
		photoService.createPhoto(photo);
		return Response.status(Status.CREATED).entity(output).build();
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
	
	// Save uploaded file to a defined location on the server
	private void saveFile(InputStream uploadedInputStream, String serverLocation) {

		try {
			OutputStream outpuStream = new FileOutputStream(new File(serverLocation));
			int read = 0;
			byte[] bytes = new byte[1024];

			outpuStream = new FileOutputStream(new File(serverLocation));
			while ((read = uploadedInputStream.read(bytes)) != -1) {
				outpuStream.write(bytes, 0, read);
			}
			outpuStream.flush();
			outpuStream.close();
		} catch (IOException e) {

			e.printStackTrace();
		}
	}
}
