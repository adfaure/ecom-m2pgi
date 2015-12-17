package fr.ujf.m2pgi.REST;

import java.util.HashSet;
import java.util.Set;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;

import fr.ujf.m2pgi.REST.Interceptors.MultiPartContentTypeFilter;
import fr.ujf.m2pgi.REST.Interceptors.SecurityInterceptor;
import fr.ujf.m2pgi.REST.Resources.RESTAuthentification;
import fr.ujf.m2pgi.REST.Resources.RESTMemberServlet;
import fr.ujf.m2pgi.REST.Resources.RESTOrdersServlet;
import fr.ujf.m2pgi.REST.Resources.RESTPhotosServlet;
import fr.ujf.m2pgi.REST.Resources.RESTSellerServlet;
import fr.ujf.m2pgi.REST.Resources.RESTTagServlet;

/**
 * Main Rest application
 * All applicationresources will be accessible through the path /api/{resources}
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
        classes.add(SecurityInterceptor.class       );
        classes.add(MultiPartContentTypeFilter.class);
        classes.add(RESTAuthentification.class      );
        classes.add(RESTMemberServlet.class         );
        classes.add(RESTSellerServlet.class         );
        classes.add(RESTPhotosServlet.class         );
        classes.add(RESTOrdersServlet.class         );
        classes.add(RESTTagServlet.class         );
        return classes;
    }


}
