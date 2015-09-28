package mi.ur.de.dasilvaapp.Reservation;

/**
 * Created by blu on 11.09.2015.
 */
public class Reservation {


    private final String name;
    private final String birthday;
    private final String mail;
    private final String phone;
    private final String date;
    private final String time;
    private final String persons;
    private final String area;
    private final String reason;

    public Reservation(String name, String birthday, String mail, String phone, String date, String time, String persons, String area, String reason) {
        this.name = name;
        this.birthday = birthday;
        this.mail = mail;
        this.phone = phone;
        this.date = date;
        this.time = time;
        this.persons = persons;
        this.area = area;
        this.reason = reason;
    }

    public String getName(){
        return name;
    }

    public String getBirthday(){
        return birthday;
    }

    public String getMail(){
        return mail;
    }

    public String getPhone(){
        return phone;
    }

    public String getDate(){
        return date;
    }

    public String getTime(){
        return time;
    }

    public String getPersons(){
        return persons;
    }

    public String getArea(){
        return area;
    }

    public String getReason(){
        return reason;
    }

}
