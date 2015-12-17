package fr.ujf.m2pgi.REST.Security.SecurityAnnotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Security annotation use by {@link fr.ujf.m2pgi.REST.Interceptors}
 * If this annotation mark a method, then only the user with the specified group will be able to access the resource.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD})
public @interface Allow {

	/**
	 * Specify the kind of permission
	 * values are : seller, member, all, admin, guess
	 * @return
	 */
	String groups();
	
}

