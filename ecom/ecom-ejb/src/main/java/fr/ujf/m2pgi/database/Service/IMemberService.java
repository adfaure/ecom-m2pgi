package fr.ujf.m2pgi.database.Service;

import fr.ujf.m2pgi.EcomException;
import fr.ujf.m2pgi.database.DTO.MemberDTO;
import fr.ujf.m2pgi.database.DTO.PhotoDTO;

import java.util.List;

/**
 * Created by dadou on 03/12/15.
 */
public interface IMemberService {
    MemberDTO createMember(MemberDTO member) throws EcomException;

    void deleteMember(Long id);

    MemberDTO getMemberByLogin(String login);

    MemberDTO getSellerById(long id);

    MemberDTO getMemberbyId(long id);

    MemberDTO addToCart(MemberDTO member, PhotoDTO photoDTO);

    MemberDTO removeToCart(MemberDTO member, PhotoDTO photoDTO);

    MemberDTO createSellerFromMember(MemberDTO memberdto);

    List<MemberDTO> getAllMembers();

    Long getMemberCount();

    MemberDTO deleteCart(MemberDTO memberDTO);

    MemberDTO updateMember(MemberDTO memberdto);

    MemberDTO updateSeller(MemberDTO memberdto);
    
    MemberDTO changePassword(MemberDTO member, String newPSW);
    
}
