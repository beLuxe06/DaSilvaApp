package mi.ur.de.dasilvaapp.Fragments;

import android.app.Activity;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.res.ResourcesCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageSwitcher;
import android.widget.ImageView;
import android.widget.Toast;
import android.widget.ViewSwitcher;

import java.util.ArrayList;

import mi.ur.de.dasilvaapp.HomeActivity;
import mi.ur.de.dasilvaapp.R;
import mi.ur.de.dasilvaapp.SwipeDetect;

/**
 * Created by blu on 17.08.2015.
 */
public class Location_Fragment extends Fragment {

    ImageSwitcher locationPictureSwitcher;
    ArrayList<Integer> pictureLinks = new ArrayList<>();
    int imageIndex = 0;

    private static final String ARG_SECTION_NUMBER = "section_number";

    @Override
    public void onStart() {
        super.onStart();
        initPictureLinksArray();
        initUi();
        initOnSwipeListener();
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    private void initOnSwipeListener() {
        locationPictureSwitcher.setOnTouchListener(new SwipeDetect() {
            public void onSwipeRight() {
                locationPictureSwitcher.setInAnimation(getActivity(), R.anim.slide_in_left);
                locationPictureSwitcher.setOutAnimation(getActivity(), R.anim.slide_out_left);
                if (imageIndex < pictureLinks.size() - 1) {
                    imageIndex++;
                    locationPictureSwitcher.setImageResource(pictureLinks.get(imageIndex));
                }
            }

            public void onSwipeLeft() {
                locationPictureSwitcher.setInAnimation(getActivity(), R.anim.slide_in_right);
                locationPictureSwitcher.setOutAnimation(getActivity(), R.anim.slide_out_right);
                if (imageIndex > 0) {
                    imageIndex--;
                    locationPictureSwitcher.setImageResource(pictureLinks.get(imageIndex));
                }
            }
        });
    }

    private void initPictureLinksArray() {
        pictureLinks.add(R.drawable.location_01);
        pictureLinks.add(R.drawable.location_02);
        pictureLinks.add(R.drawable.location_03);
        pictureLinks.add(R.drawable.location_04);
    }

    private void initUi() {
        initImageSwitcher();
    }

    private void initImageSwitcher() {
        locationPictureSwitcher = (ImageSwitcher) getView().findViewById(R.id.location_picture);
        locationPictureSwitcher.setFactory(new ViewSwitcher.ViewFactory() {
            public View makeView() {
                ImageView imageView = new ImageView(getActivity());
                imageView.setScaleType(ImageView.ScaleType.FIT_CENTER);
                imageView.setLayoutParams(new ImageSwitcher.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
                return imageView;
            }
        });

        locationPictureSwitcher.setImageResource(pictureLinks.get(imageIndex));
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_location, container, false);
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        ((HomeActivity) activity).onSectionAttached(
                getArguments().getInt(ARG_SECTION_NUMBER));
    }

    public static Location_Fragment newInstance(int sectionNumber) {
        Location_Fragment fragment = new Location_Fragment();
        Bundle args = new Bundle();
        args.putInt(ARG_SECTION_NUMBER, sectionNumber);
        fragment.setArguments(args);
        return fragment;
    }

}
