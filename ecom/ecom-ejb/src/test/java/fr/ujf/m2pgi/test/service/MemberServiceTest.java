package fr.ujf.m2pgi.test.service;

import fr.ujf.m2pgi.EcomException;
import fr.ujf.m2pgi.database.DTO.MemberDTO;
import fr.ujf.m2pgi.database.DTO.SellerInfoDTO;
import fr.ujf.m2pgi.database.Service.IMemberService;
import fr.ujf.m2pgi.test.AbstractTestArquillian;
import org.jboss.arquillian.junit.Arquillian;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.*;
import javax.inject.Inject;

/**
 * Created by FAURE Adrien on 03/12/15.
 */
@RunWith(Arquillian.class)
public class MemberServiceTest extends AbstractTestArquillian {

    @Inject
    private IMemberService memberService;

    @Test
    public void testCreateMember() throws EcomException {
        MemberDTO dto = new MemberDTO();
        dto.setAccountType('M');
        dto.setLogin("test");
        dto.setPassword("pass");
        dto.setEmail("test@mail");
        MemberDTO res = memberService.createMember(dto);
        assertNotNull(res);
    }

    @Test
    public void findMember() throws EcomException {
        MemberDTO dto = new MemberDTO();
        dto.setAccountType('M');
        dto.setLogin("findMember");
        dto.setPassword("findMember");
        dto.setEmail("findMember@mail");
        MemberDTO res = memberService.createMember(dto);
        MemberDTO found = memberService.getMemberbyId(res.getMemberID());
        assertNotNull(found);
        found = memberService.getMemberbyId(10000);

        assertNull(found);
    }

    @Test
    public void createSellerFromMember() throws EcomException {
        MemberDTO dto = new MemberDTO();
        dto.setAccountType('M');
        dto.setLogin("createSellerFromMember");
        dto.setPassword("createSellerFromMember");
        dto.setEmail("createSellerFromMember@mail");
        MemberDTO res = memberService.createMember(dto);

        SellerInfoDTO info = new SellerInfoDTO();
        info.setRIB("yolo");
        res.setSellerInfo(info);
        MemberDTO sellerDTO = memberService.createSellerFromMember(res);
        assertNotNull(sellerDTO.getSellerInfo());
        assertEquals(sellerDTO.getAccountType() , 'S');
    }

    @Test
    public void getMemberByLogin() throws EcomException {
        MemberDTO dto = new MemberDTO();
        dto.setAccountType('M');
        dto.setLogin("getMemberByID");
        dto.setPassword("getMemberByID");
        dto.setEmail("getMemberByID@mail");
        memberService.createMember(dto);
        MemberDTO res = memberService.getMemberByLogin("getMemberByID");
        assertNotNull(res);
        assertEquals(res.getLogin(), "getMemberByID");
    }

    @Test(expected = EcomException.class)
    public void insertExistingMember() throws EcomException {
        MemberDTO dto = new MemberDTO();
        dto.setAccountType('M');
        dto.setLogin("insertExistingMember");
        dto.setPassword("insertExistingMember");
        dto.setEmail("insertExistingMember@mail");
        memberService.createMember(dto);
        memberService.createMember(dto);
    }

}
