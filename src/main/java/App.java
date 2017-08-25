import com.google.gson.Gson;
import dao.Sql2oBlogFeaturesDao;
import dao.Sql2oRetailStoreDoa;
import dao.Sql2oStoreContactDoa;
import dao.Sql2oStoreTypeDao;

import models.BlogFeatures;
import models.RetailStore;
import org.sql2o.Connection;
import org.sql2o.Sql2o;

import static spark.Spark.*;

/**
 * Created by Guest on 8/25/17.
 */
public class App {
    public static void main(String[] args) {


        Sql2oStoreTypeDao storeTypeDao;
        Sql2oRetailStoreDoa retailStoreDoa;
        Sql2oBlogFeaturesDao blogFeaturesDao;
        Sql2oStoreContactDoa storeContactDoa;
        Connection con;
        Gson gson = new Gson();

        String connectionString = "jdbc:h2:~/retail.db;INIT=RUNSCRIPT from 'classpath:db/create.sql'";

        Sql2o sql2o = new Sql2o(connectionString, "", "");
        storeTypeDao = new Sql2oStoreTypeDao(sql2o);
        retailStoreDoa = new Sql2oRetailStoreDoa(sql2o);
        blogFeaturesDao = new Sql2oBlogFeaturesDao(sql2o);
        storeContactDoa = new Sql2oStoreContactDoa(sql2o);
        con = sql2o.open();

        //Create
        post("/stores/new", "application/json", (request, response) -> {
            RetailStore retailStore = gson.fromJson(request.body(), RetailStore.class);
            retailStoreDoa.add(retailStore);
            response.status(201);
            return gson.toJson(retailStore);
        });

        //Read
        get("/stores", "application/json", (request, response) -> {
           return gson.toJson(retailStoreDoa.getAllStores());
        });

        get("stores/:id", "application/json", (request, response) -> {
            int storeId = Integer.parseInt(request.params("id"));
            return gson.toJson(retailStoreDoa.findById(storeId));
        });

        //Create
        post("/stores/:storeId/blogs/new", "application/json", (request, response) -> {
            int storeId = Integer.parseInt(request.params("storeId"));
            BlogFeatures blogFeatures = gson.fromJson(request.body(), BlogFeatures.class);
            blogFeatures.setStoreId(storeId);
            blogFeaturesDao.add(blogFeatures);
            response.status(201);
            return gson.toJson(blogFeatures);
        });
        //Read
        get("/stores/:storeId/blogs", "application/json",(request, response) -> {
            int storeId = Integer.parseInt(request.params("storeId"));
            return gson.toJson(blogFeaturesDao.getAllBlogsByStore(storeId));
        });
        get("/stores/:storeId/blogs/:id", "application/json", (request, response) -> {

            int blogId = Integer.parseInt(request.params("id"));
            return gson.toJson(blogFeaturesDao.findById(blogId));
        });

        //FILTERS
        after((req, res) ->{
            res.type("application/json");
        });
    }
}
