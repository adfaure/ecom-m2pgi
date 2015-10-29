package fr.ujf.m2pgi.database.Mappers;

import fr.ujf.m2pgi.database.DTO.SellerDTO;
import fr.ujf.m2pgi.database.entities.Seller;

import javax.ejb.Stateless;

/**
 * Created by FAURE Adrien on 30/10/15.
 */
@Stateless
public class SellerMapper extends GeneriqueMapperImpl<SellerDTO, Seller> implements ISellerMapper {
}
