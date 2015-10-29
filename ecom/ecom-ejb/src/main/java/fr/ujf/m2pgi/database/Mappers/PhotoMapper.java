package fr.ujf.m2pgi.database.Mappers;

import fr.ujf.m2pgi.database.DTO.PhotoDTO;
import fr.ujf.m2pgi.database.entities.Photo;
import org.modelmapper.PropertyMap;

import javax.ejb.Stateless;

/**
 * Created by FAURE Adrien on 29/10/15.
 */
@Stateless
public class PhotoMapper extends GeneriqueMapperImpl<PhotoDTO, Photo> implements IPhotoMapper {

    public PhotoMapper() {
        super();

        PropertyMap map = new PropertyMap<Photo, PhotoDTO>() {
            @Override
            protected void configure() {
                map().setSellerID(source.getAuthor().getMemberID());
            }
        };

        this.modelMapper.addMappings(map);
    }
}




