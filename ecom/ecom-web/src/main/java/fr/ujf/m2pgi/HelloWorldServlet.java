package fr.ujf.m2pgi;

import java.io.IOException;

import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.GET;

/**
 * Servlet implementation class HelloWorldServlet
 */
@WebServlet(urlPatterns={ "/hello" })
public class HelloWorldServlet extends HttpServlet {
	
	@EJB
	HelloWorldBean helloWorld;
	
	@EJB
	PersitenceEjb persist;
	
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public HelloWorldServlet() {
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
    @GET
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		ClassEjbEntity ent = new ClassEjbEntity();
		ent.setId(19);
		ent.setName("hello");
		persist.saveEntity(ent);
		response.getWriter().append(helloWorld.sayHello());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
