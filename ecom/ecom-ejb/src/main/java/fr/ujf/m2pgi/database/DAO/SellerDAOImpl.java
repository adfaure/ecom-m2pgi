package fr.ujf.m2pgi.database.DAO;

import fr.ujf.m2pgi.database.DTO.SellerDTO;
import fr.ujf.m2pgi.database.entities.Seller;

import javax.ejb.Stateless;
import javax.persistence.Query;
import java.util.List;

/**
 * Created by FAURE Adrien on 22/10/15.
 */
@Stateless
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
}
