package fr.ujf.m2pgi.database.DTO;

import java.io.Serializable;
import java.util.Collection;
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
	private Long memberID;
	
	@XmlElement
	private Collection<FullPhotoDTO> photos;
	
	@XmlElement
	private Date dateCreated;

	@XmlElement
	private float price;

	public float getPrice() {
		return price;
	}

	public void setPrice(float price) {
		this.price = price;
	}

	public Collection<FullPhotoDTO> getPhotos() {
		return photos;
	}

	public void setPhotos(Collection<FullPhotoDTO> photos) {
		this.photos = photos;
	}

	public Long getOrderID() {
		return orderID;
	}

	public void setOrderID(Long orderID) {
		this.orderID = orderID;
	}

	public Long getMemberID() {
		return memberID;
	}

	public void setMemberID(Long memberID) {
		this.memberID = memberID;
	}
	
	public Date getDateCreated() {
		return dateCreated;
	}

	public void setDateCreated(Date dateCreated) {
		this.dateCreated = dateCreated;
	}

}
