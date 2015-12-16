package fr.ujf.m2pgi.REST.Resources;

import fr.ujf.m2pgi.EcomException;
import fr.ujf.m2pgi.REST.CustomServerResponse;
import fr.ujf.m2pgi.Security.JwtSingleton;
import fr.ujf.m2pgi.REST.Security.PrincipalUser;
import fr.ujf.m2pgi.REST.Security.SecurityAnnotations.Allow;
import fr.ujf.m2pgi.REST.Security.SecurityAnnotations.AllowAll;
import fr.ujf.m2pgi.database.DTO.MemberDTO;
import fr.ujf.m2pgi.database.DTO.OrderSellerDTO;
import fr.ujf.m2pgi.database.DTO.SellerPageDTO;
import fr.ujf.m2pgi.database.Service.ICustomerService;
import fr.ujf.m2pgi.database.Service.IMemberService;

import javax.ejb.EJB;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by FAURE Adrien son 22/10/15.
 */
@Path("/sellers")
public class RESTSellerServlet {


    @Context
    private HttpServletRequest httpServletRequest;

    @EJB
    private IMemberService memberService;

    @EJB
    private ICustomerService customerService;

    @EJB
    private JwtSingleton jwtSingleton;

    @POST
	@Path("/")
	@Produces("application/json")
	@Consumes("application/json")
    public Response createUser(MemberDTO seller) throws EcomException { //FIXME the true one shall return a Member DTO
		MemberDTO createdMember = memberService.createMember(seller);
		return Response.status(Response.Status.CREATED).entity(createdMember).build();
	}

    @GET
    @Path("id/{id}")
    @Produces("application/json")
    public Response getSellerById(@PathParam("id") long sellerId, @HeaderParam("userID") Long requesterID) {
        if(requesterID == null) {
            return Response.status(Status.OK).entity(memberService.getPublicSellerById(sellerId)).build();
        } else if(requesterID.equals(sellerId)) {
            MemberDTO memberdto = memberService.getSellerById(sellerId);
            if (memberdto == null | memberdto.getSellerInfo() == null) {
                return Response.status(Response.Status.NO_CONTENT).build();
            }
            return Response.status(Response.Status.OK).entity(memberdto).build();
        } else {
            return Response.status(Status.OK).entity(memberService.getPublicSellerById(sellerId)).build();
        }
    }

    @DELETE
	@Path("id/{id}")
	@Produces("application/json")
	@Consumes("application/json")
	public Response deleteUser(@PathParam("id") Long id) {
		memberService.deleteMember(id);
		return  Response.ok().build();
	}

    @PUT
	@Path("/update/id/{id}")
	@Produces("application/json")
	public Response updateUser(@PathParam("id") Long id, MemberDTO memberDTO) {
		MemberDTO m = memberService.getMemberbyId(id);
		if(m == null) return Response.status(Status.BAD_REQUEST).build();

		MemberDTO updatedMember = null;
		updatedMember =  memberService.updateSeller(memberDTO);
		return Response.ok(updatedMember).build();
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
            newDTO.setPassword("");
            resJson.put("message", "success upgrade");
            resJson.put("success", true);
            resJson.put("user", newDTO);
            // Issue new token
            resJson.put("token", jwtSingleton.generateToken(newDTO.getMemberID(), "sellers"));
        } else {
            resJson.put("success", false);
        }
        return  Response.ok().entity(resJson).build();
    }

    @GET
    @Path("page/{id}")
    @Produces("application/json")
    public Response getSellerPage(@PathParam("id") long sellerId) {
        MemberDTO memberdto = memberService.getSellerById(sellerId);
        if(memberdto != null && memberdto.getSellerInfo() != null) {
            SellerPageDTO page = memberdto.getSellerInfo().getPage();
            return  Response.status(Response.Status.OK).entity(page).build();
        }
        return Response.status(Response.Status.NO_CONTENT).entity(null).build();
    }

    @GET
    @Path("page/login/{login}")
    @Produces("application/json")

    public Response getSellerPagebyLogin(@PathParam("login") String login) {
        MemberDTO memberdto = memberService.getMemberByLogin(login);
        if(memberdto != null && memberdto.getSellerInfo() != null) {
            SellerPageDTO page = memberdto.getSellerInfo().getPage();
            return  Response.status(Response.Status.OK).entity(page).build();
        }
        return Response.status(Response.Status.NO_CONTENT).entity(null).build();

    }

    @POST
    @Path("page/{id}")
    @Produces("application/json")
    @Consumes("application/json")
    @Allow(groups="sellers")
    public Response postSellerPage(@PathParam("id") long sellerId, SellerPageDTO pageDTO,
     @HeaderParam("userID") Long requesterID) throws EcomException {

        if(!requesterID.equals(pageDTO.getId())) {
            return Response.status(403).build();
        }

        MemberDTO memberdto = memberService.getSellerById(sellerId);
        if(memberdto != null && memberdto.getSellerInfo() != null) {
            memberdto.getSellerInfo().setPage(pageDTO);
            memberService.updateMember(memberdto);
            return  Response.status(Response.Status.OK).entity(pageDTO).build();
        }

        return Response.status(Response.Status.NO_CONTENT).build();
    }

    @GET
    @Path("id/{id}/orders")
    @Produces("application/json")
    public Response getOrderTotalPurchase(@PathParam("id") long id) {
        List<OrderSellerDTO> list = customerService.getOrdersBySeller(id);
        return Response.ok().entity(list).build();
    }

    @GET
    @Path("/top10")
    @Produces("application/json")
    public Response getTopSellers() {
        List<MemberDTO> list = customerService.getTopSellers();
        return Response.ok().entity(list).build();
    }

    @GET
    @Path("/count")
    @Produces("application/json")
    public Response getCount() {
    	Long sellerCount = customerService.getSellerCount();
    	return Response.ok(sellerCount).build();
    }

    @GET
    @Path("id/{id}/followercount")
    @Produces("application/json")
    public Response getFollowerCount(@PathParam("id") long id) {
      Long followerCount = memberService.getSellerFollowerCount(id);
      return Response.ok(followerCount).build();
    }
}
