package fr.ujf.m2pgi.database.Service;

import fr.ujf.m2pgi.EcomException;
import fr.ujf.m2pgi.database.DTO.MemberDTO;
import fr.ujf.m2pgi.database.DTO.OrderDTO;
import fr.ujf.m2pgi.database.DTO.OrderSellerDTO;
import fr.ujf.m2pgi.database.DTO.PhotoDTO;

import javax.ejb.Local;
import java.util.Collection;
import java.util.List;

/**
 * Created by FAURE Adrien on 13/11/15.
 */
@Local
public interface ICustomerService {

    OrderDTO createOrder(String login, Collection<PhotoDTO> photos) throws EcomException;

    List<OrderSellerDTO> getOrdersBySeller(long id);

    Long getSellerCount();
    
    List<MemberDTO> getTopSellers();
}
