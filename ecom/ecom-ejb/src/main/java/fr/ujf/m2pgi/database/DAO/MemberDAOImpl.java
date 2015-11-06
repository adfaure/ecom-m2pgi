package fr.ujf.m2pgi.database.DAO;

import java.util.List;

import javax.ejb.Local;
import javax.ejb.Stateless;
import javax.persistence.Query;

import fr.ujf.m2pgi.database.DTO.MemberDTO;
import fr.ujf.m2pgi.database.entities.Member;

/**
 *
 */
@SuppressWarnings("unchecked")
public class MemberDAOImpl extends GeneriqueDAOImpl<Member> implements IMemberDAO {

	@Override 
	public Member findMemberByLogin(String login) {
		Query query = entityManager.createQuery("select m FROM Member m WHERE m.login=:login");
		query.setParameter("login", login);
		List<Member> members = query.getResultList();
		if (members != null && members.size() == 1) {
			return members.get(0);
		}
		return null;
	}
}
