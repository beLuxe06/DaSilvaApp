package mi.ur.de.dasilvaapp.Fragments;

import android.app.Activity;
import android.content.Context;
import android.location.Location;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.text.format.DateUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DecimalFormat;

import mi.ur.de.dasilvaapp.ActualCalendarProperties;
import mi.ur.de.dasilvaapp.DateHelper;
import mi.ur.de.dasilvaapp.FallbackLocationTracker;
import mi.ur.de.dasilvaapp.HomeActivity;
import mi.ur.de.dasilvaapp.LocationTracker;
import mi.ur.de.dasilvaapp.ProviderLocationTracker;
import mi.ur.de.dasilvaapp.R;
import mi.ur.de.dasilvaapp.RegularGuestDatabase;
import mi.ur.de.dasilvaapp.RegularGuestItem;
import mi.ur.de.dasilvaapp.Timestamp;

/**
 * Created by blu on 17.08.2015.
 */
public class Regular_Guest_Fragment extends Fragment {

    private static final String ARG_SECTION_NUMBER = "section_number";

    private static final double DA_SILVA_LATITUDE = 49.02;
    private static final double DA_SILVA_LONGITUDE = 12.16;

    private static final int DATABASE_ONLY_ID = 0;

    Button enterButton;
    Button leaveButton;

    TextView viewNumberOfVisits;

    LocationTracker locationTracker;
    Location daSilvaLocation;

    ActualCalendarProperties calendarProperties;
    DateHelper dateHelper;

    private RegularGuestDatabase regularGuestDatabase;
    int numberOfVisits = 0;
    String timeOfEntering = "";

    @Override
    public void onStart() {
        super.onStart();
        initDB();
        initUi();
        initDaSilvaLocation();
        startLocationTracking();
        workWithButtonClicks();
    }

    private void initDB() {
        regularGuestDatabase = new RegularGuestDatabase(getActivity());
        regularGuestDatabase.open();
        if (regularGuestDatabase.getAllRegularGuestItems().size() != 0) {
            numberOfVisits = regularGuestDatabase.getAllRegularGuestItems().get(DATABASE_ONLY_ID).getNumberOfVisits();
            timeOfEntering = regularGuestDatabase.getAllRegularGuestItems().get(DATABASE_ONLY_ID).getTimeOfEntering();
        }
    }

    private void initUi() {
        enterButton = (Button) getView().findViewById(R.id.enter_button);
        leaveButton = (Button) getView().findViewById(R.id.leave_button);
        viewNumberOfVisits = (TextView) getView().findViewById(R.id.actual_number_of_visits);
        calendarProperties = new ActualCalendarProperties(getActivity());
        dateHelper = new DateHelper(getActivity());
        updateViews();
    }

    private void initDaSilvaLocation() {
        daSilvaLocation = new Location("");
        daSilvaLocation.setLatitude(DA_SILVA_LATITUDE);
        daSilvaLocation.setLongitude(DA_SILVA_LONGITUDE);
    }

    private void startLocationTracking() {
        locationTracker = new FallbackLocationTracker(getActivity());
        locationTracker.start();
    }

