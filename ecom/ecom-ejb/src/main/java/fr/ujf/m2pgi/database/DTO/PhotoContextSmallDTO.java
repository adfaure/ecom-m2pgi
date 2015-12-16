package fr.ujf.m2pgi.database.DTO;

import java.util.Date;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAccessType;

/**
 *
 * @author AZOUZI Marwen ()
 *
 */
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class PhotoContextSmallDTO {

	private long photoID;

	private String name;

	private Date dateCreated;

	private long sellerID;

	private String thumbnail;

	private boolean wishlisted;

	private float price;

	private Integer views;

	private Integer likes;

	private boolean inCart;

	private boolean isBought;

	private boolean liked;

	private boolean flagged;

	public PhotoContextSmallDTO(long photoID, long sellerID,  String name, Date dateCreated, String thumbnail, float price, Integer views, Integer likes,
			boolean wishlisted, boolean inCart, boolean isBought, boolean liked, boolean flagged) {
		super();
		this.setSellerID(sellerID);
		this.photoID = photoID;
		this.name = name;
		this.dateCreated = dateCreated;
		this.thumbnail = thumbnail;
		this.price = price;
		this.views = views;
		this.likes = likes;
		this.wishlisted = wishlisted;
		this.inCart = inCart;
		this.isBought = isBought;
		this.liked = liked;
		this.flagged = flagged;
	}

	public String getThumbnail() {
		return thumbnail;
	}

	public void setThumbnail(String thumbnail) {
		this.thumbnail = thumbnail;
	}

	public long getPhotoId() {
		return photoID;
	}

	public void setPhotoId(long photoID) {
		this.photoID = photoID;
	}

	public Date getDateCreated() {
		return dateCreated;
	}

	public void setDateCreated(Date dateCreated) {
		this.dateCreated = dateCreated;
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

	public boolean isBought() {
		return isBought;
	}

	public void setBought(boolean bought) {
		isBought = bought;
	}

	public long getSellerID() {
		return sellerID;
	}

	public void setSellerID(long sellerID) {
		this.sellerID = sellerID;
	}
}
