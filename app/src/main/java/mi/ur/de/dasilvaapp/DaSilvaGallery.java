package mi.ur.de.dasilvaapp;

/**
 * Created by blu on 25.09.2015.
 */
public class DaSilvaGallery {

    private final long id;
    private final String facebookId;
    private final String eventName;
    private final String eventDate;
    private final String imageURL;
    private final String link;

    public DaSilvaGallery(long id, String facebookId, String eventName, String eventDate, String imageURL, String link) {
        this.id = id;
        this.facebookId = facebookId;
        this.eventName = eventName;
        this.eventDate = eventDate;
        this.imageURL = imageURL;
        this.link = link;
    }

    public long getId() {
        return id;
    }

    public String getFacebookId() {
        return facebookId;
    }

    public String getEventName() {
        return eventName;
    }

    public String getEventDate() {
        return eventDate;
    }

    public String getImageURL() {
        return imageURL;
    }

    public String getLink() {
        return link;
    }

}
