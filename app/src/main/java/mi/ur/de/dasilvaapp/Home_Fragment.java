package mi.ur.de.dasilvaapp;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * Created by blu on 17.08.2015.
 */
public class Home_Fragment extends Fragment {

    //Store Calendar Data
    private String actualDate;
    private String actualWeekday;
    private String actualHour;

    //Calendar Keys for Bundle
    public static String DATE = "0";
    public static String HOUR = "1";
    public static String DAY = "2";

    //Instances of UI
    private TextView labelNextEventDay;
    private TextView labelWeekday;
    private TextView labelDate;
    private TextView labelOpeningStatus;

    //The fragment argument representing the section number for this fragment.
    private static final String ARG_SECTION_NUMBER = "section_number";


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
    }

    private void updateDateTextViews() {
        // labelNextEventDay.setText();
        labelWeekday.setText(actualWeekday);
        labelDate.setText(actualDate);
        labelOpeningStatus.setText(actualHour);

    }

    private void getCalendarDataFromActivity() {
        Bundle calendarInfos = getArguments();
        if (calendarInfos != null) {
            actualDate = calendarInfos.getString(DATE);
            actualHour = calendarInfos.getString(HOUR);
            actualWeekday = calendarInfos.getString(DAY);
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_home, container, false);
        return rootView;
    }

}
