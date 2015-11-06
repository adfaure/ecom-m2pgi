package fr.ujf.m2pgi.REST.Resources;

import fr.ujf.m2pgi.REST.Security.PrincipalUser;
import fr.ujf.m2pgi.REST.Security.SecurityAnnotations.Allow;
import fr.ujf.m2pgi.REST.Security.SecurityAnnotations.AllowAll;
import fr.ujf.m2pgi.REST.Security.SecurityAnnotations.Deny;
import fr.ujf.m2pgi.REST.Security.SecurityAnnotations.DenyAll;
import fr.ujf.m2pgi.database.DTO.MemberDTO;
import fr.ujf.m2pgi.database.DTO.SellerDTO;
import fr.ujf.m2pgi.database.Service.MemberService;
import fr.ujf.m2pgi.database.entities.Seller;

import javax.ejb.EJB;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
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

    @GET
    @Path("/login/{login}")
    @Produces("application/json")
    public SellerDTO findSellerByLogin(@PathParam("login") String login) {
        return  memberService.findSellerByLogin(login);
    }

    @POST
	@Path("/")
	@Produces("application/json")
	@Consumes("application/json")
    public Response createUser(SellerDTO seller) { //FIXME the true one shall return a Member DTO
		MemberDTO createdMember = memberService.createSeller(seller);
		return Response.status(Status.CREATED).entity(createdMember).build();
	}

    @POST
    @Path("/upgrade")
    @Produces("application/json")
    @Consumes("application/json")
    @Allow(groups="members")
    public Response upgradeMemberToSeller(SellerDTO seller) {
        Map resJson = new HashMap<String, Object>();
        SellerDTO newDTO = memberService.createSellerFromMember(seller);
        if(newDTO != null) {
            newDTO.setAccountType('S');
            resJson.put("message", "success upgrade");
            resJson.put("success", true);
            resJson.put("user", newDTO);
            HttpSession session = httpServletRequest.getSession();
            PrincipalUser user = (PrincipalUser) session.getAttribute("principal");
            user.setGroup("sellers");
            session.setAttribute("principal", user);
        } else {
            resJson.put("success", false);
        }
        return  Response.ok().entity(resJson).build();
    }

}
