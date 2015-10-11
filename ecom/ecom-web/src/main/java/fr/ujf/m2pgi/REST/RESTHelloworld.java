package fr.ujf.m2pgi.REST;

import javax.ejb.EJB;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;

import fr.ujf.m2pgi.HelloWorldBean;


@Path("/hello")
public class RESTHelloworld {
	
	@EJB
	private HelloWorldBean hello;
	
	@GET
	public String sayHello() { //TODO those methods will need to return a javax.ws.rs.core.Response
		return hello.sayHello();		
	}
	
	@GET
	@Path("/{message}")
	public String getMessage(@PathParam("message") String message) {
	    return message;
	}
	  
}
