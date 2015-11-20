package fr.ujf.m2pgi.database.entities;

import java.util.Collection;
import java.util.Date;

import javax.persistence.*;

/**
 *
 * @author FAURE Adrien ()
 *
 */
@Entity
@Table(name="photo")
public class Photo {

	@Id
	@Column(name="photoID", columnDefinition = "serial")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long photoID;

	@ManyToOne
	@JoinColumn(name = "seller_id", nullable = false)
	private Member author;

	@Column(name="description")
	private String description;

	@Column(name="name", nullable=false)
	private String name;

	@Column(name="web_location", nullable=false)
	private String webLocation; //FIXME choose better name (maybe publicLocation, watermarkLocation ...)

	@Column(name="file_location", nullable=false)
	private String fileLocation; // FIXME same here : origirnalLocation

	@Column(name="price")
	private float price;

	@Column(name = "date_created", insertable = false, updatable = false,
	columnDefinition="timestamp default current_timestamp")
	@Temporal(TemporalType.TIMESTAMP)
	private Date dateCreated;

	@Column(name = "available")
	private boolean available = true;

	@Column(name="views", insertable = false, updatable = true, columnDefinition = "int default 0")
	private Integer views;

	@Column(name="likes", insertable = false, updatable = true, columnDefinition = "int default 0")
	private Integer likes;

	@ManyToMany(fetch = FetchType.LAZY, mappedBy = "cart")
	private Collection<Member> buyers;

	@ManyToMany(fetch = FetchType.LAZY, mappedBy = "likedPhotos")
	private Collection<Member> likers;

	@ManyToMany(fetch = FetchType.LAZY, mappedBy = "viewedPhotos")
	private Collection<Member> viewers;

	@ManyToMany(fetch = FetchType.LAZY, mappedBy = "viewedPhotos")
	private Collection<Member> wishers;

	public String getFileLocation() {
		return fileLocation;
	}

	public void setFileLocation(String fileLocation) {
		this.fileLocation = fileLocation;
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

	public Member getAuthor() {
		return author;
	}

	public void setAuthor(Member author) {
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

	public boolean isAvailable() {
		return available;
	}

	public void setAvailable(boolean available) {
		this.available = available;
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

	public Collection<Member> getBuyers() {
		return buyers;
	}

	public void setBuyers(Collection<Member> buyers) {
		this.buyers = buyers;
	}

	public Collection<Member> getLikers() {
		return likers;
	}

	public void setLikers(Collection<Member> likers) {
		this.likers = likers;
	}

	public Collection<Member> getViewers() {
		return viewers;
	}

	public void setViewers(Collection<Member> viewers) {
		this.viewers = viewers;
	}

	public Collection<Member> getWishers() {
		return wishers;
	}

	public void setWishers(Collection<Member> wishers) {
		this.wishers = wishers;
	}
}
