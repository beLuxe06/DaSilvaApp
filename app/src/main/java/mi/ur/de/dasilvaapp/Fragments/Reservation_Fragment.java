package mi.ur.de.dasilvaapp.Fragments;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;

import mi.ur.de.dasilvaapp.ActualCalendarProperties;
import mi.ur.de.dasilvaapp.DateHelper;
import mi.ur.de.dasilvaapp.Reservation;
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
    private DateHelper dh;
    private Context context;
    private Reservation reservation;

    private String selectedTime;
    private String selectedArea;
    private int nameCorrectFlag = 0;
    private int dateCorrectFlag = 0;
    private int mailCorrectFlag = 0;
    private int birthdayDateCorrectFlag = 0;
    private int birthdayFilledFlag = 0;
    private int dateFilledFlag = 0;
    private int legalAgeFlag = 0;
    private int dateInFutureFlag = 0;
    private int phoneCorrectFlag = 0;
    private int personsCorrectFlag = 0;
    private int reasonCorrectFlag = 0;
    private int areaCorrectFlag = 0;
    private int timeCorrectFlag = 0;
    private static final int FALSE = 0;
    private static final int TRUE = 1;

    private ArrayList<String> collectedFormData;
    private Button sendButton;
    private EditText name;
    private EditText birthday;
    private EditText mail;
    private EditText phone;
    private EditText date;
    private EditText persons;
    private EditText reason;
    private Spinner timeSpinner;
    private Spinner areaSpinner;

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
        sendButton = (Button) getView().findViewById(R.id.reservation_send_button);
        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (correctFlagSum()) {
                    createReservationItemFromFormData();
                    Intent mEmail = new Intent(Intent.ACTION_SEND);
                    mEmail.putExtra(Intent.EXTRA_EMAIL, new String[]{"DaSilvaReservierung@gmx.de"});
                    mEmail.putExtra(Intent.EXTRA_SUBJECT, "Da Silva App: Reservierung am " + reservation.getDate());
                    mEmail.putExtra(Intent.EXTRA_TEXT, getFormattedReservationString());
                    mEmail.setType("message/rfc822");
                    startActivity(Intent.createChooser(mEmail, "Choose your mail client"));
                }
            }
        });
    }

    private String getFormattedReservationString() {
        return "Name: " + reservation.getName() +"\n" +
                "Geburtstag: " + reservation.getBirthday() + "\n" +
                "E-Mail: " + reservation.getMail() + "\n" +
                "Telefon: " + reservation.getPhone() + "\n" +
                "Datum: " + reservation.getDate() + "\n" +
                "Uhrzeit: " + reservation.getTime() + "\n" +
                "Personenanzahl: " + reservation.getPersons() + "\n" +
                "Örtlichkeit: " + reservation.getArea() + "\n" +
                "Anlass: " + reservation.getReason() + "\n";
    }

    private void createReservationItemFromFormData() {
        String reservationName = name.getText().toString();
        String reservationBirthday = birthday.getText().toString();
        String reservationMail = mail.getText().toString();
        String reservationPhone = phone.getText().toString();
        String reservationDate = date.getText().toString();
        String reservationTime = selectedTime;
        String reservationPersons = persons.getText().toString();
        String reservationArea = selectedArea;
        String reservationReason = reason.getText().toString();
        reservation = new Reservation(reservationName, reservationBirthday, reservationMail, reservationPhone, reservationDate, reservationTime, reservationPersons, reservationArea, reservationReason);
    }

    private boolean correctFlagSum() {
        return getFlagSum() == 9;
    }

    private int getFlagSum() {
        return (nameCorrectFlag +  legalAgeFlag + mailCorrectFlag + phoneCorrectFlag + dateInFutureFlag + timeCorrectFlag + personsCorrectFlag + areaCorrectFlag+ reasonCorrectFlag );
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
        name = (EditText) getView().findViewById(R.id.reservation_name_edit);
        final ImageView inputIndicatorName = (ImageView) getView().findViewById(R.id.reservation_name_input_indicator);
        checkForCorrectNameInput(name, inputIndicatorName);
    }

    private void checkForCorrectNameInput(TextView textView, final ImageView indicator) {
        textView.addTextChangedListener(new TextInputValidator(textView) {
            @Override
            public void validate(TextView textView, String text) {
                if (text == null) {
                    nameCorrectFlag = FALSE;
                    indicator.setImageResource(R.drawable.icon_incorrect);
                } else {
                    nameCorrectFlag = TRUE;
                    indicator.setImageResource(R.drawable.icon_correct);
                }
            }
        });

    }

    private void initEnterBirthday() {
        birthday = (EditText) getView().findViewById(R.id.reservation_birthday_edit);
        final ImageView inputIndicatorBirthday = (ImageView) getView().findViewById(R.id.reservation_birthday_input_indicator);
        checkForCorrectBirthdayInput(birthday, inputIndicatorBirthday);
    }

    private void checkForCorrectBirthdayInput(TextView textView, final ImageView indicator) {
        textView.addTextChangedListener(new TextInputValidator(textView) {
            @Override
            public void validate(TextView textView, String text) {
                if ((text == null) || (text.length() != 10)) {
                    birthdayFilledFlag = FALSE;
                    indicator.setImageResource(R.drawable.icon_incorrect);
                } else {
                    birthdayFilledFlag = TRUE;
                    if (dh.incorrectDateFormat(text)) {
                        birthdayDateCorrectFlag = FALSE;
                        indicator.setImageResource(R.drawable.icon_incorrect);
                    } else {
                        birthdayDateCorrectFlag = TRUE;
                        if (dh.illegalAge(text)) {
                            legalAgeFlag = FALSE;
                            indicator.setImageResource(R.drawable.icon_incorrect);
                        } else {
                            legalAgeFlag = TRUE;
                            indicator.setImageResource(R.drawable.icon_correct);
                        }
                    }
                }
            }
        });
    }

    private void initEnterMail() {
        mail = (EditText) getView().findViewById(R.id.reservation_mail_edit);
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
                    mailCorrectFlag = TRUE;
                    indicator.setImageResource(R.drawable.icon_correct);
                } else {
                    mailCorrectFlag = FALSE;
                    indicator.setImageResource(R.drawable.icon_incorrect);
                }
            }
        });
    }

    private void initEnterPhone() {
        phone = (EditText) getView().findViewById(R.id.reservation_phone_edit);
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
                    phoneCorrectFlag = TRUE;
                    indicator.setImageResource(R.drawable.icon_correct);
                } else {
                    phoneCorrectFlag = FALSE;
                    indicator.setImageResource(R.drawable.icon_incorrect);
                }
            }
        });
    }

    private void initEnterDate() {
        date = (EditText) getView().findViewById(R.id.reservation_date_edit);
        final ImageView inputIndicatorDate = (ImageView) getView().findViewById(R.id.reservation_date_input_indicator);
        checkForCorrectDateInput(date, inputIndicatorDate);
    }

    private void checkForCorrectDateInput(TextView textView, final ImageView indicator) {
        textView.addTextChangedListener(new TextInputValidator(textView) {
            @Override
            public void validate(TextView textView, String text) {
                if ((text == null) || (text.length() != 10)) {
                    dateFilledFlag = FALSE;
                    indicator.setImageResource(R.drawable.icon_incorrect);
                } else {
                    dateFilledFlag = TRUE;
                    dh = new DateHelper(context);
                    if (dh.incorrectDateFormat(text)) {
                        dateCorrectFlag = FALSE;
                        indicator.setImageResource(R.drawable.icon_incorrect);
                    } else {
                        dateCorrectFlag = TRUE;
                        if (dh.timeInPast(text)) {
                            dateInFutureFlag = FALSE;
                            indicator.setImageResource(R.drawable.icon_incorrect);
                        } else {
                            dateInFutureFlag = TRUE;
                            indicator.setImageResource(R.drawable.icon_correct);
                        }
                    }
                }
            }
        });
    }

    private void initEnterPersons() {
        persons = (EditText) getView().findViewById(R.id.reservation_persons_edit);
        final ImageView inputIndicatorPersons = (ImageView) getView().findViewById(R.id.reservation_persons_input_indicator);
        checkForCorrectPersonsInput(persons, inputIndicatorPersons);
    }

    private void checkForCorrectPersonsInput(TextView textView, final ImageView indicator) {
        textView.addTextChangedListener(new TextInputValidator(textView) {
            @Override
            public void validate(TextView textView, String text) {
                if (text == null || text.equals("")) {
                    personsCorrectFlag = FALSE;
                    indicator.setImageResource(R.drawable.icon_incorrect);
                } else {
                    int persons = Integer.parseInt(text);
                    if ((persons < 4) || (persons > 50)) {
                        personsCorrectFlag = FALSE;
                        indicator.setImageResource(R.drawable.icon_incorrect);
                    } else {
                        personsCorrectFlag = TRUE;
                        indicator.setImageResource(R.drawable.icon_correct);
                    }
                }
            }
        });
    }

    private void initEnterReason() {
        reason = (EditText) getView().findViewById(R.id.reservation_reason_edit);
        final ImageView inputIndicatorReason = (ImageView) getView().findViewById(R.id.reservation_reason_input_indicator);
        checkForCorrectReasonInput(reason, inputIndicatorReason);
    }

    private void checkForCorrectReasonInput(TextView textView, final ImageView indicator) {
        textView.addTextChangedListener(new TextInputValidator(textView) {
            @Override
            public void validate(TextView textView, String text) {
                if ((text == null) || (text.length() < 4) || (text.length() > 30)) {
                    reasonCorrectFlag = FALSE;
                    indicator.setImageResource(R.drawable.icon_incorrect);
                } else {
                    reasonCorrectFlag = TRUE;
                    indicator.setImageResource(R.drawable.icon_correct);
                }
            }
        });
    }

    private void initSpinners() {
        initTimeSpinner();
        initAreaSpinner();
    }

    private void initTimeSpinner() {
        timeSpinner = (Spinner) getView().findViewById(R.id.time_spinner);
        timeSpinner.setAdapter(time_adapter);
        final ImageView inputIndicatorTime = (ImageView) getView().findViewById(R.id.reservation_time_input_indicator);
        timeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectedTime = time_adapter.getItem(position).toString();
                if((selectedTime.equals("") || (selectedTime == null))){
                    timeCorrectFlag = FALSE;
                    inputIndicatorTime.setImageResource(R.drawable.icon_incorrect);
                }
                else{
                    timeCorrectFlag = TRUE;
                    inputIndicatorTime.setImageResource(R.drawable.icon_correct);
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                timeCorrectFlag = FALSE;
                inputIndicatorTime.setImageResource(R.drawable.icon_incorrect);
            }
        });
    }

    private void initAreaSpinner() {
        areaSpinner = (Spinner) getView().findViewById(R.id.area_spinner);
        areaSpinner.setAdapter(area_adapter);
        final ImageView inputIndicatorArea = (ImageView) getView().findViewById(R.id.reservation_area_input_indicator);
        areaSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectedArea = area_adapter.getItem(position).toString();
                if((selectedArea.equals("") || (selectedArea == null))){
                    areaCorrectFlag = FALSE;
                    inputIndicatorArea.setImageResource(R.drawable.icon_incorrect);
                }
                else{
                    areaCorrectFlag = TRUE;
                    inputIndicatorArea.setImageResource(R.drawable.icon_correct);
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                areaCorrectFlag = FALSE;
                inputIndicatorArea.setImageResource(R.drawable.icon_incorrect);
            }
        });
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
}
