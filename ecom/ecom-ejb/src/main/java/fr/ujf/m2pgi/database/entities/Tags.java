package fr.ujf.m2pgi.database.entities;

import javax.persistence.*;
import java.util.Date;
import java.util.Collection;
import java.io.Serializable;

@Entity
@Table(name = "tags")
public class Tags implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id @ManyToOne @JoinColumn(name = "photoid")
	private Photo photo;

	@Id @ManyToOne @JoinColumn(name = "tagid")
	private Tag tag;

	@Column(name="tagged", insertable = false, updatable = false, columnDefinition = "timestamp default current_timestamp")
	@Temporal(TemporalType.TIMESTAMP)
	private Date tagged;

	public Photo getPhoto() {
		return photo;
	}

	public void setPhoto(Photo photo) {
		this.photo = photo;
	}

	public Tag getTag() {
		return tag;
	}

	public void setTag(Tag tag) {
		this.tag = tag;
	}

	public void setTagged(Date tagged){
		this.tagged = tagged;
	}

	public Date getTagged(){
		return tagged;
	}
}
