package com.example.hospital;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class DoctorDescription extends AppCompatActivity {

    public String ID;
    public Doctor doctor;
    public TextView name;
    public TextView age;
    public TextView speciality;
    public TextView email;
    public TextView phone;
    public TextView gender;
    public TextView description;
    public Button makeAppointment;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor_description);

        name = (TextView)findViewById(R.id.name);
        age = (TextView)findViewById(R.id.age);
        speciality = (TextView)findViewById(R.id.speciality);
        email = (TextView)findViewById(R.id.email);
        phone = (TextView)findViewById(R.id.phone);
        gender = (TextView)findViewById(R.id.gender);
        description = (TextView)findViewById(R.id.description);
        makeAppointment = (Button)findViewById(R.id.makeAppointment);


        ID = getIntent().getExtras().get("id").toString();

        doctor =  Doctor.getDoctor(ID,"id",this);

        displayInfo(doctor);

        makeAppointment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(DoctorDescription.this,MakeAppointment.class);
                intent.putExtra("id",ID);
                startActivity(intent);
            }
        });

    }

    public void displayInfo(Doctor doctor)
    {
        name.setText(doctor.getName());
        age.setText(String.valueOf(doctor.getAge()));
        email.setText(doctor.getEmail());
        speciality.setText(doctor.getSpeciality());
        phone.setText(doctor.getPhone());
        gender.setText(doctor.getGender());
        description.setText(doctor.getDescription());
    }

}