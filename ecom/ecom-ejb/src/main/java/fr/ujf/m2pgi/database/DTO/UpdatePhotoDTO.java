package fr.ujf.m2pgi.database.DTO;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author AZOUZI Marwen ()
 *
 */
@XmlRootElement
public class UpdatePhotoDTO implements Serializable {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

	@XmlElement(name="id")
	private long photoID;

	@XmlElement
	private String description;

	@XmlElement
	private String name;

	@XmlElement
	private float price;

	public long getPhotoId() {
		return photoID;
	}

	public void setPhotoId(long photoId) {
		this.photoID = photoId;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
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

}
