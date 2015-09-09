package mi.ur.de.dasilvaapp;

/**
 * Created by blu on 01.09.2015.
 */
public class NewsFeedItem {

    private final long id;
    private final String facebookID;
    private final String createdTimestamp;
    private final String link;
    private final String story;
    private final String message;
    private final String imageURL;

    public NewsFeedItem(long id, String facebookID, String createdTimestamp, String link, String story, String message, String imageURL) {
        this.id = id;
        this.facebookID = facebookID;
        this.createdTimestamp = createdTimestamp;
        this.link = link;
        this.story = story;
        this.message = message;
        this.imageURL = imageURL;
    }

    public long getID(){
        return id;
    }

    public String getFacebookID(){
        return facebookID;
    }

    public String getCreatedTimestamp(){
        return createdTimestamp;
    }

    public String getLink(){
        return link;
    }

    public String getStory(){
        return story;
    }

    public String getMessage(){
        return message;
    }

    public String getImageURL(){
        return imageURL;
    }
}
