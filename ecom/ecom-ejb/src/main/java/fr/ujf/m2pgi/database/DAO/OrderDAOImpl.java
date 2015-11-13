package fr.ujf.m2pgi.database.DAO;


import java.util.List;

import javax.ejb.Local;
import javax.ejb.Stateless;
import javax.persistence.Query;

import fr.ujf.m2pgi.database.DTO.OrderDTO;
import fr.ujf.m2pgi.database.entities.Order;

/**
 * 
 * @author AZOUZI Marwen
 *
 */
public class OrderDAOImpl extends GeneriqueDAOImpl<Order> implements IOrderDAO {
	
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
		Query query = entityManager.createQuery("SELECT SUM(p.price) FROM Order o left join o.photo p");
		
		if(query.getSingleResult() != null)
			price = (Double) query.getSingleResult();
		
		return price;
	}
	
	
	
}
