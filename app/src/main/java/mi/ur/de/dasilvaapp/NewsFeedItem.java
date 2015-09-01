package mi.ur.de.dasilvaapp;

/**
 * Created by blu on 01.09.2015.
 */
public class NewsFeedItem {

    private final long id;
    private final String foodieDataName;
    private final String foodieTitle;
    private final float foodieRating;

    public NewsFeedItem(long id, String foodieDataName, String foodieTitle, float foodieRating) {
        this.id = id;
        this.foodieDataName = foodieDataName;
        this.foodieTitle = foodieTitle;
        this.foodieRating = foodieRating;
    }

    public long getID(){
        return id;
    }

    public String getFoodieDataName(){
        return foodieDataName;
    }

    public String getFoodieTitle(){
        return foodieTitle;
    }

    public float getFoodieRating(){
        return foodieRating;
    }

}
