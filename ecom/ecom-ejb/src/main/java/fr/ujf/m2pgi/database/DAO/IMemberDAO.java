package fr.ujf.m2pgi.database.DAO;

import javax.ejb.Local;

import fr.ujf.m2pgi.database.DTO.MemberDTO;
import fr.ujf.m2pgi.database.entities.Member;

/**
 *
 */
@Local
public interface IMemberDAO extends IGeneriqueDAO<Member> {

	/**
	 * allow to find a memeber with his login
	 * @param login
	 * @return
	 */
	Member findMemberByLogin(String login);
	
	/**
	 * 
	 * @param member
	 * @return
	 */
	MemberDTO getMemberDTO(Member member);
	
	/**
	 * 
	 * @param member
	 * @return
	 */
	Member getMember(MemberDTO member);
	
}
