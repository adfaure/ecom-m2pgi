package fr.ujf.m2pgi.database.Mappers;

import fr.ujf.m2pgi.database.DTO.MemberDTO;
import fr.ujf.m2pgi.database.entities.Member;

import javax.ejb.Local;
import javax.ejb.Stateless;

/**
 * Created by FAURE Adrien on 29/10/15.
 */
public class MemberMapper extends GeneriqueMapperImpl<MemberDTO, Member> implements IMemberMapper {
}
