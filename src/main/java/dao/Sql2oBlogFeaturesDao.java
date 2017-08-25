package dao;

import models.BlogFeatures;
import org.sql2o.Sql2o;

import org.sql2o.Connection;
import org.sql2o.Sql2oException;

import java.util.List;

/**
 * Created by Guest on 8/25/17.
 */
public class Sql2oBlogFeaturesDao implements BlogFeaturesDao {
    private final Sql2o sql2o;
    public Sql2oBlogFeaturesDao(Sql2o sql2o){
        this.sql2o = sql2o;
    }

    @Override
    public void add(BlogFeatures blogFeatures) {
        String query = "INSERT INTO blogs (writtenBy, rating, storeId) VALUES (:writtenBy, :rating, :storeId)";
        try (Connection con = sql2o.open()){
            int id = (int)con.createQuery(query)
                    .addParameter("writtenBy", blogFeatures.getWrittenBy())
                    .addParameter("rating", blogFeatures.getRating())
                    .addParameter("storeId", blogFeatures.getStoreId())
                    .addColumnMapping("WRITTENBY", "writtenBy")
                    .addColumnMapping("RATING", "rating")
                    .addColumnMapping("STOREID", "storeId")
                    .executeUpdate()
                    .getKey();
            blogFeatures.setId(id);
        } catch (Sql2oException ex) {
            System.out.println(ex);
        }
    }

    @Override
    public List<BlogFeatures> getAllBlogsByStore(int storeId) {
        try (Connection con = sql2o.open()){
            return con.createQuery("SELECT * FROM blogs WHERE storeId = :storeId")
                    .addParameter("storeId", storeId)
                    .executeAndFetch(BlogFeatures.class);
        }
    }

    @Override
    public BlogFeatures findById(int id) {
        try (Connection con = sql2o.open()){
            return con.createQuery("SELECT * FROM blogs WHERE id = :id")
                    .addParameter("id", id)
                    .executeAndFetchFirst(BlogFeatures.class);
        }
    }

    @Override
    public void update(String writtenBy, int rating, int storeId, int id) {
        String sql = "UPDATE blogs SET (writtenBy, rating, storeId) = (:writtenBy, :rating, :storeId) WHERE id = :id";
        try (Connection con = sql2o.open()){
            con.createQuery(sql)
                    .addParameter("writtenBy", writtenBy)
                    .addParameter("rating", rating)
                    .addParameter("storeId", storeId)
                    .addParameter("id", id)
                    .executeUpdate();
        } catch (Sql2oException ex) {
            System.out.print(ex);
        }
    }

    @Override
    public void deleteById(int id) {
        try (Connection con = sql2o.open()){
            con.createQuery("DELETE FROM blogs WHERE id = :id")
                    .addParameter("id", id)
                    .executeUpdate();
        } catch (Sql2oException ex) {
            System.out.print(ex);
        }
    }
}
