package fr.ujf.m2pgi;

import javax.ejb.Stateless;

@Stateless
public class HelloWorldBean {
	
	private String message;
	
	public HelloWorldBean() {
		message = "EJB Hello world !  ";
	}
	
	public String sayHello() {
		return message;
	}
	
}
