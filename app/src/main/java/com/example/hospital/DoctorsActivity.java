package com.example.hospital;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class DoctorsActivity extends AppCompatActivity {
    public ListView listView;
    public ArrayAdapter<String> doctorNames;
    public String speciality;
    public ArrayList<Doctor> Doctors = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctors);

        listView = (ListView)findViewById(R.id.doctorsList);
        doctorNames = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1);
        listView.setAdapter(doctorNames);
        speciality = getIntent().getExtras().get("speciality").toString();
        ((TextView)findViewById(R.id.textView2)).setText(speciality);

        getDoctors();
    }

    public void getDoctors()
    {
        final ArrayList<Doctor> doctors = Doctor.getDoctors(speciality,"specialty",this);

        for (int i = 0 ; i < doctors.size();i++)
        {
            doctorNames.add(doctors.get(i).getName().toString());

            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                    doctorSelected(doctors.get(i).getID());
                }
            });
            }
    }

    public void doctorSelected(String ID)
    {
        Intent intent = new Intent(this,DoctorDescription.class);
        intent.putExtra("id", ID);
        startActivity(intent);
    }
}