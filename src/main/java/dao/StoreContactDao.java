package dao;

import models.StoreContact;

import java.util.List;

/**
 * Created by Guest on 8/25/17.
 */
public interface StoreContactDao {
    //Create
    void add (StoreContact storeContact);

    //Read
    List<StoreContact> getAll();

    StoreContact findContactById(int id);

    //Update
    void updateRetailContact(String storeName, String retailType, int yearEstablished, String contactInfo, int id);

    //Delete
    void deleteAllStoresByContact(String contactInfo);
    void deleteContactById(int id);

}
