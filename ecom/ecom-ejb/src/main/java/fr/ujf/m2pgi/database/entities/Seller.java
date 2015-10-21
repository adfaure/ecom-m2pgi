package fr.ujf.m2pgi.database.entities;

import java.util.Collection;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

@Entity
@Table(name="Seller")
@PrimaryKeyJoinColumn(name="memberID")
@DiscriminatorValue("S")
public class Seller extends Member {

	@Column(name="RIB")
	private String RIB;

	@OneToMany(mappedBy="author")
	Collection<Photo> photos;
	
	public String getRIB() {
		return RIB;
	}

	public void setRIB(String rIB) { 
		RIB = rIB;
	}
	
}
