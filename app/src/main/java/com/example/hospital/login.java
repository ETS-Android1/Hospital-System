package com.example.hospital;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Pair;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputLayout;

public class login extends AppCompatActivity {

     private TextView text_signup;
     private TextInputLayout temail;
     private TextInputLayout tpassword;
     private CheckBox remember_checkBox;
     private Button loginButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        temail =(TextInputLayout)findViewById(R.id.email);
        tpassword=(TextInputLayout)findViewById(R.id.textpass);
        text_signup=(TextView)findViewById(R.id.tsignup);
        remember_checkBox = (CheckBox) findViewById(R.id.remember_checkBox);
        loginButton = (Button) findViewById(R.id.buttonLogin);
        //forgetMe();
        rememberMe();

        text_signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent in = new Intent(login.this,signUP.class);
                startActivity(in);
            }
        });

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                log_in();
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


    public void log_in() {
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

        SQLiteDB db= new SQLiteDB(this);
        if(remember_checkBox.isChecked())
        {
            db.addUser(email,password);
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
            Intent intent = new Intent(this,NavigateActivity.class);
            intent.putExtra("Patient",user);
            intent.putExtra("pageIndex",1);
            startActivity(intent);
        }
    }

    public void rememberMe()
    {
        SQLiteDB db = new SQLiteDB(this);
        if(db.isTableExist())
        {
            Pair<String,String> pair = db.getUser();
            temail.getEditText().setText(pair.first);
            tpassword.getEditText().setText(pair.second);
            log_in();
        }
    }

    public void forgetMe()
    {
        SQLiteDB db = new SQLiteDB(this);
        db.deleteTable();
    }
}