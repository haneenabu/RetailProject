package dao;

import models.RetailStore;
import models.StoreType;

import java.util.List;

/**
 * Created by Guest on 8/25/17.
 */
public interface StoreTypeDoa {
    //create
    void add (StoreType storeType);
    void addStoreTypeToStore(StoreType storeType, RetailStore retailStore);

    //Read
    List<StoreType> getAll();
    List<RetailStore> getAllStoresForStoreType(int id);

    StoreType getById(int id);

    //update- course says to ommit for now

    //delete
    void deleteById(int id);
}
