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
	protected long memberID;

	@Column(name="login", unique = true)
	protected String login;

	@Column(name="password")
	protected String password; //FIXME find a way to encrypte this

	@Column(name="firstName")
	protected String firstName;

	@Column(name="lastName")
	protected String lastName;

	@Column(name="email" , unique = true)
	protected String email;

	@Column(name="accountType")
	protected char accountType;

	@OneToMany(mappedBy = "member", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	protected Collection<Order> orderedPhotos;


	@ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinTable(name = "cart",
		 joinColumns =  @JoinColumn(name = "memberid") , inverseJoinColumns = @JoinColumn(name = "photoid")
	)
	protected Collection<Photo> cart;

	@OneToOne(cascade = {CascadeType.ALL})
	protected SellerInfo sellerInfo;

	public void setOrderedPhotos(Collection<Order> orderedPhotos) {
		this.orderedPhotos = orderedPhotos;
	}

	public SellerInfo getSellerInfo() {
		return sellerInfo;
	}

	public void setSellerInfo(SellerInfo sellerInfo) {
		this.sellerInfo = sellerInfo;
	}

	public Collection<Order> getOrderedPhotos() {
		return orderedPhotos;
	}

	public void getOrderedPhotos(Collection<Order> orderedPhotos) {
		this.orderedPhotos = orderedPhotos;
	}

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

}
