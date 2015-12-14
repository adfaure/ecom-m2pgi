package fr.ujf.m2pgi.database.Service;

import fr.ujf.m2pgi.EcomException;
import fr.ujf.m2pgi.database.DTO.*;

import javax.ejb.Local;
import java.util.Collection;
import java.util.List;

/**
 * Created by FAURE Adrien on 13/11/15.
 */
@Local
public interface ICustomerService {

    OrderDTO createOrder(String login, Collection<PublicPhotoDTO> photos) throws EcomException;

    List<OrderSellerDTO> getOrdersBySeller(long id);

    Long getSellerCount();
    
    List<MemberDTO> getTopSellers();

    List<FullPhotoDTO>  getBoughPhoto(long id);

    boolean  ishotoBought(long id, long photoID);

}
