package fr.ujf.m2pgi.REST;

import javax.ejb.EJB;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

import fr.ujf.m2pgi.database.DTO.MemberDTO;
import fr.ujf.m2pgi.database.Service.MemberService;

@Path("/member")
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
	
}
