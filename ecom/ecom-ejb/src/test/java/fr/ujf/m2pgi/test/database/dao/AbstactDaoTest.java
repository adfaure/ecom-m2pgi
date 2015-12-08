package fr.ujf.m2pgi.test.database.dao;

import fr.ujf.m2pgi.test.AbstractTestArquillian;
import org.junit.After;
import org.junit.Before;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.UserTransaction;

/**
 * Created by FAURE Adrien on 03/12/15.
 */
public abstract class AbstactDaoTest extends AbstractTestArquillian {

    /**
     *
     */
    @PersistenceContext
    protected EntityManager em;

    /**
     *
     */
    @Inject
    protected UserTransaction utx;


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

    protected void startTransaction() throws Exception {
        utx.begin();
        em.joinTransaction();
    }

    abstract void clearData() throws Exception;

    abstract void insertData() throws Exception ;

}
