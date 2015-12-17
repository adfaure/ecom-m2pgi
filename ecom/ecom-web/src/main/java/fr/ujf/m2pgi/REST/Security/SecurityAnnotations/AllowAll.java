package fr.ujf.m2pgi.REST.Security.SecurityAnnotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Security annotation use by {@link fr.ujf.m2pgi.REST.Interceptors}
 * If this annotation mark a method, any authenticated user will be able to access the resource
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD})
public @interface AllowAll {

}
