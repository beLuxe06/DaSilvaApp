package mi.ur.de.dasilvaapp;

import android.content.Context;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;

/**
 * Created by blu on 24.09.2015.
 */
public class DateHelper {

    private Context context;
    private ActualCalendarProperties calendarProperties;
    public static String YEARS = "year";
    public static String MONTHS = "month";
    public static String WEEKS = "week";
    public static String DAYS = "day";
    public static String HOURS = "hour";
    public static String MINUTE = "minute";
    public static String LAST = "last";
    public static String THIS = "this";

    public static String OUT_OF_OPENING_DURATION = "out of opening duration";
    public static String VISIT_TOO_SHORT = "visit too short";
    public static String VISIT_TOO_LONG = "visit too long";
    public static String VISIT_CORRECT = "correct";
    public static String VISIT_INCORRECT = "incorrect";

    private int actualYear;
    private int actualMonth;
    private int actualDay;
    private int actualHour;
    private int actualMinute;
    private int actualWeekdayIndex;

    public static final int FACEBOOK_DATE_OBJECT = 1;
    public static final int CALENDAR_DATE_OBJECT = 2;
    public static final int MIN_NUM_HOURS_TO_STAY = 2;
    public static final int OPENING_TIME = 21;
    public static final int CLOSING_TIME = 2;

    public DateHelper(Context context){
        this.context = context;
        calendarProperties = new ActualCalendarProperties(context);
        updateCalendarProperties();
    }

    private void updateCalendarProperties() {
        actualYear = calendarProperties.getYear();
        actualMonth = calendarProperties.getMonth();
        actualDay = calendarProperties.getDayOfMonth();
        actualHour = calendarProperties.getHour();
        actualMinute = calendarProperties.getMinute();
        actualWeekdayIndex = calendarProperties.getWeekdayIndex();
    }

    public String isVisitLongEnough(Timestamp timestamp){
        updateCalendarProperties();

        int yearDifference = getDifference(actualYear, timestamp.getYear());
        boolean newYearsEve = isNewYearsEve(timestamp.getDay(), timestamp.getMonth());
        boolean february = wasFebruary(timestamp.getMonth());
        boolean longMonth = wasLongMonth(timestamp.getMonth());
        int monthDifference = getDifference(actualMonth, timestamp.getMonth());
        int dayDifference = getDifference(actualDay, timestamp.getDay());
        boolean lastMonthsEve = isLastMonthsEve(timestamp.getDay(), longMonth, february);
        boolean midnight = isMidnight(timestamp.getDay(), timestamp.getHour());

        int openingDuration = Integer.parseInt(context.getResources().getString(R.string.opening_duration));

        // check for incorrect year difference((> 1) or (== 1 && !NewYearsEve))
        if(yearsCorrect(yearDifference, newYearsEve)){
            if(monthsCorrect(monthDifference, lastMonthsEve, newYearsEve)){
                if(daysCorrect(dayDifference, midnight, lastMonthsEve)){
                    String returnString = hoursCorrect(timestamp.getHour(), openingDuration);
                    if(returnString.equals("")){
                        return VISIT_CORRECT;
                    }
                    else return returnString;
                }
            }
        }

        return "incorrect";
    }

    public String isOpen(){
        switch(actualWeekdayIndex){
            case 1:
                if(isOpenAtMorning()){
                    return VISIT_CORRECT;
                }
                else return OUT_OF_OPENING_DURATION;
            case 2:
                return OUT_OF_OPENING_DURATION;
            case 3:
                if(isOpenAtEvening()){
                    return VISIT_CORRECT;
                }
                else return OUT_OF_OPENING_DURATION;
            case 4:
                if(isOpenAtMorning()){
                    return VISIT_CORRECT;
                }
                else return OUT_OF_OPENING_DURATION;
            case 5:
                if(isOpenAtEvening()){
                    return VISIT_CORRECT;
                }
                else return OUT_OF_OPENING_DURATION;
            case 6:
                if(isOpenAtEveningAndMorning()){
                    return OUT_OF_OPENING_DURATION;
                }
                else return VISIT_CORRECT;
            case 7:
                if(isOpenAtEveningAndMorning()){
                    return OUT_OF_OPENING_DURATION;
                }
                else return VISIT_CORRECT;
        }
        return VISIT_INCORRECT;
    }

