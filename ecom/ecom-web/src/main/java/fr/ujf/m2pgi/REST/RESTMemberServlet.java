package fr.ujf.m2pgi.REST;

import javax.ejb.EJB;
import javax.ws.rs.*;

import fr.ujf.m2pgi.database.DTO.MemberDTO;
import fr.ujf.m2pgi.database.Service.MemberService;
import fr.ujf.m2pgi.database.entities.Member;

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
	public MemberDTO getMemberByLogin(@PathParam("login") String login) {
		MemberDTO member =  memberService.getMemberByLogin(login);
		return member;
	}

	@POST
	@Path("/")
	@Produces("application/json")
	@Consumes("application/json")
	public MemberDTO createUser(MemberDTO member) { //FIXME the true one shall return a Member DTO
		System.err.println(member);
		memberService.createMember(member);
		return  member;
	}

	
}
