package dao;

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
    public void add() throws Exception {
    }

    @Test
    public void addStoreTypeToStore() throws Exception {
    }

    @Test
    public void getAll() throws Exception {
    }

    @Test
    public void getAllStoresForStoreType() throws Exception {
    }

    @Test
    public void getById() throws Exception {
    }

    @Test
    public void deleteById() throws Exception {
    }

}