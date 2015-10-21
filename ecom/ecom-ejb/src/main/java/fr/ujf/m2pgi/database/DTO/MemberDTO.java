package fr.ujf.m2pgi.database.DTO;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * 
 * @author FAURE Adrien
 *
 */
@XmlRootElement
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
	private String emailAdress;
	
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

	public String getEmailAdress() {
		return emailAdress;
	}

	public void setEmailAdress(String emailAdress) {
		this.emailAdress = emailAdress;
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
