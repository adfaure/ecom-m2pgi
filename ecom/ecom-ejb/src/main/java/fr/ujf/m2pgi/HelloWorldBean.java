package fr.ujf.m2pgi;

import javax.ejb.Stateless;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@Stateless
@XmlRootElement(name="HelloWorld")
public class HelloWorldBean {
	
	private String helloMessage;
	
	public HelloWorldBean() {
		helloMessage = "EJB Hello world !  ";
	}
	
	public String sayHello() {
		return helloMessage;
	}
	
	@XmlElement(name="message")
	public String getMessage() {
		return helloMessage;
	}
	
}
