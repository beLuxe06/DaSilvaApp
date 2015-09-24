package mi.ur.de.dasilvaapp;

import java.util.Date;

/**
 * Created by blu on 11.09.2015.
 */
public class DaSilvaEvent {

    private final long id;
    private final long facebookId;
    private final String name;
    private final String openingTime;
    private final String closingTime;
    private final String description;
    private final String imageURL;

    public DaSilvaEvent(long id, long facebookId, String name, String openingTime, String closingTime, String description, String imageURL) {
        this.id = id;
        this.facebookId = facebookId;
        this.name = name;
        this.openingTime = openingTime;
        this.closingTime = closingTime;
        this.description = description;
        this.imageURL = imageURL;
    }

    public long getId(){
        return id;
    }

    public long getFacebookId(){
        return facebookId;
    }

    public String getName() {
        return name;
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



