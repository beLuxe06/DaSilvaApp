package mi.ur.de.dasilvaapp.Fragments;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import mi.ur.de.dasilvaapp.HomeActivity;
import mi.ur.de.dasilvaapp.R;
import mi.ur.de.dasilvaapp.ViewPagerAdapter;

/**
 * Created by blu on 17.08.2015.
 */
public class Location_Fragment extends Fragment {

    ViewPager locationImageViewPager;
    PagerAdapter locationImageViewPagerAdapter;
    int[] imageLinks;

    TextView locationImageIndicator;
    TextView locationNumberOfImages;

    private static final String ARG_SECTION_NUMBER = "section_number";

    @Override
    public void onStart() {
        super.onStart();
        initPictureLinksArray();
        initUi();
    }

    @Override
    public void onResume() {
        super.onResume();
        locationImageIndicator.setText(Integer.toString(locationImageViewPager.getCurrentItem() + 1));
    }

    private void initPictureLinksArray() {
        imageLinks = new int[]{R.drawable.location_01, R.drawable.location_02, R.drawable.location_03, R.drawable.location_04};
    }

    private void initUi() {
        initViewPager();
        initTextViews();
    }

    private void initTextViews() {
        locationImageIndicator = (TextView) getView().findViewById(R.id.indicator);
        locationNumberOfImages = (TextView) getView().findViewById(R.id.number_of_images);
        locationNumberOfImages.setText(Integer.toString(imageLinks.length));
    }

    private void initViewPager() {
        locationImageViewPager = (ViewPager) getView().findViewById(R.id.location_image_view_pager);
        locationImageViewPagerAdapter = new ViewPagerAdapter(getActivity(), imageLinks);
        locationImageViewPager.setAdapter(locationImageViewPagerAdapter);
        initViewPagerOnChangeListener();
    }

    private void initViewPagerOnChangeListener() {
        ViewPager.OnPageChangeListener onPageChangeListener = new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                locationImageIndicator.setText(Integer.toString(position + 1));
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        };
        locationImageViewPager.setOnPageChangeListener(onPageChangeListener);
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
