package mi.ur.de.dasilvaapp.Fragments;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

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

    private String actualOpeningStatus;
    private String nextEventDay;

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

    @Override
    public void onStart() {
        super.onStart();
        getCalendarDataFromActivity();
        initUI();
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
        updateActualStringValues(actualWeekdayIndex);
        labelNextEventDay.setText(nextEventDay);
        labelOpeningStatus.setText(actualOpeningStatus);
        labelWeekday.setText(actualWeekday);
        labelDate.setText(actualDate);
        flyer.setImageResource(actualFlyerSrc);

    }

    private void updateActualStringValues(int actualWeekdayIndex) {
        //Prüfung welcher Wochentag, setze Stringvariablen entsprechend
        switch (actualWeekdayIndex) {
            case 1:
                actualWeekday = getResources().getString(R.string.weekday_sunday);
                actualOpeningStatus = getResources().getString(R.string.event_status_closed);
                nextEventDay = getResources().getString(R.string.event_day_day_after_tomorrow);
                break;
            case 2:
                actualWeekday = getResources().getString(R.string.weekday_monday);
                actualOpeningStatus = getResources().getString(R.string.event_status_closed);
                nextEventDay = getResources().getString(R.string.event_day_tomorrow);
                break;
            case 3:
                actualWeekday = getResources().getString(R.string.weekday_tuesday);
                actualOpeningStatus = getResources().getString(R.string.event_status_open);
                nextEventDay = getResources().getString(R.string.event_day_today);
                actualFlyerSrc = R.drawable.dienstag;
                break;
            case 4:
                actualWeekday = getResources().getString(R.string.weekday_wednesday);
                actualOpeningStatus = getResources().getString(R.string.event_status_closed);
                nextEventDay = getResources().getString(R.string.event_day_tomorrow);
                break;
            case 5:
                actualWeekday = getResources().getString(R.string.weekday_thursday);
                actualOpeningStatus = getResources().getString(R.string.event_status_open);
                nextEventDay = getResources().getString(R.string.event_day_today);
                actualFlyerSrc = R.drawable.donnerstag;
                break;
            case 6:
                actualWeekday = getResources().getString(R.string.weekday_friday);
                actualOpeningStatus = getResources().getString(R.string.event_status_open);
                nextEventDay = getResources().getString(R.string.event_day_today);
                actualFlyerSrc = R.drawable.freitag;
                break;
            case 7:
                actualWeekday = getResources().getString(R.string.weekday_saturday);
                actualOpeningStatus = getResources().getString(R.string.event_status_open);
                nextEventDay = getResources().getString(R.string.event_day_today);
                actualFlyerSrc = R.drawable.samstag;
                break;
        }
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
        View rootView = inflater.inflate(R.layout.fragment_home, container, false);
        return rootView;
    }

}
