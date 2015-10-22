package fr.ujf.m2pgi.database.Service;

import fr.ujf.m2pgi.database.DAO.ISellerDAO;
import fr.ujf.m2pgi.database.entities.Seller;

import javax.ejb.EJB;
import javax.ejb.Stateless;

/**
 * Created by FAURE Adrien on 22/10/15.
 */
@Stateless
public class SellerService {

    /**
     *
     */
    @EJB
    private ISellerDAO sellerDAO;

    /**
     *
     * @param login
     */
    public Seller findSellerByLogin(String login) {
        return sellerDAO.findSellerByLogin(login);
    }

}
