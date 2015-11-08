package fr.ujf.m2pgi.database.entities;

import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "orders")
public class Order {
	
	@Id
	@Column(name="orderID", columnDefinition = "serial")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long orderID;
	
	@ManyToOne(cascade = {CascadeType.ALL})
	@JoinColumn(name = "login", nullable = false)
	private Member member;
	
	@ManyToOne(cascade = {CascadeType.ALL})
	@JoinColumn(name = "photoID", nullable = false)
	private Photo photo;
	
    @Column(name = "date_created", insertable = false, updatable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date dateCreated;

	public Long getOrderID() {
		return orderID;
	}

	public void setOrderID(Long orderID) {
		this.orderID = orderID;
	}

	public Member getMember() {
		return member;
	}

	public void setMember(Member member) {
		this.member = member;
	}

	public Photo getPhoto() {
		return photo;
	}

	public void setPhoto(Photo photo) {
		this.photo = photo;
	}
	
	public Date getDateCreated() {
		return dateCreated;
	}

	public void setDateCreated(Date dateCreated) {
		this.dateCreated = dateCreated;
	}
}