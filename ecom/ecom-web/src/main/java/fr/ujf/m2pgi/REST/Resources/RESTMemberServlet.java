package fr.ujf.m2pgi.REST.Resources;

import fr.ujf.m2pgi.EcomException;
import fr.ujf.m2pgi.REST.CustomServerResponse;
import fr.ujf.m2pgi.REST.Security.PrincipalUser;
import fr.ujf.m2pgi.REST.Security.SecurityAnnotations.Allow;
import fr.ujf.m2pgi.REST.Security.SecurityAnnotations.AllowAll;
import fr.ujf.m2pgi.database.DTO.AdminMemberDTO;
import fr.ujf.m2pgi.database.DTO.MemberDTO;
import fr.ujf.m2pgi.database.DTO.PublicPhotoDTO;
import fr.ujf.m2pgi.database.Service.IMemberService;

import javax.ejb.EJB;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import java.util.List;

/**
 * Created by FAURE Adrien 22/10/15
 * Member servlet will perform all action possible with the members
 * such as create, get, update and more.
 * the base route is api/members/
 */
@Path("/members")
public class RESTMemberServlet {

	/**
	 * The EJB member service, will allow to interact will the EJB application
	 */
	@EJB
	private IMemberService memberService;

	/**
	 *  The current servlet which handle the request.
	 */
	@Context
	private HttpServletRequest httpServletRequest;

	/**
	 * Get all members of the application
	 * Only the admin can access this resource because some information are confidential (memberID, email ...).
	 * The password will not be given.
	 * @return
     */
	@GET
	@Path("/")
	@Produces("application/json")
	@Allow(groups = "admin")
	public Response getAllMembers() {
		List<AdminMemberDTO> members = memberService.getAllMembers();
		return Response.ok(members).build();
	}

	/**
	 * get a member with his login
	 * @param login
	 * @return
     */
	@GET
	@Path("/login/{login}")
	@Produces("application/json")
	@AllowAll
	public Response getMemberByLogin(@PathParam("login") String login) {
		MemberDTO member =  memberService.getMemberByLogin(login, false);
		return Response.ok(member).build();
	}

	/**
	 * Return boolean based json.the response will be true if the member exist in the database. Otherwise it will be  false.
	 * @param login the login of the user
	 * @return
     */
	@GET
	@Path("/exists/{login}")
	@Produces("application/json")
	public Response isExistingMemberByLogin(@PathParam("login") String login) {
		return Response.ok(memberService.isExistingMemberByLogin(login)).build();
	}

	/**
	 * Return boolean based json.the response will be true if the email exist in the database. Otherwise it will be  false.
	 * @param email the email
	 * @return
     */
	@GET
	@Path("/exists/email/{email}")
	@Produces("application/json")
	public Response isExistingMemberByEmail(@PathParam("email") String email) {
		return Response.ok(memberService.isExistingMemberByEmail(email)).build();
	}

	/**
	 * Access member information with his id
	 * @param id the id of the user
	 * @return
     */
	@GET
	@Path("/id/{id}")
	@Produces("application/json")
	@AllowAll
	public Response getMemberById(@PathParam("id") long id) {
		MemberDTO member =  memberService.getMemberbyId(id);
		return Response.ok(member).build();
	}


	/**
	 * //TODO AngieMoomin
	 * @param id
	 * @return
     */
	@GET
	@Path("id/{id}/follows")
	@Produces("application/json")
	@AllowAll
	public Response getFollowedSellersBy(@PathParam("id") long id) {
		List<MemberDTO> members = memberService.getFollowedSellersBy(id);
		return Response.ok(members).build();
	}

	/**
	 * Inscription route.
	 * Everyone can create an account on our website
	 * @param member A json representing a member {@link }
	 * @return
     */
	@POST
	@Path("/")
	@Produces("application/json")
	@Consumes("application/json")
	public Response createUser(MemberDTO member){ //FIXME the true one shall return a Member DTO
		try{
			MemberDTO createdMember = memberService.createMember(member);
			return Response.status(Status.CREATED).entity(createdMember).build();
		}catch(EcomException e){
			return Response.status(Status.BAD_REQUEST).entity(new CustomServerResponse(false, e.getMessage())).build();
		}
	}

	/**
	 * Delete an user from the application.
	 * The user is not removed from the database but is account will be disable and so his photos.
	 * Only the admin can perform this action. (member cannot remove their account yet)
	 * @param id
	 * @return
     */
	@DELETE
	@Path("id/{id}")
	@Produces("application/json")
	@Consumes("application/json")
	@Allow(groups = "admin")
	public Response deleteUser(@PathParam("id") Long id) {
		memberService.deleteMember(id);
		return  Response.status(Status.ACCEPTED).build();
	}

	/**
	 * Route which update a member with the provided json member
	 * @param id
	 * @param memberDTO
     * @return
     */
	@PUT
	@Path("/update/id/{id}")
	@Produces("application/json")
	@AllowAll
	public Response updateUser(@PathParam("id") Long id, MemberDTO memberDTO) {
		MemberDTO m = memberService.getMemberbyId(id);
		if(m == null) return Response.status(Status.BAD_REQUEST).build();

		try{
			MemberDTO updatedMember = null;
			updatedMember =  memberService.updateMember(memberDTO);
			return Response.ok(updatedMember).build();
		}catch(EcomException e){
			return Response.status(Status.BAD_REQUEST).entity(new CustomServerResponse(false, e.getMessage())).build();
		}
	}