    private boolean isOpenAtEvening(){
        if(actualHour >= OPENING_TIME ){
            return true;
        }
        return false;
    }

    private boolean isOpenAtMorning(){
        if(actualHour <= CLOSING_TIME ){
            return true;
        }
        return false;
    }

    private boolean isOpenAtEveningAndMorning(){
        if(isOpenAtEvening()&&isOpenAtMorning()){
            return true;
        }
        return false;
    }

    public String hoursCorrect(int timestampHour, int openingDuration) {
        if((actualHour >= CLOSING_TIME) && (actualHour <= OPENING_TIME)){
            return OUT_OF_OPENING_DURATION;
        }
        int hourDifference = 0;
        if (actualHour >= OPENING_TIME){
            hourDifference = getDifference(timestampHour, actualHour);
        }
        if(actualHour <=CLOSING_TIME){
            hourDifference = getDifference(timestampHour, 24) + actualHour;
        }
        if((hourDifference < MIN_NUM_HOURS_TO_STAY)){
            return VISIT_TOO_SHORT;
        }
        if(hourDifference > openingDuration){
            return VISIT_TOO_LONG;
        }
        return "";

    }

    private boolean wasLongMonth(int timestampMonth) {
        if((timestampMonth == 2) || (timestampMonth == 4) || (timestampMonth == 6) || (timestampMonth == 9) || (timestampMonth == 11)){
            return false;
        }
        else return true;
    }

    private boolean wasFebruary(int timestampMonth) {
        if(timestampMonth == 2){
            return true;
        }
        else return false;
    }

    private boolean daysCorrect(int dayDifference, boolean midnight, boolean lastMonthsEve) {
        // true if same day
        if(dayDifference == 0){
            return true;
        }
        //true if following day and midnight
        if((dayDifference == -1 && midnight)){
            return true;
        }
        //true if other dayDifference but last months eve
        if(lastMonthsEve){
            return true;
        }
        return false;
    }

    private boolean monthsCorrect(int monthDifference, boolean lastMonthsEve, boolean newYearsEve) {
        // true if same month
        if(monthDifference == 0){
            return true;
        }
        // true if difference is 11 and is New Years Eve
        if((monthDifference == 11) && newYearsEve){
            return true;
        }
        // true if following month and last Months Eve
        if((monthDifference == -1) && (lastMonthsEve)){
            return true;
        }
        return false;
    }

    private boolean yearsCorrect(int yearDifference, boolean newYearsEve) {
        //Only true if same Year or following year and New Years Eve
        if(yearDifference == 0){
            return true;
        }
        if((yearDifference == -1) && newYearsEve){
            return true;
        }
        return false;
    }

    private boolean isMidnight(int timeStampDay, int timeStampHour) {
        updateCalendarProperties();
        if((timeStampHour < 24) && (actualHour > 0)){
            if(timeStampDay==(actualDay-1)){
                return true;
            }
        }
        return false;
    }

    private boolean isLastMonthsEve(int timeStampDay, boolean longMonth, boolean february) {
        updateCalendarProperties();
        // false if actualDay != 1
        if(actualDay != 1){
            return false;
        }
        // true if 28.2. oder 29.2.
        if(february && ((timeStampDay == 28)||(timeStampDay == 29))){
            return true;
        }
        // true if long month and timestampDay is 31
        if(longMonth && (timeStampDay == 31)){
            return true;
        }
        // true if not long month and timestampDay is 30
        if(!longMonth && (timeStampDay == 30)){
                return true;
        }
        return false;
    }

    private boolean isNewYearsEve(int timeStampDay, int timeStampMonth) {
        updateCalendarProperties();
        if((timeStampDay == 31) && (timeStampMonth == 12)){
            if((actualDay == 1) && (actualMonth == 1)){
                return true;
            }
        }
        return false;
    }

