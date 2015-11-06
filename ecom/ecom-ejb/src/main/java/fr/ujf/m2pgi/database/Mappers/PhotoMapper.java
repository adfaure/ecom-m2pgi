package fr.ujf.m2pgi.database.Mappers;

import fr.ujf.m2pgi.database.DTO.PhotoDTO;
import fr.ujf.m2pgi.database.entities.Photo;
import org.modelmapper.PropertyMap;

import javax.ejb.Local;
import javax.ejb.Stateless;

/**
 * Created by FAURE Adrien on 29/10/15.
 */
public class PhotoMapper extends GeneriqueMapperImpl<PhotoDTO, Photo> implements IPhotoMapper {

    public PhotoMapper() {
        super();

        PropertyMap map = new PropertyMap<Photo, PhotoDTO>() {
            @Override
            protected void configure() {
                map().setSellerID(source.getAuthor().getMemberID());
                /*FIXME care here we loose the information of where the file is (the original).
                        We only want to give to the client the name of the public file (the watermarked one) */
            }
        };

        this.modelMapper.addMappings(map);
    }
}




