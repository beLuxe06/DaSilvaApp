package mi.ur.de.dasilvaapp;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by blu on 24.09.2015.
 */
public class Timestamp {

    private Calendar timestampCalendar;

    public Timestamp(String timestamp){
        timestampCalendar = getCalendarFromDate(timestamp);
    }

    public int getYear(){
        return timestampCalendar.get(Calendar.YEAR);
    }

    public int getMonth(){
        return (timestampCalendar.get(Calendar.MONTH)+1);
    }

    public int getDay(){
        return timestampCalendar.get(Calendar.DAY_OF_MONTH);
    }

    public int getHour(){
        return timestampCalendar.get(Calendar.HOUR_OF_DAY);
    }

    public int getMinute(){
        return timestampCalendar.get(Calendar.MINUTE);
    }

    public int getSecond(){
        return timestampCalendar.get(Calendar.SECOND);
    }

    private Calendar getCalendarFromDate(String timestamp) {
        Date date = getDateFromTimestamp(timestamp);
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar;
    }

    private Date getDateFromTimestamp(String timestamp) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
        try {
            Date date = sdf.parse(timestamp);
            return date;
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    public String getStringfromTimestamp(Timestamp timestamp){
        int year = getYear();
        int month = getMonth();
        int day = getDay();
        int hour = getHour();
        int minute = getMinute();
        int second = getSecond();
        String timeStampString = year + "-" + month + "-" + day + "T" + hour +":" + minute + ":" + second;
        return  timeStampString;
    }
}
