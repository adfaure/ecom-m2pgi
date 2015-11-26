package fr.ujf.m2pgi.database.entities;

import java.util.Collection;
import java.util.Date;

import javax.persistence.*;

@Entity
@Table(name = "orders")
public class Order {
	
	@Id
	@Column(name="orderID", columnDefinition = "serial")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long orderID;
	
	@ManyToOne(cascade = {CascadeType.ALL})
	@JoinColumn(name = "memberID", nullable = false)
	private Member member;

	@ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	@JoinTable(name = "OrderEntry",
			joinColumns =  @JoinColumn(name = "orderID") , inverseJoinColumns = @JoinColumn(name = "photoid")
	)
	private Collection<Photo> orderedPhotos;
	
    @Column(name = "date_created", insertable = false, updatable = false, columnDefinition="timestamp default current_timestamp")
	@Temporal(TemporalType.TIMESTAMP)
    private Date dateCreated;

	@Column(name="price")
	private float price;

	@PrePersist
	public void onCreate() {
		this.dateCreated = new Date();
	}


	public Collection<Photo> getOrderedPhotos() {
		return orderedPhotos;
	}

	public void setOrderedPhotos(Collection<Photo> orderedPhotos) {
		this.orderedPhotos = orderedPhotos;
	}

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

	public Date getDateCreated() {
		return dateCreated;
	}

	public void setDateCreated(Date dateCreated) {
		this.dateCreated = dateCreated;
	}

	public void setPrice(float price) {
		this.price = price;
	}

	public float getPrice() {
		return price;
	}
}