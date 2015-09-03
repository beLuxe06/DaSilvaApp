package mi.ur.de.dasilvaapp;

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

/**
 * Created by blu on 03.09.2015.
 */
public class NewsFeedDownloadTask extends AsyncTask<String, Integer, String> {


    private static final String FACEBOOK_ID = "id";
    private static final String CREATED_TIME= "created_time";
    private static final String STORY = "story";
    private static final String MESSAGE = "message";

    private ArrayList<NewsFeedItem> newsFeed;
    private DownloadListener listener;

    public NewsFeedDownloadTask(DownloadListener listener, ArrayList<NewsFeedItem> newsFeed) {
        this.listener = listener;
        this.newsFeed = newsFeed;
    }

    @Override
    protected String doInBackground(String... params) {
        String jsonString = "";

        try {
            URL url = new URL(params[0]);

            HttpURLConnection conn = (HttpURLConnection) url.openConnection();

            int responseCode = conn.getResponseCode();

            if (responseCode == HttpURLConnection.HTTP_OK) {

                InputStream is = conn.getInputStream();
                BufferedReader br = new BufferedReader(new InputStreamReader(is));

                String line;

                while ((line = br.readLine()) != null) {
                    jsonString += line;
                }

                br.close();
                is.close();
                conn.disconnect();

            } else {
                throw new IllegalStateException("HTTP response: " + responseCode);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return jsonString;
    }

    @Override
    protected void onPostExecute(String result) {
        super.onPostExecute(result);
        processJson(result);
        listener.onDownloadFinished();
    }

    private void processJson(String text) {
        try {
            JSONArray jsonArray = new JSONArray(text);
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                String facebookID = jsonObject.getString(FACEBOOK_ID);
                String createdTime = jsonObject.getString(CREATED_TIME);
                // story may be null
                String story;
                try{
                    story = jsonObject.getString(STORY);
                }
                catch(JSONException e){
                    story = null;
                }
                // message may be null
                String message;
                try{
                    message = jsonObject.getString(MESSAGE);
                }
                catch(JSONException e){
                    message = null;
                }
                NewsFeedItem item = new NewsFeedItem(0,facebookID, createdTime, story, message, null);
                newsFeed.add(item);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
