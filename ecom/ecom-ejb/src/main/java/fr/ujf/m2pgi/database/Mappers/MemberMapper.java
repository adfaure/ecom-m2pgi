package fr.ujf.m2pgi.database.Mappers;

import fr.ujf.m2pgi.database.DTO.MemberDTO;
import fr.ujf.m2pgi.database.DTO.PhotoDTO;
import fr.ujf.m2pgi.database.entities.Member;
import fr.ujf.m2pgi.database.entities.Photo;
import org.modelmapper.Converter;
import org.modelmapper.PropertyMap;
import org.modelmapper.spi.MappingContext;

import javax.ejb.Local;
import javax.ejb.Stateless;
import javax.inject.Inject;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Created by FAURE Adrien on 29/10/15.
 */
public class MemberMapper extends GeneriqueMapperImpl<MemberDTO, Member> implements IMemberMapper {

    @Inject
    private PhotoMapper photoMapper;

    /**
     *
     */
    public MemberMapper() {
        super();

        final PropertyMap map = new PropertyMap<MemberDTO, Member>() {
            @Override
            protected void configure() {
                skip().setCart(null);
            }
        };
        this.modelMapper.addMappings(map);
    }

    @Override
    public Member getentity(MemberDTO dto) {
        Member member            = super.getentity(dto);
        Collection<Photo> photos = new ArrayList<Photo>();
        Collection<PhotoDTO> cart = dto.getCart();
        if(cart != null) {
            for (PhotoDTO photoDTO : dto.getCart()) {
                photos.add(photoMapper.getentity(photoDTO));
            }
            member.setCart(photos);
        }
        return member;
    }
}
