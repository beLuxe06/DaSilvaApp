package mi.ur.de.dasilvaapp.RegularGuest;

/**
 * Created by Bernhard on 27.09.2015.
 */
public class RegularGuestItem {
    int numberOfVisits;
    String timeOfEntering;

    public RegularGuestItem(int numberOfVisits, String timeOfEntering) {
        this.numberOfVisits = numberOfVisits;
        this.timeOfEntering = timeOfEntering;
    }

    public int getNumberOfVisits() {
        return numberOfVisits;
    }

    public String getTimeOfEntering() {
        return timeOfEntering;
    }
}
