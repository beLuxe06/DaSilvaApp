package mi.ur.de.dasilvaapp;

import java.util.Date;

/**
 * Created by blu on 11.09.2015.
 */
public class DaSilvaEvent {

    private final long id;
    private final String facebookId;
    private final String name;
    private final String date;
    private final String openingTime;
    private final String closingTime;
    private final String description;
    private final String imageURL;

    public DaSilvaEvent(long id, String facebookId, String name, String date, String openingTime, String closingTime, String description, String imageURL) {
        this.id = id;
        this.facebookId = facebookId;
        this.name = name;
        this.date = date;
        this.openingTime = openingTime;
        this.closingTime = closingTime;
        this.description = description;
        this.imageURL = imageURL;
    }

    public long getId(){
        return id;
    }

    public String getFacebookId(){
        return facebookId;
    }

    public String getName() {
        return name;
    }

    public String getDate() {
        return date;
    }

    public String getOpeningTime() {
        return openingTime;
    }

    public String getClosingTime() {
        return closingTime;
    }

    public String getDescription() {
        return description;
    }

    public String getImageURL() {
        return imageURL;
    }
}



