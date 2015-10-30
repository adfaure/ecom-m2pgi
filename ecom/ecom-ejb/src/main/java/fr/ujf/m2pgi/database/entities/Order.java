package fr.ujf.m2pgi.database.entities;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "orders")
public class Order {
	
	@Id
	@Column(name="orderID", columnDefinition = "serial")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long orderID;
	
    /*@Column(name = "date_created")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dateCreated;*/
	
	@ManyToOne(cascade = {CascadeType.ALL})
	@JoinColumn(name = "photoID", nullable = false)
	private Photo photo;
	
	@ManyToOne(cascade = {CascadeType.ALL})
	@JoinColumn(name = "memberID", nullable = false)
	private Member member;
	
	public long getOrderID() {
		return orderID;
	}

	public void setOrderID(long orderID) {
		this.orderID = orderID;
	}

	public Photo getPhoto() {
		return photo;
	}

	public void setPhoto(Photo photo) {
		this.photo = photo;
	}

	public Member getMember() {
		return member;
	}

	public void setMember(Member member) {
		this.member = member;
	}
}