package fr.ujf.m2pgi.database.DTO;

import java.io.Serializable;
import java.util.Date;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Created by AZOUZI Marwen on 29/10/15.
 */
@XmlRootElement
@XmlAccessorType(XmlAccessType.NONE)
public class OrderDTO implements Serializable {

	private static final long serialVersionUID = 1L;

	@XmlElement
	private Long orderID;
	
	@XmlElement
	private String login;
	
	@XmlElement
	private Long photoID;
	
	@XmlElement
	private Date dateCreated;

	public Long getOrderID() {
		return orderID;
	}

	public void setOrderID(Long orderID) {
		this.orderID = orderID;
	}

	public Long getPhotoID() {
		return photoID;
	}

	public void setPhotoID(Long photoID) {
		this.photoID = photoID;
	}
	
	public Date getDateCreated() {
		return dateCreated;
	}

	public void setDateCreated(Date dateCreated) {
		this.dateCreated = dateCreated;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public String getLogin() {
		return login;
	}
}
