package fr.ujf.m2pgi.database.entities;

import java.util.Collection;

import javax.persistence.*;

@Entity
@Table(name="Seller")
@PrimaryKeyJoinColumn(name="memberID")
@DiscriminatorValue(value="S")
public class Seller extends Member {

	@Column(name="RIB")
	private String RIB;

	@OneToMany(mappedBy="author", fetch = FetchType.EAGER) // TODO maybe optimize this...
	Collection<Photo> photos;
	
	public String getRIB() {
		return RIB;
	}

	public void setRIB(String rIB) { 
		RIB = rIB;
	}

	public Collection<Photo> getPhotos() {
        return photos;
	}

}
