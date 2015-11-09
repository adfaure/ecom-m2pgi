package fr.ujf.m2pgi.database.DAO;

import fr.ujf.m2pgi.database.DTO.SellerDTO;
import fr.ujf.m2pgi.database.entities.Member;
import fr.ujf.m2pgi.database.entities.Seller;

import javax.ejb.Local;
import javax.ejb.Stateless;
import javax.persistence.Query;
import java.util.List;

/**
 * Created by FAURE Adrien on 22/10/15.
 */
public class SellerDAOImpl extends GeneriqueDAOImpl<Seller> implements ISellerDAO {

    /**
     *
     * @param login
     * @return
     */
    public Seller findSellerByLogin(String login) {
        Query query = this.entityManager.createQuery("SELECT s FROM Seller s where s.login=:login");
        query.setParameter("login", login);
        List<Seller> sellers = query.getResultList();
        if(sellers != null && sellers.size() == 1) {
            return sellers.get(0);
        }
        return null;
    }

    @Override
    public boolean createWithExistingMember(Member member, String RIB) {
        Query query = this.entityManager.createNativeQuery("insert into Seller (memberid, rib) values (:id, :rib)");
        query.setParameter("rib", RIB);
        query.setParameter("id", member.getMemberID());
        entityManager.flush();
        return (query.executeUpdate() == 1);
    }
    
    @SuppressWarnings("unchecked")
	@Override
	public Long sellerCount() {
		Query query = entityManager.createQuery("SELECT count(s) FROM Seller s");
	    return (Long) query.getResultList().get(0);
	}
}
