package fr.ujf.m2pgi.REST.Interceptors;

import fr.ujf.m2pgi.REST.CustomServerResponse;
import fr.ujf.m2pgi.REST.Security.SecurityAnnotations.Allow;
import fr.ujf.m2pgi.REST.Security.SecurityAnnotations.AllowAll;
import fr.ujf.m2pgi.REST.Security.SecurityAnnotations.Deny;
import fr.ujf.m2pgi.REST.Security.SecurityAnnotations.DenyAll;
import fr.ujf.m2pgi.Security.JwtInfo;
import fr.ujf.m2pgi.Security.JwtSingleton;
import org.jboss.resteasy.core.Headers;
import org.jboss.resteasy.core.ServerResponse;

import javax.ejb.EJB;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.container.ResourceInfo;
import javax.ws.rs.core.Context;
import javax.ws.rs.ext.Provider;
import java.io.IOException;
import java.lang.reflect.Method;

/**
 * Security interceptor
 * This class will intercept each request from a client to perform a security checking
 * To see if a client is authenticated we check if he has a token.
 * In order to be stateless, the token use the API JWT, as we are able to retrieve client information without keeping states (like http session)
 */
@Provider
public class SecurityInterceptor implements ContainerRequestFilter {

    // Une authentification est nécessaire pour accéder à la ressource.
    public static final ServerResponse UNAUTHORIZED = new ServerResponse(new CustomServerResponse(false, "Une authentification est nécessaire pour accéder à la ressource !"), 401, new Headers<Object>());

    // Le serveur a compris la requête, mais refuse de l'exécuter.
    // Contrairement à l'erreur 401, s'authentifier ne fera aucune différence.
    // Sur les serveurs où l'authentification est requise, cela signifie
    // généralement que l'authentification a été acceptée mais que les droits
    // d'accès ne permettent pas au client d'accéder à la ressource
    public static final ServerResponse FORBIDDEN = new ServerResponse(new CustomServerResponse(false, "Vos droits d'accès ne vous permettent pas d'accéder à cette ressource !"), 403, new Headers<Object>());

    /**
     *
     */
    @Context
    private ResourceInfo resourceInfo;

    /**
     * Injection og the httpRequest to retrieve the token
     */
    @Context
    private HttpServletRequest httpRequest;

    /**
     * The jwtsergice which allow to verify the token (if present)
     */
    @EJB
    private JwtSingleton jwtService;

    /**
     * This function will use the java reflection to check if there is security annotation {@link fr.ujf.m2pgi.Security}
     * For each annoations, we will check if the client have the right to access the resource,
     * @param requestContext
     * @throws IOException
     */
    @Override
    public void filter(ContainerRequestContext requestContext) throws IOException {

        Method method = resourceInfo.getResourceMethod();
        String jwtToken = httpRequest.getHeader("authtoken");
        JwtInfo userInfo = null;
        if (jwtToken != null) {

            userInfo = jwtService.validate(jwtToken);

            if (userInfo == null)// Je suis anonyme :(
            {
                requestContext.abortWith(UNAUTHORIZED);
                return;
            }

            requestContext.getHeaders().add("userID", Long.toString(userInfo.getUserId()));
            requestContext.getHeaders().add("userGroup", userInfo.getUserGroup());
        }

        if (method.getAnnotation(AllowAll.class) != null) {
            if (jwtToken == null)// Je suis anonyme :(
            {
                requestContext.abortWith(UNAUTHORIZED);
                return;
            }
            return;
        }

        if (method.getAnnotation(DenyAll.class) != null) {
            requestContext.abortWith(FORBIDDEN);
            return;
        }

        if (method.getAnnotation(Deny.class) != null) {

            if (jwtToken == null) { // Je suis anonyme :(
                requestContext.abortWith(UNAUTHORIZED);
                return;
            }

            Deny denyAnnotation = method.getAnnotation(Deny.class);
            String[] denyGroups = denyAnnotation.groups().split(";");
            for (String s : denyGroups) {
                if (userInfo.getUserGroup().equals(s)) {
                    requestContext.abortWith(FORBIDDEN);
                    return;
                }
            }
        }

        if (method.getAnnotation(Allow.class) != null) {

            if (jwtToken == null) {// Je suis anonyme
                requestContext.abortWith(UNAUTHORIZED);
                return;
            }

            Allow allowAnnotation = method.getAnnotation(Allow.class);
            String[] allowedGroup = allowAnnotation.groups().split(";");
            for (String group : allowedGroup) {
                if (userInfo.getUserGroup().equals(group)) {
                    return;
                }
            }
            requestContext.abortWith(FORBIDDEN);
            return;
        }
    }
}
