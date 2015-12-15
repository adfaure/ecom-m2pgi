package fr.ujf.m2pgi.database.Mappers;

import fr.ujf.m2pgi.database.DTO.FullPhotoDTO;
import fr.ujf.m2pgi.database.DTO.MemberDTO;
import fr.ujf.m2pgi.database.DTO.OrderDTO;
import fr.ujf.m2pgi.database.DTO.PublicPhotoDTO;
import fr.ujf.m2pgi.database.entities.Member;
import fr.ujf.m2pgi.database.entities.Order;
import fr.ujf.m2pgi.database.entities.Photo;
import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;

import javax.annotation.PostConstruct;
import javax.ejb.Singleton;
import javax.ejb.Startup;

/**
 * Created by FAURE Adrien on 14/11/15.
 */
@Singleton
@Startup
public class MapperWrapper {

    private ModelMapper modelMapper;

    public MapperWrapper() {
        modelMapper = new ModelMapper();
    }

    @PostConstruct
    public void init() {
        this.modelMapper.addMappings(new PropertyMap<Order, OrderDTO>() {
            @Override
            protected void configure() {
                map().setMemberID(source.getMember().getMemberID());
            }
        });

        this.modelMapper.addMappings(new PropertyMap<Photo, PublicPhotoDTO>() {
            @Override
            protected void configure() {
                map().setSellerID(source.getAuthor().getMemberID());
                //skip().setPrivateLocation(null);
            }
        });

        this.modelMapper.addMappings(new PropertyMap<Photo, FullPhotoDTO>() {
            @Override
            protected void configure() {
                map().setSellerID(source.getAuthor().getMemberID());
                //skip().setPrivateLocation(null);
            }
        });



        this.modelMapper.addMappings(new PropertyMap<Member, MemberDTO >() {
            @Override
            protected void configure() {
                skip().setOrderedPhotos(null);
            }
        });
    }

    public ModelMapper getMapper() {
        return modelMapper;
    }
}
