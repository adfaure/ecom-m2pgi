package fr.ujf.m2pgi.database.DAO;


import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.persistence.Query;

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
			price += photo.getPrice();
			photos.add(attached);
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

	public List<Order> getSellersOrders(long id) {
		Query query = entityManager.createQuery("SELECT DISTINCT o FROM Order o left join o.orderedPhotos p WHERE p.author.memberID=:id");
		query.setParameter("id", id);
		return (List<Order>)query.getResultList();
	}

	@Override
	public List<Photo> getBoughtPhoto(long id) {
		Query query = entityManager.createQuery("SELECT DISTINCT o.orderedPhotos FROM Order o WHERE o.member.memberID=:id");
		query.setParameter("id", id);
		return (List<Photo>) query.getResultList();
	}

	public boolean isPhotoBought(long id, long photoID) {
		Query query = entityManager.createQuery("SELECT DISTINCT o.orderedPhotos FROM Order o  left join o.orderedPhotos p  WHERE o.member.memberID=:id AND p.photoID = :photoID");
		query.setParameter("id", id);
		query.setParameter("photoID", photoID);
		return (query.getResultList().size() > 0);
	}
}
