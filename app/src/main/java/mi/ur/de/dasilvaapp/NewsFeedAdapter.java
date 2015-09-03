package mi.ur.de.dasilvaapp;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by blu on 01.09.2015.
 */
public class NewsFeedAdapter extends ArrayAdapter<NewsFeedItem> {
    private Context context;
    private ArrayList<NewsFeedItem> newsFeedItems;
    private int newsFeedItemView;

    public NewsFeedAdapter(Context context, ArrayList<NewsFeedItem> newsFeedItems){
        super(context, R.layout.newsfeed_item, newsFeedItems);
        this.context = context;
        this.newsFeedItems = newsFeedItems;
        this.newsFeedItemView = R.layout.newsfeed_item;
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
    public View getView(int position, View convertView, ViewGroup parent) {
        View actualNewsFeedItemView;
        if(convertView == null){
            LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            actualNewsFeedItemView = inflater.inflate(newsFeedItemView, null);
        }
        else{
            actualNewsFeedItemView = convertView;
        }

        NewsFeedItem newsFeedItem = newsFeedItems.get(position);

        if(newsFeedItem != null) {

            TextView story = (TextView) actualNewsFeedItemView.findViewById(R.id.story);
            TextView message = (TextView) actualNewsFeedItemView.findViewById(R.id.message);
            TextView time = (TextView) actualNewsFeedItemView.findViewById(R.id.time_stamp);
            story.setText(newsFeedItems.get(position).getStory());
            message.setText(newsFeedItems.get(position).getMessage());
            time.setText(newsFeedItems.get(position).getMessage());
        }

        return actualNewsFeedItemView;
    }
}
