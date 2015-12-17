package fr.ujf.m2pgi.test.database.dao;


import fr.ujf.m2pgi.database.DAO.IMemberDAO;
import fr.ujf.m2pgi.database.DAO.IPhotoDAO;
import fr.ujf.m2pgi.database.entities.Member;
import fr.ujf.m2pgi.database.entities.Photo;
import fr.ujf.m2pgi.database.entities.SellerInfo;


import org.jboss.arquillian.junit.Arquillian;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import javax.inject.Inject;
import static org.junit.Assert.*;


/**
 * Created by smile on 11/12/15.
 */
@RunWith(Arquillian.class)
public class TestPhotoDAO extends AbstactDaoTest {

    @Inject
    private IPhotoDAO photoDAO;

    @Inject
    private IMemberDAO memberDAO;

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
        System.out.println("Dumping old photos and members records...");
        em.createQuery("delete from Photo").executeUpdate();
        em.createQuery("delete from Member").executeUpdate();
        System.out.println("Dumping Done");
        utx.commit();
    }

    public void insertData() throws Exception {

        utx.begin();
        em.joinTransaction();

        System.out.println("Inserting 10 sellers records...");
        for(int i = 0 ; i < 10; i++) {
            Member seller = new Member();
            seller.setLogin("seller"+i);
            seller.setFirstName("seller"+i);
            seller.setPassword("seller"+i);
            seller.setEmail("seller@seller"+i);
            seller.setLastName("fname"+i);
            seller.setAccountType('S');
            SellerInfo info = new SellerInfo();
            info.setRIB("RIB"+i);
            seller.setSellerInfo(info);
            memberDAO.create(seller);

            System.out.println("Inserting 10 photos records by seller...");
            for(int j = 0 ; j < 10; j++) {
                Photo photo = new Photo();
                photo.setAuthor(seller);
                photo.setDescription("photo"+j+" from seller"+i);
                photo.setName(i+"smilingFace"+j);
                photo.setWebLocation("webLocationPhoto"+j+" from seller"+i);
                if(j==0) photo.setAvailable(false);
                else photo.setAvailable(true);
                photoDAO.create(photo);
            }
        }
        utx.commit();
        // clear the persistence context (first-level cache)
        em.clear();
    }


    @Test
    public void testCreateAndFindPhoto() throws Exception {utx.commit();
        clearData();
        startTransaction();

        long id;
        Member seller = new Member();
        seller.setLogin("angie");
        seller.setFirstName("angie");
        seller.setPassword("angie");
        seller.setLastName("Ln");
        seller.setEmail("angie@angie");
        seller.setAccountType('S');
        SellerInfo info = new SellerInfo();
        info.setRIB("RIBangie");
        seller.setSellerInfo(info);
        memberDAO.create(seller);

        Photo photo = new Photo();
        photo.setAuthor(seller);
        photo.setDescription("from angie");
        photo.setWebLocation("webLocationPhotoTest");
        photo.setName("smilingFace");
        photo.setAvailable(true);
        Photo created = photoDAO.create(photo);
        assertTrue(created.getAuthor().equals(seller));

        id = created.getPhotoID();

        Photo find = photoDAO.find(id);

        assertTrue(find != null);
    }


    @Test
    public void getPhotoCounts() throws Exception {

        // photo total count test
        long count = photoDAO.getAllPhotos().size();
        assertTrue(count == 100);

        // WARNING : getphotoCount returns only available photos
        count = photoDAO.getPhotoCount();
        assertTrue(count == 90);

        // photo count test for 1 seller identified by id
        // WARNING : getUserPhotos returns only available photos
        Member m = memberDAO.findMemberByLogin("seller0");
        count = photoDAO.getUserPhotos( m.getMemberID()).size();
        System.err.println("photoDAO.getUserPhotos( m.getMemberID()).size()) >>>>>>>> "+photoDAO.getUserPhotos( m.getMemberID()).size());
        assertTrue(count == 9);

        // 1 seller photos count test (without availability)
        count = 0;
        for(Photo oneOfAllPhotos : photoDAO.getAllPhotos())
            if(oneOfAllPhotos.getAuthor().equals(m)) count++;
        assertTrue(count == 10);

        // photo count test for 1 seller identified by login
        // WARNING : getUserPhotos returns only available photos
        count = photoDAO.getUserPhotos("seller0").size();
        assertTrue(count == 9);

        utx.commit();
        clearData();
        startTransaction();
        count = photoDAO.getPhotoCount();
        assertTrue(count == 0);
    }

    @Test
    public void getAvailablePhotosCounts() throws Exception {

        // available photo count
        long count = photoDAO.getAllAvailablePhotos().size();
        assertTrue(count == 90);

        // available photo count test for 1 seller
        Member m = memberDAO.findMemberByLogin("seller0");
        count = 0;
        for(Photo oneOfAllPhotos : photoDAO.getAllPhotos())
            if(oneOfAllPhotos.getAuthor().equals(m) && oneOfAllPhotos.isAvailable()) count++;
        assertTrue(count == 9);

        utx.commit();
        clearData();
        startTransaction();
        count = photoDAO.getPhotoCount();
        assertTrue(count == 0);
    }


}
