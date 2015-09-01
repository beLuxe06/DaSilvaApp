package mi.ur.de.dasilvaapp;

import android.app.Activity;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.ActionBar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.support.v4.widget.DrawerLayout;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import android.content.pm.Signature;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import com.facebook.FacebookSdk;
import com.facebook.appevents.AppEventsLogger;

import mi.ur.de.dasilvaapp.Fragments.Drink_Of_The_Month_Fragment;
import mi.ur.de.dasilvaapp.Fragments.Gallery_Fragment;
import mi.ur.de.dasilvaapp.Fragments.Home_Fragment;
import mi.ur.de.dasilvaapp.Fragments.Location_Fragment;
import mi.ur.de.dasilvaapp.Fragments.News_Fragment;
import mi.ur.de.dasilvaapp.Fragments.Program_Fragment;
import mi.ur.de.dasilvaapp.Fragments.Regular_Guest_Fragment;
import mi.ur.de.dasilvaapp.Fragments.Reservation_Fragment;

public class HomeActivity extends AppCompatActivity
        implements NavigationDrawerFragment.NavigationDrawerCallbacks {

    /**
     * Fragment managing the behaviors, interactions and presentation of the navigation drawer.
     */
    private NavigationDrawerFragment mNavigationDrawerFragment;

    /**
     * Used to store the last screen title. For use in {@link #restoreActionBar()}.
     */
    private CharSequence mTitle;

    //Store Calendar Data
    private String actualDate;
    private int actualHour;
    public int actualWeekdayIndex;
    //Array for Future?
    //public String[] calendarInfos;

    //Calendar Keys for Bundle
    public static String DATE = "0";
    public static String HOUR = "1";
    public static String DAY_INDEX = "2";


    // StringArray to store the several section(fragment) names of the App
    private String[] appSections;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        String keyHash = printKeyHash(this);
        setContentView(R.layout.activity_home);
        setupNavigationDrawerFragment();
        updateCalendarData();
        startHomeFragmentFirst();
        overrideTransitions();
    }

    // getting the App Development Key Hashes for Facebook Development
    public static String printKeyHash(Activity context) {
        PackageInfo packageInfo;
        String key = null;
        try {
            //getting application package name, as defined in manifest
            String packageName = context.getApplicationContext().getPackageName();

            //Retriving package info
            packageInfo = context.getPackageManager().getPackageInfo(packageName,
                    PackageManager.GET_SIGNATURES);

            Log.e("Package Name=", context.getApplicationContext().getPackageName());

            for (Signature signature : packageInfo.signatures) {
                MessageDigest md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());
                key = new String(Base64.encode(md.digest(), 0));

                // String key = new String(Base64.encodeBytes(md.digest()));
                Log.e("Key Hash=", key);
            }
        } catch (PackageManager.NameNotFoundException e1) {
            Log.e("Name not found", e1.toString());
        }
        catch (NoSuchAlgorithmException e) {
            Log.e("No such an algorithm", e.toString());
        } catch (Exception e) {
            Log.e("Exception", e.toString());
        }

        return key;
    }

    private void overrideTransitions() {
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_left);
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_right);
    }

    @Override
    protected void onPause() {
        super.onPause();

        // Facebook Logs 'app deactivate' App Event.
        // Forces Shut Down -> Commented
        // AppEventsLogger.deactivateApp(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        updateCalendarData();

        // Facebook Logs 'install' and 'app activate' App Events.
        // Forces Shut Down -> Commented
        // AppEventsLogger.activateApp(this);
    }

    private void startHomeFragmentFirst() {
        Fragment newFragment = new Home_Fragment();
        Bundle calendarInfos = new Bundle();
        calendarInfos.putString(DATE, actualDate);
        calendarInfos.putInt(HOUR, actualHour);
        calendarInfos.putInt(DAY_INDEX, actualWeekdayIndex);
        newFragment.setArguments(calendarInfos);
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.container, newFragment).commit();
    }

    private void updateCalendarData() {
        Calendar c = Calendar.getInstance();
        // Datum einholen, Format: DD.MM.JJJJ
        SimpleDateFormat d = new SimpleDateFormat("dd.MM.yyyy");
        actualDate = d.format(c.getTime());
        // Uhrzeit einholen, Format: HH
        actualHour = c.get(Calendar.HOUR_OF_DAY);
        // Wochentagindex einholen, Format: 1-7
        actualWeekdayIndex = c.get(Calendar.DAY_OF_WEEK);
    }

    private void setupNavigationDrawerFragment() {
        mNavigationDrawerFragment = (NavigationDrawerFragment) getSupportFragmentManager().findFragmentById(R.id.navigation_drawer);
        mTitle = getTitle();

        // Set up the drawer.
        mNavigationDrawerFragment.setUp(R.id.navigation_drawer, (DrawerLayout) findViewById(R.id.drawer_layout));
    }

    @Override
    public void onNavigationDrawerItemSelected(int position) {

        Fragment newFragment = null;

        // Method to get FragmentName to avoid switch-case duplication
        //getFragmentName(position);

        switch (position) {
            case 0:
                Bundle calendarInfos = new Bundle();
                calendarInfos.putString(DATE, actualDate);
                calendarInfos.putInt(HOUR, actualHour);
                calendarInfos.putInt(DAY_INDEX, actualWeekdayIndex);
                newFragment = Home_Fragment.newInstance(position + 1, calendarInfos);
                break;
            case 1:
                newFragment = Program_Fragment.newInstance(position + 1);
                break;
            case 2:
                newFragment = Gallery_Fragment.newInstance(position + 1);
                break;
            case 3:
                newFragment = Location_Fragment.newInstance(position + 1);
                break;
            case 4:
                newFragment = Reservation_Fragment.newInstance(position + 1);
                break;
            case 5:
                newFragment = Drink_Of_The_Month_Fragment.newInstance(position + 1);
                break;
            case 6:
                newFragment = Regular_Guest_Fragment.newInstance(position + 1);
                break;
            case 7:
                newFragment = News_Fragment.newInstance(position + 1);
                break;
        }

        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.container, newFragment).commit();

    }

    public void onSectionAttached(int number) {
        switch (number) {
            case 1:
                mTitle = getString(R.string.title_section_home);
                break;
            case 2:
                mTitle = getString(R.string.title_section_program);
                break;
            case 3:
                mTitle = getString(R.string.title_section_gallery);
                break;
            case 4:
                mTitle = getString(R.string.title_section_location);
                break;
            case 5:
                mTitle = getString(R.string.title_section_reservation);
                break;
            case 6:
                mTitle = getString(R.string.title_section_drink_of_the_month);
                break;
            case 7:
                mTitle = getString(R.string.title_section_regular_guest);
                break;
            case 8:
                mTitle = getString(R.string.title_section_news);
                break;
        }
    }

    public void restoreActionBar() {
        ActionBar actionBar = getSupportActionBar();
        /** commented because deprecated since API level 21
         actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_STANDARD);*/;
        if (actionBar != null) {
            actionBar.setDisplayShowTitleEnabled(true);
            actionBar.setTitle(mTitle);
            actionBar.setLogo(R.mipmap.ic_launcher);
            actionBar.setDisplayUseLogoEnabled(true);
            actionBar.setDisplayShowHomeEnabled(true);
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if (!mNavigationDrawerFragment.isDrawerOpen()) {
            // Only show items in the action bar relevant to this screen
            // if the drawer is not showing. Otherwise, let the drawer
            // decide what to show in the action bar.
            getMenuInflater().inflate(R.menu.home, menu);
            restoreActionBar();
            return true;
        }
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the HomeActivity/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

}
