package fr.ujf.m2pgi.database.Service;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import fr.ujf.m2pgi.database.DAO.IMemberDAO;
import fr.ujf.m2pgi.database.DAO.IOrderDAO;
import fr.ujf.m2pgi.database.DAO.IPhotoDAO;
import fr.ujf.m2pgi.database.DTO.OrderDTO;
import fr.ujf.m2pgi.database.Mappers.IOrderMapper;
import fr.ujf.m2pgi.database.entities.Order;

/**
 * 
 * @author AZOUZI Marwen
 *
 */
@Stateless
public class OrderService {

	@EJB
	private IOrderMapper orderMapper;

	@EJB
	IOrderDAO orderDao;
	
	@EJB
	IMemberDAO memberDao;
	
	@EJB
	IPhotoDAO photoDao;

	public List<OrderDTO> getAllOrders() {
		List<OrderDTO> result = new ArrayList<OrderDTO>();
		for(Order order: orderDao.getAllOrders()) {
			result.add(orderMapper.getDTO(order));
		}
		return result;
	}
	
	public List<OrderDTO> getCustomerOrders(String login) {
		List<OrderDTO> result = new ArrayList<OrderDTO>();
		for(Order order: orderDao.getCustomerOrders(login)) {
			result.add(orderMapper.getDTO(order));
		}
		return result;
	}
}
