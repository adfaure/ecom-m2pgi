package fr.ujf.m2pgi.database.DAO;

import javax.ejb.Local;

import fr.ujf.m2pgi.database.DTO.MemberDTO;
import fr.ujf.m2pgi.database.entities.Member;

/**
 *
 */
public interface IMemberDAO extends IGeneriqueDAO<Member> {

	/**
	 * allow to find a memeber with his login
	 * @param login
	 * @return
	 */
	Member findMemberByLogin(String login);
	
	Long memberCount();

	Member updateCart(Member member);

}
