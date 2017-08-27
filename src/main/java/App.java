import com.google.gson.Gson;
import dao.Sql2oBlogFeaturesDao;
import dao.Sql2oRetailStoreDoa;
import dao.Sql2oStoreContactDoa;
import dao.Sql2oStoreTypeDao;
import exceptions.ApiException;
import models.BlogFeatures;
import models.RetailStore;
import models.StoreContact;
import models.StoreType;
import org.sql2o.Connection;
import org.sql2o.Sql2o;

import java.util.HashMap;
import java.util.Map;

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
            RetailStore retailStore= retailStoreDoa.findById(storeId);
            if(retailStore == null){
                throw new ApiException(String.format("No location with the id '%d' found", storeId), 404);
            }
            return gson.toJson(retailStore);
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

        //Create
        post("/storetype/new", "application/json", (request, response) -> {
            StoreType storeType = gson.fromJson(request.body(), StoreType.class);
            storeTypeDao.add(storeType);
            return gson.toJson(storeType);
        });
        //Read
        get("/stores/:storeId/storetype", "application/json", (request, response) -> {
            int storeId= Integer.parseInt(request.params("storeId"));
            return gson.toJson(retailStoreDoa.getAllStoreTypesForAStore(storeId));
        });

        get("/stores/:storeId/storetype/:id", "application/json", (request, response) -> {
            int storeId = Integer.parseInt(request.params("storeId"));
            RetailStore retailStore = retailStoreDoa.findById(storeId);
            int typeId = Integer.parseInt(request.params("id"));
            StoreType storeType = storeTypeDao.getById(typeId);
            storeTypeDao.addStoreTypeToStore(storeType, retailStore);
            return gson.toJson(storeType);
        });

        //READ
        get("/storetype", "application/json", (req, res) -> { //accept a request in format JSON from an app
            return gson.toJson(storeTypeDao.getAll());//send it back to be displayed
        });

        get("/storetype/:id", "application/json", (req, res) -> { //accept a request in format JSON from an app
            int storetypeId = Integer.parseInt(req.params("id"));
            return gson.toJson(storeTypeDao.getAllStoresForStoreType(storetypeId));
        });

        //Create Store with a Contact
        post("/contact/new", "application", (request, response) -> {
            StoreContact storeContact = gson.fromJson(request.body(), StoreContact.class);
            storeContactDoa.add(storeContact);
            response.status(201);
            return gson.toJson(storeContact);
        });
        //Read
        get("/contact", "application/json", (request, response) -> gson.toJson(storeContactDoa.getAll()));

        get("/contact/:id", "application/json", (request, response) -> {
            int contactStoreId = Integer.parseInt(request.params("id"));
            return gson.toJson(storeContactDoa.findContactById(contactStoreId));
        });


        //FILTERS
        after((req, res) ->{
            res.type("application/json");
        });

        exception(ApiException.class, (exc, req, res) -> {
            ApiException err = (ApiException) exc;
            Map<String, Object> jsonMap = new HashMap<>();
            jsonMap.put("status", err.getStatusCode());
            jsonMap.put("errorMessage", err.getMessage());
            res.type("application/json"); //after does not run in case of an exception.
            res.status(err.getStatusCode()); //set the status
            res.body(gson.toJson(jsonMap));  //set the output.
        });

    }
}
