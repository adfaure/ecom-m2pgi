package fr.ujf.m2pgi.database.DTO;


import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author AZOUZI Marwen ()
 *
 */
@XmlRootElement
public class UserPhotoDTO {

	public UserPhotoDTO(long photoID, String description, String name, String webLocation
		, boolean inWishList, boolean inCart) {
		this.photoID = photoID;
		this.description = description;
		this.name = name;
		this.webLocation = webLocation;
		this.inWishList = inWishList;
		this.inCart = inCart;
	}

	private long photoID;

	private String description;

	private String name;

	private String webLocation;

	private boolean inWishList;

	private boolean inCart;

	public long getPhotoId() {
		return photoID;
	}

	public void setPhotoId(long photoId) {
		this.photoID = photoId;
	}

	public String getWebLocation() {
		return webLocation;
	}

	public void setWebLocation(String webLocation) {
		this.webLocation = webLocation;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

  public boolean isInWishList() {
		return inWishList;
	}

	public void setInWishList(boolean inWishList) {
		this.inWishList = inWishList;
	}

	public boolean isInCart() {
		return inCart;
	}

	public void setInCart(boolean inCart) {
		this.inCart = inCart;
	}
}
