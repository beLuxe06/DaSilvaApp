package mi.ur.de.dasilvaapp;

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

/**
 * Created by blu on 03.09.2015.
 */
public class EventsDownloadTask extends AsyncTask<String, Integer, String> {

    private static final String DATA = "data";
    private static final String FACEBOOK_ID = "id";
    private static final String NAME = "name";
    private static final String START_TIME = "start_time";
    private static final String END_TIME = "end_time";
    private static final String DESCRIPTION = "description";
    private static final String URL = "url";

    private ArrayList<DaSilvaEvent> events;
    private DownloadListener listener;
    private Context context;

    @Override
    protected void onPreExecute() {
        listener.onDownloadStarted();
        super.onPreExecute();
    }


    @Override
    protected void onProgressUpdate(Integer... values) {
        listener.onDownloadInProgress();
        super.onProgressUpdate(values);
    }

    public EventsDownloadTask(Context context, DownloadListener listener, ArrayList<DaSilvaEvent> events) {
        this.context = context;
        this.listener = listener;
        this.events = events;
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
            JSONObject event = new JSONObject(text);
            JSONArray jsonArray = event.getJSONArray(DATA);
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                String facebookID = jsonObject.getString(FACEBOOK_ID);
                String name = jsonObject.getString(NAME);
                String date = jsonObject.getString(START_TIME).substring(0, 10);
                String openingTime = jsonObject.getString(START_TIME);
                // endTime may be null
                String closingTime;
                try {
                    closingTime = jsonObject.getString(END_TIME);
                } catch (JSONException e) {
                    closingTime = null;
                }
                // description may be null
                String description;
                try {
                    description = jsonObject.getString(DESCRIPTION);
                } catch (JSONException e) {
                    description = null;
                }
                // imageURL may be null
                String imageURL;
                try {
                    imageURL = jsonObject.getString(URL);
                } catch (JSONException e) {
                    imageURL = null;
                }

                DaSilvaEvent newEvent = new DaSilvaEvent(0, facebookID, name, date, openingTime, closingTime, description, imageURL);
                events.add(newEvent);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
