package fr.ujf.m2pgi.REST.Resources;

import java.io.*;
import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;
import java.util.Map;

import javax.ejb.EJB;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import fr.ujf.m2pgi.REST.CustomServerResponse;
import fr.ujf.m2pgi.REST.Interceptors.SecurityInterceptor;
import fr.ujf.m2pgi.REST.Security.PrincipalUser;
import fr.ujf.m2pgi.REST.Security.SecurityAnnotations.Allow;
import fr.ujf.m2pgi.REST.Security.SecurityAnnotations.AllowAll;
import fr.ujf.m2pgi.database.DTO.*;
import fr.ujf.m2pgi.database.Service.CustomerService;
import fr.ujf.m2pgi.database.Service.ICustomerService;
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

	/**
	 * the facede photo (not very needed the service could be as good)
	 */
	@EJB
	private FacadePhoto facadePhoto;

	/**
	 * the member service of the application
	 */
	@EJB
	private IMemberService memberService;

	/**
	 * the photo service of elastic search
	 */
	@EJB
	private PhotoServiceES photoServiceES;

	/**
	 * the customer service
	 */
	@EJB
	private ICustomerService customerService;

	/**
	 * the servlet which handle the current request
	 */
	@Context
	private HttpServletRequest httpServletRequest;

	/**
	 * Get all photo from the web site.
	 * If the user is identified, additionnals data will be added to the photo (like liked, wishes, isbought ...)
	 * @param requesterID
	 * @return
     */
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

	/**
	 * get all reported photos
	 * @return
     */
	@GET
	@Path("/reported")
	@Produces("application/json")
	@Allow(groups="admin")
	public Response getReportedPhotos() {
		List<ReportedPhotoDTO> photos = facadePhoto.getReportedPhotos();
		return Response.ok(photos).build();
	}

	/**
	 * Return the 10 most sales photo off all times
	 * @return
     */
	@GET
	@Path("/top10")
	@Produces("application/json")
	public Response getTop10Photos() {
		List<PublicPhotoDTO> photos = facadePhoto.getTop10Photos();
		return Response.ok(photos).build();
	}

	/**
	 * Get all photo ordered by an incoming parameter
	 * @param criteria the criteria of order : price, data, likes, views
	 * @param order DESC or ASC
     * @return
     */
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

	/**
	 * Get all photo from Elastic search service
	 * @return
     */
	@GET
	@Path("/search")
	@Produces("application/json")
	public Response getAllPhotosES() {
		return Response.ok(photoServiceES.getAllPhotos()).build();
	}

	/**
	 * Search a photo into the application. Elastic search will first handle the request and if the client is identified he will get more information.
	 * @param text
	 * @param requesterID
     * @return
     */
	@GET
	@Path("/search/{text}")
	@Produces("application/json")
	public Response searchPhotosES(@PathParam("text") String text, @HeaderParam("userID") Long requesterID) {

		if(requesterID != null) {
			List<PhotoContextSmallDTO> contextPhotos = facadePhoto.searchPhotosContext(text, requesterID);
			return Response.ok(contextPhotos).build();
		}

		return Response.ok(photoServiceES.searchPhotos(text)).build();
	}

	/**
	 * Get photo by id, if the client is identified he will get more information.
	 * @param id the id of the photo
	 * @param requesterID the id of the current client (if exist)
     * @return
     */
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

	/**
	 * Get all bought photo by a user.
	 * @param id the id of the user to search photo for
	 * @param requesterID the id of the identified user (security checking)
     * @return
     */
	@GET
	@Path("/bought/user/id/{id:[1-9][0-9]*}")
	@Produces("application/json")
	@Allow(groups = "members;sellers")
	public Response getBoughtPhoto(@PathParam("id") Long id, @HeaderParam("userID") Long requesterID) {
		if(requesterID == null) {
			return SecurityInterceptor.FORBIDDEN;
		} else if(requesterID.equals(id)) {
			return Response.status(Status.OK).entity(customerService.getBoughPhoto(id)).build();
		} else {
			return SecurityInterceptor.FORBIDDEN;
		}
	}

	/**
	 * Boolean route to check if an user have bougth a photo.
	 * @param id the id of the user
	 * @param photoId the id of the photo
	 * @param requesterID the id of the requester
     * @return
     */
	@GET
	@Path("/id/{photoId:[1-9][0-9]*}/user/id/{id:[1-9][0-9]*}/isBought")
	@Produces("application/json")
	@Allow(groups = "members;sellers")
	public Response isPhotoBought(@PathParam("id") Long id, @PathParam("photoId") Long photoId, @HeaderParam("userID") Long requesterID) {
		if(requesterID.equals(id)) {
			Map<String, Object> res = new HashMap<>();
			res.put("isBougth", (customerService.ishotoBought(id, photoId)));
			return Response.status(Status.OK).entity(res).build();
		} else {
			return SecurityInterceptor.FORBIDDEN;
		}
	}

	/**
	 * Get all photos in sale by an user
	 * @param id the id of the user
	 * @return
     */
	@GET
	@Path("/user/id/{id:[1-9][0-9]*}")
	@Produces("application/json")
	public Response getUserPhotos(@PathParam("id") Long id) {
		List<ManagePhotoDTO> photos = facadePhoto.getUserPhotos(id);
		return Response.ok(photos).build();
	}

	/**
	 * Get all photos in sale by an user
	 * @param login the login of the user
	 * @return
	 */
	@GET
	@Path("/user/login/{login}")
	@Produces("application/json")
	public Response getUserPhotos(@PathParam("login") String login) {
		List<ManagePhotoDTO> photos = facadePhoto.getUserPhotos(login);
		return Response.ok(photos).build();
	}

	/**
	 * Get all photo wished by an user
	 * @param id the id of the user.
	 * @return
     */
	@GET
	@Path("/user/id/{id:[1-9][0-9]*}/wishes")
	@Produces("application/json")
	//FIXME everybody can access to other user whish list
	public Response getUserWishedPhotos(@PathParam("id") Long id) {
		List<WishListPhotoDTO> photos = facadePhoto.getUserWishedPhotos(id);
		return Response.ok(photos).build();
	}

	/**
	 * Get all photo wished by an user
	 * @param login the login of the user.
	 * @return
	 */
	@GET
	@Path("/user/login/{login}/wishes")
	@Produces("application/json")
	//FIXME everybody can access to other user whish list
	public Response getUserWishedPhotos(@PathParam("login") String login) {
		List<PublicPhotoDTO> photos = facadePhoto.getUserWishedPhotos(login);
		return Response.ok(photos).build();
	}

	/**
	 * Get numberOfPhotos last photo added by a seller.
	 * @param followerID the id of the user to get the photo
	 * @param numberOfPhotos the number of photo to get
     * @return
     */
	@GET
	@Path("/user/id/{id:[1-9][0-9]*}/maxNum/{numberMax}")
	@Produces("application/json")
	public Response getLastPhotosFromSellers(@PathParam("id") Long followerID, @PathParam("numberMax") int numberOfPhotos) {
		List<LastPhotosDTO> sellersAndPhotos = facadePhoto.getLastPhotosFromSellers(followerID, numberOfPhotos);
		return Response.ok(sellersAndPhotos).build();
	}

	/**
	 * Delete a photo with its id
	 * The photo will not be "deleted" of the database but it will not be in sale anymore.
	 * @param id the id of the photo
	 * @param requesterID the id of the owner.
     * @return
     */
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

	/**
	 * //TODO roughbits01
	 * @param id
	 * @return
     */
	@DELETE
	@Path("/reported/{id:[1-9][0-9]*}")
	@Produces("application/json")
	@Allow(groups="admin")
	public Response deleteReportedPhoto(@PathParam("id") Long id) {
		facadePhoto.deleteReportedPhoto(id);
		return Response.ok().build();
	}

	/**
	 * //TODO roughbits01
	 * @param id
	 * @return
     */
	@POST
	@Path("/reported/validate/{id:[1-9][0-9]*}")
	@Produces("application/json")
	@Allow(groups="admin")
	public Response validateReportedPhoto(@PathParam("id") Long id) {
		facadePhoto.validateReportedPhoto(id);
		return Response.ok().build();
	}

	/**
	 * Update a photo
	 * @param photo the json representation of the photo (the id must mbe specified)
	 * @param requesterID the id of the request.(security check)
     * @return
     */
	@PUT
	@Path("/update")
	@Produces("application/json")
	@Allow(groups="sellers")
	public Response updatePhotoByID(UpdatePhotoDTO photo, @HeaderParam("userID") Long requesterID) {

		PublicPhotoDTO publicPhotoDTO = facadePhoto.getPhotoById(photo.getPhotoId());

		if(publicPhotoDTO == null) return Response.status(Status.BAD_REQUEST).build();

		if(!requesterID.equals(publicPhotoDTO.getSellerID())) { // If the photo is not owned by the seller
			return Response.status(403).build();
		}

		PublicPhotoDTO updated =  facadePhoto.updatePhoto(photo);
		return Response.ok(updated).build();
	}

	/**
	 * Since the uploda is performed by a nodejs sever, this route will just create the entity
	 * @param input
	 * @param id
     * @return
     */
	@POST
	@Path("/seller/{id:[1-9][0-9]*}")
	@Consumes("application/json")
	@Produces("application/json")
	@AllowAll // Everyone could upload photos but guests
	//FIXME only sellers can upload
	public Response uploadFile(FullPhotoDTO input, @PathParam("id") long id) {
		//FIXME check if the photo have the id of the connected seller
		PublicPhotoDTO created = facadePhoto.savePhoto(input);
		if (created == null) return Response.status(Status.BAD_REQUEST).entity("La photo n'a pas été enregistrée !").build();
		return Response.status(Status.CREATED).entity(created).build();
	}

	/**
	 * get the total number of photo in the website
	 * @return
     */
	@GET
	@Path("/count")
	@Produces("application/json")
	@Allow(groups = "admin")
	public Response getPhotoCount() {
		Long pCount = facadePhoto.getPhotoCount();
		return Response.ok(pCount).build();
	}

	/**
	 * this request will increse the number of view for this photo (only once by user)
	 * @param photoID
	 * @param requesterID
     * @return
     */
	@POST
	@Path("/view/{photoID:[1-9][0-9]*}/{memberID:[1-9][0-9]*}")
	@Produces("application/json")
	@AllowAll
	public Response viewPhoto(@PathParam("photoID") Long photoID, @HeaderParam("userID") Long requesterID) {
		facadePhoto.viewPhoto(photoID, requesterID);
		return Response.status(200).build();
	}

	/**
	 * The identified user will like the photo photoID
	 * @param photoID the photo id
	 * @param requesterID the identified user
     * @return
     */
	@POST
	@Path("/like/{photoID:[1-9][0-9]*}/{memberID:[1-9][0-9]*}")
	@Produces("application/json")
	@AllowAll
	public Response likePhoto(@PathParam("photoID") Long photoID, @HeaderParam("userID") Long requesterID) {
		facadePhoto.likePhoto(photoID, requesterID);
		return Response.ok(200).build();
	}

	/**
	 * The identified user will not like the photo anymore
	 * @param photoID
	 * @param requesterID
     * @return
     */
	@POST
	@Path("/unlike/{photoID:[1-9][0-9]*}/{memberID:[1-9][0-9]*}")
	@Produces("application/json")
	@AllowAll
	public Response unlikePhoto(@PathParam("photoID") Long photoID, @HeaderParam("userID") Long requesterID) {
		facadePhoto.unlikePhoto(photoID, requesterID);
		return Response.status(200).build();
	}

	/**
	 * Add a photo to the user wishlist
	 * @param photoID
	 * @param requesterID
     * @return
     */
	@POST
	@Path("/wish/{photoID:[1-9][0-9]*}/{memberID:[1-9][0-9]*}")
	@Produces("application/json")
	@AllowAll
	public Response addPhotoToWishList(@PathParam("photoID") Long photoID, @HeaderParam("userID") Long requesterID) {
		facadePhoto.addPhotoToWishList(photoID, requesterID);
		return Response.status(200).build();
	}

	/**
	 * remove a photo from the user wishlist
	 * @param photoID
	 * @param requesterID
     * @return
     */
	@POST
	@Path("/unwish/{photoID:[1-9][0-9]*}/{memberID:[1-9][0-9]*}")
	@Produces("application/json")
	@AllowAll
	public Response removePhotoFromWishList(@PathParam("photoID") Long photoID, @HeaderParam("userID") Long requesterID) {
		facadePhoto.removePhotoFromWishList(photoID, requesterID);
		return Response.status(200).build();
	}

	/**
	 * A member will signal a photo if he think it's content is not appropriate
	 * @param photoID
	 * @param requesterID
     * @return
     */
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
