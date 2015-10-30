package fr.ujf.m2pgi.database.Mappers;

import fr.ujf.m2pgi.database.DTO.OrderDTO;
import fr.ujf.m2pgi.database.entities.Order;
import org.modelmapper.PropertyMap;

import javax.ejb.Stateless;

/**
 * Created by AZOUZI Marwen 30/10/15.
 */
@Stateless
public class OrderMapper extends GeneriqueMapperImpl<OrderDTO, Order> implements IOrderMapper {

    public OrderMapper() {
        super();

        PropertyMap map = new PropertyMap<Order, OrderDTO>() {
            @Override
            protected void configure() {
                map().setPhotoID(source.getPhoto().getPhotoID());
                map().setMemberID(source.getMember().getMemberID());
            }
        };

        this.modelMapper.addMappings(map);
    }
}



