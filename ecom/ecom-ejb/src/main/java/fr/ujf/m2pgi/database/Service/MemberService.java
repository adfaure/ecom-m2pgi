package fr.ujf.m2pgi.database.Service;

import javax.ejb.Stateless;
import javax.inject.Inject;

import fr.ujf.m2pgi.database.DAO.IMemberDAO;
import fr.ujf.m2pgi.database.DAO.ISellerDAO;
import fr.ujf.m2pgi.database.DTO.MemberDTO;
import fr.ujf.m2pgi.database.DTO.PhotoDTO;
import fr.ujf.m2pgi.database.DTO.SellerDTO;
import fr.ujf.m2pgi.database.Mappers.IMemberMapper;
import fr.ujf.m2pgi.database.Mappers.IPhotoMapper;
import fr.ujf.m2pgi.database.Mappers.ISellerMapper;
import fr.ujf.m2pgi.database.entities.Member;
import fr.ujf.m2pgi.database.entities.Photo;
import fr.ujf.m2pgi.database.entities.Seller;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

/**
 *
 */
@Stateless
public class MemberService {

    /**
     *
     */
    @Inject
    private IMemberMapper memberMapper;

    /**
     *
     */
    @Inject
    private ISellerMapper sellerMapper;

    /**
     *
     */
    @Inject
    private IPhotoMapper photoMapper;
    /**
     *
     */
    @Inject
    private IMemberDAO memberDao;

    /**
     *
     */
    @Inject
    private ISellerDAO sellerDAO;

    /**
     * @param member
     */
    public MemberDTO createMember(MemberDTO member) {
        Member toCreate = memberMapper.getentity(member);
        Member memberEntity = memberDao.create(toCreate);
        MemberDTO res = memberMapper.getDTO(memberEntity);
        return res;
    }

    /**
     * @param login
     * @return
     */
    public MemberDTO getMemberByLogin(String login) {
        Member memberEntity = memberDao.findMemberByLogin(login);
        if (memberEntity != null)
            return memberMapper.getDTO(memberEntity);
        return null;
    }

    /**
     * @param id
     * @return
     */
    public MemberDTO getMemberbyId(long id) {
        Member memberEntity = memberDao.find(id);
        if (memberEntity != null)
            return memberMapper.getDTO(memberEntity);
        return null;
    }

    /**
     * @param login
     */
    public SellerDTO findSellerByLogin(String login) {
        Seller sellerEntity = sellerDAO.findSellerByLogin(login);
        if (sellerEntity != null)
            return sellerMapper.getDTO(sellerEntity);
        return null;
    }

    /**
     * @param seller
     * @return
     */
    public SellerDTO createSeller(SellerDTO seller) {
        return sellerMapper.getDTO(sellerDAO.create(sellerMapper.getentity(seller)));
    }

    /**
     * @param seller
     * @return
     */
    public SellerDTO createSellerFromMember(SellerDTO seller) {
        Member member = memberDao.find(seller.getMemberID(), true);
        Seller sellerEntity = sellerMapper.getentity(seller);
        memberDao.delete(seller.getMemberID());
        sellerEntity.setAccountType('S');
        sellerEntity.setPassword(member.getPassword());
        sellerDAO.create(sellerEntity);
        System.err.println("no member at this id, cannot upgrade"); //FIXME throw custom exeption ?
        return seller;
    }

    /**
     * @return
     */
    public Long getMemberCount() {
        Long count = memberDao.memberCount();
        return count;
    }

    /**
     * @return
     */
    public Long getSellerCount() {
        Long count = sellerDAO.sellerCount();
        return count;
    }

    /**
     * @param member
     * @param photoDTO
     */
    public MemberDTO addToCart(MemberDTO member, PhotoDTO photoDTO) {
        Member attachedEntity = memberDao.find(member.getMemberID());
        Collection<Photo> cart = attachedEntity.getCart();

        if (cart == null) {
            System.err.println("no cart --- creating");
            cart = new ArrayList<Photo>();
        }
        boolean exist = false;
        for (Photo photo : cart) {
            if (photo.getPhotoID() == photoDTO.getPhotoId()) {
                exist = true;
                break;
            }
        }
        if (!exist) {
            cart.add(photoMapper.getentity(photoDTO));
            attachedEntity.setCart(cart);
            return memberMapper.getDTO(memberDao.updateCart(attachedEntity));
        }
        return member;
    }

    /**
     * @param member
     * @param photoDTO
     */
    public MemberDTO removeToCart(MemberDTO member, PhotoDTO photoDTO) {
        Member attachedEntity = memberDao.find(member.getMemberID());
        Collection<Photo> cart = attachedEntity.getCart();

        if (cart == null) {
            return member;
        }

        for (Iterator<Photo> iterator = cart.iterator(); iterator.hasNext(); ) {
            Photo currentPhoto = iterator.next();
            if (currentPhoto.getPhotoID() == photoDTO.getPhotoId()) {
                iterator.remove();
                break;
            }
        }

        return memberMapper.getDTO(memberDao.updateCart(attachedEntity));
    }

    /**
     * @param memberDTO
     * @return
     */
    public MemberDTO deleteCart(MemberDTO memberDTO) {
        Member entity = memberMapper.getentity(memberDTO);
        entity.setCart(new ArrayList<Photo>());
        return memberMapper.getDTO(memberDao.updateCart(entity));
    }
}
