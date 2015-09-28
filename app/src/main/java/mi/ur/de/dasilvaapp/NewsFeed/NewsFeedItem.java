package mi.ur.de.dasilvaapp.NewsFeed;

import android.content.Context;
import android.os.AsyncTask;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

import mi.ur.de.dasilvaapp.DownloadListener;

/**
 * Created by blu on 01.09.2015.
 */
public class NewsFeedItem {

    private final String facebookID;
    private final String createdTimestamp;
    private final String link;
    private final String story;
    private final String message;
    private final String imageURL;

    public NewsFeedItem(String facebookID, String createdTimestamp, String link, String story, String message, String imageURL) {
        this.facebookID = facebookID;
        this.createdTimestamp = createdTimestamp;
        this.link = link;
        this.story = story;
        this.message = message;
        this.imageURL = imageURL;
    }

    public String getFacebookID() {
        return facebookID;
    }

    public String getCreatedTimestamp() {
        return createdTimestamp;
    }

    public String getLink() {
        return link;
    }

    public String getStory() {
        return story;
    }

    public String getMessage() {
        return message;
    }

    public String getImageURL() {
        return imageURL;
    }
}