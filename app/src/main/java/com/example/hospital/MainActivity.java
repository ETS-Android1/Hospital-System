package com.example.hospital;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.content.Intent;
import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.StrictMode;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Intent intent=new Intent(this,login.class);
        startActivity(intent);

        ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.INTERNET}, PackageManager.PERMISSION_GRANTED);
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        DataBase dataBase = new DataBase(this);
/*
        Intent intent = new Intent(this,admin.class);
        startActivity(intent);

        Intent intent = new Intent(this,tests.class);


        Intent intent = new Intent(this,admin.class);

        Intent intent = new Intent(this,addTest.class);
        startActivity(intent);
        */

      /*
        Intent intent = new Intent(this,Home.class);
        startActivity(intent);*/

        /*
        Intent intent = new Intent(tests.this,addTest.class);
        startActivity(intent);
        startActivity(intent);

        */
    }
}