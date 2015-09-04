package mi.ur.de.dasilvaapp;

import java.lang.reflect.Array;
import java.util.Calendar;

/**
 * Created by blu on 04.09.2015.
 */
public class ActualCalendarProperties {

    private Calendar calendar;

    public static final int LAST_YEAR = -1;
    public static final int LAST_MONTH = -2;
    public static final int LAST_WEEK = -3;
    public static final int YESTERDAY = -4;
    public static final int THIS_HOUR = -5;
    public static final int NOW = -6;

    public ActualCalendarProperties() {
        calendar = Calendar.getInstance();
    }

    public int getWeekdayIndex(){
        return calendar.get(Calendar.DAY_OF_WEEK);
    }

    public int getDayOfMonth(){
        return calendar.get(Calendar.DAY_OF_MONTH);
    }

    public int getMonth(){
        return calendar.get(Calendar.MONTH);
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

    public String getTimeBeforeTimestamp(int[] timestamp){
        int timeStampDay = timestamp[0];
        int timeStampMonth = timestamp[1];
        int timeStampYear = timestamp[2];
        int timeStampHour = timestamp[3];
        int actualDay = getDayOfMonth();
        int actualMonth = getMonth();
        int actualYear = getYear();
        int actualHour = getHour();
        // if timestamp>=lastYear return Number of Years

        if(timeNotEqual(timeStampYear, actualYear)){
            int difference = getDifference(timeStampYear, actualYear);
            if(difference==1){
                return LAST_YEAR+"years";
            }
            else return difference+"years";
        }
        if(timeNotEqual(timeStampMonth, actualMonth)){
            int difference = getDifference(timeStampMonth, actualMonth);
            if(difference==1){
                return LAST_MONTH+"months";
            }
            else return difference+"months";
        }
        if(timeNotEqual(timeStampDay, actualDay)){
            int difference = getDifference(timeStampDay, actualDay);
            if((difference%7)==1){
                return LAST_WEEK+"weeks";
            }
            if((difference%7)>1){
                return (difference%7)+"weeks";
            }
            else return difference+"years";
        }
        if(timeNotEqual(timeStampHour, actualHour)){
            int difference = getDifference(timeStampHour, actualHour);
            if(difference==1){
                return NOW+"hours";
            }
            else return difference+"hours";
        }
        return "";
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

}
