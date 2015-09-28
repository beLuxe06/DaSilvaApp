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
import java.util.ArrayList;
import java.util.Calendar;

import com.facebook.FacebookSdk;
import com.facebook.appevents.AppEventsLogger;

import mi.ur.de.dasilvaapp.Fragments.Drink_Of_The_Month_Fragment;
import mi.ur.de.dasilvaapp.Fragments.Drinks_Fragment;
import mi.ur.de.dasilvaapp.Fragments.Gallery_Fragment;
import mi.ur.de.dasilvaapp.Fragments.Home_Fragment;
import mi.ur.de.dasilvaapp.Fragments.Impressum_Fragment;
import mi.ur.de.dasilvaapp.Fragments.Location_Fragment;
import mi.ur.de.dasilvaapp.Fragments.News_Fragment;
import mi.ur.de.dasilvaapp.Fragments.Opening_Time_Fragment;
import mi.ur.de.dasilvaapp.Fragments.Program_Fragment;
import mi.ur.de.dasilvaapp.Fragments.Regular_Guest_Fragment;
import mi.ur.de.dasilvaapp.Fragments.Reservation_Fragment;

public class HomeActivity extends AppCompatActivity
        implements NavigationDrawerFragment.NavigationDrawerCallbacks{

    /**
     * Fragment managing the behaviors, interactions and presentation of the navigation drawer.
     */
    private NavigationDrawerFragment mNavigationDrawerFragment;

    /**
     * Used to store the last screen title. For use in {@link #restoreActionBar()}.
     */
    private CharSequence mTitle;
    private ArrayList<DaSilvaEvent> events = new ArrayList<DaSilvaEvent>();

    // StringArray to store the several section(fragment) names of the App
    private String[] appSections;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        setupNavigationDrawerFragment();
        startHomeFragmentFirst();
        overrideTransitions();
    }

    private void overrideTransitions() {
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_left);
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_right);
    }


    @Override
    protected void onPause() {
        super.onPause();
        // Facebook Logs 'app deactivate' App DaSilvaEvent.
        // Forces Shut Down -> Commented
        // AppEventsLogger.deactivateApp(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        // Facebook Logs 'install' and 'app activate' App Events.
        // Forces Shut Down -> Commented
        // AppEventsLogger.activateApp(this);
    }

    private void startHomeFragmentFirst() {
        Fragment newFragment = Home_Fragment.newInstance(1);
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.container, newFragment).commit();
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
                newFragment = Home_Fragment.newInstance(position + 1);
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
        String[] stringArray = getResources().getStringArray(R.array.sections);
        if (number >= 1) {
            mTitle = stringArray[number - 1];
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
        if (id == R.id.action_opening_time) {
            startOpeningTimeFragment();
            return true;
        }
        if (id == R.id.action_drinks) {
            startDrinksFragment();
            return true;
        }
        if (id == R.id.action_impressum) {
            startImpressumFragment();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void startImpressumFragment() {
        Fragment newFragment = null;
        newFragment = Impressum_Fragment.newInstance();
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().addToBackStack("impressum").replace(R.id.container, newFragment).commit();
    }

    private void startOpeningTimeFragment() {
        Fragment newFragment = null;
                newFragment = Opening_Time_Fragment.newInstance();
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().addToBackStack("opening_times").replace(R.id.container, newFragment).commit();
    }

    private void startDrinksFragment() {
        Fragment newFragment = null;
        newFragment = Drinks_Fragment.newInstance();
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().addToBackStack("drinks").replace(R.id.container, newFragment).commit();
    }
}
