package fr.ujf.m2pgi.database.entities;

import javax.persistence.*;
import java.util.Date;


@Entity
@IdClass(SignalID.class)
@Table(name="signal")
public class Signal {
	
	@Id
	@ManyToOne(cascade = {CascadeType.ALL})
	@JoinColumn(name="photoID", nullable = false)
	private Photo photo;
	
	@Id
	@ManyToOne(cascade = {CascadeType.ALL})
	@JoinColumn(name="memberID", nullable = false)
	private Member member;
	
	@Column(name="description")
	private String description;
	
	@Column(name="dateSignal", insertable = false, updatable = false, columnDefinition = "timestamp default current_timestamp")
	@Temporal(TemporalType.TIMESTAMP)
	private Date dateSignal;
	
	public void setPhoto(Photo photo){
		this.photo=photo;
	}
	
	public Photo getPhoto(){
		return photo;
	}
	
	public void setMember(Member member){
		this.member = member;
	}
	
	public Member getMember(){
		return member;
	}
	
	public void setDescription(String description){
		this.description= description;
	}
	
	public String getDescription(){
		return description;
	}
	
	public void setDateSignal(Date dateSignal){
		this.dateSignal = dateSignal;
	}
	
	public Date getDateSignal(){
		return dateSignal;
	}

}