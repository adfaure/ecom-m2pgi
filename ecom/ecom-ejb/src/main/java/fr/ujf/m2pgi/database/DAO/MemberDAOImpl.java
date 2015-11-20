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
		Query query = entityManager.createQuery("Select m From Member m");
		List<Member> members = query.getResultList();
		return members;
	}

	@Override
	public Member getSellerById(long id) {
		Member member = super.find(id);
		if(member == null) return null;
		return member;
	}
}

