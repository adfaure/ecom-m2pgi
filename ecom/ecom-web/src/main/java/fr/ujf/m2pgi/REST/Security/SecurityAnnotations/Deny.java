package fr.ujf.m2pgi.REST.Security.SecurityAnnotations;

/**
 * Created by FAURE Adrien on 25/10/15.
 *
 * Security annotation use by {@link fr.ujf.m2pgi.REST.Interceptors}
 * If this annotation mark a method, only authenticated user which do not own a specified group will access the resource.
 *
 */
public @interface Deny {

    /**
     * Specify the kind of permission
     * values are : seller, member, all, admin
     * @return
     */
    String groups();
}
