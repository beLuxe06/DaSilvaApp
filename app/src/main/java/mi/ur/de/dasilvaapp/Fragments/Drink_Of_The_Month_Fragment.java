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

    private String nameOfDrink;
    private String ingredientsOfDrink;
    private String descriptionOfDrink;

    private TextView labelActualMonth;
    private TextView labelNameOfDrink;
    private TextView labelIngredients;
    private TextView labelDrinkDescription;

    @Override
    public void onStart() {
        super.onStart();
        getCalendarDataFromActivity();
        initUI();
        updateViews();
    }

    private void initUI() {
        labelActualMonth = (TextView) getView().findViewById(R.id.actual_month);
        labelNameOfDrink = (TextView) getView().findViewById(R.id.drink_name_headline);
        labelIngredients = (TextView) getView().findViewById(R.id.ingredients);
        labelDrinkDescription = (TextView) getView().findViewById(R.id.description);
    }

    private void updateViews() {
        updateStringValues();
        labelActualMonth.setText(actualMonth);
        labelNameOfDrink.setText(nameOfDrink);
        labelIngredients.setText(ingredientsOfDrink);
        labelDrinkDescription.setText(descriptionOfDrink);
    }

    private void updateStringValues() {
        switch (actualMonthIndex) {
            case 0:
                actualMonth = getResources().getString(R.string.january);
                nameOfDrink = getResources().getString(R.string.drink_name_january);
                ingredientsOfDrink = getResources().getString(R.string.drink_ingredients_january);
                descriptionOfDrink = getResources().getString(R.string.drink_description_january);
                break;
            case 1:
                actualMonth = getResources().getString(R.string.february);
                nameOfDrink = getResources().getString(R.string.drink_name_february);
                ingredientsOfDrink = getResources().getString(R.string.drink_ingredients_february);
                descriptionOfDrink = getResources().getString(R.string.drink_description_february);
                break;
            case 2:
                actualMonth = getResources().getString(R.string.march);
                nameOfDrink = getResources().getString(R.string.drink_name_march);
                ingredientsOfDrink = getResources().getString(R.string.drink_ingredients_march);
                descriptionOfDrink = getResources().getString(R.string.drink_description_march);
                break;
            case 3:
                actualMonth = getResources().getString(R.string.april);
                nameOfDrink = getResources().getString(R.string.drink_name_april);
                ingredientsOfDrink = getResources().getString(R.string.drink_ingredients_april);
                descriptionOfDrink = getResources().getString(R.string.drink_description_april);
                break;
            case 4:
                actualMonth = getResources().getString(R.string.may);
                nameOfDrink = getResources().getString(R.string.drink_name_may);
                ingredientsOfDrink = getResources().getString(R.string.drink_ingredients_may);
                descriptionOfDrink = getResources().getString(R.string.drink_description_may);
                break;
            case 5:
                actualMonth = getResources().getString(R.string.june);
                nameOfDrink = getResources().getString(R.string.drink_name_june);
                ingredientsOfDrink = getResources().getString(R.string.drink_ingredients_june);
                descriptionOfDrink = getResources().getString(R.string.drink_description_june);
                break;
            case 6:
                actualMonth = getResources().getString(R.string.juli);
                nameOfDrink = getResources().getString(R.string.drink_name_juli);
                ingredientsOfDrink = getResources().getString(R.string.drink_ingredients_juli);
                descriptionOfDrink = getResources().getString(R.string.drink_description_juli);
                break;
            case 7:
                actualMonth = getResources().getString(R.string.august);
                nameOfDrink = getResources().getString(R.string.drink_name_august);
                ingredientsOfDrink = getResources().getString(R.string.drink_ingredients_august);
                descriptionOfDrink = getResources().getString(R.string.drink_description_august);
                break;
            case 8:
                actualMonth = getResources().getString(R.string.september);
                nameOfDrink = getResources().getString(R.string.drink_name_september);
                ingredientsOfDrink = getResources().getString(R.string.drink_ingredients_september);
                descriptionOfDrink = getResources().getString(R.string.drink_description_september);
                break;
            case 9:
                actualMonth = getResources().getString(R.string.october);
                nameOfDrink = getResources().getString(R.string.drink_name_october);
                ingredientsOfDrink = getResources().getString(R.string.drink_ingredients_october);
                descriptionOfDrink = getResources().getString(R.string.drink_description_october);
                break;
            case 10:
                actualMonth = getResources().getString(R.string.november);
                nameOfDrink = getResources().getString(R.string.drink_name_november);
                ingredientsOfDrink = getResources().getString(R.string.drink_ingredients_november);
                descriptionOfDrink = getResources().getString(R.string.drink_description_november);
                break;
            case 11:
                actualMonth = getResources().getString(R.string.december);
                nameOfDrink = getResources().getString(R.string.drink_name_december);
                ingredientsOfDrink = getResources().getString(R.string.drink_ingredients_december);
                descriptionOfDrink = getResources().getString(R.string.drink_description_december);
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
