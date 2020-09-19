package com.example.hospital;

import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;


import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;


public class Profile extends AppCompatActivity {
   public EditText email,phone,gender,description;
   public Button submitbutton;
    public TextView temail,tgender,tphone,tdes;
    String emaill,genderr,phonee,des;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_profile);

        temail= (TextView)findViewById(R.id.email_text);

        tgender= (TextView)findViewById(R.id.gender);
        tphone= (TextView)findViewById(R.id.phone);
        tdes= (TextView)findViewById(R.id.des);

    }




        public void openeiditing(View view) {
        email =(EditText)findViewById(R.id.editemail);

           phone =(EditText)findViewById(R.id.eidtphone);

          gender =(EditText)findViewById(R.id.eidtgender);

            description =(EditText)findViewById(R.id.editdescr);

           submitbutton =(Button) findViewById(R.id.submitedit);
     email.setVisibility(view.getVisibility());
            phone.setVisibility(view.getVisibility());
           gender.setVisibility(view.getVisibility());
           submitbutton.setVisibility(view.getVisibility());
            description.setVisibility(view.getVisibility());

           submitbutton.setOnClickListener(new View.OnClickListener() {


                @Override
                public void onClick(View v) {
                    emaill= email.getText().toString();
// get text from EditText password view
                    phonee = phone.getText().toString();

                    genderr= gender.getText().toString();
                    des = description.getText().toString();

                    temail.setText(emaill);
                    tgender.setText(genderr);
                    tphone.setText(phonee);
                    tdes.setText(des);


                    temail.setText(" ");
                    tgender.setText(" ");
                    tphone.setText(" ");
                    tdes.setText(" ");

                }
            });

        }
    }

