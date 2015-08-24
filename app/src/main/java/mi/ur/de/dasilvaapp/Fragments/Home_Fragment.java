package mi.ur.de.dasilvaapp.Fragments;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import mi.ur.de.dasilvaapp.HomeActivity;
import mi.ur.de.dasilvaapp.R;

/**
 * Created by blu on 17.08.2015.
 */
public class Home_Fragment extends Fragment {

    //Store Calendar Data
    private String actualDate;
    private String actualWeekday;
    private int actualHour;
    private int actualWeekdayIndex;
    private int actualFlyerSrc;
    private int actualTimeToOpening;

    private String actualOpeningStatus;
    private String nextEventDay;

    private static final int OPENING_TIME = 21;
    private static final int CLOSING_TIME = 2;

    //Calendar Keys for Bundle
    public static String DATE = "0";
    public static String HOUR = "1";
    public static String DAY_INDEX = "2";

    //Instances of UI
    private TextView labelNextEventDay;
    private TextView labelWeekday;
    private TextView labelDate;
    private TextView labelOpeningStatus;
    private ImageView flyer;

    private static final String ARG_SECTION_NUMBER = "section_number";

    @Override
    public void onStart() {
        super.onStart();
        getCalendarDataFromActivity();
        initUI();
        updateDateTextViews();
    }

    @Override
    public void onResume() {
        super.onResume();
        updateDateTextViews();
    }

    private void initUI() {
        labelNextEventDay = (TextView) getView().findViewById(R.id.next_event_day);
        labelWeekday = (TextView) getView().findViewById(R.id.weekday_today);
        labelDate = (TextView) getView().findViewById(R.id.date_today);
        labelOpeningStatus = (TextView) getView().findViewById(R.id.event_status);
        flyer = (ImageView) getView().findViewById(R.id.flyer_next_event);
    }

    private void updateDateTextViews() {
        updateActualStringValues(actualWeekdayIndex, actualHour);
        labelNextEventDay.setText(nextEventDay);
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
                actualOpeningStatus = getResources().getString(R.string.event_status_open);
                actualFlyerSrc = R.drawable.samstag;
                // Ab 2 Uhr geschlossen
                if(actualHour >= CLOSING_TIME){
                    actualOpeningStatus = getResources().getString(R.string.event_status_closed);
                    nextEventDay = getResources().getString(R.string.event_day_day_after_tomorrow);
                    actualFlyerSrc = R.drawable.dienstag;
                }
                break;
            // Montag
            case 2:
                actualWeekday = getResources().getString(R.string.weekday_monday);
                nextEventDay = getResources().getString(R.string.event_day_tomorrow);
                // Immer geschlossen
                actualOpeningStatus = getResources().getString(R.string.event_status_closed);
                actualFlyerSrc = R.drawable.dienstag;
                break;
            // Dienstag
            case 3:
                actualWeekday = getResources().getString(R.string.weekday_tuesday);
                nextEventDay = getResources().getString(R.string.event_day_today);
                actualOpeningStatus = getOpenInString(actualHour);
                actualFlyerSrc = R.drawable.dienstag;
                // Ab 21 Uhr geöffnet
                if(actualHour >= OPENING_TIME) {
                    actualOpeningStatus = getResources().getString(R.string.event_status_open);
                }
                break;
            // Mittwoch
            case 4:
                actualWeekday = getResources().getString(R.string.weekday_wednesday);
                // bis 2 Uhr geöffnet von Dienstag
                actualOpeningStatus = getResources().getString(R.string.event_status_open);
                nextEventDay = getResources().getString(R.string.event_day_today);
                actualFlyerSrc = R.drawable.dienstag;
                // Ab 2 Uhr geschlossen
                if(actualHour >= CLOSING_TIME){
                    actualOpeningStatus = getResources().getString(R.string.event_status_closed);
                    nextEventDay = getResources().getString(R.string.event_day_tomorrow);
                    actualFlyerSrc = R.drawable.donnerstag;
                }
                break;
            // Donnerstag
            case 5:
                actualWeekday = getResources().getString(R.string.weekday_thursday);
                nextEventDay = getResources().getString(R.string.event_day_today);
                actualOpeningStatus = getOpenInString(actualHour);
                actualFlyerSrc = R.drawable.donnerstag;
                // Ab 21 Uhr geöffnet
                if(actualHour >= OPENING_TIME) {
                    actualOpeningStatus = getResources().getString(R.string.event_status_open);
                }
                break;
            // Freitag
            case 6:
                actualWeekday = getResources().getString(R.string.weekday_friday);
                nextEventDay = getResources().getString(R.string.event_day_today);
                // Zwischen 2 und 21 Uhr geschlossen
                actualOpeningStatus = getOpenInString(actualHour);
                actualFlyerSrc = R.drawable.freitag;
                // Bis 2 Uhr geöffnet von Donnerstag
                if(actualHour <= CLOSING_TIME) {
                    actualOpeningStatus = getResources().getString(R.string.event_status_open);
                    actualFlyerSrc = R.drawable.donnerstag;
                }
                // Ab 21 Uhr geöffnet
                if(actualHour >= OPENING_TIME) {
                    actualOpeningStatus = getResources().getString(R.string.event_status_open);
                    actualFlyerSrc = R.drawable.freitag;
                }
                break;
            // Samstag
            case 7:
                actualWeekday = getResources().getString(R.string.weekday_saturday);
                nextEventDay = getResources().getString(R.string.event_day_today);
                // Zwischen 2 und 21 Uhr geschlossen
                actualOpeningStatus = getOpenInString(actualHour);
                actualFlyerSrc = R.drawable.samstag;
                // Bis 2 Uhr geöffnet von Donnerstag
                if(actualHour <= CLOSING_TIME) {
                    actualOpeningStatus = getResources().getString(R.string.event_status_open);
                    actualFlyerSrc = R.drawable.freitag;
                }
                // Ab 21 Uhr geöffnet
                if(actualHour >= OPENING_TIME) {
                    actualOpeningStatus = getResources().getString(R.string.event_status_open);
                    actualFlyerSrc = R.drawable.samstag;
                }
                break;
        }
    }

    private String getOpenInString(int actualHour) {
        actualOpeningStatus = getResources().getString(R.string.event_status_open_in);
        actualTimeToOpening = OPENING_TIME - actualHour;
        actualOpeningStatus = actualOpeningStatus + " " + actualTimeToOpening + " " + getResources().getString(R.string.event_status_unit);
        return actualOpeningStatus;
    }


    private void getCalendarDataFromActivity() {
        Bundle calendarInfos = getArguments();
        if (calendarInfos != null) {
            actualDate = calendarInfos.getString(DATE);
            actualHour = calendarInfos.getInt(HOUR);
            actualWeekdayIndex = calendarInfos.getInt(DAY_INDEX);
        }

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

    public static Home_Fragment newInstance(int sectionNumber, Bundle bundle) {
        Home_Fragment fragment = new Home_Fragment();
        bundle.putInt(ARG_SECTION_NUMBER, sectionNumber);
        fragment.setArguments(bundle);
        return fragment;
    }

}
