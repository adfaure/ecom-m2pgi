package fr.ujf.m2pgi.test.database.dao;

import fr.ujf.m2pgi.database.DAO.IMemberDAO;
import fr.ujf.m2pgi.database.entities.Member;
import fr.ujf.m2pgi.database.entities.SellerInfo;
import org.jboss.arquillian.junit.Arquillian;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import javax.inject.Inject;
import static org.junit.Assert.*;

/**
 * Created by FAURE Adrien on 07/11/15.
 */
@RunWith(Arquillian.class)
public class TestMemberDao extends AbstactDaoTest {

    /**
     *
     */
    @Inject
    private IMemberDAO memberDao;

    @Before
    public void preparePersistenceTest() throws Exception {
        clearData();
        insertData();
        startTransaction();
    }

    @After
    public void commitTransaction() throws Exception {
        utx.commit();
    }

    public void clearData() throws Exception {
        utx.begin();
        em.joinTransaction();
        System.out.println("Dumping old records...");
        em.createQuery("delete from Member").executeUpdate();
        utx.commit();
    }

    public void insertData() throws Exception {
        utx.begin();
        em.joinTransaction();
        System.out.println("Inserting records...");
        Member member = new Member();
        member.setLogin("Dadou");
        member.setFirstName("Dadou");
        member.setPassword("Dadou");
        member.setEmail("dadou@dadou");
        member.setAccountType('M');
        memberDao.create(member);
        Member seller = new Member();
        seller.setLogin("seller");
        seller.setFirstName("seller");
        seller.setPassword("seller");
        seller.setEmail("seller@seller");
        member.setAccountType('S');
        SellerInfo info = new SellerInfo();
        info.setRIB("RIB");
        seller.setSellerInfo(info);
        memberDao.create(seller);
        utx.commit();
        // clear the persistence context (first-level cache)
        em.clear();
    }



    @Test
    public void testCreateMember() throws Exception {
        Member member = new Member();
        member.setLogin("Marwen");
        member.setFirstName("Marwen");
        member.setPassword("Marwen");
        member.setEmail("marwen@marwen");
        Member created = memberDao.create(member);
        assertTrue(created.getLogin().equals(member.getLogin()));
    }

    @Test
    public void createAndFind() throws Exception {
        long id;
        Member member = new Member();
        member.setLogin("Marwen");
        member.setFirstName("Marwen");
        member.setPassword("Marwen");
        member.setEmail("marwen@marwen");
        Member created = memberDao.create(member);
        id = created.getMemberID();

        Member find = memberDao.find(id);

        assertTrue(find != null);
    }

    @Test
    public void findByLoginTest() {
        Member m = memberDao.findMemberByLogin("Dadou");
        assertEquals(m.getLogin(), "Dadou");
        m = memberDao.findMemberByLogin("unexist");
        assertTrue( m == null);
    }

    @Test
    public void getMemberCount() throws Exception {

        for(int i = 0 ; i < 100; i++) {
            Member member = new Member();
            member.setLogin("lePap" + i);
            member.setFirstName("lePap"+i);
            member.setPassword("lePap"+i);
            member.setEmail("lePap@lePap"+i);
            memberDao.create(member);
        }
        utx.commit();
        startTransaction();

        long count = memberDao.getMemberCount();
        assertTrue(count == 2 + 100);

        utx.commit();
        clearData();
        startTransaction();
        count = memberDao.getMemberCount();
        assertTrue(count == 0);
    }

    @Test
    public void updateMember() throws Exception {
        Member m = memberDao.findMemberByLogin("Dadou");
        m.setEmail("newMail");
        memberDao.update(m);
        utx.commit();
        startTransaction();
        Member mupdated = memberDao.findMemberByLogin("Dadou");
        assertTrue(mupdated != null);
        assertEquals(m.getEmail(), "newMail");
    }

    @Test
    public void findSelelrById() {
        Member m = memberDao.findMemberByLogin("seller");
        Member s = memberDao.getSellerById(m.getMemberID());
        assertNotNull(s);

        Member m2 = memberDao.findMemberByLogin("Dadou");
        s = memberDao.getSellerById(m2.getMemberID());
        assertNull(s);
    }

    @Test
    public void sellerCount() throws Exception {
        long numberOfSellers = 1;

        for(int i = 0 ; i < 100; i++) {
            Member member = new Member();
            member.setLogin("lePap" + i);
            member.setFirstName("lePap"+i);
            member.setPassword("lePap"+i);
            member.setEmail("lePap@lePap"+i);
            if(i % 3 == 0) {
                numberOfSellers++;
                SellerInfo info = new SellerInfo();
                member.setAccountType('S');
                info.setRIB("lePapsRIB" + i);
                member.setSellerInfo(info);
            } else {
                member.setAccountType('M');
            }
            memberDao.create(member);
        }

        utx.commit();
        startTransaction();

        long count = memberDao.getSellerCount();
        System.err.println(count + " " + numberOfSellers);
        assertTrue(count == numberOfSellers);
    }

    @Test
    public void deleteMember() throws Exception {
        long id = 1;
        Member find = memberDao.find(id);
        assertTrue(find == null);
    }

}