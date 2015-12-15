package fr.ujf.m2pgi.database.entities;

import javax.persistence.*;

import java.io.Serializable;

@Entity
@Table(name = "orderentry")
public class OrderEntry implements Serializable {

  /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

  @Id @ManyToOne @JoinColumn(name = "orderid")
  private Order order;

  @Id @ManyToOne @JoinColumn(name = "photoid")
  private Photo photo;

  public Order getOrder() {
    return order;
  }

  public void setOrder(Order order) {
    this.order = order;
  }

  public Photo getPhoto() {
    return photo;
  }

  public void setPhoto(Photo photo) {
    this.photo = photo;
  }
}
