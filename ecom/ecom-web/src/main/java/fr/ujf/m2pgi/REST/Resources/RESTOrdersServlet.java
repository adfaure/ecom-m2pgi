package fr.ujf.m2pgi.REST.Resources;

import java.util.List;

import javax.ejb.EJB;
import javax.ws.rs.*;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import fr.ujf.m2pgi.REST.Security.SecurityAnnotations.Allow;
import fr.ujf.m2pgi.database.DTO.OrderDTO;
import fr.ujf.m2pgi.database.Service.OrderService;

/**
 * Created by AZOUZI Marwen 23/10/15
 */
@Path("/orders")
public class RESTOrdersServlet {

	@EJB
	private OrderService orderService;

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
		List<OrderDTO> photos = orderService.getCustomerOrders(login);
		return Response.ok(photos).build();
	}
	
	@POST
	@Path("/add")
	@Produces("application/json")
	public Response addOrder(OrderDTO order) {
		OrderDTO created = orderService.createOrder(order);
		if (created == null) {
			return Response.status(Status.BAD_REQUEST).entity("L'achat n'a pas été enregistré !").build();
		}
		return Response.status(Status.CREATED).entity(created).build();
	}
	
	@GET
	@Path("/count")
	@Produces("application/json")
	@Allow(groups = "admin")
	public Response getOrderCount() {
		Long orderCount = orderService.getOrderCount();
		return Response.ok(orderCount).build();
	}
	
	@GET
	@Path("/totalPurchaseCost")
	@Produces("application/json")
	@Allow(groups = "admin")
	public Response getOrderTotalPurchase() {
		double orderTotalPurchase = orderService.getTotalPurchaseCost();
		return Response.ok(orderTotalPurchase).build();
	}
}
