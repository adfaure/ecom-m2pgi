package fr.ujf.m2pgi.test.database.dao;

import fr.ujf.m2pgi.database.DAO.IMemberDAO;
import fr.ujf.m2pgi.database.DAO.IOrderDAO;
import fr.ujf.m2pgi.database.DAO.IPhotoDAO;
import fr.ujf.m2pgi.database.entities.Member;
import fr.ujf.m2pgi.database.entities.Order;
import fr.ujf.m2pgi.database.entities.Photo;
import fr.ujf.m2pgi.database.entities.SellerInfo;
import org.jboss.arquillian.junit.Arquillian;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import javax.inject.Inject;
import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;


@RunWith(Arquillian.class)
public class TestOrderDao extends AbstactDaoTest {

    /**
     *
     */
    @Inject
    private IMemberDAO memberDao;

    @Inject
    private IOrderDAO orderDao;

    @Inject
    private IPhotoDAO photoDao;

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

        em.createQuery("delete from Order").executeUpdate();
        em.createQuery("delete from Photo").executeUpdate();
        em.createQuery("delete from Member").executeUpdate();

        utx.commit();
    }

    public void insertData() throws Exception {

    	System.out.println("THE INSERT DATAAAAAAAAAA");

        utx.begin();
        em.joinTransaction();
        System.out.println("Inserting records...");
        Member member = new Member();
        member.setLogin("Dadou");
        member.setFirstName("Dadou");
        member.setPassword("Dadou");
        member.setEmail("dadou@dadou");
        member.setLastName("dadoulastname");
        member.setAccountType('M');
        memberDao.create(member);
        Member member2 = new Member();
        member2.setLogin("Marwen");
        member2.setLastName("Marwen");
        member2.setFirstName("Marwen");
        member2.setPassword("Marwen");
        member2.setEmail("marwen@marwen");
        member2.setAccountType('M');
        memberDao.create(member2);
        Member seller = new Member();
        seller.setLogin("seller");
        seller.setLastName("seller");
        seller.setFirstName("seller");
        seller.setPassword("seller");
        seller.setEmail("seller@seller");
        member.setAccountType('S');
        SellerInfo info = new SellerInfo();
        info.setRIB("RIB");
        seller.setSellerInfo(info);
        memberDao.create(seller);


        Photo p = new Photo();
        p.setName("whatever");
        p.setAvailable(true);
        p.setPrice(10);
        p.setWebLocation("this is webLocation");
        p.setAuthor(seller);

        photoDao.create(p);


        utx.commit();
        // clear the persistence context (first-level cache)
        em.clear();
    }


    @Test
    public void testCreateOrder() throws Exception {

    	System.err.println("The test create order****************************");

    	Member m = memberDao.findMemberByLogin("seller");
    	Collection<Photo> orderedPhotos = photoDao.getUserPhotosEntity(m.getLogin());

    	Member m2 = memberDao.findMemberByLogin("Dadou");
    	Order order = new Order();
    	order.setDateCreated(new Date());
    	order.setOrderedPhotos(orderedPhotos);
    	order.setMember(m2);

    	Order created = orderDao.create(order);

        assertTrue(created.getMember().getLogin().equals(m2.getLogin()));

    }


    @Test
    public void createAndFindOrder() throws Exception {

    	System.err.println("The test create AND FIND order****************************");

    	long id;
    	Member m = memberDao.findMemberByLogin("seller");
    	Collection<Photo> orderedPhotos = photoDao.getUserPhotosEntity(m.getLogin());

    	Member m2 = memberDao.findMemberByLogin("Dadou");
    	Order order = new Order();
    	order.setDateCreated(new Date());
    	order.setOrderedPhotos(orderedPhotos);
    	order.setMember(m2);

    	Order created = orderDao.create(order);
    	id = created.getOrderID();

    	Order find = orderDao.find(id);

    	assertTrue(find != null);
    }

    @Test
    public void getCustomerOrdersTest() throws Exception {

    	System.err.println("The test getCustomerOrders****************************");

    	Member m = memberDao.findMemberByLogin("seller");
    	Collection<Photo> orderedPhotos = photoDao.getUserPhotosEntity(m.getLogin());

    	Member m2 = memberDao.findMemberByLogin("Dadou");
    	Order order = new Order();
    	order.setDateCreated(new Date());
    	order.setOrderedPhotos(orderedPhotos);
    	order.setMember(m2);
    	orderDao.create(order);


    	assertEquals(order.getOrderedPhotos().size(), orderedPhotos.size());

    }

    @Test
    public void getAllOrdersTest() throws Exception {

    	System.err.println("The test getAllOrdersTest****************************");
    	int numberOfOrders = 10;


    	Collection<Order> orders = createOrders(numberOfOrders, "Dadou");

    	Member m2 = memberDao.findMemberByLogin("Dadou");


    	assertEquals(m2.getOrderedPhotos().size(), numberOfOrders);
    	//assertEquals(m2.getOrderedPhotos(), orders);

    }


	@Test
    public void getTotalPurchaseTest() throws Exception {

    	System.err.println("The test getTotalPurchaseTest****************************");

    	int numberOfOrders = 10;
    	String customer = "Dadou";
    	Member m = memberDao.findMemberByLogin("seller");
    	Photo photo = photoDao.getUserPhotosEntity("seller").get(0);

    	Collection<Order> orders = createOrders(numberOfOrders, customer);

    	Member m2 = memberDao.findMemberByLogin(customer);

    	float totalPurchaseCost = 0;
    	for(Order o: orders){
    		totalPurchaseCost += o.getPrice();
    	}

    	assertEquals(m2.getOrderedPhotos().size(), numberOfOrders);

    	assertEquals(photo.getPrice()*numberOfOrders, totalPurchaseCost, 0.001);
    }


    @Test
    public void getSellersOrdersTest() throws Exception {

    	System.err.println("The test getSellersOrdersTest****************************");

    	int numberOfOrders = 10;
    	String customer = "Dadou";
    	String customer2 = "Marwen";

    	Collection<Order> orders = createOrders(numberOfOrders, customer);
    	Collection<Order> orders2 = createOrders(numberOfOrders, customer2);

    	Member m1 = memberDao.findMemberByLogin(customer);
    	Member m2 = memberDao.findMemberByLogin(customer);

    	Collection<Order> totalOrders = new ArrayList<Order>();
    	totalOrders.addAll(orders);
    	totalOrders.addAll(orders2);

    	assertEquals(m1.getOrderedPhotos().size() + m2.getOrderedPhotos().size(), orders.size() + orders2.size());

    }

    private Collection<Order> createOrders(int numberOfOrders, String loginCustomer){
    	Member m = memberDao.findMemberByLogin("seller");
    	Collection<Photo> orderedPhotos = photoDao.getUserPhotosEntity(m.getLogin());
    	Collection<Order> ordersColl = new ArrayList<Order>();
    	Member m2 = memberDao.findMemberByLogin(loginCustomer);
    	Order order;

    	for(int i=0; i<numberOfOrders; i++){
    		order = new Order();
        	order.setDateCreated(new Date());
        	order.setOrderedPhotos(orderedPhotos);
        	order.setMember(m2);
        	ordersColl.add(orderDao.create(order));
    	}

    	return ordersColl;
    }

}
