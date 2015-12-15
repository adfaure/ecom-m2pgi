package fr.ujf.m2pgi.database.DTO;


import java.util.Collection;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;


@XmlRootElement
public class LastPhotosDTO {

	//private long photoID;

	public LastPhotosDTO(String sellerLogin){
		this.sellerLogin = sellerLogin;
	}
	
	private String sellerLogin;
	
	private Collection<PhotoContextSmallDTO> photos;

	public String getSellerLogin() {
		return sellerLogin;
	}

	public void setSellerLogin(String sellerLogin) {
		this.sellerLogin = sellerLogin;
	}

	public Collection<PhotoContextSmallDTO> getPhotos() {
		return photos;
	}

	public void setPhotos(Collection<PhotoContextSmallDTO> photos) {
		this.photos = photos;
	}
	
	
	
}
