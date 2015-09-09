package mi.ur.de.dasilvaapp;

import android.content.Context;

import java.lang.reflect.Array;
import java.util.Calendar;

/**
 * Created by blu on 04.09.2015.
 */
public class ActualCalendarProperties {

    private Context context;
    private Calendar calendar;

    public static String YEARS = "years";
    public static String MONTHS = "months";
    public static String WEEKS = "weeks";
    public static String DAYS = "days";
    public static String HOURS = "hours";
    public static String LAST = "last";
    public static String THIS = "this";

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

    public int getHour(){
        return calendar.get(Calendar.HOUR_OF_DAY);
    }

    public int[] getCalendarData(){
        int[] calendarData = new int[]{getWeekdayIndex(), getDayOfMonth(), getMonth(), getYear(), getHour()};
                return calendarData;
    }

    public int getHoursTillTime(int time){
        return time-getHour();
    }

    private String getTimeBeforeTimestamp(String timestamp){
        int timeStampDay = Integer.parseInt(timestamp.substring(8,10));
        int timeStampMonth = Integer.parseInt(timestamp.substring(5,7));
        int timeStampYear = Integer.parseInt(timestamp.substring(0, 4));
        int timeStampHour = Integer.parseInt(timestamp.substring(11,13));
        int actualDay = getDayOfMonth();
        int actualMonth = getMonth();
        int actualYear = getYear();
        int actualHour = getHour();

        // if timestamp>=lastYear return number of years
        if(timeNotEqual(timeStampYear, actualYear)){
            return getTimeAgoPlusUnitAsString(YEARS, getDifference(timeStampYear, actualYear));
        }
        // if timestamp >= last month, return number of months
        if(timeNotEqual(timeStampMonth, actualMonth)){
            return getTimeAgoPlusUnitAsString(MONTHS, getDifference(timeStampMonth, actualMonth));
        }
        // if timestamp >= last day, return number of weeks for more than 7 days, else number of days
        if(timeNotEqual(timeStampDay, actualDay)){
            int dayDifference = getDifference(timeStampDay, actualDay);
            if(dayDifference<7){
                return getTimeAgoPlusUnitAsString(DAYS, dayDifference);
            }
            else{
                return getNumberOfWeeksAgoAsString(dayDifference);
            }
        }
        // if timestamp == this day, return number of hour, or last hour or now
        if(timeNotEqual(timeStampHour, actualHour)){
            return getTimeAgoPlusUnitAsString(HOURS, getDifference(timeStampHour, actualHour));
        }
        return HOURS+0+THIS;
    }

    private String getTimeAgoPlusUnitAsString(String Unit, int difference) {
        // Check for last year, return last year
        if(difference==1){
            return Unit+difference+LAST;
        }
        // Else return number of years
        else return Unit+difference;
    }

    private String getNumberOfWeeksAgoAsString(int dayDifference){
        int numberOfWeeks = dayDifference/7;
        return getTimeAgoPlusUnitAsString(WEEKS, numberOfWeeks);
    }

    private int getDifference(int timeStampTime, int actualTime) {
        return actualTime-timeStampTime;
    }

    private boolean timeNotEqual(int timeStampTime, int actualTime) {
        if(timeStampTime == actualTime){
            return false;
        }
        else return true;
    }


    // Creating a time-difference String from a facebook timestamp in format: "vor 8 Stunden" oder "letzte Woche"
    public String getFormattedTimeAgoString(String timestamp) {
        String AGO = context.getResources().getString(R.string.news_feed_time_stamp_ago);
        // Getting the timedifference
        String timeBeforeTimestamp = getTimeBeforeTimestamp(timestamp);

        // formatting the new String
        // check for unit years, then if last year oder else the number of years
        if(checkForPrefix(YEARS, timeBeforeTimestamp)){
            if(checkForSuffix(LAST, timeBeforeTimestamp)){
                return context.getResources().getString(R.string.news_feed_time_stamp_last_year);
            }
            return changeUnitAndConcatString(timeBeforeTimestamp, AGO, YEARS, context.getResources().getString(R.string.news_feed_time_stamp_unit_years));
        }
        // check for unit months, then if last month oder else the number of months
        if(checkForPrefix(MONTHS, timeBeforeTimestamp)){
            if(checkForSuffix(LAST, timeBeforeTimestamp)){
                return context.getResources().getString(R.string.news_feed_time_stamp_last_month);
            }
            return changeUnitAndConcatString(timeBeforeTimestamp, AGO, MONTHS, context.getResources().getString(R.string.news_feed_time_stamp_unit_months));
        }
        // check for unit weeks, then if last week oder else the number of weeks
        if(checkForPrefix(WEEKS, timeBeforeTimestamp)){
            if(checkForSuffix(LAST, timeBeforeTimestamp)){
                return context.getResources().getString(R.string.news_feed_time_stamp_last_week);
            }
            return changeUnitAndConcatString(timeBeforeTimestamp, AGO, WEEKS, context.getResources().getString(R.string.news_feed_time_stamp_unit_weeks));
        }
        // check for unit days, then if yesterday oder else the number of days
        if(checkForPrefix(DAYS, timeBeforeTimestamp)){
            if(checkForSuffix(LAST, timeBeforeTimestamp)){
                return context.getResources().getString(R.string.news_feed_time_stamp_last_day);
            }
            return changeUnitAndConcatString(timeBeforeTimestamp, AGO, DAYS, context.getResources().getString(R.string.news_feed_time_stamp_unit_days));
        }
        // check for unit hours, then if this hour, last hour oder else the number of hourrs
        if(checkForPrefix(HOURS, timeBeforeTimestamp)){
            if(checkForSuffix(THIS, timeBeforeTimestamp)){
                return context.getResources().getString(R.string.news_feed_time_stamp_this_hour);
            }
            if(checkForSuffix(LAST, timeBeforeTimestamp)){
                return context.getResources().getString(R.string.news_feed_time_stamp_last_hour);
            }
            return changeUnitAndConcatString(timeBeforeTimestamp, AGO, HOURS, context.getResources().getString(R.string.news_feed_time_stamp_unit_hours));
        }

        return null;
    }
    // Getting the unit from string ressource, change the string to the correct outputformat
    private String changeUnitAndConcatString(String timeBeforeTimestamp, String ago, String unit, String newUnit) {
        String formattedString = timeBeforeTimestamp.replace(unit,(ago+" "));
        return formattedString.concat(" ".concat(newUnit));
    }

    private boolean checkForSuffix(String suffix, String timeBeforeTimestamp) {
        if(timeBeforeTimestamp.endsWith(suffix)){
            return true;
        }
        return false;
    }

    private boolean checkForPrefix(String unit, String timeBeforeTimestamp) {
        if(timeBeforeTimestamp.startsWith(unit)){
            return true;
        }
        return false;
    }

}
