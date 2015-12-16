package fr.ujf.m2pgi.REST.Resources;

import fr.ujf.m2pgi.REST.Security.SecurityAnnotations.Allow;
import fr.ujf.m2pgi.REST.Security.SecurityAnnotations.Deny;
import fr.ujf.m2pgi.REST.CustomServerResponse;
import fr.ujf.m2pgi.Security.JwtSingleton;
import fr.ujf.m2pgi.Security.IStringDigest;
import fr.ujf.m2pgi.database.DTO.MemberDTO;
import fr.ujf.m2pgi.database.DTO.PublicPhotoDTO;
import fr.ujf.m2pgi.database.Service.IMemberService;
import org.hibernate.validator.internal.xml.XmlMappingParser;
import org.jose4j.json.internal.json_simple.JSONArray;
import org.jose4j.json.internal.json_simple.JSONObject;
import org.jose4j.json.internal.json_simple.parser.JSONParser;

import javax.ejb.EJB;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by FAURE Adrien on 25/10/15.
 */
@Path("/auth")
public class RESTAuthentification {

    @EJB
    private IMemberService memberService;

    @EJB
    private IStringDigest stringDigest;

    @EJB
    private JwtSingleton jwtSingleton;

    @Context
    private HttpServletRequest httpServletRequest;

    @POST
    @Path("/login/{username}")
    @Consumes("application/json")
    @Produces("application/json")
    @Deny(groups="sellers;members;admin")
    public Response login(Map<String, Object> body, @PathParam("username") String username)  {


      if (username == null || username.equals("")) {
    	 return Response.status(401).entity(
    			 new CustomServerResponse(false, "Authentification failed: username and password required!")).build();
      }

      String password = (String) body.get("password");
      if (password == null || password.equals("")) {
    	 return Response.status(401).entity(
    			 new CustomServerResponse(false, "Authentification failed: username and password required!")).build();
      }

      MemberDTO member = memberService.getMemberByLogin(username , true);

      if(member == null) {
        member = memberService.getMemberByEmail(username , true);

        if(member == null)
        {
          return Response.status(401).entity(
     			 new CustomServerResponse(false, "Authentification failed: invalid username/email or password!")).build();
        }
      }

      if(member.getPassword().equals(stringDigest.digest(password))) {
        Long id = member.getMemberID();
        String group = "";
        switch (member.getAccountType()) {
          case 'S':
            group = "sellers";
            break;
          case 'M':
            group = "members";
            break;
          case 'A':
          group = "admin";
            break;
        }

          List<Map<String, Object>>  cart = (List<Map<String, Object>>) body.get("cart");
          List<PublicPhotoDTO> photos = new ArrayList<>();
          if (cart != null) {
              for(Map<String, Object> photoMap : cart) {
                  PublicPhotoDTO photo = new PublicPhotoDTO();
                  photo.setPhotoID(new Long((Integer) photoMap.get("photoID")));
                  photo.setSellerID(new Long((Integer) photoMap.get("sellerID")));
                  member = memberService.addToCart(member, photo);
              }
          }


          Map<String, Object> resJson = new HashMap<String, Object>();
        resJson.put("token", jwtSingleton.generateToken(id, group));
        member.setPassword("");
        resJson.put("user", member);
        return Response.ok().entity(new CustomServerResponse(true, "Authentification successed!", resJson)).build();
      } else {
          return Response.status(401).entity(
        		  new CustomServerResponse(false, "Authentification failed: invalid username/email or password!")).build();
      }
    }

    @POST
    @Path("/logout")
    @Produces("application/json")
    public Response logout() {
      // Here we should invalidate the token
      return Response.ok().entity(new CustomServerResponse(true, "Logout successed!")).build();
    }

    @POST
    @Path("/refresh")
    @Consumes("application/json")
    @Produces("application/json")
    @Allow(groups="sellers;members;admin")
    public Response refresh(@HeaderParam("userID") Long id) {
      MemberDTO member = memberService.getSellerById(id);
      if (member == null) {
        return Response.status(401).entity(
            new CustomServerResponse(false, "Something went wrong! Try to reconnect!")).build();
      }
      String group = "";
      switch (member.getAccountType()) {
        case 'S':
        group = "sellers";
        break;
        case 'M':
        group = "members";
        break;
        case 'A':
        group = "admin";
        break;
      }
      member.setPassword("");
      Map<String, Object> resJson = new HashMap<String, Object>();
      resJson.put("token", jwtSingleton.generateToken(id, group));
      resJson.put("user", member);
      return Response.ok().entity(new CustomServerResponse(true, "Refreshing session successed!", resJson)).build();
    }
}
