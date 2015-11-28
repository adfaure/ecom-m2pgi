package fr.ujf.m2pgi.database.Service;

import fr.ujf.m2pgi.EcomException;
import fr.ujf.m2pgi.database.DAO.IMemberDAO;
import fr.ujf.m2pgi.database.DAO.IOrderDAO;
import fr.ujf.m2pgi.database.DTO.OrderDTO;
import fr.ujf.m2pgi.database.DTO.OrderSellerDTO;
import fr.ujf.m2pgi.database.DTO.PhotoDTO;
import fr.ujf.m2pgi.database.Mappers.IOrderMapper;
import fr.ujf.m2pgi.database.Mappers.IOrderSellerMapper;
import fr.ujf.m2pgi.database.Mappers.IPhotoMapper;
import fr.ujf.m2pgi.database.entities.Member;
import fr.ujf.m2pgi.database.entities.Order;
import fr.ujf.m2pgi.database.entities.Photo;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Created by FAURE Adrien on 13/11/15.
 */
@Stateless
public class CustomerService implements ICustomerService {

    /**
     *
     */
    @Inject
    private IMemberDAO memberDAO;

    /**
     *
     */
    @Inject
    private IOrderDAO orderDAO;

    /**
     *
     */
    @Inject
    private IPhotoMapper photoMapper;

    /**
     *
     */
    @Inject
    private IOrderMapper orderMapper;

    /**
     *
     */
    @Inject
    private IOrderSellerMapper orderSellerMapper;

    /**
     *
     * @param login
     * @param photos
     * @return
     */
    public OrderDTO  createOrder(String login, Collection<PhotoDTO> photos) throws EcomException {
        Member member = memberDAO.findMemberByLogin(login);
        if(member == null) {
            throw new EcomException("member does not exist");
        }
        Order order = new Order();
        Collection<Photo> orderedEntities = new ArrayList<Photo>();
        for(PhotoDTO photoDto : photos ) {
            orderedEntities.add(photoMapper.getentity(photoDto));
        }
        order.setOrderedPhotos(orderedEntities);
        order.setMember(member);
        member.setCart(new ArrayList<Photo>());
        memberDAO.updateCart(member);
        return orderMapper.getDTO(orderDAO.create(order));
    }

    @Override
    public List<OrderSellerDTO> getOrdersBySeller(long id) {
        List<Order> orders = orderDAO.getSellersOrders(id);
        List<OrderSellerDTO> ordersDTO = new ArrayList<>();
        for(Order o : orders) {
            List<PhotoDTO> photos = new ArrayList<>();
            OrderSellerDTO orderDTO = orderSellerMapper.getDTO(o);
            for(PhotoDTO photo : orderDTO.getPhotos()) { //filter of photos, the ordersSellerDTO will contain only the data connected with the seller with the id "id"
                if(photo.getSellerID() == id)
                    photos.add(photo);
            }
            orderDTO.setPhotos(photos);
            ordersDTO.add(orderDTO);
        }
        return ordersDTO;
    }
    
    public Long getSellerCount(){
    	Long sellerCount = memberDAO.getSellerCount();
    	return sellerCount;
    }


}
