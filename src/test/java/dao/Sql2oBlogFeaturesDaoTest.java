package dao;

import models.BlogFeatures;
import models.RetailStore;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.sql2o.Connection;
import org.sql2o.Sql2o;

import static org.junit.Assert.*;

/**
 * Created by Guest on 8/25/17.
 */
public class Sql2oBlogFeaturesDaoTest {
    private Connection con;
    private Sql2oBlogFeaturesDao blogFeaturesDao;
    private Sql2oRetailStoreDoa retailStoreDoa;

    @Before
    public void setUp() throws Exception {
        String connectionString = "jdbc:h2:mem:testing;INIT=RUNSCRIPT from 'classpath:db/create.sql'";
        Sql2o sql2o = new Sql2o(connectionString, "", "");
        blogFeaturesDao = new Sql2oBlogFeaturesDao(sql2o);
        retailStoreDoa = new Sql2oRetailStoreDoa(sql2o);
        con = sql2o.open();
    }

    @After
    public void tearDown() throws Exception {
        con.close();
    }

    @Test
    public void add() throws Exception {
        RetailStore retailStore = new RetailStore("test", "clothing", 2009, "phone");
        retailStoreDoa.add(retailStore);
        BlogFeatures blogFeatures = new BlogFeatures("Haneen", 3, retailStore.getId());
        int originalBlogId = blogFeatures.getId();
        blogFeaturesDao.add(blogFeatures);
        assertNotEquals(originalBlogId, blogFeatures.getId());
    }

    @Test
    public void getAllBlogsByStore() throws Exception {
        RetailStore retailStore =  new RetailStore("test", "clothing", 2009, "phone");
        retailStoreDoa.add(retailStore);
        BlogFeatures blogFeatures = new BlogFeatures("Tom", 5, retailStore.getId());
        blogFeaturesDao.add(blogFeatures);
        assertEquals(1, blogFeaturesDao.getAllBlogsByStore(retailStore.getId()).size());
    }

    @Test
    public void findById() throws Exception {
    }

    @Test
    public void update() throws Exception {
    }

    @Test
    public void deleteById() throws Exception {
    }

}