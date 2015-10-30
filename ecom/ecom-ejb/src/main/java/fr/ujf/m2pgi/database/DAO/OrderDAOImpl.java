package fr.ujf.m2pgi.database.DAO;


import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.Query;

import fr.ujf.m2pgi.database.DTO.OrderDTO;
import fr.ujf.m2pgi.database.entities.Order;

/**
 * 
 * @author AZOUZI Marwen
 *
 */
@Stateless
public class OrderDAOImpl extends GeneriqueDAOImpl<Order> implements IOrderDAO {
	
	@SuppressWarnings("unchecked")
	@Override
	public List<Order> getCustomerOrders(String login) {
		Query query = entityManager.createQuery("SELECT o FROM Order o left join o.member m WHERE m.login=:login");
		query.setParameter("login", login);
		return (List<Order>)query.getResultList();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Order> getAllOrders() {
		Query query = entityManager.createQuery("SELECT o FROM Order o");
	    return (List<Order>)query.getResultList();
	}
	
}
