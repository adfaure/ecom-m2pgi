package fr.ujf.m2pgi.database.DAO;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.ejb.Local;
import javax.ejb.Stateless;
import javax.persistence.Query;

import fr.ujf.m2pgi.database.DTO.MemberDTO;
import fr.ujf.m2pgi.database.entities.*;

/**
 *
 */
@SuppressWarnings("unchecked")
public class MemberDAOImpl extends GeneriqueDAOImpl<Member> implements IMemberDAO {

	@Override
	public Member create(Member entity) {
		MemberIdGenerator id = new MemberIdGenerator();
		this.entityManager.persist(id);
		entity.setMemberID(id.getSequence());
		SellerInfo sellerInfo = entity.getSellerInfo();
		if(sellerInfo != null) {
			sellerInfo.setId(id.getSequence());
		}
		return super.create(entity);
	}

	@Override
	public Member findMemberByLogin(String login) {
		Query query = entityManager.createQuery("select m FROM Member m WHERE m.login=:login");
		query.setParameter("login", login);
		List<Member> members = query.getResultList();
		if (members != null && members.size() == 1) {
			Member member = members.get(0);
			return member ;
		}
		return null;
	}

	//https://forum.hibernate.org/viewtopic.php?p=2404391
	public Member updateCart(Member member) {
		Member attachedMember  = entityManager.getReference(Member.class, member.getMemberID());
		Collection<Photo> attachedCart = new ArrayList<Photo>();
		for(Photo photo : member.getCart()) {
			attachedCart .add(this.entityManager.getReference(Photo.class, photo.getPhotoID()));
		}
		attachedMember.setCart(attachedCart );
		return super.update(attachedMember);
	}
	
	public List<Member> getAllMembers(){
		Query query = entityManager.createQuery("SELECT m FROM Member m ORDER BY m.memberID");
		List<Member> members = query.getResultList();
		return members;
	}

	@Override
	public Member getSellerById(long id) {
		Member member = super.find(id);
		if(member == null || member.getSellerInfo() == null) return null;
		return member;
	}
	
	public Long getSellerCount(){
		String q = "SELECT count(e) FROM Member e where e.accountType = 'S'";
		Query query = entityManager.createQuery(q);
		return (Long) query.getSingleResult();
	}
	
	public Long getMemberCount(){
		String q = "SELECT count(e) FROM Member e where e.accountType <> 'A'";
		Query query = entityManager.createQuery(q);
		return (Long) query.getSingleResult();
	}

	public List<Member> getTopSellers() {
		String q = "Select m FROM Member m where m.memberID in (Select p.author FROM Order o JOIN o.orderedPhotos p GROUP BY p.author ORDER BY count(p.author) DESC)" ;
		Query query = entityManager.createQuery(q).setMaxResults(10);
		List<Member> members = query.getResultList();
		return members;
	}
}

