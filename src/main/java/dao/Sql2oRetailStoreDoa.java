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
public class Sql2oRetailStoreDoa implements RetailStoreDao{
    private final Sql2o sql2o;

    public Sql2oRetailStoreDoa(Sql2o sql2o){
        this.sql2o = sql2o;
    }

    @Override
    public void add(RetailStore retailStore) {
        String query = "INSERT INTO stores(storeName, retailType, yearEstablished, contact) VALUES (:storeName, :retailType, :yearEstablished, :contact)";
        try(Connection con = sql2o.open()){
            int id =(int) con.createQuery(query)
                    .bind(retailStore)
                    .throwOnMappingFailure(false)
                    .executeUpdate()
                    .getKey();
            retailStore.setId(id);
        }catch (Sql2oException ex) {
            System.out.println(ex);
        }
    }

    @Override
    public void addStoreToStoreType(RetailStore retailStore, StoreType storeType) {
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
    public List<RetailStore> getAllStores() {
        String query = "SELECT * FROM stores";
        try(Connection con = sql2o.open()){
            return con.createQuery(query)
                    .throwOnMappingFailure(false)
                    .executeAndFetch(RetailStore.class);
        }

    }

    @Override
    public List<StoreType> getAllStoreTypesForAStore(int storeId) {
        ArrayList<StoreType> storeTypes = new ArrayList<>();

        String joinQuery = "SELECT storetypeId FROM stores_storetypes WHERE storeId = :storeId";

        try (Connection con = sql2o.open()) {
            List<Integer> allStoretypesIds = con.createQuery(joinQuery)
                    .addParameter("storeId", storeId)
                    .executeAndFetch(Integer.class);
            for (Integer storetypeId : allStoretypesIds){
                String foodtypeQuery = "SELECT * FROM storetypes WHERE id = :storetypeId";
                storeTypes.add(
                        con.createQuery(foodtypeQuery)
                                .addParameter("storetypeId", storetypeId)
                                .executeAndFetchFirst(StoreType.class));
            }
        } catch (Sql2oException ex){
            System.out.println(ex);
        }
        return storeTypes;
    }

    @Override
    public RetailStore findById(int id) {
        String query = "SELECT * FROM stores WHERE id = :id";
        try(Connection con = sql2o.open()){
            return con.createQuery(query)
                    .addParameter("id", id)
                    .throwOnMappingFailure(false)
                    .executeAndFetchFirst(RetailStore.class);
        }
    }

    @Override
    public void update(String storeName, String retailType, int yearEstablished, int id) {
       String query= "UPDATE stores SET (storeName, retailType, yearEstablished) = (:storeName, :retailType, :yearEstablished)";
       try (Connection con = sql2o.open()){
           con.createQuery(query)
                   .addParameter("storeName", storeName)
                   .addParameter("retailType", retailType)
                   .addParameter("yearEstablished", yearEstablished)
                   .executeUpdate();
       }catch (Sql2oException ex){
           System.out.println(ex);
       }
    }

    @Override
    public void deleteAll() {
        try(Connection con = sql2o.open()){
            con.createQuery("DELETE FROM stores")
                    .executeUpdate();
        }catch (Sql2oException ex){
            System.out.println(ex);
        }
    }

    @Override
    public void deleteById(int id) {
        String deleteJoin = "DELETE FROM stores_storetypes WHERE storeId = :storeId";
        try(Connection con = sql2o.open()){
            con.createQuery("DELETE FROM stores WHERE id = :id")
                    .addParameter("id", id)
                    .executeUpdate();
            con.createQuery(deleteJoin)
                    .addParameter("storeId", id)
                    .executeUpdate();
        }catch (Sql2oException ex){
            System.out.println(ex);
        }
    }
}
