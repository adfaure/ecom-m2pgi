package fr.ujf.m2pgi.database.DAO;

import fr.ujf.m2pgi.database.entities.Member;

import fr.ujf.m2pgi.database.entities.Seller;

import javax.ejb.Local;

/**
 * Created by FAURE Adrien on 22/10/15.
 */
public interface ISellerDAO extends IGeneriqueDAO<Seller> {

    /**
     *
     * @param login
     * @return
     */
    Seller findSellerByLogin(String login);

    /**
     *
     * @param member
     * @param RIB
     * @return
     */
    boolean createWithExistingMember(Member member, String RIB);
}