    public String getTimeBeforeTimestamp(Timestamp timestamp){
        updateCalendarProperties();

        // if timestamp>=lastYear return number of years
        if(timeNotEqual(timestamp.getYear(), actualYear)){
            return getTimeAgoPlusUnitAsString(YEARS, getDifference(timestamp.getYear(), actualYear));
        }
        // if timestamp >= last month, return number of months
        if(timeNotEqual(timestamp.getMonth(), actualMonth)){
            return getTimeAgoPlusUnitAsString(MONTHS, getDifference(timestamp.getMonth(), actualMonth));
        }
        // if timestamp >= last day, return number of weeks for more than 7 days, else number of days
        if(timeNotEqual(timestamp.getDay(), actualDay)){
            int dayDifference = getDifference(timestamp.getDay(), actualDay);
            if(dayDifference<7){
                return getTimeAgoPlusUnitAsString(DAYS, dayDifference);
            }
            else{
                return getNumberOfWeeksAgoAsString(dayDifference);
            }
        }
        // if timestamp == this day, return number of hour, or last hour or now
        if(timeNotEqual(timestamp.getHour(), actualHour)){
            return getTimeAgoPlusUnitAsString(HOURS, getDifference(timestamp.getHour(), actualHour));
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

    private int getDifference(int timeToSubtract, int timeToSubtractFrom) {
        return timeToSubtractFrom-timeToSubtract;
    }

    private boolean timeNotEqual(int timeStampTime, int actualTime) {
        if(timeStampTime == actualTime){
            return false;
        }
        else return true;
    }


    // Creating a time-difference String from a facebook timestamp in format: "vor 8 Stunden" oder "letzte Woche"
    public String getFormattedTimeAgoString(Timestamp timestamp) {
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

    public boolean incorrectDateFormat(String date){
        int day = getIntegerFromDateAsString(date, 0, 2);
        int month = getIntegerFromDateAsString(date, 3, 5);
        int year = getIntegerFromDateAsString(date, 6, 0);
        if((day < 1) || (day > 31)){
            return true;
        }
        if((month < 1) || (month > 12)){
            return true;
        }
        if((year<1900) || (year > (actualYear+1))){
            return true;
        }
        return false;
    }

    private Integer getIntegerFromDateAsString(String string, int startIndex, int endIndex) {
        if(endIndex == 0){
            return Integer.parseInt(string.substring(startIndex));
        }
        else return Integer.parseInt(string.substring(startIndex, endIndex));
    }

    public boolean illegalAge(String birthday){
        int dayOfBirth = getIntegerFromDateAsString(birthday, 0, 2);
        int monthOfBirth = getIntegerFromDateAsString(birthday, 3, 5);
        int yearOfBirth = getIntegerFromDateAsString(birthday, 6, 0);
        updateCalendarProperties();
        int yearDifference = getDifference(yearOfBirth, actualYear);

        if(yearDifference>17){
            return false;
        }
        if(yearDifference<17){
            return true;
        }
        int monthDifference = getDifference(monthOfBirth, actualMonth);
        if(monthDifference>0){
            return false;
        }
        if(monthDifference<0){
            return true;
        }
        int dayDifference = getDifference(dayOfBirth, actualDay);
        if(dayDifference<0){
            return true;
        }
        return false;
    }

    public boolean timeInPast(String date) {
        int day = getIntegerFromDateAsString(date, 0, 2);
        int month = getIntegerFromDateAsString(date, 3, 5);
        int year = getIntegerFromDateAsString(date, 6, 0);
        updateCalendarProperties();
        int yearDifference = getDifference(year, actualYear);

        if(yearDifference>0){
            return true;
        }
        if(yearDifference<0){
            return false;
        }
        int monthDifference = getDifference(month, actualMonth);
        if(monthDifference>0){
            return true;
        }
        if(monthDifference<0){
            return false;
        }
        int dayDifference = getDifference(day, actualDay);
        if(dayDifference<0){
            return false;
        }
        return true;
    }


}
