package fr.ujf.m2pgi.database.Service;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import fr.ujf.m2pgi.database.DAO.IMemberDAO;
import fr.ujf.m2pgi.database.DAO.ISellerDAO;
import fr.ujf.m2pgi.database.DTO.MemberDTO;
import fr.ujf.m2pgi.database.DTO.SellerDTO;
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
	@EJB
	private IMemberDAO memberDao;

	/**
	 *
	 */
	@EJB
	private ISellerDAO sellerDAO;

	/**
	 * 
	 * @param member
	 */
	public MemberDTO createMember(MemberDTO member) {
		MemberDTO res = memberDao.getMemberDTO(memberDao.create(memberDao.getMember(member)));
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
			return memberDao.getMemberDTO(memberEntity);
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
			return memberDao.getMemberDTO(memberEntity);
		return null;
	}

	/**
	 *
	 * @param login
	 */
	public SellerDTO findSellerByLogin(String login) {
		Seller sellerEntity = sellerDAO.findSellerByLogin(login);
	    if(sellerEntity != null)
            return sellerDAO.getSellerDTO(sellerEntity);
        return null;
    }

	/**
	 *
	 * @param seller
	 * @return
	 */
	public SellerDTO createSeller(SellerDTO seller) {
		return sellerDAO.getSellerDTO(sellerDAO.create(sellerDAO.getSeller(seller)));
	}

}
