package models;

/**
 * Created by Guest on 8/25/17.
 */
public class StoreContact extends RetailStore {
    private String contactInfo;

    public StoreContact (String storeName, String retailType, int yearEstablished, String contact, String contactInfo){
        super(storeName, retailType, yearEstablished, contact);
        this.contactInfo = contactInfo;
    }

    //Getter
    public String getContactInfo() {
        return contactInfo;
    }

    //Setter
    public void setContactInfo(String contactInfo) {
        this.contactInfo = contactInfo;
    }

    //Equals and HashCode
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;

        StoreContact that = (StoreContact) o;

        return contactInfo.equals(that.contactInfo);
    }
    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + contactInfo.hashCode();
        return result;
    }
}
