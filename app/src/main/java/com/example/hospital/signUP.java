package com.example.hospital;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputLayout;

import java.util.ArrayList;
import java.util.Date;
import java.util.regex.Pattern;

public class signUP extends AppCompatActivity {

    private static final Pattern PASSWORD_PATTERN =
            Pattern.compile("^" +
                    //"(?=.*[0-9])" +         //at least 1 digit
                    //"(?=.*[a-z])" +         //at least 1 lower case letter
                    //"(?=.*[A-Z])" +         //at least 1 upper case letter
                    "(?=.*[a-zA-Z])" +      //any letter
                    "(?=.*[@#$%^&+_=])" +    //at least 1 special character
                    "(?=\\S+$)" +           //no white spaces
                    ".{8,}" +               //at least 4 characters
                    "$");


    private TextInputLayout text_email;
    private TextInputLayout text_username;
    private TextInputLayout text_password;
    private TextInputLayout text_phone;
    private RadioGroup rb_group;
    private TextView text_start;
    private Spinner spinner_day;
    private Spinner spinner_month;
    private Spinner spinner_year;
    private String gender;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_u_p);

        text_email = findViewById(R.id.text_email);
        text_username = findViewById(R.id.text_user_name);
        text_password = findViewById(R.id.text_pass);
        text_phone = findViewById(R.id.text_phone);
        rb_group = findViewById(R.id.rdgroup);
        text_start = (TextView) findViewById(R.id.tlog);
        spinner_day = (Spinner) findViewById(R.id.spinner_day);
        spinner_month = (Spinner) findViewById(R.id.spinner_month);
        spinner_year = (Spinner) findViewById(R.id.spinner_year);

        initializeSpinners();


        text_start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(signUP.this, login.class);
                startActivity(intent);
            }
        });

    }

    private boolean validateEmail() {
        String emailInput = text_email.getEditText().getText().toString().trim();
        if (emailInput.isEmpty()) {
            text_email.setError("Field can't be empty");
            return false;
        } else if (!Patterns.EMAIL_ADDRESS.matcher(emailInput).matches()) {
            text_email.setError("Please enter a valid email address");
            return false;
        } else {
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

    private boolean validatePassword() {
        String passwordInput = text_password.getEditText().getText().toString().trim();
        if (passwordInput.isEmpty()) {
            text_password.setError("Field can't be empty");
            return false;
        } else if (!PASSWORD_PATTERN.matcher(passwordInput).matches()) {
            text_password.setError("Password too weak");
            return false;
        } else {
            text_password.setError(null);
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

    private boolean validateRadioGroup() {
        int isSelected = rb_group.getCheckedRadioButtonId();
        if (isSelected == -1) {
            Toast.makeText(signUP.this, "Please select gender", Toast.LENGTH_SHORT).show();
            return false;
        } else {
            if (rb_group.getCheckedRadioButtonId() == R.id.rdmale)
                gender = "Male";
            else
                gender = "Female";
            return true;
        }
    }

    public boolean validateBirthDate() {
        if (spinner_year.getSelectedItemPosition() == 0 || spinner_month.getSelectedItemPosition() == 0 ||
                spinner_day.getSelectedItemPosition() == 0) {
            Toast.makeText(this, "Please select your birth date", Toast.LENGTH_LONG).show();
            return false;
        }
        return true;
    }

    public void confirmInput(View v) {
        if (!validateEmail() | !validateUsername() | !validatePassword() | !validatePhoneNumber() |
                !validateRadioGroup() | !validateBirthDate()) {
            return;
        }
        Registration();
    }

    public void initializeSpinners() {
        ArrayList<String> arrayList = new ArrayList<>();
        for (int i = 1; i <= 31; i++)
            arrayList.add(String.valueOf(i));
        arrayList.add(0, "Day");
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, arrayList);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_day.setAdapter(adapter);

        arrayList = new ArrayList<>();
        for (int i = 1; i <= 12; i++)
            arrayList.add(String.valueOf(i));
        arrayList.add(0, "Month");
        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, arrayList);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_month.setAdapter(adapter);

        arrayList = new ArrayList<>();
        int year = new Date().getYear() + 1900;
        for (int i = year - 18; i >= year - 100; i--)
            arrayList.add(String.valueOf(i));
        arrayList.add(0, "Year");
        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, arrayList);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_year.setAdapter(adapter);
    }

    public void Registration() {
        String email = text_email.getEditText().getText().toString().trim();
        int prevPatient = DataBase.resultSize("select * from Patient where e_mail ='" + email + "'", this);
        int prevDoctor = DataBase.resultSize("select * from Doctor where e_mail ='" + email + "'", this);
        if (prevPatient == 1 || prevDoctor == 1) {
            text_email.setError("This e-mail is used before");
            return;
        }
        String userName = text_username.getEditText().getText().toString();
        String password = text_password.getEditText().getText().toString().trim();
        String phone = text_phone.getEditText().getText().toString().trim();
        int dayBirth = Integer.parseInt(spinner_day.getSelectedItem().toString()),
                monthBirth = Integer.parseInt(spinner_month.getSelectedItem().toString()),
                yearBirth = Integer.parseInt(spinner_year.getSelectedItem().toString());
        DataBase.excutQuery("insert into Patient values ('" + userName + "','" + email + "','"
                + phone + "','" + password + "','" + yearBirth + "-" + monthBirth + "-" + dayBirth +
                "','" + gender + "')", this);
        Patient patient = Patient.getPatient("e_mail", email, this);
        Toast.makeText(this,"Successful Registration",Toast.LENGTH_LONG).show();
        Intent intent = new Intent(this,NavigateActivity.class);
        intent.putExtra("Patient",patient);
        startActivity(intent);
    }

}