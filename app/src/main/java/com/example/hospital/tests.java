package com.example.hospital;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class tests extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tests);
        //loading data and send it to the constructor
        Test t = new Test();

        ListView tests = (ListView) findViewById(R.id.viewTests);

        final ArrayAdapter<String> testsData = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1);
        tests.setAdapter(testsData);
        t.loadData(testsData);

        tests.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //message box asking to confirm the deleting

            }
        });



    }
}