    private void workWithButtonClicks() {
        enterButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                enterButtonClicked();
            }
        });
        leaveButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                leaveButtonClicked();
            }
        });
    }

    private void enterButtonClicked() {
        String isOpened = dateHelper.isOpen();
        if (isOpened.equals("out of opening duration")) {
            Toast.makeText(getActivity(), R.string.out_of_opening_times, Toast.LENGTH_SHORT).show();
        } else {
            if (isLocationEnabled(getActivity())) {
                compareEnteringLocations();
            } else {
                Toast.makeText(getActivity(), R.string.activate_location, Toast.LENGTH_SHORT).show();
            }
        }

    }

    private void compareEnteringLocations() {
        Location currentLocation;
        currentLocation = locationTracker.getLocation();
        if (currentLocation == null) {
            currentLocation = locationTracker.getPossiblyStaleLocation();
        }
        if (currentLocation == null) {
            Toast.makeText(getActivity(), R.string.no_locaion_available, Toast.LENGTH_SHORT).show();
        } else {
            Location approximateLocation = approximateLocation(currentLocation);
            if (approximateLocation.getLatitude() == daSilvaLocation.getLatitude()
                    && approximateLocation.getLongitude() == daSilvaLocation.getLongitude()) {
                Toast.makeText(getActivity(), R.string.you_entered, Toast.LENGTH_SHORT).show();
                saveEnteringInDatabase();
            } else {
                Toast.makeText(getActivity(), R.string.please_enter, Toast.LENGTH_SHORT).show();
                //Toast.makeText(getActivity(), Double.toString(approximateLocation.getLatitude()) + " - " + Double.toString(approximateLocation.getLongitude()), Toast.LENGTH_SHORT).show();
            }
        }
    }

    private Location approximateLocation(Location location) {
        double latitude = location.getLatitude();
        double longitude = location.getLongitude();
        double approximateLatitude = roundDown2(latitude);
        double approximateLongitude = roundDown2(longitude);
        location.setLatitude(approximateLatitude);
        location.setLongitude(approximateLongitude);
        return location;
    }

    public static double roundDown2(double d) {
        return (long) (d * 1e2) / 1e2;
    }

    private void saveEnteringInDatabase() {
        String timeString = calendarProperties.getCurrentTimestampAsString();
        if (regularGuestDatabase.getAllRegularGuestItems().size() == 0) {
            RegularGuestItem savings = new RegularGuestItem(0, timeString);
            regularGuestDatabase.addRegularGuestItem(savings);
        } else {
            regularGuestDatabase.updateTimeOfEntering(DATABASE_ONLY_ID, timeString);
        }
    }

    private void leaveButtonClicked() {
        if (regularGuestDatabase.getAllRegularGuestItems().size() == 0
                || regularGuestDatabase.getAllRegularGuestItems().get(DATABASE_ONLY_ID).getTimeOfEntering().length() == 1) {
            Toast.makeText(getActivity(), R.string.enter_first, Toast.LENGTH_SHORT).show();
        } else {
            compareTimings();
        }
    }

    private void compareTimings() {
        String visitDescription = dateHelper.isVisitLongEnough(new Timestamp(regularGuestDatabase.getAllRegularGuestItems().get(DATABASE_ONLY_ID).getTimeOfEntering()));
        if (visitDescription == null || visitDescription.length() == 0) {
            Toast.makeText(getActivity(), R.string.not_right_period_of_time, Toast.LENGTH_SHORT).show();
            regularGuestDatabase.updateTimeOfEntering(DATABASE_ONLY_ID, "0");
        }
        switch (visitDescription) {
            case "correct":
                compareLeavingLocations();
                break;
            case "incorrect":
                Toast.makeText(getActivity(), R.string.not_right_period_of_time, Toast.LENGTH_SHORT).show();
                regularGuestDatabase.updateTimeOfEntering(DATABASE_ONLY_ID, "0");
                break;
            case "visit too short":
                Toast.makeText(getActivity(), R.string.visit_too_short, Toast.LENGTH_SHORT).show();
                break;
            case "visit too long":
                Toast.makeText(getActivity(), R.string.visit_too_long, Toast.LENGTH_SHORT).show();
                regularGuestDatabase.updateTimeOfEntering(DATABASE_ONLY_ID, "0");
                break;
            case "out of opening duration":
                Toast.makeText(getActivity(), R.string.out_of_opening_times, Toast.LENGTH_SHORT).show();
                regularGuestDatabase.updateTimeOfEntering(DATABASE_ONLY_ID, "0");
                break;
        }
    }

    private void compareLeavingLocations() {
        Location currentLocation;
        currentLocation = locationTracker.getLocation();
        if (currentLocation == null) {
            currentLocation = locationTracker.getPossiblyStaleLocation();
        }
        Location approximateLocation = approximateLocation(currentLocation);
        if (approximateLocation.getLatitude() == daSilvaLocation.getLatitude()
                && approximateLocation.getLongitude() == daSilvaLocation.getLongitude()) {
            Toast.makeText(getActivity(), R.string.you_left, Toast.LENGTH_SHORT).show();
            saveLeavingInDatabase();
            updateViews();
        } else {
            Toast.makeText(getActivity(), R.string.please_enter, Toast.LENGTH_SHORT).show();
            regularGuestDatabase.updateTimeOfEntering(DATABASE_ONLY_ID, "0");
        }
    }

    private void saveLeavingInDatabase() {
        numberOfVisits = regularGuestDatabase.getAllRegularGuestItems().get((DATABASE_ONLY_ID)).getNumberOfVisits();
        numberOfVisits++;
        regularGuestDatabase.updateNumberOfVisits(DATABASE_ONLY_ID, numberOfVisits);
        regularGuestDatabase.updateTimeOfEntering(DATABASE_ONLY_ID, "0");
    }

    public static boolean isLocationEnabled(Context context) {
        int locationMode = 0;
        String locationProviders;

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            try {
                locationMode = Settings.Secure.getInt(context.getContentResolver(), Settings.Secure.LOCATION_MODE);

            } catch (Settings.SettingNotFoundException e) {
                e.printStackTrace();
            }

            return locationMode != Settings.Secure.LOCATION_MODE_OFF;

        } else {
            locationProviders = Settings.Secure.getString(context.getContentResolver(), Settings.Secure.LOCATION_PROVIDERS_ALLOWED);
            return !TextUtils.isEmpty(locationProviders);
        }
    }

    private void updateViews() {
        if (regularGuestDatabase.getAllRegularGuestItems().size() != 0) {
            numberOfVisits = regularGuestDatabase.getAllRegularGuestItems().get(DATABASE_ONLY_ID).getNumberOfVisits();
        }
        viewNumberOfVisits.setText(Integer.toString(numberOfVisits));
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_regular_guest, container, false);
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        ((HomeActivity) activity).onSectionAttached(
                getArguments().getInt(ARG_SECTION_NUMBER));
    }

    public static Regular_Guest_Fragment newInstance(int sectionNumber) {
        Regular_Guest_Fragment fragment = new Regular_Guest_Fragment();
        Bundle args = new Bundle();
        args.putInt(ARG_SECTION_NUMBER, sectionNumber);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        regularGuestDatabase.close();
    }

}
