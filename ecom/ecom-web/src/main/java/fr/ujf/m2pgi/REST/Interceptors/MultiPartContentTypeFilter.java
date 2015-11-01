package fr.ujf.m2pgi.REST.Interceptors;

import org.jboss.resteasy.plugins.providers.multipart.InputPart;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.core.Context;
import javax.ws.rs.ext.Provider;
import java.io.IOException;

/**
 *
 */
@Provider
public class MultiPartContentTypeFilter implements ContainerRequestFilter {

    @Context
    private HttpServletRequest servletRequest;

    @Override
    public void filter(ContainerRequestContext requestContext) throws IOException {
        servletRequest.setAttribute(InputPart.DEFAULT_CONTENT_TYPE_PROPERTY, "text/plain; charset=UTF-8");
    }
}