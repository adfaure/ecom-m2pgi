package fr.ujf.m2pgi.REST;

import java.util.HashSet;
import java.util.Set;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;


/**
 *
 */
@ApplicationPath("api")
public class RESTWebService extends Application {

    /**
     *
     * @return
     */
    @Override
    public Set<Class<?>> getClasses() {
        Set<Class<?>> classes = new HashSet<Class<?>>();
        classes.add(RESTHelloworld.class);
        classes.add(RESTMemberServlet.class);
        classes.add(RESTSellerServlet.class);
        return classes;
    }

}