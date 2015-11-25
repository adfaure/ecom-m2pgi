package fr.ujf.m2pgi.database.DAO;


import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.ejb.Local;
import javax.ejb.Stateless;
import javax.persistence.Query;

import fr.ujf.m2pgi.database.DTO.OrderDTO;
import fr.ujf.m2pgi.database.entities.Order;
import fr.ujf.m2pgi.database.entities.Photo;

/**
 * 
 * @author AZOUZI Marwen
 *
 */
public class OrderDAOImpl extends GeneriqueDAOImpl<Order> implements IOrderDAO {

	@Override
	public Order create(Order entity) {
		Collection<Photo> photos = new ArrayList<Photo>();
		float price = 0;
		for(Photo photo : entity.getOrderedPhotos()) {
			Photo attached = entityManager.find(Photo.class, photo.getPhotoID());
			attached.setSales(attached.getSales() + 1);
			photos.add(attached);
			price += photo.getPrice();
			entityManager.merge(attached);
		}
		entity.setPrice(price);
		entity.setOrderedPhotos(photos);
		return super.create(entity);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Order> getCustomerOrders(String login) {
		Query query = entityManager.createQuery("SELECT o FROM Order o left join o.member m WHERE m.login=:login ORDER BY o.dateCreated DESC");
		query.setParameter("login", login);
		return (List<Order>)query.getResultList();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Order> getAllOrders() {
		Query query = entityManager.createQuery("SELECT o FROM Order o ORDER BY o.dateCreated DESC");
	    return (List<Order>)query.getResultList();
	}
	
	public Double getTotalPurchaseCost(){
		Double price = 0.0;
		Query query = entityManager.createQuery("SELECT SUM(o.price) FROM Order o ");
		
		if(query.getSingleResult() != null)
			price = (Double) query.getSingleResult();
		
		return price;
	}
}
