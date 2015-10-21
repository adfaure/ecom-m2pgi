package fr.ujf.m2pgi.database.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * 
 * @author FAURE Adrien
 *
 */

@Table(name="Photo")
@Entity
public class Photo {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long photoID;
	
	@Column(name="description", nullable=true)
	private String description;
	
	@Column(name="name", nullable=false)
	private String name;
	
	@Column(name="location", nullable=false)
	private String location;
	
	@Column(name="price")
	private float price;
	
	@ManyToOne
	private Seller author;

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

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public float getPrice() {
		return price;
	}

	public void setPrice(float price) {
		this.price = price;
	}

}
