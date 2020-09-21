package com.example.hospital;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputLayout;

import java.lang.reflect.Array;
import java.sql.Date;
import java.util.ArrayList;
import java.util.regex.Pattern;

public class AdminAddFrag extends Fragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";


    private String mParam1;
    private String mParam2;
    private TextInputLayout text_email;
    private TextInputLayout text_username;
    private TextInputLayout text_passord;
    private TextInputLayout text_phone;
    private Spinner text_specialty;
    private TextInputLayout text_description;
    private TextInputLayout text_birth_date;
    private Spinner spinner_day;
    private Spinner spinner_month;
    private Spinner spinner_year;
    private RadioGroup rdGroup;


    private static final Pattern PASSWORD_PATTERN =
            Pattern.compile("^" +
                    //"(?=.*[0-9])" +         //at least 1 digit
                    //"(?=.*[a-z])" +         //at least 1 lower case letter
                    //"(?=.*[A-Z])" +         //at least 1 upper case letter
                    "(?=.*[a-zA-Z])" +      //any letter
                    "(?=.*[@#$%^&+=])" +    //at least 1 special character
                    "(?=\\S+$)" +           //no white spaces
                    ".{4,}" +               //at least 4 characters
                    "$");


    public AdminAddFrag() {
        // Required empty public constructor
    }

    public static AdminAddFrag newInstance(String param1, String param2) {
        AdminAddFrag fragment = new AdminAddFrag();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView= inflater.inflate(R.layout.fragment_admin_add, container, false);

        text_email=rootView.findViewById(R.id.text_email);
        text_username=rootView.findViewById(R.id.text_user_name);
        text_passord=rootView.findViewById(R.id.text_pass);
        text_phone=rootView.findViewById(R.id.text_phone);
        text_specialty=rootView.findViewById(R.id.spin_specialty);
        text_description=rootView.findViewById(R.id.text_description);
        spinner_day = rootView.findViewById(R.id.spinner_day);
        spinner_month = rootView.findViewById(R.id.spinner_month);
        spinner_year = rootView.findViewById(R.id.spinner_year);
        initializeSpinners();
        Button confirm= rootView.findViewById(R.id.button_add);
        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                signup2(view);
            }
        });

        return  rootView;
    }


    private boolean validateEmail() {
        String emailInput = text_email.getEditText().getText().toString().trim();
        if (emailInput.isEmpty()) {
            text_email.setError("Field can't be empty");
            return false;
        }
        else if (!Patterns.EMAIL_ADDRESS.matcher(emailInput).matches()) {
            text_email.setError("Please enter a valid email address");
            return false;
        }
        else {
            text_email.setError(null);
            return true;
        }
    }

    private boolean validateUsername() {
        String usernameInput = text_username.getEditText().getText().toString().trim();
        if (usernameInput.isEmpty()) {
            text_username.setError("Field can't be empty");
            return false;
        } else if (usernameInput.length() > 15) {
            text_username.setError("Username too long");
            return false;
        } else {
            text_username.setError(null);
            return true;
        }
    }

    private boolean validateSpecialty() {
        String usernameInput = text_specialty.getSelectedItem().toString();
        return true;
    }


    private boolean validateDescription() {
        String usernameInput = text_description.getEditText().getText().toString().trim();
        if (usernameInput.isEmpty()) {
            text_description.setError("Field can't be empty");
            return false;

        } else {
            text_description.setError(null);
            return true;
        }
    }
    private boolean validateBirthDate() {
        String usernameInput = text_birth_date.getEditText().getText().toString().trim();
        if (usernameInput.isEmpty()) {
            text_birth_date.setError("Field can't be empty");
            return false;
        } else {
            text_birth_date.setError(null);
            return true;
        }
    }

    private boolean validatePassword() {
        String passwordInput = text_passord.getEditText().getText().toString().trim();
        if (passwordInput.isEmpty()) {
            text_passord.setError("Field can't be empty");
            return false;
        }
        else if (!PASSWORD_PATTERN.matcher(passwordInput).matches()) {
            text_passord.setError("Password too weak");
            return false;
        }

        else {
            text_passord.setError(null);
            return true;
        }
    }

    private boolean validatePhoneNumber() {
        String phoneInput = text_phone.getEditText().getText().toString().trim();
        if (phoneInput.isEmpty()) {
            text_phone.setError("Field can't be empty");
            return false;
        } else if (phoneInput.length() > 11) {
            text_phone.setError("not valid number");
            return false;
        } else {
            text_phone.setError(null);
            return true;
        }
    }
    public void signup(View v) {
        Toast.makeText(getContext(),"zeft",Toast.LENGTH_SHORT).show();
        if (!validateEmail() | !validateUsername() | !validatePassword()| !validatePhoneNumber()|!validateBirthDate()|!validateSpecialty()|!validateDescription()) {
            return;
        }
        String name=text_username.getEditText().getText().toString();
        String email=text_email.getEditText().getText().toString();
        String pass=text_passord.getEditText().getText().toString();
        String phone=text_phone.getEditText().getText().toString();
        String specialty=text_specialty.getSelectedItem().toString();
        String description=text_description.getEditText().getText().toString();
        Date birthDate=new Date(Integer.parseInt(text_birth_date.getEditText().getText().toString()));
        String gender="male";
        Doctor doctor=new Doctor("-1",name,phone,email,gender,text_birth_date.getEditText().getText().toString(),specialty,description,0);
        addDoctor(doctor,pass,getContext());
    }
    public void signup2(View v) {

        String name="moh";
        String email="ay@gmail.com";
        String pass="132456789";
        String phone="0213865";
        String specialty="den";
        String description="gamazan";
        String birthDate="2000-1-1";
        String gender="Male";
        Doctor doctor=new Doctor("-1",name,phone,email,gender,birthDate,specialty,description,0);
        addDoctor(doctor,pass,getContext());
        Toast.makeText(getContext(),"Done",Toast.LENGTH_SHORT).show();

    }

    private void addDoctor(Doctor doctor, String password, Context context) {
        String query = "INSERT INTO Doctor(name,e_mail,phone,password,birth_date,gender,specialty,description,max_app_per_day)"
                + "VALUES ('" + doctor.getName() + "','" + doctor.getEmail() + "','" + doctor.getPhone() + "','" + password + "','" + doctor.getDateOfBirth() + "','" + doctor.getGender() + "','" + doctor.getSpeciality() + "','" + doctor.getDescription() + "','" + doctor.getMaxAppointmentsPerDay() + "');";
        DataBase.excutQuery(query, context);
        /**   ,sunday,monday,tuesday,wednesday,thursday,friday,saturday */
        /**   + "','" + doctor.getTime(0) + "','" + doctor.getTime(1) + "','" + doctor.getTime(2) + "','" + doctor.getTime(3) + "','" + doctor.getTime(4) + "','" + doctor.getTime(5) + "','" + "NULL"  */
    }

    public void initializeSpinners() {
        ArrayList<String> arrayList = new ArrayList<>();
        for (int i = 1; i <= 31; i++)
            arrayList.add(String.valueOf(i));
        arrayList.add(0, "Day");
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this.getContext(), android.R.layout.simple_spinner_dropdown_item, arrayList);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_day.setAdapter(adapter);

        arrayList = new ArrayList<>();
        for (int i = 1; i <= 12; i++)
            arrayList.add(String.valueOf(i));
        arrayList.add(0, "Month");
        adapter = new ArrayAdapter<String>(this.getContext(), android.R.layout.simple_spinner_dropdown_item, arrayList);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_month.setAdapter(adapter);

        arrayList = new ArrayList<>();
        int year = new java.util.Date().getYear() + 1900;
        for (int i = year - 18; i >= year - 100; i--)
            arrayList.add(String.valueOf(i));
        arrayList.add(0, "Year");
        adapter = new ArrayAdapter<String>(this.getContext(), android.R.layout.simple_spinner_dropdown_item, arrayList);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_year.setAdapter(adapter);
    }


}