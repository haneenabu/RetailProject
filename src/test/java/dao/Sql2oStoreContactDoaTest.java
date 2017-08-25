package dao;

import models.RetailStore;
import models.StoreContact;
import org.h2.mvstore.db.MVTableEngine;
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
public class Sql2oStoreContactDoaTest {
    private Sql2oStoreContactDoa storeContactDoa;
    private Sql2oRetailStoreDoa retailStoreDoa;
    private Connection con;

    @Before
    public void setUp() throws Exception {
        String connectionString ="jdbc:h2:mem:testing;INIT=RUNSCRIPT from 'classpath:db/create.sql'";
        Sql2o sql2o = new Sql2o(connectionString,"","");
        storeContactDoa = new Sql2oStoreContactDoa(sql2o);
        retailStoreDoa = new Sql2oRetailStoreDoa(sql2o);
        con = sql2o.open();
    }

    @After
    public void tearDown() throws Exception {
        con.close();
    }

    @Test
    public void add() throws Exception {
        StoreContact storeContact = newStoreContact();
        int id = storeContact.getId();
        storeContactDoa.add(storeContact);
        assertNotEquals(id, storeContact.getId());
    }

    @Test
    public void getAll() throws Exception {
        StoreContact storeContact = newStoreContact();
        StoreContact storeContact1 = anotherStoreContact();
        storeContactDoa.add(storeContact);
        storeContactDoa.add(storeContact1);
        List<StoreContact> storeContactList = storeContactDoa.getAll();
        assertEquals(2, storeContactList.size());
    }

    @Test
    public void findContactById() throws Exception {
        StoreContact storeContact = newStoreContact();
        storeContactDoa.add(storeContact);
        int id = storeContact.getId();
        StoreContact finder = storeContactDoa.findContactById(id);
        assertEquals(storeContact.getRetailType(), finder.getRetailType());
    }

    @Test
    public void updateRetailContact() throws Exception {
        StoreContact storeContact = newStoreContact();
        StoreContact storeContact1 = anotherStoreContact();
        storeContactDoa.add(storeContact);
        storeContactDoa.add(storeContact1);
        int id = storeContact.getId();
        storeContactDoa.updateRetailContact("Test", "clothing", 2012, "503-555-5555", id);
        assertEquals("Test", retailStoreDoa.findById(id).getStoreName());
    }

    @Test
    public void deleteAllStoresByContact() throws Exception {
        StoreContact storeContact = newStoreContact();
        StoreContact storeContact1 = newStoreContact();
        storeContactDoa.add(storeContact);
        storeContactDoa.add(storeContact1);
        RetailStore retailStore = new RetailStore("Test", "clothing", 2012, "test");
        retailStoreDoa.add(retailStore);
        String contactInfo = storeContact.getContactInfo();
        storeContactDoa.deleteAllStoresByContact(contactInfo);
        assertEquals(1, retailStoreDoa.getAllStores().size());
        assertEquals(0, storeContactDoa.getAll().size());
    }

    @Test
    public void deleteContactById() throws Exception {
        StoreContact storeContact = newStoreContact();
        storeContactDoa.add(storeContact);
        int id = storeContact.getId();
        System.out.println(retailStoreDoa.getAllStores().size()); //to make sure they are getting added correctly
        storeContactDoa.deleteContactById(id);
        assertEquals(0, retailStoreDoa.getAllStores().size());
    }

    //Helper Methods
    public StoreContact newStoreContact(){
        return new StoreContact("Massah", "Scarves", 2014, "place", "Portland,OR");
    }
    public StoreContact anotherStoreContact(){
        return new StoreContact("Etsy", "Handmade", 2003, "place", "etsy.com");

    }
}