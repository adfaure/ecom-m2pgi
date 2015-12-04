package fr.ujf.m2pgi.REST.Resources;

import fr.ujf.m2pgi.EcomException;
import fr.ujf.m2pgi.REST.Security.PrincipalUser;
import fr.ujf.m2pgi.REST.Security.SecurityAnnotations.Allow;
import fr.ujf.m2pgi.REST.Security.SecurityAnnotations.Deny;
import fr.ujf.m2pgi.REST.Security.SecurityAnnotations.DenyAll;
import fr.ujf.m2pgi.REST.Security.SecurityAnnotations.AllowAll;
import fr.ujf.m2pgi.database.DTO.MemberDTO;
import fr.ujf.m2pgi.database.DTO.PhotoDTO;
import fr.ujf.m2pgi.database.Service.IMemberService;
import fr.ujf.m2pgi.database.Service.MemberService;
import javax.ejb.EJB;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import java.util.List;

/**
 * Created by FAURE Adrien 22/10/15
 */
@Path("/members")
public class RESTMemberServlet {

	@EJB
	private IMemberService memberService;

	@Context
	private HttpServletRequest httpServletRequest;
	
	@GET
	@Path("/")
	@Produces("application/json")
	//@Allow(groups = "admin")
	public Response getAllMembers() {
		List<MemberDTO> members = memberService.getAllMembers();
		return Response.ok(members).build();
	}

	@GET
	@Path("/login/{login}")
	@Produces("application/json")
	public Response getMemberByLogin(@PathParam("login") String login) {
		MemberDTO member =  memberService.getMemberByLogin(login);
		return Response.ok(member).build();
	}

	@GET
	@Path("/id/{id}")
	@Produces("application/json")
	public Response getMemberById(@PathParam("id") long id) {
		MemberDTO member =  memberService.getMemberbyId(id);
		return Response.ok(member).build();
	}

	@POST
	@Path("/")
	@Produces("application/json")
	@Consumes("application/json")
	public Response createUser(MemberDTO member) throws EcomException { //FIXME the true one shall return a Member DTO
		MemberDTO createdMember = memberService.createMember(member);
		return Response.status(Status.CREATED).entity(createdMember).build();
	}

	
	@DELETE
	@Path("id/{id}")
	@Produces("application/json")
	@Consumes("application/json")
	public Response deleteUser(@PathParam("id") Long id) {
		memberService.deleteMember(id);
		return  Response.status(Status.ACCEPTED).build();
	}
	
	@PUT
	@Path("/update/id/{id}")
	@Produces("application/json")
	public Response updateUser(@PathParam("id") Long id, MemberDTO memberDTO) {
		MemberDTO m = memberService.getMemberbyId(id);
		if(m == null) return Response.status(Status.BAD_REQUEST).build();
		
		MemberDTO updatedMember = null;
		updatedMember =  memberService.updateMember(memberDTO);
		return Response.ok(updatedMember).build();
	}
	
	@GET
	@Path("/count")
	@Produces("application/json")
	@Allow(groups = "admin")
	public Response getMemberCount() {
		Long pCount = memberService.getMemberCount();
		return Response.ok(pCount).build();
	}

	@POST
	@Path("id/{id}/cart/photo/id/{photoId}")
	@Produces("application/json")
	@Consumes("application/json")
	public Response addToCart(@PathParam("id") Long id, @PathParam("photoId") Long photoId ) {
		PrincipalUser principal = (PrincipalUser) httpServletRequest.getSession().getAttribute("principal");
		//if(principal.getUser().getMemberID() != id) return Response.status(Status.FORBIDDEN).build();
		PhotoDTO p = new PhotoDTO();
		p.setPhotoId(photoId);
		MemberDTO m = new MemberDTO();
		m.setMemberID(id);
		MemberDTO res = memberService.addToCart(m, p);
		return  Response.status(Status.ACCEPTED).entity(res).build();
	}

	@DELETE
	@Path("id/{id}/cart/photo/id/{photoId}")
	@Produces("application/json")
	@Consumes("application/json")
	public Response deleteToCart(@PathParam("id") Long id, @PathParam("photoId") Long photoId) {
		PrincipalUser principal = (PrincipalUser) httpServletRequest.getSession().getAttribute("principal");
		//if(principal.getUser().getMemberID() != id) return Response.status(Status.FORBIDDEN).build();
		PhotoDTO p = new PhotoDTO();
		p.setPhotoId(photoId);
		MemberDTO m = new MemberDTO();
		m.setMemberID(id);
		MemberDTO res = memberService.removeToCart(m, p);
		return  Response.status(Status.ACCEPTED).entity(res).build();
	}

	@DELETE
	@Path("id/{id}/cart")
	@Produces("application/json")
	@Consumes("application/json")
	public Response deleteCart(@PathParam("id") Long id) {
		PrincipalUser principal = (PrincipalUser) httpServletRequest.getSession().getAttribute("principal");
		if(principal.getUser().getMemberID() != id) return Response.status(Status.FORBIDDEN).build();
		MemberDTO dto = new MemberDTO();
		dto.setMemberID(id); //FIXME USe principal user
		MemberDTO res = memberService.deleteCart(dto);
		return  Response.status(Status.ACCEPTED).entity(res).build();
	}
}
