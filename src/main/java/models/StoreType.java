package models;

/**
 * Created by Guest on 8/25/17.
 */
public class StoreType {
    private String name;
    private int id;

    public StoreType(String name){
        this.name = name;
    }

    //Getters
    public String getName() {
        return name;
    }
    public int getId() {
        return id;
    }

    //Setters

    public void setName(String name) {
        this.name = name;
    }
    public void setId(int id) {
        this.id = id;
    }

    //Equals and HashCode

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        StoreType storeType = (StoreType) o;

        if (id != storeType.id) return false;
        return name.equals(storeType.name);
    }

    @Override
    public int hashCode() {
        int result = name.hashCode();
        result = 31 * result + id;
        return result;
    }
}
