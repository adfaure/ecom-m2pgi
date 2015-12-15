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
public class ManagePhotoDTO {

	public ManagePhotoDTO(long photoID, String name, String description, float price,
	int views, int likes, int wishes, int sales, String thumbnail) {
		this.photoID = photoID;
		this.name = name;
		this.description = description;
		this.price = price;
		this.views = views;
		this.likes = likes;
		this.wishes = wishes;
		this.sales = sales;
		this.thumbnail = thumbnail;
	}

	private long photoID;

	private String name;

	private String description;

	private String tags;

	private float price;

	private int views;

	private int likes;

	private int wishes;

	private int sales;

	private String thumbnail;


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

	public String getTags() {
		return tags;
	}

	public void setTags(String tags) {
		this.tags = tags;
	}

	public int getViews() {
		return views;
	}

	public void setViews(int views) {
		this.views = views;
	}

	public int getLikes() {
		return likes;
	}

	public void setLikes(int likes) {
		this.likes = likes;
	}

	public int getWishes() {
		return wishes;
	}

	public void setWishes(int wishes) {
		this.wishes = wishes;
	}

	public int getSales() {
		return sales;
	}

	public void setSales(int sales) {
		this.sales = sales;
	}
}
