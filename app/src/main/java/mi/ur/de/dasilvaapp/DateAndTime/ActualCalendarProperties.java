package mi.ur.de.dasilvaapp.DateAndTime;

import android.content.Context;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import mi.ur.de.dasilvaapp.R;

/**
 * Created by blu on 04.09.2015.
 */
public class ActualCalendarProperties {

    private Calendar calendar;
    private Context context;

    public ActualCalendarProperties(Context context) {
        this.context = context;
        calendar = Calendar.getInstance();
    }

    public int getWeekdayIndex() {
        return calendar.get(Calendar.DAY_OF_WEEK);
    }

    public String getWeekday() {
        int index = getWeekdayIndex();
        String weekday = null;
        switch (index) {
            case 1:
                weekday = context.getResources().getString(R.string.weekday_sunday);
                break;
            case 2:
                weekday = context.getResources().getString(R.string.weekday_monday);
                break;
            case 3:
                weekday = context.getResources().getString(R.string.weekday_tuesday);
                break;
            case 4:
                weekday = context.getResources().getString(R.string.weekday_wednesday);
                break;
            case 5:
                weekday = context.getResources().getString(R.string.weekday_thursday);
                break;
            case 6:
                weekday = context.getResources().getString(R.string.weekday_friday);
                break;
            case 7:
                weekday = context.getResources().getString(R.string.weekday_saturday);
                break;
        }
        return weekday;
    }

    public String getOpeningStatus(String openingTime) {
        Timestamp timestamp = new Timestamp(openingTime);
        String actualOpeningStatus = context.getResources().getString(R.string.event_status_open_in);
        int actualTimeToOpening = getHoursTillTime(timestamp.getHour());
        actualOpeningStatus = actualOpeningStatus + " " + actualTimeToOpening + " " + context.getResources().getString(R.string.event_status_unit);
        return actualOpeningStatus;
    }

    private int getHoursTillTime(int timestampHour) {
        return timestampHour - getHour();
    }

    public int getDayOfMonth() {
        return calendar.get(Calendar.DAY_OF_MONTH);
    }

    public int getMonth() {
        return (calendar.get(Calendar.MONTH) + 1);
    }

    public int getYear() {
        return calendar.get(Calendar.YEAR);
    }

    public int getHour() {
        return calendar.get(Calendar.HOUR_OF_DAY);
    }

    public int getMinute() {
        return calendar.get(Calendar.MINUTE);
    }

    public String getDateString() {
        SimpleDateFormat df = new SimpleDateFormat("dd.MM.yyyy");
        return df.format(calendar.getTime());
    }

    public String getDBSearchString() {
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        return df.format(calendar.getTime());
    }

    public long getDateAsLong() {
        return calendar.getTime().getTime();
    }

    public String getTimeString() {
        SimpleDateFormat df = new SimpleDateFormat("HH:mm:ss");
        return df.format(calendar.getTime());
    }

    public String getCurrentTimestampAsString() {
        return getDBSearchString() + "T" + getTimeString();
    }


}
