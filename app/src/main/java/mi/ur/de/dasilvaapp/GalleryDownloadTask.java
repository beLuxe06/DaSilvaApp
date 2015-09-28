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
 * Created by blu on 25.09.2015.
 */
public class GalleryDownloadTask extends AsyncTask<String, Integer, String> {

    private static final String DATA = "data";
    private static final String FACEBOOK_ID = "id";
    private static final String NAME = "name";
    private static final String DATE = "backdated_time";
    private static final String LINK = "link";
    private static final String URL = "url";

    private ArrayList<DaSilvaGallery> galleries;
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

    public GalleryDownloadTask(Context context, DownloadListener listener, ArrayList<DaSilvaGallery> galleries) {
        this.context = context;
        this.listener = listener;
        this.galleries = galleries;
    }

    @Override
    protected String doInBackground(String... params) {
        String jsonString = "";

        try {
            java.net.URL url = new URL(params[0]);

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
            JSONObject gallery = new JSONObject(text);
            JSONArray jsonArray = gallery.getJSONArray(DATA);
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                String facebookID = jsonObject.getString(FACEBOOK_ID);
                String name;
                // name for pictures is null
                try {
                    name = jsonObject.getString(NAME);
                } catch (JSONException e) {
                    name = null;
                }
                String link = jsonObject.getString(LINK);
                // date may be null
                String date;
                try {
                    date = jsonObject.getString(DATE).substring(0, 10);
                } catch (JSONException e) {
                    date = null;
                }

                // imageURL may be null
                String imageURL = null;
                try {
                    imageURL = jsonObject.getJSONObject("picture").getJSONObject("data").getString(URL);
                } catch (JSONException e) {
                    try {
                        imageURL = jsonObject.getString("picture");
                    } catch (JSONException ex) {
                        imageURL = null;
                    }
                }

                if ((date != null)) {
                    DaSilvaGallery newGallery = new DaSilvaGallery(0, facebookID, name, date, imageURL, link);
                    galleries.add(newGallery);
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
