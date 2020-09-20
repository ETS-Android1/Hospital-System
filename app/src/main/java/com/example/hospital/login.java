package com.example.hospital;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputLayout;

public class login extends AppCompatActivity {

     private TextView text_signup;
     private TextInputLayout temail;
     private TextInputLayout tpassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        temail =(TextInputLayout)findViewById(R.id.email);
        tpassword=(TextInputLayout)findViewById(R.id.textpass);
        text_signup=(TextView)findViewById(R.id.tsignup);


        text_signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent in = new Intent(login.this,signUP.class);
                startActivity(in);
            }
        });
    }

    private boolean validateUsername() {
        String usernameInput = temail.getEditText().getText().toString().trim();
        if (usernameInput.isEmpty()) {
            temail.setError("Field can't be empty");
            return false;
        } else {
            temail.setError(null);
            return true;
        }
    }

    private boolean validatepass() {
        String usernameInput = tpassword.getEditText().getText().toString().trim();
        if (usernameInput.isEmpty()) {
            tpassword.setError("Field can't be empty");
            return false;
        }  else {
            tpassword.setError(null);
            return true;
        }
    }


    public void log_in(View v) {
        if ( !validateUsername() | !validatepass() ) {
            return;
        }
        String email = temail.getEditText().getText().toString().trim();
        String password = tpassword.getEditText().getText().toString();
        int prev_patient = DataBase.resultSize("select * from Patient where e_mail = '" +email+"' " +
                "and password = '" + password + "'",this);
        int prev_doctor = DataBase.resultSize("select * from Doctor where e_mail = '" +email+"' " +
                "and password = '" + password + "'",this);
        if(prev_patient == 0 && prev_doctor == 0)
        {
            Toast.makeText(this,"Wrong e-mail or password",Toast.LENGTH_LONG).show();
            return;
        }

        Person user;
        if(prev_doctor == 1)
        {
            user = Doctor.getDoctor(email ,"e_mail",this);
            //Intent intent = new Intent(this,);
            //startActivity(intent);
        }

        if(prev_patient == 1)
        {
            user = (Person) Patient.getPatient("e_mail",email,this);
            Toast.makeText(this,"Welcome " + user.getName() ,Toast.LENGTH_LONG).show();
            Intent intent = new Intent(this,Home.class);
            intent.putExtra("Patient",user);
            startActivity(intent);
        }
    }
}