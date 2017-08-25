package models;

/**
 * Created by Guest on 8/25/17.
 */
public class BlogFeatures {
    private String writtenBy;
    private int rating;
    private int storeId;
    private int id;

    public BlogFeatures(String writtenBy, int rating, int storeId){
        this.writtenBy = writtenBy;
        this.rating = rating;
        this.storeId = storeId;
    }

    //Getters
    public String getWrittenBy() {
        return writtenBy;
    }
    public int getRating() {
        return rating;
    }
    public int getStoreId() {
        return storeId;
    }
    public int getId() {
        return id;
    }

    //Setters

    public void setWrittenBy(String writtenBy) {
        this.writtenBy = writtenBy;
    }
    public void setRating(int rating) {
        this.rating = rating;
    }
    public void setStoreId(int storeId) {
        this.storeId = storeId;
    }
    public void setId(int id) {
        this.id = id;
    }

    //Equals and HashCode
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        BlogFeatures that = (BlogFeatures) o;

        if (rating != that.rating) return false;
        if (storeId != that.storeId) return false;
        return writtenBy.equals(that.writtenBy);
    }

    @Override
    public int hashCode() {
        int result = writtenBy.hashCode();
        result = 31 * result + rating;
        result = 31 * result + storeId;
        return result;
    }
}
