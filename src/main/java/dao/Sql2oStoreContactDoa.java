package dao;

import models.StoreContact;
import org.sql2o.Connection;
import org.sql2o.Sql2o;
import org.sql2o.Sql2oException;
import sun.security.x509.CertificatePolicyMap;

import java.util.List;

/**
 * Created by Guest on 8/25/17.
 */
public class Sql2oStoreContactDoa implements StoreContactDao {
    private final Sql2o sql2o;
    public Sql2oStoreContactDoa (Sql2o sql2o){
        this.sql2o = sql2o;
    }

    @Override
    public void add(StoreContact storeContact) {
        String query = "INSERT INTO stores (storeName, retailType, yearEstablished, contactInfo, contact) VALUES (:storeName, :retailType, :yearEstablished, :contactInfo, :contact)";
        try(Connection con = sql2o.open()){
            int id = (int) con.createQuery(query)
                    .bind(storeContact)
                    .throwOnMappingFailure(false)
                    .executeUpdate()
                    .getKey();
            storeContact.setId(id);
        } catch (Sql2oException ex){
            System.out.println(ex);
        }
    }

    @Override
    public List<StoreContact> getAll() {
        String query = "SELECT * FROM stores WHERE contact = :contact";
        try(Connection con =sql2o.open()){
            return con.createQuery(query)
                    .addParameter("contact", "place")
                    .throwOnMappingFailure(false)
                    .executeAndFetch(StoreContact.class);

        }
    }

    @Override
    public StoreContact findContactById(int id) {
        try (Connection con = sql2o.open()){
            return con.createQuery("SELECT * FROM stores WHERE id = :id")
                    .addParameter("id", id)
                    .throwOnMappingFailure(false)
                    .executeAndFetchFirst(StoreContact.class);
        }
    }

    @Override
    public void updateRetailContact(String storeName, String retailType, int yearEstablished, String contactInfo, int id) {
        String query = "UPDATE stores SET (storeName, retailType, yearEstablished, contactInfo)=(:storeName, :retailType, :yearEstablished, :contactInfo) WHERE id = :id";
        try(Connection con = sql2o.open()){
            con.createQuery(query)
                    .addParameter("storeName", storeName)
                    .addParameter("retailType", retailType)
                    .addParameter("yearEstablished", yearEstablished)
                    .addParameter("contactInfo", contactInfo)
                    .addParameter("id", id)
                    .throwOnMappingFailure(false)
                    .executeUpdate();
        }catch (Exception ex){
            System.out.println(ex);
        }
    }

    @Override
    public void deleteAllStoresByContact(String contactInfo) {
        String query = "DELETE FROM stores WHERE contactInfo = :contactInfo";
        try (Connection con = sql2o.open()){
            con.createQuery(query)
                    .addParameter("contactInfo", contactInfo)
                    .throwOnMappingFailure(false)
                    .executeUpdate();
        }
    }

    @Override
    public void deleteContactById(int id) {
        String query = "DELETE FROM stores WHERE id = :id";
        try (Connection con = sql2o.open()){
            con.createQuery(query)
                    .addParameter("id", id)
                    .throwOnMappingFailure(false)
                    .executeUpdate();
        }
    }
}
