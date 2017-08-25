package dao;

import com.sun.org.apache.bcel.internal.generic.RET;
import models.RetailStore;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.sql2o.Sql2o;

import org.sql2o.Connection;

import java.util.List;

import static org.junit.Assert.*;

/**
 * Created by Guest on 8/25/17.
 */
public class Sql2oRetailStoreDoaTest {
    private Sql2oRetailStoreDoa retailStoreDoa;
    private Connection con;

    @Before
    public void setUp() throws Exception {
        String connectionString = "jdbc:h2:mem:testing;INIT=RUNSCRIPT from 'classpath:db/create.sql'";
        Sql2o sql2o = new Sql2o(connectionString, "", "");
        retailStoreDoa = new Sql2oRetailStoreDoa(sql2o);
        con= sql2o.open();
    }

    @After
    public void tearDown() throws Exception {
        con.close();
    }

    @Test
    public void add() throws Exception {
        RetailStore retailStore = newRetailStore();
        int id = retailStore.getId();
        retailStoreDoa.add(retailStore);
        assertNotEquals(id, retailStore.getId());
    }

    @Test
    public void getAllStores() throws Exception {
        RetailStore retailStore = newRetailStore();
        RetailStore retailStore1 = anotherRetailStore();
        retailStoreDoa.add(retailStore);
        retailStoreDoa.add(retailStore1);
        List<RetailStore> retailStoreList = retailStoreDoa.getAllStores();
        assertEquals(2, retailStoreList.size());
    }

    @Test
    public void findById() throws Exception {
        RetailStore retailStore = newRetailStore();
        RetailStore retailStore1 = anotherRetailStore();
        retailStoreDoa.add(retailStore);
        retailStoreDoa.add(retailStore1);
        int id = retailStore.getId();
        RetailStore find = retailStoreDoa.findById(id);
        assertEquals(retailStore.getStoreName(), find.getStoreName());
    }

    @Test
    public void update() throws Exception {
        RetailStore retailStore = newRetailStore();
        retailStoreDoa.add(retailStore);
        int id = retailStore.getId();
        retailStoreDoa.update("StoreOne", "Everything", 2017, id);
        assertNotEquals("Nordstrom", retailStoreDoa.findById(id).getStoreName());
    }

    @Test
    public void deleteAll() throws Exception {
        RetailStore retailStore = newRetailStore();
        RetailStore retailStore1 = anotherRetailStore();
        retailStoreDoa.add(retailStore);
        retailStoreDoa.add(retailStore1);
        retailStoreDoa.deleteAll();
        assertEquals(0, retailStoreDoa.getAllStores().size());
    }

    @Test
    public void deleteById() throws Exception {
        RetailStore retailStore = newRetailStore();
        RetailStore retailStore1 = anotherRetailStore();
        retailStoreDoa.add(retailStore);
        retailStoreDoa.add(retailStore1);
        int deleteId = retailStore.getId();
        retailStoreDoa.deleteById(deleteId);
        assertEquals(1, retailStoreDoa.getAllStores().size());
    }

    //Helper Methods
    public RetailStore newRetailStore(){
        return new RetailStore("Nordstrom", "Clothing", 1990, "all types");
    }
    public RetailStore anotherRetailStore(){
        return new RetailStore("Macy's", "Department", 1970, "women and children");
    }
}