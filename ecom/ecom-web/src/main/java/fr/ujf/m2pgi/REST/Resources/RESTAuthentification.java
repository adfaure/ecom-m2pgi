package fr.ujf.m2pgi.REST.Resources;

import fr.ujf.m2pgi.REST.Security.PrincipalUser;
import fr.ujf.m2pgi.REST.Security.SecurityAnnotations.Allow;
import fr.ujf.m2pgi.REST.Security.SecurityAnnotations.Deny;
import fr.ujf.m2pgi.REST.CustomServerResponse;
import fr.ujf.m2pgi.Security.ITokenGenerator;
import fr.ujf.m2pgi.Security.IStringDigest;
import fr.ujf.m2pgi.database.DTO.MemberDTO;
import fr.ujf.m2pgi.database.Service.IMemberService;
import fr.ujf.m2pgi.database.Service.MemberService;

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
    private IMemberService memberService;

    @EJB
    private ITokenGenerator tokenGenerator;

    @EJB
    private IStringDigest stringDigest;

    @Context
    private HttpServletRequest httpServletRequest;

    @POST
    @Path("/login/{username}")
    @Consumes("application/x-www-form-urlencoded")
    @Produces("application/json")
    @Deny(groups="sellers;members;admin")
    public Response login(@PathParam("username") String username)  {

      if (username == null || username.equals("")) {
    	 return Response.status(401).entity(
    			 new CustomServerResponse(false, "Authentification failed: username and password required!")).build(); 
      }
      
      String password = httpServletRequest.getParameter("password");
      if (password == null || password.equals("")) {
    	 return Response.status(401).entity(
    			 new CustomServerResponse(false, "Authentification failed: username and password required!")).build(); 
      }

      MemberDTO member = memberService.getMemberByLogin(username);

      if(member == null) {
        return Response.status(401).entity(
   			 new CustomServerResponse(false, "Authentification failed: invalid username or password!")).build();
      }

      HttpSession session = httpServletRequest.getSession();
      PrincipalUser principal = null;

      if(member.getPassword().equals(stringDigest.digest(password))) {
        principal = new PrincipalUser();
        principal.setToken(tokenGenerator.nextSessionId());
        principal.setUser(member);
        switch (member.getAccountType()) {
          case 'S':
            principal.setGroup("sellers");
            break;
          case 'M':
            principal.setGroup("members");
            break;
          case 'A':
          	principal.setGroup("admin");
            break;
        }
        session.setAttribute("principal",principal);
      } else {
          return Response.status(401).entity(
        		  new CustomServerResponse(false, "Authentification failed: invalid username or password!")).build();
      }
      Map<String, Object> resJson = new HashMap<String, Object>();
      resJson.put("token", principal.getToken());
      resJson.put("user", principal.getUser());
      return Response.ok().entity(new CustomServerResponse(true, "Authentification successed!", resJson)).build();
    }

    @POST
    @Path("/logout")
    @Produces("application/json")
    @Allow(groups="sellers;members;admin")
    public Response logout() {
        HttpSession session = httpServletRequest.getSession();
        session.setAttribute("principal", null);
        return Response.ok().entity(new CustomServerResponse(true, "Logout successed!")).build();
    }
}
