package fr.ujf.m2pgi.REST.Resources;

import fr.ujf.m2pgi.REST.Security.SecurityAnnotations.Allow;
import fr.ujf.m2pgi.REST.Security.SecurityAnnotations.Deny;
import fr.ujf.m2pgi.REST.Security.SecurityAnnotations.DenyAll;
import fr.ujf.m2pgi.database.DTO.MemberDTO;
import fr.ujf.m2pgi.database.Service.MemberService;

import javax.ejb.EJB;
import javax.ws.rs.*;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

/**
 * Created by FAURE Adrien 22/10/15
 */
@Path("/members")
public class RESTMemberServlet {

	@EJB
	private MemberService memberService;

	@GET
	@Path("/login/{login}")
	@Produces("application/json")
	public Response getMemberByLogin(@PathParam("login") String login) {
		MemberDTO member =  memberService.getMemberByLogin(login);
		return Response.ok(member).build();
	}

	@POST
	@Path("/")
	@Produces("application/json")
	@Consumes("application/json")
	public Response createUser(MemberDTO member) { //FIXME the true one shall return a Member DTO
		MemberDTO createdMember = memberService.createMember(member);
		return Response.status(Status.CREATED).entity(createdMember).build();
	}
	
	@GET
	@Path("/count")
	@Produces("application/json")
	@Allow(groups = "admin")
	public Response getMemberCount() {
		Long pCount = memberService.getMemberCount();
		return Response.ok(pCount).build();
	}
}
