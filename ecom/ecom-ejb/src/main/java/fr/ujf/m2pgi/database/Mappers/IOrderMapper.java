package fr.ujf.m2pgi.database.Mappers;

import fr.ujf.m2pgi.database.DTO.OrderDTO;
import fr.ujf.m2pgi.database.entities.Order;

import javax.ejb.Local;

/**
 * Created by FAURE Adrien on 29/10/15.
 */
@Local
public interface IOrderMapper extends IGeneriqueMapper<OrderDTO, Order> {

}
