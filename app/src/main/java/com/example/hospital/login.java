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
     private TextInputLayout tusername;
     private TextInputLayout tpassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        tusername=findViewById(R.id.textusername);
        tpassword=findViewById(R.id.textpass);
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
        String usernameInput = tusername.getEditText().getText().toString().trim();
        if (usernameInput.isEmpty()) {
            tusername.setError("Field can't be empty");
            return false;
        } else if (usernameInput.length() > 15) {
            tusername.setError("Username too long");
            return false;
        } else {
            tusername.setError(null);
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

    }


}