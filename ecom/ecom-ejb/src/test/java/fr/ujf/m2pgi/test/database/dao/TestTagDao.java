package fr.ujf.m2pgi.test.database.dao;


import fr.ujf.m2pgi.database.DAO.IMemberDAO;
import fr.ujf.m2pgi.database.DAO.IPhotoDAO;
import fr.ujf.m2pgi.database.DAO.ITagDAO;
import fr.ujf.m2pgi.database.DTO.TagCountDTO;
import fr.ujf.m2pgi.database.entities.Member;
import fr.ujf.m2pgi.database.entities.Photo;
import fr.ujf.m2pgi.database.entities.SellerInfo;


import fr.ujf.m2pgi.database.entities.Tag;
import org.jboss.arquillian.junit.Arquillian;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import javax.inject.Inject;
import java.util.*;

import static org.junit.Assert.*;


/**
 * Created by smile on 11/12/15.
 */
@RunWith(Arquillian.class)
public class TestTagDao extends AbstactDaoTest {

    @Inject
    private ITagDAO tagDAO;

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
        em.createQuery("delete from Tag").executeUpdate();
        System.out.println("Dumping Done");
        utx.commit();
    }

    public void insertData() throws Exception {
        utx.begin();
        em.joinTransaction();
        System.out.println("Inserting 10 sellers records...");

        Tag tag = new Tag();
        tag.setName("test");
        tag.setPhotos(new ArrayList<Photo>());
        tagDAO.create(tag);
        utx.commit();
        // clear the persistence context (first-level cache)
        em.clear();
    }

    @Test
    public void testgetByName() {
        Tag test = tagDAO.getByString("test");
        assertNotNull(test);
        test = tagDAO.getByString("noppp");
        assertNull(test);
    }

    @Test
    public void testDelete() {
        Tag test = tagDAO.getByString("test");
        long id = test.getId();
        tagDAO.delete(test.getId());
        test = tagDAO.find(id);
        assertNull(test);
    }

    @Test
    public void top10() throws Exception {
        Map<java.lang.String, Integer> record = new HashMap<java.lang.String, Integer>();
        Random rand = new Random();
        for(int i = 0 ; i < 50; i++) {
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
                Collection<Tag> tags = new ArrayList<>();
                for(int t = 0; t < 10; t++) {
                    Tag tag = new Tag();
                    Integer theRand = rand.nextInt() % 50;
                    if(record.get("tags"+theRand) == null) {
                        record.put("tags"+theRand, 1);
                    } else {
                        record.put("tags"+theRand, record.get("tags"+theRand) + 1);
                    }
                    tag.setName("tags"+theRand);
                    tags.add(tag);
                }
                photo.setTags(tags);
                photo.setWebLocation("webLocationPhoto"+j+" from seller"+i);
                if(j==0) photo.setAvailable(false);
                else photo.setAvailable(true);
                photoDAO.create(photo);
            }
        }

        List<Map.Entry<Integer, String>> records = new ArrayList<>();
        for(Map.Entry<java.lang.String, Integer> tag : record.entrySet()) {
            Map.Entry<Integer, String> cur = new AbstractMap.SimpleEntry<Integer, String>(tag.getValue(),tag.getKey());
            records.add(cur);
        }
        records.sort(new Comparator<Map.Entry<Integer, String>>() {
            @Override
            public int compare(Map.Entry<Integer, String> o1, Map.Entry<Integer, String> o2) {
                return -(o1.getKey() - o2.getKey());
            }
        });

        System.out.println("ici--");
        for(int i = 0 ; i < 10; i++) {
            System.err.println(records.get(i).getKey());
            System.err.println(records.get(i).getValue());
        }
        System.out.println("et la");
        for(TagCountDTO tags :  tagDAO.getTop10Tags()) {
            System.err.println(tags.getName());
        }
        assertTrue(true);
    }

}
