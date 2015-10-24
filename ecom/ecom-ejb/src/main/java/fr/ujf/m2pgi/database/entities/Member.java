package fr.ujf.m2pgi.database.entities;

import javax.persistence.*;

/**
 * 
 * @author FAURE Adrien
 *
 */
@Entity
@Table(name="member")
@Inheritance(strategy=InheritanceType.JOINED)
@DiscriminatorValue(value = "N")
public class Member {

	@Id
	@Column(name="memberID" ,columnDefinition = "serial")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	protected long memberID;

	@Column(name="login")
	protected String login;

	@Column(name="password")
	protected String password; //FIXME find a way to encrypte this

	@Column(name="firstName")
	protected String firstName;

	@Column(name="lastName")
	protected String lastName;
	
	@Column(name="email")
	protected String email;
	
	@Column(name="accountType")
	protected char accountType;

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


}