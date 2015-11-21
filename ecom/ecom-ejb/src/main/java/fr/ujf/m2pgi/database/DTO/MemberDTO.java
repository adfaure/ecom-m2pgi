package fr.ujf.m2pgi.database.DTO;

import fr.ujf.m2pgi.database.entities.SellerInfo;

import java.io.Serializable;
import java.util.Collection;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * 
 * @author FAURE Adrien
 *
 */
@XmlRootElement
@XmlAccessorType(XmlAccessType.NONE)
public class MemberDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */
	@XmlElement
	private long memberID;

	/**
	 * 
	 */
	@XmlElement
	private String firstName;

	/**
	 * 
	 */
	@XmlElement
	private String lastName;	
	
	/**
	 * 
	 */
	@XmlElement
	private String email;
	
	/**
	 * 
	 */
	@XmlElement
	private String login;

	/**
	 * 
	 */
	@XmlElement
	private String password;
	
	/**
	 * 
	 */
	@XmlElement 
	private char accountType;

	/**
	 *
	 */
	@XmlElement
	private Collection<PhotoDTO> cart;

	@XmlElement
	private SellerInfoDTO sellerInfo;

	@XmlElement
	private Collection<OrderDTO> orderedPhotos;

	public SellerInfoDTO getSellerInfo() {
		return sellerInfo;
	}

	public void setSellerInfo(SellerInfoDTO sellerInfo) {
		this.sellerInfo = sellerInfo;
	}

	public Collection<OrderDTO> getOrderedPhotos() {
		return orderedPhotos;
	}

	public void setOrderedPhotos(Collection<OrderDTO> orderedPhotos) {
		this.orderedPhotos = orderedPhotos;
	}

	public Collection<PhotoDTO> getCart() {
		return cart;
	}

	public void setCart(Collection<PhotoDTO> cart) {
		this.cart = cart;
	}

	public long getMemberID() {
		return memberID;
	}

	public MemberDTO setMemberID(long memberID) {
		this.memberID = memberID;
		return null;
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

	public String getEmail() {
		return email;
	}

	public void setEmail(String emailAdress) {
		this.email = emailAdress;
	}

	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public char getAccountType() {
		return accountType;
	}

	public void setAccountType(char accountType) {
		this.accountType = accountType;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
}
