package fr.ujf.m2pgi.database.DTO;

import java.util.Date;

import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author AZOUZI Marwen ()
 *
 */
@XmlRootElement
public class PhotoContextBigDTO {

	private long photoID;

	private String name;
	
	private String description;

	private String webLocation;
	
	private float price;
	
	private long sellerID;
	
	private int sales;

	private Date dateCreated;

	private Integer views;

	private Integer likes;
	
	private boolean wishlisted;

	private boolean inCart;
	
	private boolean liked;
	
	private boolean flagged;
	
	public PhotoContextBigDTO(long photoID, String name, String description, String webLocation, float price,
			long sellerID, int sales, Date dateCreated, Integer views, Integer likes, boolean wishlisted,
			boolean inCart, boolean liked, boolean flagged) {
		super();
		this.photoID = photoID;
		this.name = name;
		this.description = description;
		this.webLocation = webLocation;
		this.price = price;
		this.sellerID = sellerID;
		this.sales = sales;
		this.dateCreated = dateCreated;
		this.views = views;
		this.likes = likes;
		this.wishlisted = wishlisted;
		this.inCart = inCart;
		this.liked = liked;
		this.flagged = flagged;
	}

	public long getPhotoId() {
		return photoID;
	}

	public void setPhotoId(long photoID) {
		this.photoID = photoID;
	}

	public String getWebLocation() {
		return webLocation;
	}

	public void setWebLocation(String webLocation) {
		this.webLocation = webLocation;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public float getPrice() {
		return price;
	}

	public void setPrice(float price) {
		this.price = price;
	}
	
	public long getSellerID() {
		return sellerID;
	}

	public void setSellerID(long sellerID) {
		this.sellerID = sellerID;
	}

	public int getSales() {
		return sales;
	}

	public void setSales(int sales) {
		this.sales = sales;
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
	
	public boolean isWishlisted() {
		return wishlisted;
	}

	public void setWishlisted(boolean wishlisted) {
		this.wishlisted = wishlisted;
	}
	
	public boolean isInCart() {
		return inCart;
	}

	public void setInCart(boolean inCart) {
		this.inCart = inCart;
	}
	
	public boolean isLiked() {
		return liked;
	}

	public void setLiked(boolean liked) {
		this.liked = liked;
	}
	
	public boolean isFlagged() {
		return flagged;
	}

	public void setFlagged(boolean flagged) {
		this.flagged = flagged;
	}
}
