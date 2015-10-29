package fr.ujf.m2pgi.database.Mappers;

import fr.ujf.m2pgi.database.DTO.MemberDTO;
import fr.ujf.m2pgi.database.entities.Member;

import javax.ejb.Local;

/**
 * Created by FAURE Adrien on 29/10/15.
 */
@Local
public interface IMemberMapper extends IGeneriqueMapper<MemberDTO, Member> {

}
