package fr.ujf.m2pgi.REST.Resources;

import java.util.Collection;
import java.util.List;

import javax.ejb.EJB;
import javax.ws.rs.*;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import fr.ujf.m2pgi.EcomException;
import fr.ujf.m2pgi.REST.CustomServerResponse;
import fr.ujf.m2pgi.REST.Security.SecurityAnnotations.Allow;
import fr.ujf.m2pgi.REST.Security.SecurityAnnotations.AllowAll;
import fr.ujf.m2pgi.database.DTO.MemberDTO;
import fr.ujf.m2pgi.database.DTO.OrderDTO;
import fr.ujf.m2pgi.database.DTO.PublicPhotoDTO;
import fr.ujf.m2pgi.database.Service.ICustomerService;
import fr.ujf.m2pgi.database.Service.IMemberService;
import fr.ujf.m2pgi.database.Service.OrderService;

/**
 * Created by AZOUZI Marwen 23/10/15
 * The base route to interact with the order and allow user to buy photos.
 */
@Path("/orders")
public class RESTOrdersServlet {

	/**
	 * The application order service
	 */
	@EJB
	private OrderService orderService;

	/**
	 * the application member service
	 */
	@EJB
	private IMemberService memberService;

	/**
	 * the customer service (basically the same as orderservice)
	 */
	@EJB
	private ICustomerService customerService;

	/**
	 *	Return all orders from the application
	 * @return
     */
	@GET
	@Path("/")
	@Produces("application/json")
	//FIXME admin route only with this route open no need to buy photo because we all can acces to order which contains the private location
	public Response getAllOrders() {
		List<OrderDTO> orders = orderService.getAllOrders();
		return Response.ok(orders).build();
	}

	/**
	 * Get all orders performed by one user (access by login).
	 * @param login the login of the user
	 * @return a json with all orders related to the user which own the parameter login
     */
	@GET
	@Path("/customer/login/{login}")
	@Produces("application/json")
	//FIXME member;sellers route only. with this open route if only one user buy a photo everyone can also access to it easily
	public Response getUserOrders(@PathParam("login") String login) {
		MemberDTO member = memberService.getMemberByLogin(login, true);
		if(member == null) {
			return Response.status(Status.NOT_FOUND).build();
		}
		List<OrderDTO> photos = orderService.getCustomerOrders(login);
		return Response.ok(photos).build();
	}

	/**
	 * Create an order to the user with login login
	 * @param login the login of the user
	 * @param order A collections of photo to add to the order
     * @return
     */
    @POST
    @Path("/customer/login/{login}")
    @Produces("application/json")
	//FIXME You can create an order for any member you want
    public Response createOrders(@PathParam("login") String login, Collection<PublicPhotoDTO> order) {
        MemberDTO member = memberService.getMemberByLogin(login, true);
        if(member == null) {
            return Response.status(Status.NO_CONTENT).build();
        }
		if(order.size() == 0) {
			return  Response.status(Status.BAD_REQUEST).entity(new CustomServerResponse(false, "Cannot create empty order")).build();
		}
		try {
			customerService.createOrder(login, order);
		} catch (EcomException e) {
			e.printStackTrace();
			return  Response.status(Status.BAD_REQUEST).entity(new CustomServerResponse(false, "duplicate photo order")).build();
		}
		member = memberService.getMemberByLogin(login, true);
		return Response.ok(member).build();
    }

	/**
	 * get the number of order in the whole application.
	 * @return
     */
	@GET
	@Path("/count")
	@Produces("application/json")
	@Allow(groups = "admin")
	public Response getOrderCount() {
		Long orderCount = orderService.getOrderCount();
		return Response.ok(orderCount).build();
	}

	/**
	 * get the total sum of each orders.
	 * @return
     */
	@GET
	@Path("/totalPurchaseCost")
	@Produces("application/json")
	@Allow(groups = "admin")
	public Response getOrderTotalPurchase() {
		double orderTotalPurchase = orderService.getTotalPurchaseCost();
		return Response.ok(orderTotalPurchase).build();
	}

}
