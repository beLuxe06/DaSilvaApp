package mi.ur.de.dasilvaapp;

import android.content.Context;

import java.lang.reflect.Array;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

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

    public int getWeekdayIndex(){
        return calendar.get(Calendar.DAY_OF_WEEK);
    }

    public int getDayOfMonth(){
        return calendar.get(Calendar.DAY_OF_MONTH);
    }

    public int getMonth(){
        return (calendar.get(Calendar.MONTH)+1);
    }

    public int getYear(){
        return calendar.get(Calendar.YEAR);
    }

    public int getHour() {
        return calendar.get(Calendar.HOUR_OF_DAY);
    }

    public int getMinute(){
        return calendar.get(Calendar.MINUTE);
    }

    public String getDateString() {
        SimpleDateFormat df = new SimpleDateFormat("dd.MM.yyyy");
        return df.format(calendar.getTime());
    }

    public long getDateAsLong(){
        return calendar.getTime().getTime();
    }

    public String getTimeString() {
        SimpleDateFormat df = new SimpleDateFormat("HH:mm");
        return df.format(calendar.getTime());
    }

    public String getCurrentTimestamp(){
        return getDateString() + " " + getTimeString();
    }



}
