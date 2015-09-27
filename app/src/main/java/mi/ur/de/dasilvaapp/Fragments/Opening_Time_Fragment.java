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

import mi.ur.de.dasilvaapp.DaSilvaGallery;
import mi.ur.de.dasilvaapp.DownloadListener;
import mi.ur.de.dasilvaapp.GalleryDetailAdapter;
import mi.ur.de.dasilvaapp.GalleryDownloadTask;
import mi.ur.de.dasilvaapp.R;

/**
 * Created by blu on 17.08.2015.
 */
public class Opening_Time_Fragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_opening_times, container, false);
        return rootView;
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    public static Opening_Time_Fragment newInstance() {
        Opening_Time_Fragment fragment = new Opening_Time_Fragment();
        return fragment;
    }
}
