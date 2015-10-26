package fr.ujf.m2pgi.REST.Security.SecurityAnnotations;

/**
 * Created by FAURE Adrien on 25/10/15.
 */
public @interface Deny {

    /**
     * Specify the kind of permission
     * values are : seller, member, all, admin
     * @return
     */
    String groups();
}
