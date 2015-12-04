package fr.ujf.m2pgi.database.DTO;

import java.util.Date;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
@XmlAccessorType(XmlAccessType.NONE)
public class SignalDTO implements Serializable {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

	@XmlElement
	private Long photoID;

	@XmlElement
	private Long memberID;
	
	@XmlElement
	private String description;

	@XmlElement
	private Date dateSignal;




	public Long getMemberID() {
		return memberID;
	}

	public void setMemberID(Long memberID) {
		this.memberID = memberID;
	}
	
	public Long getPhotoID() {
		return photoID;
	}

	public void setPhotoID(Long photoID) {
		this.photoID = photoID;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Date getDateSignal() {
		return dateSignal;
	}

	public void setDateSignal(Date dateSignal) {
		this.dateSignal = dateSignal;
	}


	public static long getSerialversionuid() {
		return serialVersionUID;
	}
}

