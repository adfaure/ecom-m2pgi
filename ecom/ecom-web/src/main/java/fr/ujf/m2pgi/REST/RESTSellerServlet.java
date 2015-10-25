package fr.ujf.m2pgi.REST;

import fr.ujf.m2pgi.database.DTO.MemberDTO;
import fr.ujf.m2pgi.database.DTO.SellerDTO;
import fr.ujf.m2pgi.database.Service.SellerService;
import fr.ujf.m2pgi.database.entities.Seller;

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
    private SellerService sellerService;

    @GET
    @Path("/login/{login}")
    @Produces("application/json")
    public Seller findSellerByLogin(@PathParam("login") String login) {
        System.err.println("looking for " + login);
        return  sellerService.findSellerByLogin(login);
    }
    
	@POST
	@Path("/")
	@Produces("application/json")
	@Consumes("application/json")
	public Response createUser(SellerDTO seller) { //FIXME the true one shall return a Member DTO
		MemberDTO createdMember = sellerService.createSeller(seller);
		return Response.status(Status.CREATED).entity(createdMember).build();
	}

}
