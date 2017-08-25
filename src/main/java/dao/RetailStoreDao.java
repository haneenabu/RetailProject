package dao;

import models.RetailStore;

import java.util.List;

/**
 * Created by Guest on 8/25/17.
 */
public interface RetailStoreDao {
    //Create
    void add (RetailStore retailStore);

    //Read
    List<RetailStore> getAllStores();

    RetailStore findById(int id);

    //Update
    void update(String storeName, String retailType, int yearEstablished, int id);

    //Delete
    void deleteAll();

    void deleteById(int id);
}
