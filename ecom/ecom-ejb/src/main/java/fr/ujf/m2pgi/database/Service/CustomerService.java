package fr.ujf.m2pgi.database.Service;

import fr.ujf.m2pgi.EcomException;
import fr.ujf.m2pgi.database.DAO.IMemberDAO;
import fr.ujf.m2pgi.database.DAO.IOrderDAO;
import fr.ujf.m2pgi.database.DTO.*;
import fr.ujf.m2pgi.database.Mappers.*;
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
    private IPublicPhotoMapper publicPhotoMapper;


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
    */
   @Inject
   private IMemberMapper memberMapper;
   
   
    /**
     *
     * @param login
     * @param photos
     * @return
     */
    public OrderDTO  createOrder(String login, Collection<PublicPhotoDTO> photos) throws EcomException {
        Member member = memberDAO.findMemberByLogin(login);
        if(member == null) {
            throw new EcomException("member does not exist");
        }
        Order order = new Order();
        Collection<Photo> orderedEntities = new ArrayList<Photo>();
        for(PublicPhotoDTO publicPhotoDto : photos ) {
            orderedEntities.add(publicPhotoMapper.getentity(publicPhotoDto));
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
            List<FullPhotoDTO> photos = new ArrayList<>();
            OrderSellerDTO orderDTO = orderSellerMapper.getDTO(o);
            for(FullPhotoDTO photo : orderDTO.getPhotos()) { //filter of photos, the ordersSellerDTO will contain only the data connected with the seller with the id "id"
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

	@Override
	public List<MemberDTO> getTopSellers() {
		List<Member> topSellers = memberDAO.getTopSellers();
		List<MemberDTO> membersDTO = new ArrayList<>();
		for(Member m: topSellers){
			membersDTO.add(memberMapper.getDTO(m));
		}
		return membersDTO;
	}

}
