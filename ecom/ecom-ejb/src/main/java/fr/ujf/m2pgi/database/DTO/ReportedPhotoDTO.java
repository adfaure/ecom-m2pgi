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

	private String thumbnail;

	private float price;

	private int reports;

	private Date dateCreated;

	public ReportedPhotoDTO(long photoID, String name, String description, String thumbnail, float price,
		 int reports, Date dateCreated) {
		this.photoID = photoID;
		this.name = name;
		this.description = description;
		this.thumbnail = thumbnail;
		this.price = price;
		this.reports = reports;
		this.dateCreated = dateCreated;
	}

	public long getPhotoId() {
		return photoID;
	}

	public void setPhotoId(long photoID) {
		this.photoID = photoID;
	}

	public String getThumbnail() {
		return thumbnail;
	}

	public void setThumbnail(String thumbnail) {
		this.thumbnail = thumbnail;
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

	public int getReports() {
		return reports;
	}

	public void setReports(int reports) {
		this.reports = reports;
	}
}
