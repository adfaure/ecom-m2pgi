package fr.ujf.m2pgi.database.entities;

import javax.persistence.Column;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.DiscriminatorType;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.Table;

/**
 * 
 * @author FAURE Adrien
 *
 */
@Entity
@Table(name="Member")
@Inheritance(strategy=InheritanceType.JOINED)
@DiscriminatorColumn(name="accountType",discriminatorType=DiscriminatorType.CHAR)
@DiscriminatorValue(value="N")
public class Member {

	@Id
	private long memberID;

	@Column(name="login")
	private String login;

	@Column(name="password")
	private String password; //FIXME find a way to encrypte this

	@Column(name="firstname")
	private String firstName;

	@Column(name="lastname")
	private String lastName;
	
	@Column(name="email")
	private String email;
	
	@Column(name="accountType")
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
		return email;
	}

	public void setEmailAdress(String emailAdress) {
		this.email = emailAdress;
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

	public void setAccountType(char accountType) {
		this.accountType = accountType;
	}
	
}
