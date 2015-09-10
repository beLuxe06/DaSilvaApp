package mi.ur.de.dasilvaapp.Fragments;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;

import mi.ur.de.dasilvaapp.ActualCalendarProperties;
import mi.ur.de.dasilvaapp.SpinnerAdapter;
import mi.ur.de.dasilvaapp.HomeActivity;
import mi.ur.de.dasilvaapp.R;
import mi.ur.de.dasilvaapp.TextInputValidator;

/**
 * Created by blu on 17.08.2015.
 */
public class Reservation_Fragment extends Fragment {

    private static final String ARG_SECTION_NUMBER = "section_number";

    private SpinnerAdapter time_adapter;
    private SpinnerAdapter area_adapter;
    private ActualCalendarProperties calendarProperties;
    private Context context;

    private boolean nameCorrectFlag = false;
    private boolean dateCorrectFlag = false;
    private boolean birthdayDateCorrectFlag = false;
    private boolean birthdayFilledFlag = false;
    private boolean dateFilledFlag = false;
    private boolean legalAgeFlag = false;
    private boolean dateInFutureFlag = false;
    private boolean personsCorrectFlag = false;
    private boolean reasonCorrectFlag = false;
    private boolean spinnerCorrectFlag = false;

    private ArrayList<String> collectedFormData;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_reservation, container, false);
        return rootView;
    }

    @Override
    public void onStart() {
        super.onStart();
        context = getActivity();
        initArrayList();
        initAdapters();
        initUI();
    }

    private void initArrayList() {
        collectedFormData = new ArrayList<>();
    }

    private void initUI() {
        initInputAndValidateFields();
        initSpinners();
        initSendButton();
    }

    private void initSendButton() {
        Button sendButton = (Button) getView().findViewById(R.id.reservation_send_button);
        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent mEmail = new Intent(Intent.ACTION_SEND);
                mEmail.putExtra(Intent.EXTRA_EMAIL, new String[]{"benediktlux@gmx.net"});
                mEmail.putExtra(Intent.EXTRA_SUBJECT, "Da Silva App: Reservierung");
                mEmail.putExtra(Intent.EXTRA_TEXT, collectedFormData.toString());
                mEmail.setType("message/rfc822");
                startActivity(Intent.createChooser(mEmail, "Choose your mail client"));
            }
        });

    }

    private void initInputAndValidateFields() {
        initEnterName();
        initEnterBirthday();
        initEnterMail();
        initEnterPhone();
        initEnterDate();
        initEnterPersons();
        initEnterReason();
    }

    private void initEnterName() {
        EditText name = (EditText) getView().findViewById(R.id.reservation_name_edit);
        final ImageView inputIndicatorName = (ImageView) getView().findViewById(R.id.reservation_name_input_indicator);
        checkForCorrectNameInput(name, inputIndicatorName);
    }

    private void checkForCorrectNameInput(TextView textView, final ImageView indicator) {
        textView.addTextChangedListener(new TextInputValidator(textView) {
            @Override
            public void validate(TextView textView, String text) {
                if (text == null) {
                    setFlag(nameCorrectFlag, false);
                    indicator.setImageResource(R.drawable.icon_incorrect);
                } else {
                    setFlag(nameCorrectFlag, true);
                    indicator.setImageResource(R.drawable.icon_correct);
                    collectedFormData.add(0, text);
                }
            }
        });

    }

    private void initEnterBirthday() {
        EditText birthday = (EditText) getView().findViewById(R.id.reservation_birthday_edit);
        final ImageView inputIndicatorBirthday = (ImageView) getView().findViewById(R.id.reservation_birthday_input_indicator);
        checkForCorrectBirthdayInput(birthday, inputIndicatorBirthday);
    }

    private void checkForCorrectBirthdayInput(TextView textView, final ImageView indicator) {
        textView.addTextChangedListener(new TextInputValidator(textView) {
            @Override
            public void validate(TextView textView, String text) {
                if ((text == null) || (text.length() != 10)) {
                    setFlag(birthdayFilledFlag, false);
                    indicator.setImageResource(R.drawable.icon_incorrect);
                } else {
                    setFlag(birthdayFilledFlag, true);
                    calendarProperties = new ActualCalendarProperties(context);
                    if (calendarProperties.incorrectDateFormat(text)) {
                        setFlag(birthdayDateCorrectFlag, false);
                        indicator.setImageResource(R.drawable.icon_incorrect);
                    } else {
                        setFlag(birthdayDateCorrectFlag, true);
                        if (calendarProperties.illegalAge(text)) {
                            setFlag(legalAgeFlag, false);
                            indicator.setImageResource(R.drawable.icon_incorrect);
                        } else {
                            setFlag(legalAgeFlag, true);
                            indicator.setImageResource(R.drawable.icon_correct);
                            collectedFormData.add(text);
                        }
                    }
                }
            }
        });
    }

    private void initEnterMail() {
        EditText mail = (EditText) getView().findViewById(R.id.reservation_mail_edit);
        final ImageView inputIndicatorMail = (ImageView) getView().findViewById(R.id.reservation_mail_input_indicator);
        checkForCorrectEMailInput(mail, inputIndicatorMail);
    }

    private void checkForCorrectEMailInput(TextView textView, final ImageView indicator) {
        textView.addTextChangedListener(new TextInputValidator(textView) {
            @Override
            public void validate(TextView textView, String text) {
                String email = text.trim();
                String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
                if ((email.matches(emailPattern)) && (email != null)) {
                    indicator.setImageResource(R.drawable.icon_correct);
                    collectedFormData.add(email);
                } else {
                    indicator.setImageResource(R.drawable.icon_incorrect);
                }
            }
        });
    }

    private void initEnterPhone() {
        EditText phone = (EditText) getView().findViewById(R.id.reservation_phone_edit);
        final ImageView inputIndicatorPhone = (ImageView) getView().findViewById(R.id.reservation_phone_input_indicator);
        checkForCorrectPhoneInput(phone, inputIndicatorPhone);
    }

    private void checkForCorrectPhoneInput(TextView textView, final ImageView indicator) {
        textView.addTextChangedListener(new TextInputValidator(textView) {
            @Override
            public void validate(TextView textView, String text) {
                String phone = text.trim();
                String phonePattern = "^[+]?[0-9]{6,20}$";
                if ((phone.matches(phonePattern)) && (phone != null)) {
                    indicator.setImageResource(R.drawable.icon_correct);
                    collectedFormData.add(phone);
                } else {
                    indicator.setImageResource(R.drawable.icon_incorrect);
                }
            }
        });
    }

    private void initEnterDate() {
        EditText date = (EditText) getView().findViewById(R.id.reservation_date_edit);
        final ImageView inputIndicatorDate = (ImageView) getView().findViewById(R.id.reservation_date_input_indicator);
        checkForCorrectDateInput(date, inputIndicatorDate);
    }

    private void checkForCorrectDateInput(TextView textView, final ImageView indicator) {
        textView.addTextChangedListener(new TextInputValidator(textView) {
            @Override
            public void validate(TextView textView, String text) {
                if ((text == null) || (text.length() != 10)) {
                    setFlag(dateFilledFlag, false);
                    indicator.setImageResource(R.drawable.icon_incorrect);
                } else {
                    setFlag(dateFilledFlag, true);
                    calendarProperties = new ActualCalendarProperties(context);
                    if (calendarProperties.incorrectDateFormat(text)) {
                        setFlag(dateCorrectFlag, false);
                        indicator.setImageResource(R.drawable.icon_incorrect);
                    } else {
                        setFlag(dateCorrectFlag, true);
                        if (calendarProperties.timeInPast(text)) {
                            setFlag(dateInFutureFlag, false);
                            indicator.setImageResource(R.drawable.icon_incorrect);
                        } else {
                            setFlag(dateInFutureFlag, true);
                            indicator.setImageResource(R.drawable.icon_correct);
                            collectedFormData.add(text);
                        }
                    }
                }
            }
        });
    }

    private void initEnterPersons() {
        EditText persons = (EditText) getView().findViewById(R.id.reservation_persons_edit);
        final ImageView inputIndicatorPersons = (ImageView) getView().findViewById(R.id.reservation_persons_input_indicator);
        checkForCorrectPersonsInput(persons, inputIndicatorPersons);
    }

    private void checkForCorrectPersonsInput(TextView textView, final ImageView indicator) {
        textView.addTextChangedListener(new TextInputValidator(textView) {
            @Override
            public void validate(TextView textView, String text) {
                if (text == null || text.equals("")) {
                    setFlag(personsCorrectFlag, false);
                    indicator.setImageResource(R.drawable.icon_incorrect);
                } else {
                    int persons = Integer.parseInt(text);
                    if ((persons < 4) || (persons > 50)) {
                        setFlag(personsCorrectFlag, false);
                        indicator.setImageResource(R.drawable.icon_incorrect);
                    } else {
                        setFlag(personsCorrectFlag, true);
                        indicator.setImageResource(R.drawable.icon_correct);
                        collectedFormData.add(text);
                    }
                }
            }
        });
    }

    private void initEnterReason() {
        EditText reason = (EditText) getView().findViewById(R.id.reservation_reason_edit);
        final ImageView inputIndicatorReason = (ImageView) getView().findViewById(R.id.reservation_reason_input_indicator);
        checkForCorrectReasonInput(reason, inputIndicatorReason);
    }

    private void checkForCorrectReasonInput(TextView textView, final ImageView indicator) {
        textView.addTextChangedListener(new TextInputValidator(textView) {
            @Override
            public void validate(TextView textView, String text) {
                if ((text == null) || (text.length() < 4) || (text.length() > 30)) {
                    setFlag(reasonCorrectFlag, false);
                    indicator.setImageResource(R.drawable.icon_incorrect);
                } else {
                    setFlag(personsCorrectFlag, true);
                    indicator.setImageResource(R.drawable.icon_correct);
                    collectedFormData.add(text);
                }
            }
        });
    }

    private void initSpinners() {
        initTimeSpinner();
        initAreaSpinner();
    }

    private void initTimeSpinner() {
        Spinner timeSpinner = (Spinner) getView().findViewById(R.id.time_spinner);
        timeSpinner.setAdapter(time_adapter);
        final ImageView inputIndicatorTime = (ImageView) getView().findViewById(R.id.reservation_time_input_indicator);
        checkForCorrectTimeSpinnerInput(timeSpinner, inputIndicatorTime);
    }

    private void checkForCorrectTimeSpinnerInput(Spinner spinner, ImageView indicator) {
        String text = spinner.getSelectedItem().toString();
        if ((text == null) || (text.equals(""))) {
            setFlag(spinnerCorrectFlag, false);
            indicator.setImageResource(R.drawable.icon_incorrect);
        } else {
            setFlag(spinnerCorrectFlag, true);
            indicator.setImageResource(R.drawable.icon_correct);
            collectedFormData.add(text);
        }
    }

    private void initAreaSpinner() {
        Spinner areaSpinner = (Spinner) getView().findViewById(R.id.area_spinner);
        TextView selectedItem = (TextView) getView().findViewById(R.id.spinner_item_text);
        areaSpinner.setAdapter(area_adapter);
        final ImageView inputIndicatorArea = (ImageView) getView().findViewById(R.id.reservation_area_input_indicator);
        checkForCorrectAreaSpinnerInput(areaSpinner, inputIndicatorArea);
    }

    private void checkForCorrectAreaSpinnerInput(Spinner spinner, ImageView indicator) {
        String text = spinner.getSelectedItem().toString();
        if ((text == null) || (text.equals(""))) {
            setFlag(spinnerCorrectFlag, false);
            indicator.setImageResource(R.drawable.icon_incorrect);
        } else {
            setFlag(spinnerCorrectFlag, true);
            indicator.setImageResource(R.drawable.icon_correct);
            collectedFormData.add(text);
        }
    }

    private void initAdapters() {
        initTimeSpinnerAdapter();
        initAreaSpinnerAdapter();
    }

    private void initAreaSpinnerAdapter() {
        String [] area_array = getActivity().getResources().getStringArray(R.array.area_array);
        this.area_adapter = new SpinnerAdapter<String>(getActivity(), area_array);
    }

    private void initTimeSpinnerAdapter() {
        String [] time_array = getActivity().getResources().getStringArray(R.array.time_array);
        this.time_adapter = new SpinnerAdapter<String>(getActivity(), time_array);
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        ((HomeActivity) activity).onSectionAttached(
                getArguments().getInt(ARG_SECTION_NUMBER));
    }

    public static Reservation_Fragment newInstance(int sectionNumber) {
        Reservation_Fragment fragment = new Reservation_Fragment();
        Bundle args = new Bundle();
        args.putInt(ARG_SECTION_NUMBER, sectionNumber);
        fragment.setArguments(args);
        return fragment;
    }

    public void setFlag(boolean flagToSet, boolean value) {
        flagToSet = value;
    }
}
