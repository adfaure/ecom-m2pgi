package fr.ujf.m2pgi.database.DAO;

import fr.ujf.m2pgi.database.DTO.OrderDTO;
import fr.ujf.m2pgi.database.entities.Order;

import java.util.List;

import javax.ejb.Local;

/**
 * Created by AZOUZI Marwen on 30/10/15
 */
public interface IOrderDAO extends IGeneriqueDAO<Order> {
	
	List<Order> getAllOrders();
	
	List<Order> getCustomerOrders(String login);
	
	Double getTotalPurchaseCost();

	List<Order> getSellersOrders(long id);
	

}
