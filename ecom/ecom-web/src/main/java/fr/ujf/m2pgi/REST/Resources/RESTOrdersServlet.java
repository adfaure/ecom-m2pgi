package fr.ujf.m2pgi.REST.Resources;

import java.util.Collection;
import java.util.List;

import javax.ejb.EJB;
import javax.ws.rs.*;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import fr.ujf.m2pgi.EcomException;
import fr.ujf.m2pgi.database.DTO.MemberDTO;
import fr.ujf.m2pgi.database.DTO.OrderDTO;
import fr.ujf.m2pgi.database.DTO.PhotoDTO;
import fr.ujf.m2pgi.database.Service.ICustomerService;
import fr.ujf.m2pgi.database.Service.MemberService;
import fr.ujf.m2pgi.database.Service.OrderService;

/**
 * Created by AZOUZI Marwen 23/10/15
 */
@Path("/orders")
public class RESTOrdersServlet {

	@EJB
	private OrderService orderService;

	@EJB
	private MemberService memberService;

	@EJB
	private ICustomerService customerService;

	@GET
	@Path("/")
	@Produces("application/json")
	public Response getAllOrders() {
		List<OrderDTO> orders = orderService.getAllOrders();
		return Response.ok(orders).build();
	}
	
	@GET
	@Path("/customer/login/{login}")
	@Produces("application/json")
	public Response getUserOrders(@PathParam("login") String login) {
		MemberDTO member = memberService.getMemberByLogin(login);
		if(member == null) {
			return Response.status(Status.NOT_FOUND).build();
		}
		List<OrderDTO> photos = orderService.getCustomerOrders(login);
		return Response.ok(photos).build();
	}

    @POST
    @Path("/customer/login/{login}")
    @Produces("application/json")
    public Response createOrders(@PathParam("login") String login, Collection<PhotoDTO> order) {
        MemberDTO member = memberService.getMemberByLogin(login);
        if(member == null) {
            return Response.status(Status.NOT_FOUND).build();
        }
		try {
			customerService.createOrder(login, order);
		} catch (EcomException e) {
			e.printStackTrace();
			return  Response.status(Status.BAD_REQUEST).build();
		}
		member = memberService.getMemberByLogin(login);
		return Response.ok(member).build();
    }

	@GET
	@Path("/count")
	@Produces("application/json")
	public Response getOrderCount() {
		Long orderCount = orderService.getOrderCount();
		return Response.ok(orderCount).build();
	}
	
	@GET
	@Path("/totalPurchaseCost")
	@Produces("application/json")
	public Response getOrderTotalPurchase() {
		float orderTotalPurchase = orderService.getTotalPurchaseCost();
		return Response.ok(orderTotalPurchase).build();
	}
}
