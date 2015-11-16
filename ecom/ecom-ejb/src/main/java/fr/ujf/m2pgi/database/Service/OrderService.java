package fr.ujf.m2pgi.database.Service;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.inject.Inject;

import fr.ujf.m2pgi.database.DAO.IMemberDAO;
import fr.ujf.m2pgi.database.DAO.IOrderDAO;
import fr.ujf.m2pgi.database.DAO.IPhotoDAO;
import fr.ujf.m2pgi.database.DTO.OrderDTO;
import fr.ujf.m2pgi.database.Mappers.IOrderMapper;
import fr.ujf.m2pgi.database.entities.Order;
import fr.ujf.m2pgi.database.entities.Photo;
import fr.ujf.m2pgi.database.entities.Member;

/**
 * 
 * @author AZOUZI Marwen
 *
 */
@Stateless
public class OrderService {

	/**
	 *
	 */
	@Inject
	private IOrderMapper orderMapper;

	/**
	 *
	 */
	@Inject
	private IOrderDAO orderDao;

	/**
	 *
	 */
	@Inject
	private IMemberDAO memberDao;

	/**
	 *
	 */
	@Inject
	private IPhotoDAO photoDao;

	/**
	 *
	 * @return
     */
	public List<OrderDTO> getAllOrders() {
		List<OrderDTO> result = new ArrayList<OrderDTO>();
		for(Order order: orderDao.getAllOrders()) {
			result.add(orderMapper.getDTO(order));
		}
		return result;
	}

	/**
	 *
	 * @param login
	 * @return
     */
	public List<OrderDTO> getCustomerOrders(String login) {
		List<OrderDTO> result = new ArrayList<OrderDTO>();
		for(Order order: orderDao.getCustomerOrders(login)) {
			result.add(orderMapper.getDTO(order));
		}
		return result;
	}
	
	public Long getOrderCount() {
		Long oCount = orderDao.getEntityCount();
		return oCount;
	}

	/**
	 *
	 * @param order
	 * @return
     */
	public OrderDTO createOrder(OrderDTO order) {
		Member member = memberDao.find(order.getMemberID());
		if (member == null) return null;// FixeME it would be better to throw custom exception such as CustomerNotFoundException
		Photo photo = photoDao.find(order.getPhotoID());
		if (photo == null) return null;
		Order orderEntity = new Order();// FIXME I get java.lang.ClassCastException when I call orderMapper.getentity(dto)
		orderEntity.setMember(member);
		orderEntity.setPhoto(photo);
		return orderMapper.getDTO(orderDao.create(orderEntity));
	}
	
	public Double getTotalPurchaseCost() {
		Double sum = orderDao.getTotalPurchaseCost();
		return sum;
	}
}
