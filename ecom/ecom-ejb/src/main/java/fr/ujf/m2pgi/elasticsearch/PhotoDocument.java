package fr.ujf.m2pgi.elasticsearch;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class PhotoDocument {

	@XmlElement(name = "photoID")
	private Long id;

	@XmlElement
	private String description;

	@XmlElement
	private String name;

	@XmlElement
	private String tags;

	@XmlElement(name = "thumbnail")
	private String location;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
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

	public String getTags() {
		return tags;
	}

	public void setTags(String tags) {
		this.tags = tags;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}
}
