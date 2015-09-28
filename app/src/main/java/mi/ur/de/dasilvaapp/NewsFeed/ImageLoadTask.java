package mi.ur.de.dasilvaapp.NewsFeed;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by blu on 03.09.2015.
 */

// ImageView aus URL anzeigen, bzw. Download Bitmap asynchron
//        http://stackoverflow.com/questions/18953632/how-to-set-image-from-url-for-imageview
public class ImageLoadTask extends AsyncTask<Void, Void, Bitmap> {

    private String url;
    private ImageView imageView;
    private ProgressBar progressBar;

    public ImageLoadTask(String url, ImageView imageView, ProgressBar progressBar) {
        this.url = url;
        this.imageView = imageView;
        this.progressBar = progressBar;
    }

    @Override
    protected Bitmap doInBackground(Void... params) {
        try {
            URL urlConnection = new URL(url);
            HttpURLConnection connection = (HttpURLConnection) urlConnection
                    .openConnection();
            connection.setDoInput(true);
            connection.connect();
            InputStream input = connection.getInputStream();
            return BitmapFactory.decodeStream(input);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected void onPostExecute(Bitmap result) {
        imageView.setImageBitmap(result);
        progressBar.setVisibility(View.GONE);
        super.onPostExecute(result);
    }

}