	/**
	 * Change the password of an user
	 * @param id the id of the member to update
	 * @param memberDTO
	 * @param newPSW
     * @return
     */
	@PUT
	@Path("id/{id}/pwd/{newPSW}")
	@Produces("application/json")
	//FIXME add the proper rigth
	public Response updateUserPSW(@PathParam("id") Long id, MemberDTO memberDTO, @PathParam("newPSW") String newPSW) {
		MemberDTO m = memberService.getMemberbyId(id);
		if(m == null) return Response.status(Status.BAD_REQUEST).build();

		MemberDTO updatedMember = null;
		updatedMember =  memberService.changePassword(memberDTO, newPSW);
		return Response.ok(updatedMember).build();
	}

	/**
	 * Get the number of member in the application.
	 * Allthe account will be counted unless the admin. But the result will also include all disabled account.
	 * @return a response http
     */
	@GET
	@Path("/count")
	@Produces("application/json")
	@Allow(groups = "admin")
	public Response getMemberCount() {
		Long pCount = memberService.getMemberCount();
		return Response.ok(pCount).build();
	}

	/**
	 * Add a photo to the cart.
	 * @param id the id of the user to add the photo to his cart
	 * @param photoId the id of the photo to add
	 * @param requesterID the id of the client (for the security checking)
     * @return
     */
	@POST
	@Path("id/{id}/cart/photo/id/{photoId}")
	@Produces("application/json")
	@Consumes("application/json")
	@AllowAll
	public Response addToCart(@PathParam("id") Long id, @PathParam("photoId") Long photoId, @HeaderParam("userID") Long requesterID) {
		if(!requesterID.equals(id)) return Response.status(Status.FORBIDDEN).build();

		PublicPhotoDTO p = new PublicPhotoDTO();
		p.setPhotoID(photoId);
		MemberDTO m = new MemberDTO();
		m.setMemberID(id);
		MemberDTO res = memberService.addToCart(m, p);
		return  Response.status(Status.ACCEPTED).entity(res).build();
	}

	/**
	 * Remove a photo to the cart.
	 * @param id the id of the user to add the photo to his cart
	 * @param photoId the id of the photo to add
	 * @param requesterID the id of the client (for the security checking)
	 * @return
     * @return
     */
	@DELETE
	@Path("id/{id}/cart/photo/id/{photoId}")
	@Produces("application/json")
	@Consumes("application/json")
	@AllowAll
	public Response deleteFromCart(@PathParam("id") Long id, @PathParam("photoId") Long photoId, @HeaderParam("userID") Long requesterID) {
		if(!requesterID.equals(id)) return Response.status(Status.FORBIDDEN).build();
		PublicPhotoDTO p = new PublicPhotoDTO();
		p.setPhotoID(photoId);
		MemberDTO m = new MemberDTO();
		m.setMemberID(id);
		MemberDTO res = memberService.removeToCart(m, p);
		return  Response.status(Status.ACCEPTED).entity(res).build();
	}

	/**
	 * Clear the cart
	 * @param id the if of the user to delete the cart
	 * @param requesterID the id of the current identified user (security checking)
     * @return
     */
	@DELETE
	@Path("id/{id}/cart")
	@Produces("application/json")
	@Consumes("application/json")
	@AllowAll
	public Response deleteCart(@PathParam("id") Long id, @HeaderParam("userID") Long requesterID) {
		if(!requesterID.equals(id)) return Response.status(Status.FORBIDDEN).build();
		MemberDTO dto = new MemberDTO();
		dto.setMemberID(id); //FIXME USe principal user
		MemberDTO res = memberService.deleteCart(dto);
		return  Response.status(Status.ACCEPTED).entity(res).build();
	}

	/**
	 * Allow user to follow an other user
	 * @param memberId the id of the member who want to follow an user
	 * @param followedId the id of the (future) followed member
	 * @param requesterID the id of the identified user (security checking)
     * @return
     */
	@POST
	@Path("id/{memberId}/follow/{followedId}")
	@Produces("application/json")
	@Consumes("application/json")
	@AllowAll
	public Response follow(@PathParam("memberId") Long memberId, @PathParam("followedId") Long followedId, @HeaderParam("userID") Long requesterID) {

		if(!requesterID.equals(memberId)) return Response.status(Status.FORBIDDEN).build();

		if(!memberId.equals(followedId)){
			boolean response = memberService.follow(memberId, followedId);
			return  Response.ok(response).build();
		}
		return Response.status(405).build();
	}

	/**
	 * Allow user to unfollow an other user
	 * @param memberId the id of the member who want to follow an user
	 * @param followedId the id of the (future) followed member
	 * @param requesterID the id of the identified user (security checking)
	 * @return
	 */
	@POST
	@Path("id/{memberId}/unfollow/{followedId}")
	@Produces("application/json")
	@Consumes("application/json")
	@AllowAll
	public Response unfollow(@PathParam("memberId") Long memberId, @PathParam("followedId") Long followedId, @HeaderParam("userID") Long requesterID) {
		if(!requesterID.equals(memberId)) return Response.status(Status.FORBIDDEN).build();

		boolean response = memberService.unfollow(memberId, followedId);
		return  Response.ok(response).build();
	}

	/**
	 * boolean route which return if a iuser follow an other user
	 * @param memberId the member to ask for
	 * @param followerId the (maybe) followed member
     * @return
     */
	@GET
	@Path("id/{memberId}/isfollowedby/{followerId}")
	@Produces("application/json")
	@Consumes("application/json")
	@AllowAll
	public Response isFollowedBy(@PathParam("memberId") Long memberId, @PathParam("followerId") Long followerId) {
		boolean response = memberService.isFollowedBy(followerId, memberId);
		return  Response.ok(response).build();
	}
}
