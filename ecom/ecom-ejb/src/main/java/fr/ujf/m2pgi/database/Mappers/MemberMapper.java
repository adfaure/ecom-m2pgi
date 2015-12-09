package fr.ujf.m2pgi.database.Mappers;

import fr.ujf.m2pgi.database.DTO.FullPhotoDTO;
import fr.ujf.m2pgi.database.DTO.MemberDTO;
import fr.ujf.m2pgi.database.DTO.PublicPhotoDTO;
import fr.ujf.m2pgi.database.entities.Member;
import fr.ujf.m2pgi.database.entities.Photo;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.Collection;

/**
 * Created by FAURE Adrien on 29/10/15.
 */
public class MemberMapper extends GeneriqueMapperImpl<MemberDTO, Member> implements IMemberMapper {

    @Inject
    private IPublicPhotoMapper publicPhotoMapper;

    @Inject
    private OrderMapper orderMapper;

    @Override
    public Member getentity(MemberDTO dto) {
        Member member            = super.getentity(dto);
        Collection<Photo> photos = new ArrayList<Photo>();
        Collection<PublicPhotoDTO> cart = dto.getCart();
         if(cart != null) {
            for (PublicPhotoDTO fullPhotoDTO : dto.getCart()) {
                photos.add(publicPhotoMapper.getentity(fullPhotoDTO));
            }
            member.setCart(photos);
        }
        return member;
    }
}
