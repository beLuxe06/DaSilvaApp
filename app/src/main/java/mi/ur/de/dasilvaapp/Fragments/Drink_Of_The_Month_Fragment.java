package mi.ur.de.dasilvaapp.Fragments;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import mi.ur.de.dasilvaapp.ActualCalendarProperties;
import mi.ur.de.dasilvaapp.HomeActivity;
import mi.ur.de.dasilvaapp.R;

/**
 * Created by blu on 17.08.2015.
 */
public class Drink_Of_The_Month_Fragment extends Fragment {

    private static final String ARG_SECTION_NUMBER = "section_number";

    private int actualMonthIndex;
    private String actualMonth;

    private String nameOfDrink;
    private String ingredientsOfDrink;
    private String descriptionOfDrink;
    private int imageResource;

    private TextView labelActualMonth;
    private TextView labelNameOfDrink;
    private TextView labelIngredients;
    private TextView labelDrinkDescription;
    private ImageView imageOfDrink;

    @Override
    public void onStart() {
        super.onStart();
        getCalendarData();
        initUI();
        updateViews();
        setOnClickListenersForFullScreenImage();
    }

    private void initUI() {
        labelActualMonth = (TextView) getView().findViewById(R.id.actual_month);
        labelNameOfDrink = (TextView) getView().findViewById(R.id.drink_name_headline);
        labelIngredients = (TextView) getView().findViewById(R.id.ingredients);
        labelDrinkDescription = (TextView) getView().findViewById(R.id.description);
        imageOfDrink = (ImageView) getView().findViewById(R.id.drink_image);
    }

    private void updateViews() {
        updateStringValues();
        labelActualMonth.setText(actualMonth);
        labelNameOfDrink.setText(nameOfDrink);
        labelIngredients.setText(ingredientsOfDrink);
        labelDrinkDescription.setText(descriptionOfDrink);
        imageOfDrink.setImageResource(imageResource);
    }

    private void updateStringValues() {
        switch (actualMonthIndex) {
            case 1:
                actualMonth = getResources().getString(R.string.january);
                nameOfDrink = getResources().getString(R.string.drink_name_january);
                ingredientsOfDrink = getResources().getString(R.string.drink_ingredients_january);
                descriptionOfDrink = getResources().getString(R.string.drink_description_january);
                imageResource = R.drawable.china_town;
                break;
            case 2:
                actualMonth = getResources().getString(R.string.february);
                nameOfDrink = getResources().getString(R.string.drink_name_february);
                ingredientsOfDrink = getResources().getString(R.string.drink_ingredients_february);
                descriptionOfDrink = getResources().getString(R.string.drink_description_february);
                imageResource = R.drawable.china_town;
                break;
            case 3:
                actualMonth = getResources().getString(R.string.march);
                nameOfDrink = getResources().getString(R.string.drink_name_march);
                ingredientsOfDrink = getResources().getString(R.string.drink_ingredients_march);
                descriptionOfDrink = getResources().getString(R.string.drink_description_march);
                imageResource = R.drawable.china_town;
                break;
            case 4:
                actualMonth = getResources().getString(R.string.april);
                nameOfDrink = getResources().getString(R.string.drink_name_april);
                ingredientsOfDrink = getResources().getString(R.string.drink_ingredients_april);
                descriptionOfDrink = getResources().getString(R.string.drink_description_april);
                imageResource = R.drawable.china_town;
                break;
            case 5:
                actualMonth = getResources().getString(R.string.may);
                nameOfDrink = getResources().getString(R.string.drink_name_may);
                ingredientsOfDrink = getResources().getString(R.string.drink_ingredients_may);
                descriptionOfDrink = getResources().getString(R.string.drink_description_may);
                imageResource = R.drawable.china_town;
                break;
            case 6:
                actualMonth = getResources().getString(R.string.june);
                nameOfDrink = getResources().getString(R.string.drink_name_june);
                ingredientsOfDrink = getResources().getString(R.string.drink_ingredients_june);
                descriptionOfDrink = getResources().getString(R.string.drink_description_june);
                imageResource = R.drawable.china_town;
                break;
            case 7:
                actualMonth = getResources().getString(R.string.juli);
                nameOfDrink = getResources().getString(R.string.drink_name_juli);
                ingredientsOfDrink = getResources().getString(R.string.drink_ingredients_juli);
                descriptionOfDrink = getResources().getString(R.string.drink_description_juli);
                imageResource = R.drawable.china_town;
                break;
            case 8:
                actualMonth = getResources().getString(R.string.august);
                nameOfDrink = getResources().getString(R.string.drink_name_august);
                ingredientsOfDrink = getResources().getString(R.string.drink_ingredients_august);
                descriptionOfDrink = getResources().getString(R.string.drink_description_august);
                imageResource = R.drawable.china_town;
                break;
            case 9:
                actualMonth = getResources().getString(R.string.september);
                nameOfDrink = getResources().getString(R.string.drink_name_september);
                ingredientsOfDrink = getResources().getString(R.string.drink_ingredients_september);
                descriptionOfDrink = getResources().getString(R.string.drink_description_september);
                imageResource = R.drawable.china_town;
                break;
            case 10:
                actualMonth = getResources().getString(R.string.october);
                nameOfDrink = getResources().getString(R.string.drink_name_october);
                ingredientsOfDrink = getResources().getString(R.string.drink_ingredients_october);
                descriptionOfDrink = getResources().getString(R.string.drink_description_october);
                imageResource = R.drawable.china_town;
                break;
            case 11:
                actualMonth = getResources().getString(R.string.november);
                nameOfDrink = getResources().getString(R.string.drink_name_november);
                ingredientsOfDrink = getResources().getString(R.string.drink_ingredients_november);
                descriptionOfDrink = getResources().getString(R.string.drink_description_november);
                imageResource = R.drawable.china_town;
                break;
            case 12:
                actualMonth = getResources().getString(R.string.december);
                nameOfDrink = getResources().getString(R.string.drink_name_december);
                ingredientsOfDrink = getResources().getString(R.string.drink_ingredients_december);
                descriptionOfDrink = getResources().getString(R.string.drink_description_december);
                imageResource = R.drawable.china_town;
                break;
        }
    }

    private void getCalendarData() {
        ActualCalendarProperties calenderProperties = new ActualCalendarProperties(getActivity());
        actualMonthIndex = calenderProperties.getMonth();
    }

    private void setOnClickListenersForFullScreenImage() {
        imageOfDrink.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Bundle imageResourceBundle = new Bundle();
                imageResourceBundle.putInt(Image_Fullscreen_Fragment.IMAGE_RESOURCE, imageResource);
                Fragment newFragment = Image_Fullscreen_Fragment.newInstance(imageResourceBundle);
                FragmentManager fragmentManager = getFragmentManager();
                fragmentManager.beginTransaction().addToBackStack("fullscreen").replace(R.id.container, newFragment).commit();
            }
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_drink_of_the_month, container, false);
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        ((HomeActivity) activity).onSectionAttached(
                getArguments().getInt(ARG_SECTION_NUMBER));
    }

    public static Drink_Of_The_Month_Fragment newInstance(int sectionNumber) {
        Drink_Of_The_Month_Fragment fragment = new Drink_Of_The_Month_Fragment();
        Bundle args = new Bundle();
        args.putInt(ARG_SECTION_NUMBER, sectionNumber);
        fragment.setArguments(args);
        return fragment;
    }

}
