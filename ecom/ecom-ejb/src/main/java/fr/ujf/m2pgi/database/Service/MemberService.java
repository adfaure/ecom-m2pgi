package fr.ujf.m2pgi.database.Service;

import fr.ujf.m2pgi.EcomException;
import fr.ujf.m2pgi.Security.IStringDigest;
import fr.ujf.m2pgi.database.DAO.IFollowDAO;
import fr.ujf.m2pgi.database.DAO.IMemberDAO;
import fr.ujf.m2pgi.database.DAO.IOrderDAO;
import fr.ujf.m2pgi.database.DAO.IPhotoDAO;
import fr.ujf.m2pgi.database.DTO.*;
import fr.ujf.m2pgi.database.Mappers.IMemberMapper;
import fr.ujf.m2pgi.database.Mappers.IPublicPhotoMapper;
import fr.ujf.m2pgi.database.Mappers.MapperWrapper;
import fr.ujf.m2pgi.database.entities.*;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

/**
 *
 */
@Stateless
public class MemberService implements IMemberService {

    @Inject
    private MapperWrapper mapperWrapper;

    /**
     *
     */
    @Inject
    private IMemberMapper memberMapper;

    /**
     *
     */
    @Inject
    private IPublicPhotoMapper publicPhotoMapper;

    /**
     *
     */
    @Inject
    private IMemberDAO memberDao;

    /**
     *
     */
    @Inject
    private IOrderDAO orderDAO;

    /**
     *
     */
    @Inject
    private IStringDigest stringDigest;

    /**
     *
     */
    @Inject
    private IPhotoDAO photoDAO;

    /**
     *
     */
    @Inject
    private IFollowDAO followDao;

    /**
     *
     */
    @Inject
    private IPhotoService photoService;

    /**
     * @param member
     */
    @Override
    public MemberDTO createMember(MemberDTO member) throws EcomException {
        Member m = memberDao.findMemberByLogin(member.getLogin());
        if(m != null) throw  new EcomException("Login already in use");
        member.setPassword(stringDigest.digest(member.getPassword()));
        Member toCreate = memberMapper.getentity(member);
        Member memberEntity = memberDao.create(toCreate);
        MemberDTO res = memberMapper.getDTO(memberEntity);
        return res;
    }

    @Override
    public void deleteMember(Long id) {
        memberDao.delete(id);
        List<ManagePhotoDTO> photos = photoDAO.getUserPhotos(id);
        if(photos != null) {
            for(ManagePhotoDTO photo : photos)
                photoService.deletePhoto(photo.getPhotoID());
        }
        //return res;
    }

    /**
     * @param login
     * @return
     */
    @Override
    public MemberDTO getMemberByLogin(String login,boolean active) {
        Member memberEntity=null;
        if(active) {
            memberEntity = memberDao.findActiveMemberByLogin(login);
        } else {
            memberEntity = memberDao.findMemberByLogin(login);
        }
        if (memberEntity != null)
            return memberMapper.getDTO(memberEntity);
        return null;
    }

    /**
     * @param
     * @return
     */
    @Override
    public MemberDTO getMemberByEmail(String email, boolean active) {
        Member memberEntity=null;
        if(active) {
            memberEntity = memberDao.findActiveMemberByEmail(email);
        } else {
            memberEntity = memberDao.findMemberByEmail(email);
        }
        if (memberEntity != null)
            return memberMapper.getDTO(memberEntity);
        return null;
    }

    /**
     * @param login
     * @return
     */
    @Override
    public Boolean isExistingMemberByLogin(String login) {
        Member memberEntity = memberDao.findMemberByLogin(login);
        if (memberEntity == null)
            return false;
        return true;
    }

    /**
     * @param email
     * @return
     */
    @Override
    public Boolean isExistingMemberByEmail(String email) {
        Member memberEntity = memberDao.findMemberByEmail(email);
        if (memberEntity == null)
            return false;
        return true;
    }

    @Override
    public MemberDTO getSellerById(long id) {
        Member member = memberDao.getSellerById(id);
        if(member != null)
            return  memberMapper.getDTO(member);
        return  null;
    }

    @Override
    public PublicSeller getPublicSellerById(long id) {
        Member member = memberDao.getSellerById(id);
        if(member != null)
            return  mapperWrapper.getMapper().map(member, PublicSeller.class);
        return  null;
    }



    /**
     * @param id
     * @return
     */
    @Override
    public MemberDTO getMemberbyId(long id) {
        Member memberEntity = memberDao.find(id);
        if (memberEntity != null)
            return memberMapper.getDTO(memberEntity);
        return null;
    }

