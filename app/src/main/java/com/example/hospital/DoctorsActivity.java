package com.example.hospital;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavGraph;
import androidx.navigation.Navigation;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class DoctorsActivity extends AppCompatActivity {
    public ArrayList<Doctor> doctors = new ArrayList<>();
    public DoctorArrayAdapter doctorAdapter;
    public ListView listView;
    public String speciality;
    public Patient patient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctors);

        listView = (ListView)findViewById(R.id.doctorsList);
        doctorAdapter = new DoctorArrayAdapter(this,R.layout.doctor_list,doctors);
        listView.setAdapter(doctorAdapter);
        speciality = getIntent().getExtras().get("speciality").toString();
        ((TextView)findViewById(R.id.textView2)).setText(speciality);

        patient = (Patient) getIntent().getSerializableExtra("Patient");

        getDoctors();
    }

    public void getDoctors()
    {
        final ArrayList<Doctor> Doctors = Doctor.getDoctors(speciality,"specialty",this);

        for (Doctor doctor : Doctors)
        {
            doctors.add(doctor);
            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                    doctorSelected(doctors.get(i));
                }
            });
            }
    }

    public void doctorSelected(Doctor doctor)
    {
        Intent intent = new Intent(this,DoctorDescription.class);
        intent.putExtra("Doctor", doctor);
        intent.putExtra("Patient",patient);
        startActivity(intent);
    }
}