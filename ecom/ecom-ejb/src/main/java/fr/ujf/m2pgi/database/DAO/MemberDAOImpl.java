package fr.ujf.m2pgi.database.DAO;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.Query;

import fr.ujf.m2pgi.database.DTO.MemberDTO;
import fr.ujf.m2pgi.database.entities.Member;

/**
 *
 */
@Stateless 
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

	@Override
	public MemberDTO getMemberDTO(Member member) {
		MemberDTO mDTO = new MemberDTO();
		mDTO.setEmail(member.getEmail());
		mDTO.setFirstName(member.getFirstName());
		mDTO.setLastName(member.getLastName());
		mDTO.setAccountType(member.getAccountType());
		mDTO.setLogin(member.getLogin());
		mDTO.setPassword(member.getPassword());
		mDTO.setMemberID(member.getMemberID());
		return mDTO;
	}

	@Override
	public Member getMember(MemberDTO member) {
		Member memberEntity =  new Member();
		memberEntity.setAccountType(member.getAccountType());
		memberEntity.setEmail(member.getEmail());
		memberEntity.setFirstName(member.getFirstName());
		memberEntity.setLastName(member.getLastName());
		memberEntity.setLogin(member.getLogin());
		memberEntity.setPassword(member.getPassword());
		return memberEntity;
	}
	
}
