package fr.ujf.m2pgi.REST.Resources;

import java.util.List;

import javax.ejb.EJB;
import javax.ws.rs.*;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import fr.ujf.m2pgi.database.DTO.OrderDTO;
import fr.ujf.m2pgi.database.DTO.PhotoDTO;
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
	public Response getAllPhotos() {
		List<OrderDTO> photos = orderService.getAllOrders();
		return Response.ok(photos).build();
	}
	
	@GET
	@Path("/customer/login/{login}")
	@Produces("application/json")
	public Response getUserPhotos(@PathParam("login") String login) {
		List<OrderDTO> photos = orderService.getCustomerOrders(login);
		return Response.ok(photos).build();
	}
	
	@POST
	@Path("/add")
	@Produces("application/json")
	public Response addOrder(@FormParam("customer") Long customer, @FormParam("product") Long product) {
		OrderDTO inserted = new OrderDTO();
		inserted.setMemberID(customer);
		inserted.setPhotoID(product);
		OrderDTO created = orderService.createOrder(inserted);
		if (created == null) {
			return Response.status(Status.BAD_REQUEST).entity("L'achat n'a pas été enregistré !").build();
		}
		return Response.status(Status.CREATED).entity(created).build();
	}
}
