package fr.ujf.m2pgi.REST.Resources;

import javax.ejb.EJB;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;

import fr.ujf.m2pgi.database.Service.FileService;
import fr.ujf.m2pgi.database.Service.MemberService;
import fr.ujf.m2pgi.database.Service.PhotoService;

/**
 * Created by OLVERA Angélica 04/11/15
 */
@Path("/administrator")
public class RESTAdministratorServlet {

	@EJB
	private PhotoService photoService;

	@EJB
	private MemberService memberService;

	@GET
	@Path("/")
	@Produces("application/json")
	public Response getPhotoCount() {
		Long pCount = photoService.getPhotoCount();
		return Response.ok(pCount).build();
	}

}
