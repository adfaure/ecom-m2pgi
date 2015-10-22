package fr.ujf.m2pgi.database.DAO;

import fr.ujf.m2pgi.database.DTO.SellerDTO;
import fr.ujf.m2pgi.database.entities.Seller;

import javax.ejb.Local;

/**
 * Created by FAURE Adrien on 22/10/15.
 */
@Local
public interface ISellerDAO extends IGeneriqueDAO<Seller> {

    /**
     *
     * @param login
     * @return
     */
    public Seller findSellerByLogin(String login);

    /**
     *
     * @param sellerDTO
     * @return
     */
    Seller getSeller(SellerDTO sellerDTO);

    /**
     *
     * @param seller
     * @return
     */
    SellerDTO getSellerDTO(Seller seller);

}
