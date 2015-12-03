package fr.ujf.m2pgi.database.Service;

import javax.ejb.Stateless;
import javax.inject.Inject;

import fr.ujf.m2pgi.database.DAO.IMemberDAO;
import fr.ujf.m2pgi.database.DTO.MemberDTO;
import fr.ujf.m2pgi.database.DTO.PhotoDTO;
import fr.ujf.m2pgi.database.DTO.SellerInfoDTO;
import fr.ujf.m2pgi.database.Mappers.IMemberMapper;
import fr.ujf.m2pgi.database.Mappers.IPhotoMapper;
import fr.ujf.m2pgi.database.entities.Member;
import fr.ujf.m2pgi.database.entities.Photo;
import fr.ujf.m2pgi.database.entities.SellerInfo;
import fr.ujf.m2pgi.database.entities.SellerPage;
import fr.ujf.m2pgi.Security.IStringDigest;
import fr.ujf.m2pgi.database.entities.Follow;
import fr.ujf.m2pgi.database.DAO.IFollowDAO;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.*;

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
    private IStringDigest stringDigest;


    @Inject
    private IFollowDAO followDao;

    /**
     * @param member
     */
    public MemberDTO createMember(MemberDTO member) {
        member.setPassword(stringDigest.digest(member.getPassword()));
        Member toCreate = memberMapper.getentity(member);
        Member memberEntity = memberDao.create(toCreate);
        MemberDTO res = memberMapper.getDTO(memberEntity);
        return res;
    }
    
    public void deleteMember(Long id) {
        memberDao.delete(id);
        System.out.println("Deleted user: "+id);
        //return res;
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

    public MemberDTO getSellerById(long id) {
        Member member = memberDao.getSellerById(id);
        if(member != null)
            return  memberMapper.getDTO(member);
        return  null;
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

    public MemberDTO createSellerFromMember(MemberDTO memberdto) {
        Member member   = memberDao.find(memberdto.getMemberID());
        SellerInfo info = new SellerInfo();
        info.setId(memberdto.getMemberID());
        info.setRIB(memberdto.getSellerInfo().getRIB());
        member.setSellerInfo(info);
        member.setAccountType('S');
        memberDao.update(member);
        return  memberMapper.getDTO(member);
    }
    
    
    public List<MemberDTO> getAllMembers(){
    	List<MemberDTO> result = new ArrayList<MemberDTO>();

    	for(Member mem: memberDao.getAllMembers()) {
    		result.add(memberMapper.getDTO(mem));
    	}

    	return result;

    }


	public Long getMemberCount() {
		Long count = memberDao.getMemberCount();
		return count;
	}


	/**
	 *
	 * @param memberDTO
	 * @return
     */
	public MemberDTO deleteCart(MemberDTO memberDTO) {
		Member entity = memberMapper.getentity(memberDTO);
		entity.setCart(new ArrayList<Photo>());
		return  memberMapper.getDTO(memberDao.updateCart(entity));
	}

    public MemberDTO updateMember(MemberDTO memberdto) {
        Member entity = memberMapper.getentity(memberdto);
        if(memberdto.getSellerInfo() != null){
        	memberdto.setSellerInfo(null);
        }
        memberDao.update(entity);
        return memberdto;
    }
    
    public MemberDTO updateSeller(MemberDTO memberdto) {
       
        Member member = memberMapper.getentity(memberdto);
        
        SellerPage page = new SellerPage();
        page.setId(memberdto.getMemberID());
        
        SellerInfo info = new SellerInfo();
        info.setId(memberdto.getMemberID());
        info.setRIB(memberdto.getSellerInfo().getRIB());
        info.setPage(page);
        
        member.setSellerInfo(info);
        memberDao.update(member);
        return  memberMapper.getDTO(member);
    }

     public boolean follow(Long followerID, Long followedID)
    {
        Follow follow = followDao.findFollowbyCoupleId(followerID,followedID);
        Member follower = memberDao.find(followerID);
        Member followed = memberDao.find(followedID);
        if(follow == null && follower != null && followed != null && followed.getAccountType() == 'S'){
            follow = new Follow();
            follow.setFollower(follower);
            follow.setFollowed(followed);
            followDao.create(follow);
            return true;
        }
        return false;
    }
    
    public boolean unfollow(Long followerID, Long followedID)
    {
        Follow follow = followDao.findFollowbyCoupleId(followerID,followedID);
        if(follow != null){
            followDao.delete(follow.getId());
            return true;
        }
        return false;
    }
    
    public boolean isFollowedBy(Long followerID, Long memberID)
    {
        if(followDao.findFollowbyCoupleId(followerID, memberID) != null){
                return true;
        }
        return false;
    }

}
