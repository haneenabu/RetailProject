package dao;

import models.BlogFeatures;

import java.util.List;

/**
 * Created by Guest on 8/25/17.
 */
public interface BlogFeaturesDao {
    //Create
    void add (BlogFeatures blogFeatures);

    //Read
    List<BlogFeatures> getAllBlogsByStore(int storeId);

    BlogFeatures findById(int id);

    //Update
    void update(String writtenBy, int rating, int storeId, int id);

    //delete
    void deleteById(int id);

}
