package mi.ur.de.dasilvaapp.Fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.ArrayList;

import mi.ur.de.dasilvaapp.Gallery.DaSilvaGallery;
import mi.ur.de.dasilvaapp.DownloadListener;
import mi.ur.de.dasilvaapp.Gallery.GalleryDetailAdapter;
import mi.ur.de.dasilvaapp.Gallery.GalleryDownloadTask;
import mi.ur.de.dasilvaapp.R;

/**
 * Created by blu on 17.08.2015.
 */
public class Gallery_Detail_Fragment extends Fragment implements DownloadListener {

    public static final String FACEBOOK_ID_KEY = "0";
    public static final String EVENT_NAME_KEY = "1";
    public static final String EVENT_DATE_KEY = "2";

    // Facebook Access Token:
    // https://smashballoon.com/custom-facebook-feed/access-token/
    // Facebook-GraphAPI Link bekommen
    // https://developers.facebook.com/tools/explorer/145634995501895
    private static final String ADDRESS_PREFIX = "https://graph.facebook.com/";
    private static final String ADDRESS_SUFFIX = "/photos?fields=id,picture,name,link,backdated_time&access_token=504302586404216|WUO3JsCn9BioDFifJv0hpgzaiRE";

    private GalleryDetailAdapter gallery_detail_items_adapter;
    private ArrayList<DaSilvaGallery> galleryDetailItems = new ArrayList<>();
    private ProgressBar progressBar;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_gallery_detail, container, false);
    }

    @Override
    public void onStart() {
        super.onStart();
        initAdapter();
        initUI();
        fetchImagesFromFacebook();
    }

    private void fetchImagesFromFacebook() {
        galleryDetailItems.clear();
        Bundle bundleImageResources = getArguments();
        if (bundleImageResources != null) {
            String facebookID = bundleImageResources.getString(FACEBOOK_ID_KEY);
            new GalleryDownloadTask(getActivity(), this, galleryDetailItems).execute(getAddress(facebookID));
        }
    }

    private String getAddress(String facebookID) {
        return ADDRESS_PREFIX + facebookID + ADDRESS_SUFFIX;
    }

    private void initAdapter() {
        gallery_detail_items_adapter = new GalleryDetailAdapter(getActivity(), galleryDetailItems);
    }

    private void initUI() {
        initAndUpdateTextViews();
        initListView();
    }


    private void initAndUpdateTextViews() {
        TextView eventName = (TextView) getView().findViewById(R.id.gallery_detail_event_name);
        TextView eventDate = (TextView) getView().findViewById(R.id.gallery_detail_event_date);

        Bundle bundleStringResources = getArguments();
        if (bundleStringResources != null) {
            String eventNameString;
            eventNameString = bundleStringResources.getString(EVENT_NAME_KEY);
            eventName.setText(eventNameString);
            String eventDateString;
            eventDateString = bundleStringResources.getString(EVENT_DATE_KEY);
            eventDate.setText(eventDateString);
        }
    }

    private void initListView() {
        ListView galleries = (ListView) getView().findViewById(R.id.gallery_detail);
        galleries.setAdapter(gallery_detail_items_adapter);
        galleries.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long ViewID) {
                startFullscreenImageFragment(galleryDetailItems.get(position));
            }
        });
    }

    private void startFullscreenImageFragment(DaSilvaGallery daSilvaDetailGalleryImage) {
    }

    public static Gallery_Detail_Fragment newInstance(Bundle bundle) {
        Gallery_Detail_Fragment fragment = new Gallery_Detail_Fragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onDownloadFinished() {
        progressBar.setVisibility(View.GONE);
        gallery_detail_items_adapter.notifyDataSetChanged();
    }

    @Override
    public void onDownloadStarted() {
        initProgressBar();
        progressBar.setVisibility(View.VISIBLE);
    }

    private void initProgressBar() {
        progressBar = (ProgressBar) getView().findViewById(R.id.gallery_detail_progress_bar);
    }

    @Override
    public void onDownloadInProgress() {

    }
}
