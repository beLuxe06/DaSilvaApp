package mi.ur.de.dasilvaapp.Fragments;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ProgressBar;

import java.util.ArrayList;

import mi.ur.de.dasilvaapp.DaSilvaGallery;
import mi.ur.de.dasilvaapp.DownloadListener;
import mi.ur.de.dasilvaapp.GalleryAdapter;
import mi.ur.de.dasilvaapp.GalleryDownloadTask;
import mi.ur.de.dasilvaapp.HomeActivity;
import mi.ur.de.dasilvaapp.R;

/**
 * Created by blu on 17.08.2015.
 */
public class Gallery_Fragment extends Fragment implements DownloadListener {

    private static final String ARG_SECTION_NUMBER = "section_number";

    private ArrayList<DaSilvaGallery> galleryItems = new ArrayList<>();
    private GalleryAdapter gallery_items_adapter;

    public ProgressBar progressBar;

    private final static String ADDRESS = "https://graph.facebook.com/58336779060/albums?fields=id,link,backdated_time,name,picture{url}&access_token=504302586404216|WUO3JsCn9BioDFifJv0hpgzaiRE";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_gallery, container, false);
    }

    @Override
    public void onStart() {
        super.onStart();
        initAdapter();
        initUI();
        fetchDataFromFacebook();
    }

    private void fetchDataFromFacebook() {
        galleryItems.clear();
        new GalleryDownloadTask(getActivity(), this, galleryItems).execute(ADDRESS);
    }

    private void initUI() {
        initListView();
    }

    private void initListView() {
        ListView galleries = (ListView) getView().findViewById(R.id.gallery);
        galleries.setAdapter(gallery_items_adapter);
        galleries.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                startGalleryDetailFragment(galleryItems.get(position));
            }
        });
    }

    private void startGalleryDetailFragment(DaSilvaGallery daSilvaGallery) {
        Bundle imageResource = new Bundle();
        imageResource.putString(Gallery_Detail_Fragment.FACEBOOK_ID_KEY, daSilvaGallery.getFacebookId());
        imageResource.putString(Gallery_Detail_Fragment.EVENT_NAME_KEY, daSilvaGallery.getEventName());
        imageResource.putString(Gallery_Detail_Fragment.EVENT_DATE_KEY, daSilvaGallery.getEventDate());
        Fragment newFragment = Gallery_Detail_Fragment.newInstance(imageResource);
        FragmentManager fragmentManager = getFragmentManager();
        fragmentManager.beginTransaction().addToBackStack("detail").replace(R.id.container, newFragment).commit();
    }

    private void initAdapter() {
        gallery_items_adapter = new GalleryAdapter(getActivity(), galleryItems);
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        ((HomeActivity) activity).onSectionAttached(
                getArguments().getInt(ARG_SECTION_NUMBER));
    }

    public static Gallery_Fragment newInstance(int sectionNumber) {
        Gallery_Fragment fragment = new Gallery_Fragment();
        Bundle args = new Bundle();
        args.putInt(ARG_SECTION_NUMBER, sectionNumber);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onDownloadFinished() {
        progressBar.setVisibility(View.GONE);
        gallery_items_adapter.notifyDataSetChanged();
    }

    @Override
    public void onDownloadStarted() {
        initProgressBar();
        progressBar.setVisibility(View.VISIBLE);

    }

    public void initProgressBar() {
        progressBar = (ProgressBar) getView().findViewById(R.id.gallery_progress_bar);
    }


    @Override
    public void onDownloadInProgress() {

    }
}
