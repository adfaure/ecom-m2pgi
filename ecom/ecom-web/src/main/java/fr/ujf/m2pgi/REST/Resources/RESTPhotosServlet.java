package fr.ujf.m2pgi.REST.Resources;

import java.io.*;
import java.util.List;
import java.util.ArrayList;

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
import fr.ujf.m2pgi.database.DTO.*;
import fr.ujf.m2pgi.database.Service.IMemberService;
import fr.ujf.m2pgi.facades.FacadePhoto;
import org.jboss.resteasy.plugins.providers.multipart.InputPart;

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
	private IMemberService memberService;

	@EJB
	private PhotoServiceES photoServiceES;

	@Context
	private HttpServletRequest httpServletRequest;

	@GET
	@Path("/")
	@Produces("application/json")
	public Response getAllPhotos(@HeaderParam("userID") Long requesterID) {

		if(requesterID != null) {
			List<PhotoContextSmallDTO> contextPhotos = facadePhoto.getAllPhotosContext(requesterID);
			return Response.ok(contextPhotos).build();
		}

		List<PublicPhotoDTO> photos = facadePhoto.getAllAvailablePhotos();
		return Response.ok(photos).build();
	}

	@GET
	@Path("/reported")
	@Produces("application/json")
	@Allow(groups="admin")
	public Response getReportedPhotos() {
		List<PublicPhotoDTO> photos = facadePhoto.getReportedPhotos();
		return Response.ok(photos).build();
	}

	@GET
	@Path("/top10")
	@Produces("application/json")
	public Response getTop10Photos() {
		List<PublicPhotoDTO> photos = facadePhoto.getTop10Photos();
		return Response.ok(photos).build();
	}

	@GET
	@Path("/orderby")
	@Produces("application/json")
	public Response getAllPhotosSortBy(@DefaultValue("date") @QueryParam("criteria") String criteria,
		@DefaultValue("ASC") @QueryParam("order") String order) {

		boolean ascending = !order.equals("DESC");
		List<PublicPhotoDTO> photos = new ArrayList<PublicPhotoDTO>();
		if (criteria.equals("date")) {
			photos = facadePhoto.getPhotosSortByDate(ascending);
		} else if(criteria.equals("price")) {
			photos = facadePhoto.getPhotosSortByPrice(ascending);
		} else if(criteria.equals("likes")) {
			photos = facadePhoto.getPhotosSortByLikes(ascending);
		} else if(criteria.equals("views")) {
			photos = facadePhoto.getPhotosSortByViews(ascending);
		}

		return Response.ok(photos).build();
	}

	@GET
	@Path("/search")
	@Produces("application/json")
	public Response getAllPhotosES() {
		SearchResult photos = photoServiceES.getAllPhotos();
		return Response.ok(photos).build();
	}

	@GET
	@Path("/search/{text}")
	@Produces("application/json")
	public Response searchPhotosES(@PathParam("text") String text, @HeaderParam("userID") Long requesterID) {

		//if(requesterID != null) {
			List<PhotoContextSmallDTO> contextPhotos = facadePhoto.searchPhotosContext(text, requesterID);
			return Response.ok(contextPhotos).build();
		//}

		//SearchResult photos = photoServiceES.searchPhotos(text);
		//return Response.ok(photos).build();
	}

	@GET
	@Path("/id/{id:[1-9][0-9]*}")
	@Produces("application/json")
	public Response getPhotoByID(@PathParam("id") Long id, @HeaderParam("userID") Long requesterID) {
		if(requesterID != null) {
			PhotoContextBigDTO contextPhoto = facadePhoto.getPhotoById(id, requesterID);
			return Response.ok(contextPhoto).build();
		}
		PublicPhotoDTO photo = facadePhoto.getPhotoById(id);
		return Response.ok(photo).build();
	}

	@GET
	@Path("/user/id/{id:[1-9][0-9]*}")
	@Produces("application/json")
	public Response getUserPhotos(@PathParam("id") Long id) {
		List<PublicPhotoDTO> photos = facadePhoto.getUserPhotos(id);
		return Response.ok(photos).build();
	}

	@GET
	@Path("/user/login/{login}")
	@Produces("application/json")
	public Response getUserPhotos(@PathParam("login") String login) {
		List<PublicPhotoDTO> photos = facadePhoto.getUserPhotos(login);
		return Response.ok(photos).build();
	}

	@GET
	@Path("/user/id/{id:[1-9][0-9]*}/wishes")
	@Produces("application/json")
	public Response getUserWishedPhotos(@PathParam("id") Long id) {
		List<WishListPhotoDTO> photos = facadePhoto.getUserWishedPhotos(id);
		return Response.ok(photos).build();
	}

	@GET
	@Path("/user/login/{login}/wishes")
	@Produces("application/json")
	public Response getUserWishedPhotos(@PathParam("login") String login) {
		List<PublicPhotoDTO> photos = facadePhoto.getUserWishedPhotos(login);
		return Response.ok(photos).build();
	}

	@DELETE
	@Path("/delete/{id:[1-9][0-9]*}")
	@Produces("application/json")
	@Allow(groups="sellers")
	public Response deletePhotoByID(@PathParam("id") Long id, @HeaderParam("userID") Long requesterID) {
		PublicPhotoDTO photo = facadePhoto.getPhotoById(id);

		if(photo == null) return Response.status(Status.BAD_REQUEST).build();

		if(!requesterID.equals(photo.getSellerID())) {
			return Response.status(403).build();
		}
		facadePhoto.deletePhoto(id);
		return Response.ok(photo).build();
	}

	@DELETE
	@Path("/reported/{id:[1-9][0-9]*}")
	@Produces("application/json")
	@Allow(groups="admin")
	public Response deleteReportedPhoto(@PathParam("id") Long id) {
		facadePhoto.deleteReportedPhoto(id);
		return Response.ok().build();
	}

	@POST
	@Path("/reported/validate/{id:[1-9][0-9]*}")
	@Produces("application/json")
	@Allow(groups="admin")
	public Response validateReportedPhoto(@PathParam("id") Long id) {
		facadePhoto.validateReportedPhoto(id);
		return Response.ok().build();
	}

	@PUT
	@Path("/update")
	@Produces("application/json")
	@Allow(groups="sellers")
	public Response updatePhotoByID(UpdatePhotoDTO photo, @HeaderParam("userID") Long requesterID) {

		PublicPhotoDTO publicPhotoDTO = facadePhoto.getPhotoById(photo.getPhotoId());

		if(publicPhotoDTO == null) return Response.status(Status.BAD_REQUEST).build();

		if(!requesterID.equals(publicPhotoDTO.getSellerID())) {
			return Response.status(403).build();
		}

		PublicPhotoDTO updated =  facadePhoto.updatePhoto(photo);
		return Response.ok(updated).build();
	}

	@POST
	@Path("/seller/{id:[1-9][0-9]*}")
	@Consumes("application/json")
	@Produces("application/json")
	@AllowAll // Everyone could upload photos but guests
	public Response uploadFile(FullPhotoDTO input, @PathParam("id") long id) {
		PublicPhotoDTO created = facadePhoto.savePhoto(input);
		if (created == null) return Response.status(Status.BAD_REQUEST).entity("La photo n'a pas été enregistrée !").build();
		return Response.status(Status.CREATED).entity(created).build();
	}

	@GET
	@Path("/count")
	@Produces("application/json")
	@Allow(groups = "admin")
	public Response getPhotoCount() {
		Long pCount = facadePhoto.getPhotoCount();
		return Response.ok(pCount).build();
	}

	@POST
	@Path("/view/{photoID:[1-9][0-9]*}/{memberID:[1-9][0-9]*}")
	@Produces("application/json")
	@AllowAll
	public Response viewPhoto(@PathParam("photoID") Long photoID, @HeaderParam("userID") Long requesterID) {
		facadePhoto.viewPhoto(photoID, requesterID);
		return Response.status(200).build();
	}

	@POST
	@Path("/like/{photoID:[1-9][0-9]*}/{memberID:[1-9][0-9]*}")
	@Produces("application/json")
	@AllowAll
	public Response likePhoto(@PathParam("photoID") Long photoID, @HeaderParam("userID") Long requesterID) {
		facadePhoto.likePhoto(photoID, requesterID);
		return Response.ok(200).build();
	}

	@POST
	@Path("/unlike/{photoID:[1-9][0-9]*}/{memberID:[1-9][0-9]*}")
	@Produces("application/json")
	@AllowAll
	public Response unlikePhoto(@PathParam("photoID") Long photoID, @HeaderParam("userID") Long requesterID) {
		facadePhoto.unlikePhoto(photoID, requesterID);
		return Response.status(200).build();
	}

	@POST
	@Path("/wish/{photoID:[1-9][0-9]*}/{memberID:[1-9][0-9]*}")
	@Produces("application/json")
	@AllowAll
	public Response addPhotoToWishList(@PathParam("photoID") Long photoID, @HeaderParam("userID") Long requesterID) {
		facadePhoto.addPhotoToWishList(photoID, requesterID);
		return Response.status(200).build();
	}

	@POST
	@Path("/unwish/{photoID:[1-9][0-9]*}/{memberID:[1-9][0-9]*}")
	@Produces("application/json")
	@AllowAll
	public Response removePhotoFromWishList(@PathParam("photoID") Long photoID, @HeaderParam("userID") Long requesterID) {
		facadePhoto.removePhotoFromWishList(photoID, requesterID);
		return Response.status(200).build();
	}

	@POST
	@Path("flag/{photoID}/{memberID}")
	@Produces("application/json")
	@Consumes("application/json")
	@Allow(groups="sellers;members;admin")
	public Response signalPhoto(@PathParam("photoID") Long photoID, @HeaderParam("userID") Long requesterID) {

		SignalDTO signalDTO = new SignalDTO();
		signalDTO.setMemberID(requesterID);
		signalDTO.setPhotoID(photoID);
		signalDTO.setDescription("yo");

		try{
			SignalDTO created = facadePhoto.signalPhoto(signalDTO);
			if(created == null){
				return Response.status(Status.BAD_REQUEST).entity("La photo n'a pas été enregistrée !").build();
			}
			return Response.status(Status.CREATED).entity(created).build();
		} catch(Exception e){
			e.printStackTrace();
			return Response.status(Status.BAD_REQUEST).build();
		}
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
