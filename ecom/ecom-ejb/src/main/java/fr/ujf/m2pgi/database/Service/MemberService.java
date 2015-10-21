package fr.ujf.m2pgi.database.Service;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import fr.ujf.m2pgi.database.DAO.IMemberDAO;
import fr.ujf.m2pgi.database.DAO.MemberDAOImpl;
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
	public void createMember(MemberDTO member) {
		memberDao.create(memberDao.getMember(member));
	}
	
	/**
	 * 
	 * @param login
	 * @return
	 */
	public MemberDTO getMemberByLogin(String login) {
		Member memberEntity = memberDao.findMemberByLogin(login);
		if(memberEntity != null)
			return memberDao.getMemberrDTO(memberEntity);
		return null;
	}
	
	public MemberDTO getMemberbyId(long id) {
		Member memberEntity = memberDao.find(id);
		if(memberEntity != null)
			return memberDao.getMemberrDTO(memberEntity);
		return null;
	}
}
