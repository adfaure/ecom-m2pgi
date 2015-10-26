package fr.ujf.m2pgi.REST;

import java.util.HashSet;
import java.util.Set;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;

import fr.ujf.m2pgi.REST.Interceptors.SecurityInterceptor;
import fr.ujf.m2pgi.REST.Resources.RESTAuthentification;
import fr.ujf.m2pgi.REST.Resources.RESTMemberServlet;
import fr.ujf.m2pgi.REST.Resources.RESTSellerServlet;

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
        classes.add(RESTAuthentification.class);
        classes.add(RESTMemberServlet.class);
        classes.add(RESTSellerServlet.class);
        classes.add(SecurityInterceptor.class);
        return classes;
    }
  

}