package mi.ur.de.dasilvaapp.Fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import mi.ur.de.dasilvaapp.R;

/**
 * Created by blu on 17.08.2015.
 */
public class Impressum_Fragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_impressum, container, false);
        return rootView;
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    public static Impressum_Fragment newInstance() {
        Impressum_Fragment fragment = new Impressum_Fragment();
        return fragment;
    }
}
