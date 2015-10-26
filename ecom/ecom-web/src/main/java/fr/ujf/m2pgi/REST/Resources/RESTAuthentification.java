package fr.ujf.m2pgi.REST.Resources;

import fr.ujf.m2pgi.REST.Security.PrincipalUser;
import fr.ujf.m2pgi.REST.Security.SecurityAnnotations.Allow;
import fr.ujf.m2pgi.REST.Security.SecurityAnnotations.Deny;
import fr.ujf.m2pgi.Security.ITokenGenerator;
import fr.ujf.m2pgi.database.DTO.MemberDTO;
import fr.ujf.m2pgi.database.Service.MemberService;
import org.jboss.resteasy.core.Headers;
import org.jboss.resteasy.core.ServerResponse;

import javax.ejb.EJB;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by FAURE Adrien on 25/10/15.
 */
@Path("/auth")
public class RESTAuthentification {

    @EJB
    private MemberService memberService;

    @EJB
    private ITokenGenerator tokenGenerator;

    @Context
    private HttpServletRequest httpServletRequest;

    @POST
    @Path("/login/{username}")
    @Consumes("application/x-www-form-urlencoded")
    @Produces("application/json")
    @Deny(groups="seller;members;admin")
    public Response login(@PathParam("username") String username)  {

        MemberDTO member    = memberService.getMemberByLogin(username);
        HttpSession session = httpServletRequest.getSession();
        PrincipalUser principal = null;
        if(session.getAttribute("principal") != null) {
            return new ServerResponse("ALREADY LOGGED", 500, new Headers<Object>());
        } else if(member == null) {
            return new ServerResponse("Authentification failed", 500, new Headers<Object>());
        }

        String password = httpServletRequest.getParameter("password");
        if(member.getPassword().equals(password)) {
            principal = new PrincipalUser();
            principal.setToken(tokenGenerator.nextSessionId());
            principal.setUser(member);
            switch (member.getAccountType()) {
                case 'S' :
                    principal.setGroup("seller");
                    break;
                case 'M' :
                    principal.setGroup("members");
                    break;
            }
            session.setAttribute("principal", principal);
        }
        Map resJson = new HashMap<String, Object>();
        resJson.put("token", principal.getToken());
        resJson.put("user" , principal.getUser());
        return Response.ok().entity(resJson).build();
    }

    @POST
    @Path("/logout")
    @Produces("application/json")
    @Allow(groups="seller;members;admin")
    public Response logout() {
        HttpSession session = httpServletRequest.getSession();
        session.setAttribute("principal", null);
        Map resJson = new HashMap<String, Object>();
        resJson.put("message", "success");
        return Response.ok().entity(resJson).build();
    }

}
