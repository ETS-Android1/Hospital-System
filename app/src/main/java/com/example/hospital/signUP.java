package com.example.hospital;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputLayout;

import java.util.regex.Pattern;

public class signUP extends AppCompatActivity {

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


    private TextInputLayout text_email;
    private TextInputLayout text_username;
    private TextInputLayout text_passord;
    private TextInputLayout text_phone;
    private RadioGroup rb_group;
    private TextView text_start;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_u_p);

         text_email=findViewById(R.id.text_email);
        text_username=findViewById(R.id.text_user_name);
        text_passord=findViewById(R.id.text_pass);
        text_phone=findViewById(R.id.text_phone);
        rb_group=findViewById(R.id.rdgroup);
        text_start=(TextView)findViewById(R.id.tlog);


        text_start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent in = new Intent(signUP.this,login.class);
                startActivity(in);

            }
        });

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

    private boolean validatephonenumber() {
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

    private boolean validateradiogroup()
    {
        int isselected=rb_group.getCheckedRadioButtonId();
        if(isselected==-1)
        {
            Toast.makeText(signUP.this,"you have not selected any of gender",Toast.LENGTH_SHORT).show();
            return false;
        }
        else {
            return true;
        }
    }
    public void confirminput(View v) {
        if (!validateEmail() | !validateUsername() | !validatePassword()| !validatephonenumber() | !validateradiogroup() ) {
            return;
        }
        String input = "Email: " + text_email.getEditText().getText().toString();
        input += "\n";
        input += "Username: " + text_username.getEditText().getText().toString();
        input += "\n";
        input += "Password: " + text_passord.getEditText().getText().toString();
        Toast.makeText(this, input, Toast.LENGTH_SHORT).show();
    }

}