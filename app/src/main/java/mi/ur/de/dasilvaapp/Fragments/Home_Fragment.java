package mi.ur.de.dasilvaapp.Fragments;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import mi.ur.de.dasilvaapp.ActualCalendarProperties;
import mi.ur.de.dasilvaapp.DaSilvaAppContentTextView;
import mi.ur.de.dasilvaapp.DaSilvaAppTitleTextView;
import mi.ur.de.dasilvaapp.DaSilvaEvent;
import mi.ur.de.dasilvaapp.DateHelper;
import mi.ur.de.dasilvaapp.EventDatabase;
import mi.ur.de.dasilvaapp.HomeActivity;
import mi.ur.de.dasilvaapp.R;

/**
 * Created by blu on 17.08.2015.
 */
public class Home_Fragment extends Fragment {

    //Store Calendar Data
    private String nextEventTitle;
    private String actualDate;
    private String actualDBSearchDate;
    private String actualWeekday;
    private int actualHour;
    private int actualWeekdayIndex;
    private int actualFlyerSrc;
    private int actualTimeToOpening;

    private Context context;
    private ActualCalendarProperties calendarProperties;
    private DateHelper dh;

    private String actualOpeningStatus;
    private String nextEventDay;

    private static final int OPENING_TIME = 21;
    private static final int CLOSING_TIME = 2;
    private static final String ADDRESS = "https://graph.facebook.com/58336779060?fields=events{start_time,end_time,name,description,id,picture{url}}&access_token=504302586404216|WUO3JsCn9BioDFifJv0hpgzaiRE";



    //Instances of UI
    private DaSilvaAppTitleTextView labelNextEventDay;
    private DaSilvaAppTitleTextView labelNextEventTitle;
    private TextView labelWeekday;
    private TextView labelDate;
    private TextView labelOpeningStatus;
    private ImageView flyer;

    private static final String ARG_SECTION_NUMBER = "section_number";

