package mi.ur.de.dasilvaapp;

import android.content.Context;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.ArrayList;


/**
 * Created by blu on 01.09.2015.
 */
public class NewsFeedAdapter extends ArrayAdapter<NewsFeedItem> {
    private Context context;
    private ArrayList<NewsFeedItem> newsFeedItems;
    private int newsFeedItemView;
    private DateHelper dh;

    public NewsFeedAdapter(Context context, ArrayList<NewsFeedItem> newsFeedItems) {
        super(context, R.layout.newsfeed_item, newsFeedItems);
        this.context = context;
        this.newsFeedItems = newsFeedItems;
        this.newsFeedItemView = R.layout.newsfeed_item;
        dh = new DateHelper(context);
    }

    @Override
    public int getCount() {
        return newsFeedItems.size();
    }

    @Override
    public NewsFeedItem getItem(int position) {
        return newsFeedItems.get(position);
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        View actualNewsFeedItemView;
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            actualNewsFeedItemView = inflater.inflate(newsFeedItemView, null);
        } else {
            actualNewsFeedItemView = convertView;

        }

        final NewsFeedItem newsFeedItem = newsFeedItems.get(position);

        if (newsFeedItem != null) {

            ProgressBar progressBar = (ProgressBar) actualNewsFeedItemView.findViewById(R.id.news_feed_item_progress_bar);
            ImageView image = (ImageView) actualNewsFeedItemView.findViewById(R.id.news_feed_image);
            TextView time = (TextView) actualNewsFeedItemView.findViewById(R.id.time_stamp);
            TextView story = (TextView) actualNewsFeedItemView.findViewById(R.id.story);
            TextView message = (TextView) actualNewsFeedItemView.findViewById(R.id.message);
            new ImageLoadTask(newsFeedItem.getImageURL(), image, progressBar).execute();
            Timestamp timestamp = new Timestamp(newsFeedItem.getCreatedTimestamp());
            String actualTimeAgoString = dh.getFormattedTimeAgoString(timestamp);
            time.setText(actualTimeAgoString);
            story.setText(newsFeedItem.getStory());
            message.setText(newsFeedItem.getMessage());
        }

        return actualNewsFeedItemView;
    }
}
