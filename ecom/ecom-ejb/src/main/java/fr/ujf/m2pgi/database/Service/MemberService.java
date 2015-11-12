package fr.ujf.m2pgi.database.Service;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.inject.Inject;

import fr.ujf.m2pgi.database.DAO.IMemberDAO;
import fr.ujf.m2pgi.database.DAO.ISellerDAO;
import fr.ujf.m2pgi.database.DTO.MemberDTO;
import fr.ujf.m2pgi.database.DTO.SellerDTO;
import fr.ujf.m2pgi.database.Mappers.IMemberMapper;
import fr.ujf.m2pgi.database.Mappers.ISellerMapper;
import fr.ujf.m2pgi.database.entities.Member;
import fr.ujf.m2pgi.database.entities.Seller;

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
	private IMemberDAO memberDao;

	/**
	 *
	 */
	@Inject
	private ISellerDAO sellerDAO;

	/**
	 * 
	 * @param member
	 */
	public MemberDTO createMember(MemberDTO member) {
		Member toCreate = memberMapper.getentity(member);
		Member memberEntity = memberDao.create(toCreate);
		MemberDTO res = memberMapper.getDTO(memberEntity);
		return res;
	}
	
	/**
	 * 
	 * @param login
	 * @return
	 */
	public MemberDTO getMemberByLogin(String login) {
		Member memberEntity = memberDao.findMemberByLogin(login);
		if(memberEntity != null)
			return memberMapper.getDTO(memberEntity);
		return null;
	}

	/**
	 *
	 * @param id
	 * @return
	 */
	public MemberDTO getMemberbyId(long id) {
		Member memberEntity = memberDao.find(id);
		if(memberEntity != null)
			return memberMapper.getDTO(memberEntity);
		return null;
	}

	/**
	 *
	 * @param login
	 */
	public SellerDTO findSellerByLogin(String login) {
		Seller sellerEntity = sellerDAO.findSellerByLogin(login);
	    if(sellerEntity != null)
            return sellerMapper.getDTO(sellerEntity);
        return null;
    }

	/**
	 *
	 * @param seller
	 * @return
	 */
	public SellerDTO createSeller(SellerDTO seller) {
		return sellerMapper.getDTO(sellerDAO.create(sellerMapper.getentity(seller)));
	}

	/**
	 *
	 * @param seller
	 * @return
	 */
	public SellerDTO createSellerFromMember(SellerDTO seller) {
		Member member = memberDao.find(seller.getMemberID());
		if(member != null) {
			if(sellerDAO.createWithExistingMember(member, seller.getRIB())) {
				member.setAccountType('S');
				memberDao.update(member);
				return  seller;
			}
		}
		return null;
	}
	
	public Long getMemberCount() {
		Long count = memberDao.getEntityCount();
		return count;
	}
	
	public Long getSellerCount(){
		Long count = sellerDAO.getEntityCount();
		return count;
	}

}
