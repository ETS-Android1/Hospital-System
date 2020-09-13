package com.example.hospital;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

public class DoctorsActivity extends AppCompatActivity {
    public ListView listView;
    public ArrayAdapter<String> doctorNames;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctors);

        listView = (ListView)findViewById(R.id.doctorsList);
        doctorNames = new ArrayAdapter<String>(getApplicationContext(),android.R.layout.simple_list_item_1);
        listView.setAdapter(doctorNames);
        

    }
}