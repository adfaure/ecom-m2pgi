package fr.ujf.m2pgi.database.Service;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import fr.ujf.m2pgi.database.DAO.IMemberDAO;
import fr.ujf.m2pgi.database.DTO.MemberDTO;
import fr.ujf.m2pgi.database.entities.Member;

@Stateless
public class MemberService {

	@EJB
	IMemberDAO memberDao;
	
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
	
	public MemberDTO getMemberbyId(long id) {
		Member memberEntity = memberDao.find(id);
		if(memberEntity != null)
			return memberDao.getMemberDTO(memberEntity);
		return null;
	}
}