    @Override
    public void onStart() {
        super.onStart();
        getCalendarData();
        initUI();
        updateDateTextViews();
        setOnClickListenersForFullScreenImage();
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onResume() {
        super.onResume();
        updateDateTextViews();
    }

    private void initUI() {
        labelNextEventTitle = (DaSilvaAppTitleTextView) getView().findViewById(R.id.next_event_title_home);
        labelNextEventDay = (DaSilvaAppTitleTextView) getView().findViewById(R.id.next_event_day_home);
        labelWeekday = (TextView) getView().findViewById(R.id.weekday_today_home);
        labelDate = (TextView) getView().findViewById(R.id.date_today_home);
        labelOpeningStatus = (TextView) getView().findViewById(R.id.event_status_home);
        flyer = (ImageView) getView().findViewById(R.id.flyer_next_event_home);
    }

    private void updateDateTextViews() {
        updateActualStringValues(actualWeekdayIndex, actualHour);
        labelNextEventDay.setText(nextEventDay);
        labelNextEventTitle.setText(nextEventTitle);
        labelOpeningStatus.setText(actualOpeningStatus);
        labelWeekday.setText(actualWeekday);
        labelDate.setText(actualDate);
        flyer.setImageResource(actualFlyerSrc);
    }

    private void updateActualStringValues(int actualWeekdayIndex, int actualHour) {
        //Prüfung welcher Wochentag, setze Stringvariablen entsprechend
        switch (actualWeekdayIndex) {
            // Sonntag
            case 1:
                actualWeekday = getResources().getString(R.string.weekday_sunday);
                // bis 2 Uhr geöffnet von Samstag
                nextEventDay = getResources().getString(R.string.event_day_today);
                nextEventTitle = getResources().getString(R.string.event_saturday);
                actualOpeningStatus = getResources().getString(R.string.event_status_open);
                labelOpeningStatus.setTextColor(getResources().getColor(R.color.text_color_open));
                actualFlyerSrc = R.drawable.samstag_neu;
                // Ab 2 Uhr geschlossen
                if (actualHour >= CLOSING_TIME) {
                    actualOpeningStatus = getResources().getString(R.string.event_status_closed);
                    labelOpeningStatus.setTextColor(getResources().getColor(R.color.text_color_closed));
                    nextEventDay = getResources().getString(R.string.event_day_day_after_tomorrow);
                    nextEventTitle = getResources().getString(R.string.event_tuesday);
                    actualFlyerSrc = R.drawable.dienstag_neu;
                }
                break;
            // Montag
            case 2:
                actualWeekday = getResources().getString(R.string.weekday_monday);
                nextEventDay = getResources().getString(R.string.event_day_tomorrow);
                nextEventTitle = getResources().getString(R.string.event_tuesday);
                // Immer geschlossen
                actualOpeningStatus = getResources().getString(R.string.event_status_closed);
                labelOpeningStatus.setTextColor(getResources().getColor(R.color.text_color_closed));
                actualFlyerSrc = R.drawable.dienstag_neu;
                break;
            // Dienstag
            case 3:
                actualWeekday = getResources().getString(R.string.weekday_tuesday);
                nextEventDay = getResources().getString(R.string.event_day_today);
                nextEventTitle = getResources().getString(R.string.event_tuesday);
                actualOpeningStatus = getOpenInString(actualHour);
                actualFlyerSrc = R.drawable.dienstag_neu;
                // Ab 21 Uhr geöffnet
                if (actualHour >= OPENING_TIME) {
                    actualOpeningStatus = getResources().getString(R.string.event_status_open);
                    labelOpeningStatus.setTextColor(getResources().getColor(R.color.text_color_open));
                }
                break;
            // Mittwoch
            case 4:
                actualWeekday = getResources().getString(R.string.weekday_wednesday);
                // bis 2 Uhr geöffnet von Dienstag
                actualOpeningStatus = getResources().getString(R.string.event_status_open);
                labelOpeningStatus.setTextColor(getResources().getColor(R.color.text_color_open));
                nextEventDay = getResources().getString(R.string.event_day_today);
                nextEventTitle = getResources().getString(R.string.event_tuesday);
                actualFlyerSrc = R.drawable.dienstag_neu;
                // Ab 2 Uhr geschlossen
                if (actualHour >= CLOSING_TIME) {
                    actualOpeningStatus = getResources().getString(R.string.event_status_closed);
                    labelOpeningStatus.setTextColor(getResources().getColor(R.color.text_color_closed));
                    nextEventDay = getResources().getString(R.string.event_day_tomorrow);
                    nextEventTitle = getResources().getString(R.string.event_thursday);
                    actualFlyerSrc = R.drawable.donnerstag_neu;
                }
                break;
            // Donnerstag
            case 5:
                actualWeekday = getResources().getString(R.string.weekday_thursday);
                nextEventDay = getResources().getString(R.string.event_day_today);
                nextEventTitle = getResources().getString(R.string.event_thursday);
                actualOpeningStatus = getOpenInString(actualHour);
                actualFlyerSrc = R.drawable.donnerstag_neu;
                // Ab 21 Uhr geöffnet
                if (actualHour >= OPENING_TIME) {
                    actualOpeningStatus = getResources().getString(R.string.event_status_open);
                    labelOpeningStatus.setTextColor(getResources().getColor(R.color.text_color_open));
                }
                break;
            // Freitag
            case 6:
                actualWeekday = getResources().getString(R.string.weekday_friday);
                nextEventDay = getResources().getString(R.string.event_day_today);
                nextEventTitle = getResources().getString(R.string.event_friday);
                // Zwischen 2 und 21 Uhr geschlossen
                actualOpeningStatus = getOpenInString(actualHour);
                actualFlyerSrc = R.drawable.freitag_neu;
                // Bis 2 Uhr geöffnet von Donnerstag
                if (actualHour <= CLOSING_TIME) {
                    actualOpeningStatus = getResources().getString(R.string.event_status_open);
                    labelOpeningStatus.setTextColor(getResources().getColor(R.color.text_color_open));
                    nextEventTitle = getResources().getString(R.string.event_thursday);
                    actualFlyerSrc = R.drawable.donnerstag_neu;
                }
                // Ab 21 Uhr geöffnet
                if (actualHour >= OPENING_TIME) {
                    actualOpeningStatus = getResources().getString(R.string.event_status_open);
                    labelOpeningStatus.setTextColor(getResources().getColor(R.color.text_color_open));
                    actualFlyerSrc = R.drawable.freitag_neu;
                    nextEventTitle = getResources().getString(R.string.event_friday);
                }
                break;
            // Samstag
            case 7:
                actualWeekday = getResources().getString(R.string.weekday_saturday);
                nextEventDay = getResources().getString(R.string.event_day_today);
                nextEventTitle = getResources().getString(R.string.event_saturday);
                // Zwischen 2 und 21 Uhr geschlossen
                actualOpeningStatus = getOpenInString(actualHour);
                actualFlyerSrc = R.drawable.samstag_neu;
                // Bis 2 Uhr geöffnet von Donnerstag
                if (actualHour <= CLOSING_TIME) {
                    actualOpeningStatus = getResources().getString(R.string.event_status_open);
                    labelOpeningStatus.setTextColor(getResources().getColor(R.color.text_color_open));
                    actualFlyerSrc = R.drawable.freitag_neu;
                    nextEventTitle = getResources().getString(R.string.event_friday);
                }
                // Ab 21 Uhr geöffnet
                if (actualHour >= OPENING_TIME) {
                    actualOpeningStatus = getResources().getString(R.string.event_status_open);
                    labelOpeningStatus.setTextColor(getResources().getColor(R.color.text_color_open));
                    actualFlyerSrc = R.drawable.samstag_neu;
                    nextEventTitle = getResources().getString(R.string.event_saturday);
                }
                break;
        }
    }

    private String getOpenInString(int actualHour) {
        actualOpeningStatus = getResources().getString(R.string.event_status_open_in);
        labelOpeningStatus.setTextColor(getResources().getColor(R.color.text_color_open_in));
        actualTimeToOpening = OPENING_TIME - actualHour;
        actualOpeningStatus = actualOpeningStatus + " " + actualTimeToOpening + " " + getResources().getString(R.string.event_status_unit);
        return actualOpeningStatus;
    }


    private void getCalendarData() {
        context = getActivity();
        calendarProperties = new ActualCalendarProperties(context);
        actualWeekdayIndex = calendarProperties.getWeekdayIndex();
        actualHour = calendarProperties.getHour();
        actualDBSearchDate = calendarProperties.getDBSearchString();
        actualDate = calendarProperties.getDateString();
        dh = new DateHelper(context);
    }

    private void setOnClickListenersForFullScreenImage() {
        flyer.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Bundle imageResource = new Bundle();
                imageResource.putInt(Image_Fullscreen_Fragment.IMAGE_RESOURCE, actualFlyerSrc);
                Fragment newFragment = Image_Fullscreen_Fragment.newInstance(imageResource);
                FragmentManager fragmentManager = getFragmentManager();
                fragmentManager.beginTransaction().addToBackStack("fullscreen").replace(R.id.container, newFragment).commit();
            }
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        ((HomeActivity) activity).onSectionAttached(
                getArguments().getInt(ARG_SECTION_NUMBER));
    }

    public static Home_Fragment newInstance(int sectionNumber) {
        Home_Fragment fragment = new Home_Fragment();
        Bundle args = new Bundle();
        args.putInt(ARG_SECTION_NUMBER, sectionNumber);
        fragment.setArguments(args);
        return fragment;
    }

}
