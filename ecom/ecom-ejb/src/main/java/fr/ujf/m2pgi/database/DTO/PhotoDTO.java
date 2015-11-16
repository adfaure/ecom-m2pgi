package fr.ujf.m2pgi.database.DTO;

import java.io.Serializable;

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
@XmlAccessorType(XmlAccessType.NONE)
public class PhotoDTO implements Serializable {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

	@XmlElement
	private long photoID;

	@XmlElement
	private String description;

	@XmlElement
	private String name;

	@XmlElement
	private String fileLocation;

	@XmlElement
	private String webLocation;

	@XmlElement
	private float price;

	@XmlElement
	private Integer views;

	@XmlElement
	private Integer likes;


	@XmlElement
	private long sellerID;

	public String getWebLocation() {
		return webLocation;
	}

	public void setWebLocation(String webLocation) {
		this.webLocation = webLocation;
	}

	public String getFileLocation() {
		return fileLocation;
	}

	public void setFileLocation(String fileLocation) {
		this.fileLocation = fileLocation;
	}

	public long getPhotoId() {
		return photoID;
	}

	public void setPhotoId(long photoId) {
		this.photoID = photoId;
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

	public float getPrice() {
		return price;
	}

	public void setPrice(float price) {
		this.price = price;
	}

	public Integer getViews() {
		return views;
	}

	public void setViews(Integer views) {
		this.views = views;
	}

	public Integer getLikes() {
		return likes;
	}

	public void setLikes(Integer likes) {
		this.likes = likes;
	}

	public long getSellerID() {
		return sellerID;
	}

	public void setSellerID(long sellerID) {
		this.sellerID = sellerID;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}
}
