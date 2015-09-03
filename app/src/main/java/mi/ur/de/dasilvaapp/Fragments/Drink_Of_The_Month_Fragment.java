package mi.ur.de.dasilvaapp.Fragments;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import mi.ur.de.dasilvaapp.HomeActivity;
import mi.ur.de.dasilvaapp.R;

/**
 * Created by blu on 17.08.2015.
 */
public class Drink_Of_The_Month_Fragment extends Fragment {

    private static final String ARG_SECTION_NUMBER = "section_number";

    private int actualMonthIndex;
    public static String MONTH_INDEX = "0";
    private String actualMonth;

    private TextView labelActualMonth;

    @Override
    public void onStart() {
        super.onStart();
        getCalendarDataFromActivity();
        updateStringValues();
        initUI();
    }

    private void initUI() {
        labelActualMonth = (TextView) getView().findViewById(R.id.actual_month);
        labelActualMonth.setText(actualMonth);
    }

    private void updateStringValues() {
        switch (actualMonthIndex) {
            case 0:
                actualMonth = getResources().getString(R.string.january);
                break;
            case 1:
                actualMonth = getResources().getString(R.string.february);
                break;
            case 2:
                actualMonth = getResources().getString(R.string.march);
                break;
            case 3:
                actualMonth = getResources().getString(R.string.april);
                break;
            case 4:
                actualMonth = getResources().getString(R.string.may);
                break;
            case 5:
                actualMonth = getResources().getString(R.string.june);
                break;
            case 6:
                actualMonth = getResources().getString(R.string.juli);
                break;
            case 7:
                actualMonth = getResources().getString(R.string.august);
                break;
            case 8:
                actualMonth = getResources().getString(R.string.september);
                break;
            case 9:
                actualMonth = getResources().getString(R.string.october);
                break;
            case 10:
                actualMonth = getResources().getString(R.string.november);
                break;
            case 11:
                actualMonth = getResources().getString(R.string.december);
                break;
        }
    }

    private void getCalendarDataFromActivity() {
        Bundle calendarInfos = getArguments();
        if (calendarInfos != null) {
            actualMonthIndex = calendarInfos.getInt(MONTH_INDEX);
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_drink_of_the_month, container, false);
        return rootView;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        ((HomeActivity) activity).onSectionAttached(
                getArguments().getInt(ARG_SECTION_NUMBER));
    }

    public static Drink_Of_The_Month_Fragment newInstance(int sectionNumber, Bundle bundle) {
        Drink_Of_The_Month_Fragment fragment = new Drink_Of_The_Month_Fragment();
        bundle.putInt(ARG_SECTION_NUMBER, sectionNumber);
        fragment.setArguments(bundle);
        return fragment;
    }

}
