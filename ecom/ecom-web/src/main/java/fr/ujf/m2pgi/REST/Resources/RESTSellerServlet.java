package fr.ujf.m2pgi.REST.Resources;

import fr.ujf.m2pgi.REST.Security.PrincipalUser;
import fr.ujf.m2pgi.REST.Security.SecurityAnnotations.Allow;
import fr.ujf.m2pgi.database.DTO.MemberDTO;
import fr.ujf.m2pgi.database.Service.MemberService;

import javax.ejb.EJB;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by FAURE Adrien son 22/10/15.
 */
@Path("/sellers")
public class RESTSellerServlet {


    @Context
    private HttpServletRequest httpServletRequest;

    @EJB
    private MemberService memberService;

    /*@GET
    @Path("/login/{login}")
    @Produces("application/json")
    public SellerDTO findSellerByLogin(@PathParam("login") String login) {
        return  memberService.getMemberByLogin(login);
    } */

 /*   @GET
    @Path("/count")
    @Produces("application/json")
    public Long getSellerCount() {
        return  memberService.getSellerCount();
    }
 */
    @POST
	@Path("/")
	@Produces("application/json")
	@Consumes("application/json")
    public Response createUser(MemberDTO seller) { //FIXME the true one shall return a Member DTO
		MemberDTO createdMember = memberService.createMember(seller);
		return Response.status(Response.Status.CREATED).entity(createdMember).build();
	}

    @POST
    @Path("/upgrade")
    @Produces("application/json")
    @Consumes("application/json")
    @Allow(groups="members")
    public Response upgradeMemberToSeller(MemberDTO seller) {
        Map resJson = new HashMap<String, Object>();
        MemberDTO newDTO = memberService.createSellerFromMember(seller);
        if(newDTO != null) {
            newDTO.setAccountType('S');
            resJson.put("message", "success upgrade");
            resJson.put("success", true);
            resJson.put("user", newDTO);
            HttpSession session = httpServletRequest.getSession();
            PrincipalUser user = (PrincipalUser) session.getAttribute("principal");
            user.setUser(newDTO);
            user.setGroup("sellers");
            session.setAttribute("principal", user);
        } else {
            resJson.put("success", false);
        }
        return  Response.ok().entity(resJson).build();
    }

}
