package dao;

import models.RetailStore;
import models.StoreType;
import org.sql2o.Connection;
import org.sql2o.Sql2o;
import org.sql2o.Sql2oException;

import java.util.List;

/**
 * Created by Guest on 8/25/17.
 */
public class Sql2oStoreTypeDao implements StoreTypeDoa {
    private final Sql2o sql2o;
    public Sql2oStoreTypeDao(Sql2o sql2o){
        this.sql2o = sql2o;
    }

    @Override
    public void add(StoreType storeType) {
        String query = "INSERT INTO storetypes (name) VALUES (:name)";
        try (Connection con = sql2o.open()){
            int id =(int) con.createQuery(query)
                    .addParameter("name", storeType.getName())
                    .addColumnMapping("NAME", "name")
                    .executeUpdate()
                    .getKey();
            storeType.setId(id);
        }catch (Sql2oException ex) {
            System.out.println(ex);
        }
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
