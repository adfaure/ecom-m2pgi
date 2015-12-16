package fr.ujf.m2pgi.database.Service;

import fr.ujf.m2pgi.EcomException;
import fr.ujf.m2pgi.database.DTO.MemberDTO;
import fr.ujf.m2pgi.database.DTO.PublicPhotoDTO;
import fr.ujf.m2pgi.database.DTO.PublicSeller;

import java.util.List;

/**
 * Created by dadou on 03/12/15.
 */
public interface IMemberService {
    MemberDTO createMember(MemberDTO member) throws EcomException;

    void deleteMember(Long id);

    MemberDTO getMemberByLogin(String login);

    MemberDTO getMemberByEmail(String email);

    Boolean isExistingMemberByLogin(String login);

    Boolean isExistingMemberByEmail(String email);

    MemberDTO getSellerById(long id);

    PublicSeller getPublicSellerById(long id);

    MemberDTO getMemberbyId(long id);

    MemberDTO addToCart(MemberDTO member, PublicPhotoDTO publicPhotoDTO);

    MemberDTO removeToCart(MemberDTO member, PublicPhotoDTO publicPhotoDTO);

    MemberDTO createSellerFromMember(MemberDTO memberdto);

    List<MemberDTO> getAllMembers();

    Long getMemberCount();

    MemberDTO deleteCart(MemberDTO memberDTO);

    MemberDTO updateMember(MemberDTO memberdto) throws EcomException;

    MemberDTO updateSeller(MemberDTO memberdto);

    MemberDTO changePassword(MemberDTO member, String newPSW);

    List<MemberDTO> getFollowedSellersBy(long followerID);

    Long getSellerFollowerCount(Long sellerID);

    boolean follow(Long followerID, Long followedID);

    boolean unfollow(Long followerID, Long followedID);

    boolean isFollowedBy(Long followerID, Long memberID);

}
