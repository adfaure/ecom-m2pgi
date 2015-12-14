package fr.ujf.m2pgi.database.DTO;

import java.util.Date;
import java.util.List;
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
public class FullPhotoDTO implements Serializable {

	private static final long serialVersionUID = 1L;

	@XmlElement
	private long photoID;

	@XmlElement
	private String description;

	@XmlElement
	private String name;

	@XmlElement
	private String thumbnail;

	@XmlElement
	private String privateLocation;

	@XmlElement
	private String ext;

	@XmlElement
	private String webLocation;

	@XmlElement
	private float price;

	@XmlElement
	private Date dateCreated;

	@XmlElement
	private Integer views;

	@XmlElement
	private Integer likes;

	@XmlElement
	private long sellerID;

	@XmlElement
	private int sales;

	@XmlElement
	private List<String> tags;

    public String getPrivateLocation() {
        return privateLocation;
    }

    public void setPrivateLocation(String privateLocation) {
        this.privateLocation = privateLocation;
    }

    public String getExt() {
		return ext;
	}

	public void setExt(String ext) {
		this.ext = ext;
	}

	public int getSales() {
		return sales;
	}

	public void setSales(int sales) {
		this.sales = sales;
	}

	public String getWebLocation() {
		return webLocation;
	}

	public void setWebLocation(String webLocation) {
		this.webLocation = webLocation;
	}

	public long getPhotoID() {
		return photoID;
	}

	public void setPhotoID(long photoID) {
		this.photoID = photoID;
	}

	public String getThumbnail() {
		return thumbnail;
	}

	public void setThumbnail(String thumbnail) {
		this.thumbnail = thumbnail;
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

	public Date getDateCreated() {
		return dateCreated;
	}

	public void setDateCreated(Date dateCreated) {
		this.dateCreated = dateCreated;
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

	public List<String> getTags() {
		return tags;
	}

	public void setTags(List<String> tags) {
		this.tags = tags;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}
}
