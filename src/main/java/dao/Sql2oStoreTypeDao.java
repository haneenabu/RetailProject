package dao;

import models.RetailStore;
import models.StoreType;
import org.sql2o.Connection;
import org.sql2o.Sql2o;
import org.sql2o.Sql2oException;

import java.util.ArrayList;
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
        String sql = "INSERT INTO stores_storetypes (storeId, storetypeId) VALUES (:storeId, :storetypeId)";
        try (Connection con = sql2o.open()) {
            con.createQuery(sql)
                    .addParameter("storeId", retailStore.getId())
                    .addParameter("storetypeId", storeType.getId())
                    .executeUpdate();
        } catch (Sql2oException ex){
            System.out.println(ex);
        }
    }

    @Override
    public List<StoreType> getAll() {
        try(Connection con = sql2o.open()) {
            return con.createQuery("SELECT * FROM storetypes")
                    .executeAndFetch(StoreType.class);
        }
    }

    @Override
    public List<RetailStore> getAllStoresForStoreType(int storetypeId) {
        ArrayList<RetailStore> retailStores = new ArrayList<>();

        String joinQuery = "SELECT storeId FROM stores_storetypes WHERE storetypeId = :storetypeId";

        try (Connection con = sql2o.open()) {
            List<Integer> allStoreIds = con.createQuery(joinQuery)
                    .addParameter("storetypeId", storetypeId)
                    .executeAndFetch(Integer.class); //what is happening in the lines above?
            for (Integer storeId : allStoreIds){
                String storeQuery = "SELECT * FROM stores WHERE id = :storeId";
                retailStores.add(
                        con.createQuery(storeQuery)
                                .addParameter("storeId", storeId)
                                .executeAndFetchFirst(RetailStore.class));
            }
        } catch (Sql2oException ex){
            System.out.println(ex);
        }
        return retailStores;
    }

    @Override
    public StoreType getById(int id) {
        try (Connection con = sql2o.open()) {
            return con.createQuery("SELECT * FROM storetypes WHERE id = :id")
                    .addParameter("id", id)
                    .executeAndFetchFirst(StoreType.class);
        }
    }

    @Override
    public void deleteById(int id) {
        String sql = "DELETE from storetypes WHERE id = :id";
        String deleteJoin = "DELETE from stores_storetypes WHERE storetypeId = :storetypeId";
        try (Connection con = sql2o.open()) {
            con.createQuery(sql)
                    .addParameter("id", id)
                    .executeUpdate();
            con.createQuery(deleteJoin)
                    .addParameter("storetypeId", id)
                    .executeUpdate();

        } catch (Sql2oException ex){
            System.out.println(ex);
        }
    }
}
