package fr.ujf.m2pgi.database.entities;

import javax.persistence.*;

/**
 * 
 * @author FAURE Adrien
 *
 */
@Entity
@Table(name="photo")
public class Photo {

	@Id
	@Column(name="photoID", columnDefinition = "serial")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long photoID;
	
	@ManyToOne(cascade = {CascadeType.ALL})
	@JoinColumn(name = "seller_id", nullable = false)
	private Seller author;

	@Column(name="description")
	private String description;
	
	@Column(name="name", nullable=false)
	private String name;
	
	@Column(name="location", nullable=false)
	private String location;
	
	@Column(name="price")
	private float price;

	public long getPhotoID() {
		return photoID;
	}

	public void setPhotoID(long photoID) {
		this.photoID = photoID;
	}

	public Seller getAuthor() {
		return author;
	}

	public void setAuthor(Seller author) {
		this.author = author;
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
