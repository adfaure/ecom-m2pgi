package fr.ujf.m2pgi;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="entity")
public class ClassEjbEntity {
	
	@Id
	private int id;
	
	@Column
	String name;

	public ClassEjbEntity() {
		id = (int) Math.random();
		name = "test " + name;
	}
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}
