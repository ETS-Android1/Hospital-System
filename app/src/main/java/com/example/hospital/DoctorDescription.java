package com.example.hospital;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.TextView;

import java.sql.Time;

public class DoctorDescription extends AppCompatActivity {

    public Doctor doctor;
    public TextView name;
    public TextView age;
    public TextView speciality;
    public TextView email;
    public TextView phone;
    public TextView gender;
    public TextView description;
    public Button makeAppointment;
    public Patient patient;

    private RadioButton Saturday;
    private RadioButton Sunday;
    private RadioButton Monday;
    private RadioButton Tuesday;
    private RadioButton Wednesday;
    private RadioButton Thursday;
    private RadioButton Friday;


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
        Saturday = (RadioButton) findViewById(R.id.Saturday);
        Sunday = (RadioButton) findViewById(R.id.Sunday);
        Monday = (RadioButton) findViewById(R.id.Monday);
        Tuesday = (RadioButton) findViewById(R.id.Tuesday);
        Wednesday = (RadioButton) findViewById(R.id.Wednesday);
        Thursday = (RadioButton) findViewById(R.id.Thursday);
        Friday = (RadioButton) findViewById(R.id.Friday);
        Saturday.setClickable(false);
        Sunday.setClickable(false);
        Monday.setClickable(false);
        Tuesday.setClickable(false);
        Wednesday.setClickable(false);
        Thursday.setClickable(false);
        Friday.setClickable(false);

        doctor = (Doctor) getIntent().getSerializableExtra("Doctor");
        patient = (Patient) getIntent().getSerializableExtra("Person");

        displayInfo(doctor);

        makeAppointment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(DoctorDescription.this,MakeAppointment.class);
                intent.putExtra("Doctor",doctor);
                intent.putExtra("Patient",patient);
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
        setAvailableDays();
    }

    public void setAvailableDays()
    {
        Time[] days = doctor.getAvailableDays();
        if(days[0] != null)
        {
            Sunday.setChecked(true);
        }
        if(days[1] != null)
        {
            Monday.setChecked(true);
        }
        if(days[2] != null)
        {
            Tuesday.setChecked(true);
        }
        if(days[3] != null)
        {
            Wednesday.setChecked(true);
        }
        if(days[4] != null)
        {
            Thursday.setChecked(true);
        }
        if(days[5] != null)
        {
            Friday.setChecked(true);
        }
        if(days[6] != null)
        {
            Saturday.setChecked(true);
        }
    }
}