    /**
     * @param member
     * @param publicPhotoDTO
     */
    @Override
    public MemberDTO addToCart(MemberDTO member, PublicPhotoDTO publicPhotoDTO) {
        Member attachedEntity = memberDao.find(member.getMemberID());
        Collection<Photo> cart = attachedEntity.getCart();

        if (cart == null) {
            System.err.println("no cart --- creating");
            cart = new ArrayList<Photo>();
        }
        if(publicPhotoDTO.getSellerID() == member.getMemberID()) {
            System.err.println("cannot add own photo"); // on verify que le seller n'ajoute pas ses propres photo
            return member;
        }
        boolean exist = false;
        for (Photo photo : cart) {
            if (photo.getPhotoID() == publicPhotoDTO.getPhotoID()) { // on a joute pas deux fois une photo dans le cadi
                exist = true;
                break;
            }
        }
        boolean bought = orderDAO.isPhotoBought(member.getMemberID(), publicPhotoDTO.getPhotoID());
        if (!exist && !bought) {
            cart.add(publicPhotoMapper.getentity(publicPhotoDTO));
            attachedEntity.setCart(cart);
            return memberMapper.getDTO(memberDao.updateCart(attachedEntity));
        }
        return memberMapper.getDTO(attachedEntity);
    }

    /**
     * @param member
     * @param publicPhotoDTO
     */
    @Override
    public MemberDTO removeToCart(MemberDTO member, PublicPhotoDTO publicPhotoDTO) {
        Member attachedEntity = memberDao.find(member.getMemberID());
        Collection<Photo> cart = attachedEntity.getCart();

        if (cart == null) {
            return member;
        }

        for (Iterator<Photo> iterator = cart.iterator(); iterator.hasNext(); ) {
            Photo currentPhoto = iterator.next();
            if (currentPhoto.getPhotoID() == publicPhotoDTO.getPhotoID()) {
                iterator.remove();
                break;
            }
        }

        return memberMapper.getDTO(memberDao.updateCart(attachedEntity));
    }

    @Override
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


    @Override
    public List<AdminMemberDTO> getAllMembers(){
    	List<AdminMemberDTO> result = new ArrayList<AdminMemberDTO>();

    	for(Member mem: memberDao.getAllMembers()) {
    		result.add(mapperWrapper.getMapper().map(mem, AdminMemberDTO.class));
    	}
    	return result;
    }


    @Override
    public List<MemberDTO> getFollowedSellersBy(long followerID){
    	List<MemberDTO> result = new ArrayList<MemberDTO>();

    	for(Member mem: memberDao.getSellersFollowedBy(followerID)) {
    		result.add(memberMapper.getDTO(mem));
    	}

    	return result;

    }

    @Override
    public Long getSellerFollowerCount(Long sellerID) {
      return memberDao.getSellerFollowerCount(sellerID);
    }


	@Override
    public Long getMemberCount() {
		Long count = memberDao.getMemberCount();
		return count;
	}


	/**
	 *
	 * @param memberDTO
	 * @return
     */
	@Override
    public MemberDTO deleteCart(MemberDTO memberDTO) {
		Member entity = memberMapper.getentity(memberDTO);
		entity.setCart(new ArrayList<Photo>());
		return  memberMapper.getDTO(memberDao.updateCart(entity));
	}

    @Override
    public MemberDTO updateMember(MemberDTO memberdto) throws EcomException{
        Member entity = memberMapper.getentity(memberdto);

        //It means the person wants to change his/her email
        Member mem = memberDao.findMemberByLogin(memberdto.getLogin());
        if(!mem.getEmail().equalsIgnoreCase(memberdto.getEmail())){
        	Member m = memberDao.findMemberByEmail(memberdto.getEmail());
            if(m != null) throw  new EcomException("Email already in use");
        }

        if(memberdto.getSellerInfo() != null){
        	memberdto.setSellerInfo(null);
        }

        System.out.println("This will be executed");
        memberDao.update(entity);
        return memberdto;
    }

    @Override
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


    public MemberDTO changePassword(MemberDTO member, String newPSW){

    	MemberDTO result = null;
    	Member memberEntity = memberDao.find(member.getMemberID());

        if (memberEntity != null)
        {
        	String actualPSW = memberEntity.getPassword();
        	String pswTransformed = stringDigest.digest(member.getPassword());
        	if(actualPSW.equalsIgnoreCase(pswTransformed)){
        		memberEntity.setPassword(stringDigest.digest(newPSW));
        		Member memberEnt = memberDao.update(memberEntity);
        	    result = memberMapper.getDTO(memberEnt);
        	}
        }
        return result;
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
