package fr.ujf.m2pgi.REST.Resources;

import fr.ujf.m2pgi.REST.Security.PrincipalUser;
import fr.ujf.m2pgi.REST.Security.SecurityAnnotations.Allow;
import fr.ujf.m2pgi.REST.Security.SecurityAnnotations.AllowAll;
import fr.ujf.m2pgi.database.DTO.MemberDTO;
import fr.ujf.m2pgi.database.DTO.OrderSellerDTO;
import fr.ujf.m2pgi.database.DTO.PhotoDTO;
import fr.ujf.m2pgi.database.DTO.SellerPageDTO;
import fr.ujf.m2pgi.database.Service.CustomerService;
import fr.ujf.m2pgi.database.Service.ICustomerService;
import fr.ujf.m2pgi.database.Service.MemberService;
import fr.ujf.m2pgi.database.entities.Member;

import javax.ejb.EJB;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
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
    private MemberService memberService;

    @EJB
    private ICustomerService customerService;

    @POST
	@Path("/")
	@Produces("application/json")
	@Consumes("application/json")
    public Response createUser(MemberDTO seller) { //FIXME the true one shall return a Member DTO
		MemberDTO createdMember = memberService.createMember(seller);
		return Response.status(Response.Status.CREATED).entity(createdMember).build();
	}

    @GET
    @Path("id/{id}")
    @Produces("application/json")
    @AllowAll
    public Response getSellerById(@PathParam("id") long sellerId) {
        MemberDTO memberdto = memberService.getSellerById(sellerId);
        if(memberdto == null | memberdto.getSellerInfo() == null) {
            return Response.status(Response.Status.NO_CONTENT).build();
        }
        return  Response.status(Response.Status.FOUND).entity(memberdto).build();
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

    @GET
    @Path("page/{id}")
    @Produces("application/json")
    @AllowAll
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
    @AllowAll
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
    public Response postSellerPage(@PathParam("id") long sellerId, SellerPageDTO pageDTO) {
        HttpSession session = httpServletRequest.getSession();
        PrincipalUser user = (PrincipalUser) session.getAttribute("principal");

        if(user.getUser().getMemberID() != pageDTO.getId()) {
            return Response.status(403).build();
        }

        MemberDTO memberdto = memberService.getSellerById(sellerId);
        if(memberdto != null && memberdto.getSellerInfo() != null) {
            memberdto.getSellerInfo().setPage(pageDTO);
            memberService.updateSeller(memberdto);
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
}
