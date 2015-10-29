package fr.ujf.m2pgi.REST.Resources;

import fr.ujf.m2pgi.REST.Security.SecurityAnnotations.AllowAll;
import fr.ujf.m2pgi.REST.Security.SecurityAnnotations.Deny;
import fr.ujf.m2pgi.REST.Security.SecurityAnnotations.DenyAll;
import fr.ujf.m2pgi.database.DTO.MemberDTO;
import fr.ujf.m2pgi.database.DTO.SellerDTO;
import fr.ujf.m2pgi.database.Service.MemberService;

import javax.ejb.EJB;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

/**
 * Created by FAURE Adrien son 22/10/15.
 */
@Path("/sellers")
public class RESTSellerServlet {

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

}
