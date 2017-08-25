package models;

/**
 * Created by Guest on 8/25/17.
 */
public class RetailStore {
    private String storeName;
    private String retailType;
    private int yearEstablished;
    private String contact;
    private int id;

    public RetailStore(String storeName, String retailType, int yearEstablished, String contact){
        this.storeName = storeName;
        this.retailType= retailType;
        this.yearEstablished = yearEstablished;
        this.contact = contact;
    }

    //Getters

    public String getStoreName() {
        return storeName;
    }
    public String getRetailType() {
        return retailType;
    }
    public int getYearEstablished() {
        return yearEstablished;
    }
    public String getContact() {
        return contact;
    }
    public int getId() {
        return id;
    }

    //Setters
    public void setStoreName(String storeName) {
        this.storeName = storeName;
    }
    public void setRetailType(String retailType) {
        this.retailType = retailType;
    }
    public void setYearEstablished(int yearEstablished) {
        this.yearEstablished = yearEstablished;
    }
    public void setContact(String contact) {
        this.contact = contact;
    }
    public void setId(int id) {
        this.id = id;
    }

    //Equals and HashCode

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        RetailStore that = (RetailStore) o;

        if (yearEstablished != that.yearEstablished) return false;
        if (!storeName.equals(that.storeName)) return false;
        if (!retailType.equals(that.retailType)) return false;
        return contact.equals(that.contact);
    }

    @Override
    public int hashCode() {
        int result = storeName.hashCode();
        result = 31 * result + retailType.hashCode();
        result = 31 * result + yearEstablished;
        result = 31 * result + contact.hashCode();
        return result;
    }
}
