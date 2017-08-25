package dao;

import models.RetailStore;
import models.StoreType;

import java.util.List;

/**
 * Created by Guest on 8/25/17.
 */
public class Sql2oStoreTypeDao implements StoreTypeDoa {
    @Override
    public void add(StoreType storeType) {

    }

    @Override
    public void addStoreTypeToStore(StoreType storeType, RetailStore retailStore) {

    }

    @Override
    public List<StoreType> getAll() {
        return null;
    }

    @Override
    public List<RetailStore> getAllStoresForStoreType(int id) {
        return null;
    }

    @Override
    public StoreType getById(int id) {
        return null;
    }

    @Override
    public void deleteById(int id) {

    }
}
