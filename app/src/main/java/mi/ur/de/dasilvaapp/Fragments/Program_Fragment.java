package mi.ur.de.dasilvaapp.Fragments;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import mi.ur.de.dasilvaapp.HomeActivity;
import mi.ur.de.dasilvaapp.R;
import mi.ur.de.dasilvaapp.ViewPagerAdapter;

/**
 * Created by blu on 17.08.2015.
 */
public class Program_Fragment extends Fragment {

    ViewPager programViewPager;
    PagerAdapter programViewPagerAdapter;
    int[] imageLinks;

    int[] programDescriptions;
    int[] programTitles;

    ImageView previousFlyerProgram;
    ImageView nextFlyerProgram;
    TextView eventDescriptionProgram;
    TextView eventTitleProgram;

    boolean visibilityPreviousFlyerProgram = false;
    boolean visibilityNextFlyerProgram = true;

    private static final String ARG_SECTION_NUMBER = "section_number";

    @Override
    public void onStart() {
        super.onStart();
        initArrays();
        initUi();
        makePreviousAndNextFlyerClickable();
    }

    @Override
    public void onResume() {
        super.onResume();
        refactorFlyers();
    }

    private void refactorFlyers() {
        if (programViewPager.getCurrentItem() > 0) {
            previousFlyerProgram.setImageResource(imageLinks[programViewPager.getCurrentItem() - 1]);
        }
        if (programViewPager.getCurrentItem() < imageLinks.length - 1) {
            nextFlyerProgram.setImageResource(imageLinks[programViewPager.getCurrentItem() + 1]);
        }
        if (visibilityPreviousFlyerProgram) {
            previousFlyerProgram.setVisibility(View.VISIBLE);
            previousFlyerProgram.setClickable(true);
        } else {
            previousFlyerProgram.setVisibility(View.INVISIBLE);
            previousFlyerProgram.setClickable(false);
        }

        if (visibilityNextFlyerProgram) {
            nextFlyerProgram.setVisibility(View.VISIBLE);
            nextFlyerProgram.setClickable(true);
        } else {
            nextFlyerProgram.setVisibility(View.INVISIBLE);
            nextFlyerProgram.setClickable(false);
        }
    }

    private void makePreviousAndNextFlyerClickable() {
        previousFlyerProgram.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                programViewPager.setCurrentItem(programViewPager.getCurrentItem() - 1);
            }
        });
        nextFlyerProgram.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                programViewPager.setCurrentItem(programViewPager.getCurrentItem() + 1);
            }
        });
    }

    private void initUi() {
        initViewPager();
        initViews();
    }

    private void initViews() {
        previousFlyerProgram = (ImageView) getView().findViewById(R.id.previous_flyer_program);
        nextFlyerProgram = (ImageView) getView().findViewById(R.id.next_flyer_program);
        eventDescriptionProgram = (TextView) getView().findViewById(R.id.actual_event_title_program);
        eventTitleProgram = (TextView) getView().findViewById(R.id.actual_event_description_program);
    }

    private void initViewPager() {
        programViewPager = (ViewPager) getView().findViewById(R.id.flyer_switcher_program);
        programViewPagerAdapter = new ViewPagerAdapter(getActivity(), imageLinks);
        programViewPager.setAdapter(programViewPagerAdapter);
        initViewPagerOnChangeListener();
    }

    private void initViewPagerOnChangeListener() {
        ViewPager.OnPageChangeListener onPageChangeListener = new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                eventDescriptionProgram.setText(programDescriptions[position]);
                eventTitleProgram.setText(programTitles[position]);

                previousAndNextFlyer(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        };
        programViewPager.setOnPageChangeListener(onPageChangeListener);
    }

    private void previousAndNextFlyer(int position) {
        if (position != 0) {
            previousFlyerProgram.setVisibility(View.VISIBLE);
            previousFlyerProgram.setClickable(true);
            previousFlyerProgram.setImageResource(imageLinks[position - 1]);
            visibilityPreviousFlyerProgram = true;
        } else {
            previousFlyerProgram.setVisibility(View.INVISIBLE);
            previousFlyerProgram.setClickable(false);
            visibilityPreviousFlyerProgram = false;
        }

        if (position != imageLinks.length - 1) {
            nextFlyerProgram.setVisibility(View.VISIBLE);
            nextFlyerProgram.setClickable(true);
            nextFlyerProgram.setImageResource(imageLinks[position + 1]);
            visibilityNextFlyerProgram = true;
        } else {
            nextFlyerProgram.setVisibility(View.INVISIBLE);
            nextFlyerProgram.setClickable(false);
            visibilityNextFlyerProgram = false;
        }
    }

    private void initArrays() {
        initPictureLinksArray();
        initDescriptionArray();
        initTitleArray();
    }

    private void initPictureLinksArray() {
        imageLinks = new int[]{R.drawable.dienstag_neu, R.drawable.donnerstag_neu, R.drawable.freitag_neu, R.drawable.samstag_neu};
    }

    private void initDescriptionArray() {
        programDescriptions = new int[]{R.string.event_description_tuesday, R.string.event_description_thursday, R.string.event_description_friday, R.string.event_description_saturday};
    }

    private void initTitleArray() {
        programTitles = new int[]{R.string.event_tuesday, R.string.event_thursday, R.string.event_friday, R.string.event_saturday};
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_program, container, false);
        return rootView;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        ((HomeActivity) activity).onSectionAttached(
                getArguments().getInt(ARG_SECTION_NUMBER));
    }

    public static Program_Fragment newInstance(int sectionNumber) {
        Program_Fragment fragment = new Program_Fragment();
        Bundle args = new Bundle();
        args.putInt(ARG_SECTION_NUMBER, sectionNumber);
        fragment.setArguments(args);
        return fragment;
    }

}
