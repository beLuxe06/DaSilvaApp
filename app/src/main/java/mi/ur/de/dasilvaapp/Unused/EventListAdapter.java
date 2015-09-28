/**package mi.ur.de.dasilvaapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Date;


/**
 * Created by blu on 01.09.2015.
 */
/**public class EventListAdapter extends ArrayAdapter<DaSilvaEvent> {
    private Context context;
    private ArrayList<DaSilvaEvent> events;
    private int EventView;
    private ActualCalendarProperties calendarProperties;

    public EventListAdapter(Context context, ArrayList<DaSilvaEvent> events){
        super(context, null, events);
        this.context = context;
        this.events = events;
        this.eventItemView = R.layout.newsfeed_item;
    }

    @Override
    public int getCount() {
        return events.size();
    }

    @Override
    public DaSilvaEvent getItem(int position) {
        return events.get(position);
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
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

            ImageView image = (ImageView) actualNewsFeedItemView.findViewById(R.id.news_feed_image);
            TextView time = (TextView) actualNewsFeedItemView.findViewById(R.id.time_stamp);
            TextView story = (TextView) actualNewsFeedItemView.findViewById(R.id.story);
            TextView message = (TextView) actualNewsFeedItemView.findViewById(R.id.message);
            TextView link = (TextView) actualNewsFeedItemView.findViewById(R.id.link_to_facebook_entry);
            new ImageLoadTask(newsFeedItem.getImageURL(), image).execute();
            setUpCalendarProperties(context);
            String actualTimeAgoString = calendarProperties.getFormattedTimeAgoString(newsFeedItem.getCreatedTimestamp());
            time.setText(actualTimeAgoString);
            story.setText(newsFeedItem.getStory());
            message.setText(newsFeedItem.getMessage());
            link.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    try {
                        URL linkToFacebookPost = new URL(newsFeedItems.get(position).getLink());
                    } catch (MalformedURLException e) {
                        e.printStackTrace();
                    }
                }
            });
        }

        return actualNewsFeedItemView;
    }


    private void hideTextViewIfEmptyString(TextView textView, String string) {
        if(string == null || string.isEmpty()){
            textView.setVisibility(View.GONE);
        }
    }


    private void setUpCalendarProperties(Context context) {
        calendarProperties = new ActualCalendarProperties(context);
    }
}
*/
