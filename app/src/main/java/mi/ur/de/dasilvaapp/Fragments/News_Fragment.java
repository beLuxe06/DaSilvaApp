package mi.ur.de.dasilvaapp.Fragments;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ProgressBar;

import java.util.ArrayList;

import mi.ur.de.dasilvaapp.DownloadListener;
import mi.ur.de.dasilvaapp.HomeActivity;
import mi.ur.de.dasilvaapp.NewsFeed.NewsFeedAdapter;
import mi.ur.de.dasilvaapp.NewsFeed.NewsFeedDownloadTask;
import mi.ur.de.dasilvaapp.NewsFeed.NewsFeedItem;
import mi.ur.de.dasilvaapp.R;

/**
 * Created by blu on 17.08.2015.
 */

// Getting News Feed From Facebook
// https://developers.facebook.com/docs/graph-api/reference/v2.4/page/feed
public class News_Fragment extends Fragment implements DownloadListener {

    private static final String ARG_SECTION_NUMBER = "section_number";

    private ArrayList<NewsFeedItem> newsFeedItems = new ArrayList<>();
    private NewsFeedAdapter news_feed_items_adapter;

    public ProgressBar progressBar;

   // Facebook Access Token:
   // https://smashballoon.com/custom-facebook-feed/access-token/
    // Facebook-GraphAPI Link bekommen
    // https://developers.facebook.com/tools/explorer/145634995501895

    private final static String ADDRESS = "https://graph.facebook.com/58336779060/posts?fields=id,created_time,link,story,message,full_picture&access_token=504302586404216|WUO3JsCn9BioDFifJv0hpgzaiRE";

    @Override
    public void onStart() {
        super.onStart();
        initAdapter();
        initUI();
        fetchDataFromFacebook();
    }

    private void fetchDataFromFacebook() {
        newsFeedItems.clear();
        new NewsFeedDownloadTask(getActivity(), this, newsFeedItems).execute(ADDRESS);
    }


    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void onResume() {
        super.onResume();
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
        newsFeed.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long ViewID) {
            }
        });
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

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_news, container, false);
    }
}
