package fr.ujf.m2pgi.database.entities;

import java.io.Serializable;
import java.util.Collection;

import javax.persistence.*;

/**
 *
 * @author FAURE Adrien
 *
 */
@Entity
@Table(name="member")
public class Member {

	@Id
	@Column(name="memberID")
	private long memberID;

	@Column(name="login", unique = true)
	private String login;

	@Column(name="password")
	private String password; //FIXME find a way to encrypte this

	@Column(name="firstName")
	private String firstName;

	@Column(name="lastName")
	private String lastName;

	@Column(name="email" , unique = true)
	private String email;

	@Column(name="accountType")
	private char accountType;

	@OneToMany(mappedBy = "member", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	private Collection<Order> orderedPhotos;

	@ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinTable(name = "likes",
			joinColumns =  @JoinColumn(name = "memberid") , inverseJoinColumns = @JoinColumn(name = "photoid")
	)
	private Collection<Photo> likedPhotos;

	@ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinTable(name = "views",
			joinColumns =  @JoinColumn(name = "memberid") , inverseJoinColumns = @JoinColumn(name = "photoid")
	)
	private Collection<Photo> viewedPhotos;

	@ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinTable(name = "cart",
		 joinColumns =  @JoinColumn(name = "memberid") , inverseJoinColumns = @JoinColumn(name = "photoid")
	)
	private Collection<Photo> cart;

	@ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinTable(name = "wishes",
		 joinColumns =  @JoinColumn(name = "memberid") , inverseJoinColumns = @JoinColumn(name = "photoid")
	)
	private Collection<Photo> wishedPhotos;

	@OneToOne(cascade = {CascadeType.ALL})
	private SellerInfo sellerInfo;

	public long getMemberID() {
		return memberID;
	}

	public void setMemberID(long memberID) {
		this.memberID = memberID;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public char getAccountType() {
		return this.accountType;
	}

	public void setAccountType(char accountType) {
		this.accountType = accountType;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Collection<Photo> getCart() {
		return cart;
	}

	public void setCart(Collection<Photo> cart) {
		this.cart = cart;
	}

	public void setOrderedPhotos(Collection<Order> orderedPhotos) {
		this.orderedPhotos = orderedPhotos;
	}

	public Collection<Order> getOrderedPhotos() {
		return orderedPhotos;
	}

	public Collection<Photo> getLikedPhotos() {
		return likedPhotos;
	}

	public void setLikedPhotos(Collection<Photo> likedPhotos) {
		this.likedPhotos = likedPhotos;
	}

	public Collection<Photo> getViewedPhotos() {
		return viewedPhotos;
	}

	public void setViewedPhotos(Collection<Photo> viewedPhotos) {
		this.viewedPhotos = viewedPhotos;
	}

	public Collection<Photo> getWishedPhotos() {
		return wishedPhotos;
	}

	public void setWishedPhotos(Collection<Photo> wishedPhotos) {
		this.wishedPhotos = wishedPhotos;
	}

	public SellerInfo getSellerInfo() {
		return sellerInfo;
	}

	public void setSellerInfo(SellerInfo sellerInfo) {
		this.sellerInfo = sellerInfo;
	}

}
