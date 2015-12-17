package fr.ujf.m2pgi.REST.Resources;

import fr.ujf.m2pgi.database.DTO.TagCountDTO;
import fr.ujf.m2pgi.database.Service.ITagService;

import javax.ejb.EJB;
import javax.ws.rs.*;
import javax.ws.rs.core.Response;

import java.util.List;

/**
 * Created by AZOUZI Marwen on 12/12/15.
 * The tags service
 *
 */
@Path("/tags")
public class RESTTagServlet {

  @EJB
  private ITagService tagService;

  @GET
  @Path("/")
  @Produces("application/json")
  public Response getAllTags() {
    List<TagCountDTO> tags = tagService.getAllTags();
    return Response.ok(tags).build();
  }

  @GET
  @Path("/top10")
  @Produces("application/json")
  public Response getTop10Tags() {
    List<TagCountDTO> tags = tagService.getTop10Tags();
    return Response.ok(tags).build();
  }

  @GET
  @Path("/trends")
  @Produces("application/json")
  public Response getTrends() {
    List<TagCountDTO> tags = tagService.getTrends();
    return Response.ok(tags).build();
  }
}
