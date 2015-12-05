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
public class ReportedPhotoDTO {

	private long photoID;

	private String name;

	private String description;

	private String webLocation;

	private float price;

	private Date dateCreated;

	private Integer reportCount;

	public ReportedPhotoDTO(long photoID, String name, String description, String webLocation, float price,
		 Date dateCreated) {
		this.photoID = photoID;
		this.name = name;
		this.description = description;
		this.webLocation = webLocation;
		this.price = price;
		this.dateCreated = dateCreated;
		this.reportCount = reportCount;
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

	public Date getDateCreated() {
		return dateCreated;
	}

	public void setDateCreated(Date dateCreated) {
		this.dateCreated = dateCreated;
	}

	public Integer getReportCount() {
		return reportCount;
	}

	public void setgetReportCount(Integer reportCount) {
		this.reportCount = reportCount;
	}
}
