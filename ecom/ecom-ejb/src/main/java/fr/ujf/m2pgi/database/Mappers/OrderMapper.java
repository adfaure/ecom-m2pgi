package fr.ujf.m2pgi.database.Mappers;

import fr.ujf.m2pgi.database.DTO.OrderDTO;
import fr.ujf.m2pgi.database.DTO.PhotoDTO;
import fr.ujf.m2pgi.database.entities.Order;
import fr.ujf.m2pgi.database.entities.Photo;
import org.modelmapper.PropertyMap;

import javax.ejb.Local;
import javax.ejb.Stateless;
import javax.inject.Inject;
import java.util.ArrayList;
import java.util.Collection;

/**
 * Created by AZOUZI Marwen 30/10/15.
 */
public class OrderMapper extends GeneriqueMapperImpl<OrderDTO, Order> implements IOrderMapper {

    @Inject
    private IPhotoMapper photoMapper;

    public OrderMapper() {
        super();
    }

    @Override
    public OrderDTO getDTO(Order entity) {
        OrderDTO orderDto = super.getDTO(entity);
        Collection<PhotoDTO> photos = new ArrayList<PhotoDTO>();
        for(Photo photoEntity : entity.getOrderedPhotos()) {
            photos.add(photoMapper.getDTO(photoEntity));
        }
        orderDto.setPhotos(photos);
        return  orderDto;
    }
}




