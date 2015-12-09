package fr.ujf.m2pgi.REST.Interceptors;

import fr.ujf.m2pgi.REST.CustomServerResponse;
import fr.ujf.m2pgi.REST.Security.PrincipalUser;
import fr.ujf.m2pgi.REST.Security.SecurityAnnotations.Allow;
import fr.ujf.m2pgi.REST.Security.SecurityAnnotations.AllowAll;
import fr.ujf.m2pgi.REST.Security.SecurityAnnotations.Deny;
import fr.ujf.m2pgi.REST.Security.SecurityAnnotations.DenyAll;
import org.jboss.resteasy.core.Headers;
import org.jboss.resteasy.core.ServerResponse;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.container.ResourceInfo;
import javax.ws.rs.core.Context;
import javax.ws.rs.ext.Provider;
import java.io.IOException;
import java.lang.reflect.Method;
import java.util.Properties;

/**
 *
 */
@Provider
public class SecurityInterceptor implements ContainerRequestFilter {

  	private static final ServerResponse ACCESS_DENIED    =  new ServerResponse(new CustomServerResponse(true, "acces denied"), 401, new Headers<Object>());;
	private static final ServerResponse ACCESS_FORBIDDEN = new ServerResponse(new CustomServerResponse(true, "UnAllow"), 403, new Headers<Object>());;
	private static final ServerResponse SERVER_ERROR     = new ServerResponse(new CustomServerResponse(true, "ERROR"), 500, new Headers<Object>());;

	/**
	 *
	 */
	@Context
	private ResourceInfo resourceInfo;

	/**
	 *
	 */
	@Context
	private HttpServletRequest httpRequest;

	/**
	 *
	 * @param requestContext
	 * @throws IOException
	 */
	@Override
	public void filter(ContainerRequestContext requestContext) throws IOException {

		HttpSession session = httpRequest.getSession();
		Method method = resourceInfo.getResourceMethod();
		PrincipalUser principal = (PrincipalUser) session.getAttribute("principal");
		String headerToken = httpRequest.getHeader("sessionToken");

		if (method.getAnnotation(DenyAll.class) != null) {
			requestContext.abortWith(ACCESS_FORBIDDEN);
		} else if(method.getAnnotation(AllowAll.class) != null) {
			return;
		} else {
			Deny denyAnnotation = method.getAnnotation(Deny.class);
			if (denyAnnotation != null) {
				String[] denyGroups = denyAnnotation.groups().split(";");
				for (String s : denyGroups) {
					if (principal.getGroup().equals(s)) {
						requestContext.abortWith(ACCESS_DENIED);
						return;
					}
				}
			}

			Allow allowAnnotation = method.getAnnotation(Allow.class);
			if (allowAnnotation != null) {
				if (principal == null || headerToken == null) {
					requestContext.abortWith(ACCESS_DENIED);
					return;
				} else {
					String[] allowedGroup = allowAnnotation.groups().split(";");
					for (String group : allowedGroup) {
						if (principal.getGroup().equals(group)) {
							if (principal.getToken().equals(headerToken)) {
								return;
							} else {
								requestContext.abortWith(ACCESS_DENIED);
								return;
							}
						}
					}
					requestContext.abortWith(ACCESS_DENIED);
					return;
				}
			}
		}
	}
}
