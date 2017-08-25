package dao;

import models.RetailStore;
import models.StoreType;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.sql2o.Connection;
import org.sql2o.Sql2o;

import static org.junit.Assert.*;

/**
 * Created by Guest on 8/25/17.
 */
public class Sql2oStoreTypeDaoTest {
    private Sql2oStoreTypeDao storeTypeDao;
    private Sql2oRetailStoreDoa retailStoreDoa;
    private Connection con;

    @Before
    public void setUp() throws Exception {
        String connectionString = "jdbc:h2:mem:testing;INIT=RUNSCRIPT from 'classpath:db/create.sql'";
        Sql2o sql2o = new Sql2o(connectionString, "", "");
        storeTypeDao = new Sql2oStoreTypeDao(sql2o);
        retailStoreDoa = new Sql2oRetailStoreDoa(sql2o);
        con= sql2o.open();
    }

    @After
    public void tearDown() throws Exception {
        con.close();
    }

    @Test
    public void instantiatesnewStore() throws Exception{
        StoreType storeType = setUpStoreType();
        assertTrue(storeType instanceof StoreType);
    }
    @Test
    public void getAllStoreTypes() throws Exception {
        StoreType storeType = setUpStoreType();
        StoreType storeType1 = new StoreType("app");
        storeTypeDao.add(storeType);
        storeTypeDao.add(storeType1);
        assertEquals(2, storeTypeDao.getAll().size());
    }

    @Test
    public void addStoreTypeToStore() throws Exception {
        RetailStore retailStore = new RetailStore("Nordstroms", "clothing", 1990, "email");
        RetailStore retailStore1 = new RetailStore("Macys", "department", 1978, "phone");
        retailStoreDoa.add(retailStore);
        retailStoreDoa.add(retailStore1);

        StoreType storeType = setUpStoreType();
        storeTypeDao.add(storeType);

        storeTypeDao.addStoreTypeToStore(storeType, retailStore);
        storeTypeDao.addStoreTypeToStore(storeType, retailStore1);
    }

    @Test
    public void deletingStoresAlsoUpdatesTable() throws Exception {
        StoreType testStoretype  = new StoreType("Seafood");
        storeTypeDao.add(testStoretype);

        RetailStore retailStore = new RetailStore("Test", "whatever", 2345, "location");
        retailStoreDoa.add(retailStore);

        RetailStore retailStore1 = new RetailStore("Sloan", "everything", 5656, "email");
        retailStoreDoa.add(retailStore1);

        retailStoreDoa.addStoreToStoreType(retailStore, testStoretype);
        retailStoreDoa.addStoreToStoreType(retailStore1, testStoretype);

        retailStoreDoa.deleteById(retailStore.getId());
        assertEquals(0, retailStoreDoa.getAllStoreTypesForAStore(retailStore.getId()).size());
    }


    //Helper Method
    StoreType setUpStoreType(){ return new StoreType("online");}

}