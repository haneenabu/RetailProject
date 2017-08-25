package dao;

import models.RetailStore;

import java.util.List;

/**
 * Created by Guest on 8/25/17.
 */
public class Sql2oRetailStoreDoa implements RetailStoreDao{
    @Override
    public void add(RetailStore retailStore) {
        String query = "INSERT INTO ";

    }

    @Override
    public List<RetailStore> getAllStores() {
        return null;
    }

    @Override
    public RetailStore findById(int id) {
        return null;
    }

    @Override
    public void update(String storeName, String retailType, int yearEstablished, int id) {

    }

    @Override
    public void deleteAll() {

    }

    @Override
    public void deleteById(int id) {

    }
}
