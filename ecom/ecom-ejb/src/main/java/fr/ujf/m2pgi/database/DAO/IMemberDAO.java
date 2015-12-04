package fr.ujf.m2pgi.database.DAO;

import javax.ejb.Local;

import fr.ujf.m2pgi.database.DTO.MemberDTO;
import fr.ujf.m2pgi.database.entities.Member;
import java.util.List;

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
	
	Member updateCart(Member member);

	List<Member> getAllMembers();
	
	List<Member> getTopSellers();

	Member getSellerById(long id);
	
	public Long getSellerCount();
	
	public Long getMemberCount();

}
