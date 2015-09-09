package mi.ur.de.dasilvaapp.Fragments;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.ProgressBar;

import com.facebook.AccessToken;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.HttpMethod;

import org.json.JSONArray;

import java.util.ArrayList;

import mi.ur.de.dasilvaapp.DownloadListener;
import mi.ur.de.dasilvaapp.HomeActivity;
import mi.ur.de.dasilvaapp.NewsFeedAdapter;
import mi.ur.de.dasilvaapp.NewsFeedDatabase;
import mi.ur.de.dasilvaapp.NewsFeedDownloadTask;
import mi.ur.de.dasilvaapp.NewsFeedItem;
import mi.ur.de.dasilvaapp.R;

import static com.facebook.AccessToken.setCurrentAccessToken;

/**
 * Created by blu on 17.08.2015.
 */
public class News_Fragment extends Fragment implements DownloadListener {

    private static final String ARG_SECTION_NUMBER = "section_number";

    private ArrayList<NewsFeedItem> newsFeedItems = new ArrayList<NewsFeedItem>();
    private NewsFeedAdapter news_feed_items_adapter;
    private NewsFeedDatabase db;

    public ProgressBar progressBar;

    private final static String ADDRESS = "https://graph.facebook.com/58336779060/posts?fields=id,created_time,link,story,message,full_picture&access_token=504302586404216|WUO3JsCn9BioDFifJv0hpgzaiRE";
    private final static String IMAGE_ADDRESS_PREFIX = "";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_news, container, false);
        return rootView;
    }

    @Override
    public void onStart() {
        super.onStart();
        // initDatabase();
        initAdapter();
        updateNewsFeedItemsFromDB();
        initUI();
        new NewsFeedDownloadTask(getActivity(),this,newsFeedItems).execute(ADDRESS);
        // sendRequestToFacebook();
    }



    @Override
    public void onPause() {
        super.onPause();
        updateNewsFeedItemsFromDB();
    }

    @Override
    public void onResume() {
        super.onResume();
        updateNewsFeedItemsFromDB();
    }

    @Override
    public void onDestroy() {
        // db.close();
        super.onDestroy();
    }

    private void initUI() {
        initListView();
    }

    @Override
    public void onDownloadInProgress() {

    }

    @Override
    public void onDownloadStarted() {
        initProgressBar();
        progressBar.setVisibility(View.VISIBLE);
    }

    public void initProgressBar() {
        progressBar = (ProgressBar) getView().findViewById(R.id.news_feed_progress_bar);
    }

    private void initListView() {
        ListView newsFeed = (ListView) getView().findViewById(R.id.news_feed);
        newsFeed.setAdapter(news_feed_items_adapter);
        // Perhaps setOnClickListeners ?
    }

    private void updateNewsFeedItemsFromDB() {
      //  newsFeedItems.clear();
      //  newsFeedItems.addAll(db.getAllNewsFeedItems());
      //  news_feed_items_adapter.notifyDataSetChanged();
    }

    private void initDatabase() {
        db = new NewsFeedDatabase(getActivity());
        db.open();
    }


    private void initAdapter() {
        news_feed_items_adapter = new NewsFeedAdapter(getActivity(), newsFeedItems);
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        ((HomeActivity) activity).onSectionAttached(
                getArguments().getInt(ARG_SECTION_NUMBER));
    }

    public static News_Fragment newInstance(int sectionNumber) {
        News_Fragment fragment = new News_Fragment();
        Bundle args = new Bundle();
        args.putInt(ARG_SECTION_NUMBER, sectionNumber);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onDownloadFinished() {
        progressBar.setVisibility(View.GONE);
        news_feed_items_adapter.notifyDataSetChanged();
    }
}
