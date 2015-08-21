package mi.ur.de.dasilvaapp.Fragments;

import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.res.ResourcesCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import java.util.ArrayList;

import mi.ur.de.dasilvaapp.R;
import mi.ur.de.dasilvaapp.SwipeDetect;

/**
 * Created by blu on 17.08.2015.
 */
public class Location_Fragment extends Fragment {

    ImageView locationPicture;
    ArrayList<Integer> pictureLinks = new ArrayList<>();
    int imageIndex = 0;

    @Override
    public void onStart() {
        super.onStart();
        initUi();
        initPictureLinksArray();
        initOnSwipeListener();
    }

    private void initOnSwipeListener() {
        locationPicture.setOnTouchListener(new SwipeDetect() {
            public void onSwipeRight() {
                if (imageIndex < pictureLinks.size() - 1) {
                    imageIndex++;
                    locationPicture.setImageResource(pictureLinks.get(imageIndex));
                }
            }

            public void onSwipeLeft() {
                if (imageIndex > 0) {
                    imageIndex--;
                    locationPicture.setImageResource(pictureLinks.get(imageIndex));
                }
            }
        });
    }

    private void initPictureLinksArray() {
        pictureLinks.add(R.drawable.dienstag);
        pictureLinks.add(R.drawable.donnerstag);
        pictureLinks.add(R.drawable.freitag);
        pictureLinks.add(R.drawable.samstag);
    }

    private void initUi() {
        locationPicture = (ImageView) getView().findViewById(R.id.location_picture);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_location, container, false);
        return rootView;
    }

}
