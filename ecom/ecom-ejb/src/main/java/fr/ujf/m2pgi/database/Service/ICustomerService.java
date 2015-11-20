package fr.ujf.m2pgi.database.Service;

import fr.ujf.m2pgi.EcomException;
import fr.ujf.m2pgi.database.DTO.OrderDTO;
import fr.ujf.m2pgi.database.DTO.PhotoDTO;

import javax.ejb.Local;
import java.util.Collection;

/**
 * Created by FAURE Adrien on 13/11/15.
 */
@Local
public interface ICustomerService {

    OrderDTO createOrder(String login, Collection<PhotoDTO> photos) throws EcomException;
}
