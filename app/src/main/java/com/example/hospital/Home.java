package com.example.hospital;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class Home extends AppCompatActivity {
    public Set<String> speciality = new HashSet<>();
    public final int COL = 2;
    public int width = 150,height = 150;
    public ArrayList<ImageButton> buttonArrayList = new ArrayList<>();

    private TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        textView = (TextView) findViewById(R.id.connectView);

        ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.INTERNET}, PackageManager.PERMISSION_GRANTED);
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        testDynamic();
    }

    public void testDynamic()
    {
        if(DataBase.connection != null)
        {
            try {
                int col = -1;
                ResultSet resultSet = DataBase.excutQuery("select specialty from Doctor",this);
                TableLayout tableLayout = (TableLayout)findViewById(R.id.buttons);
                TableRow tableRow = new TableRow(this);

                while (resultSet.next()){
                    col =  ++col % COL;
                    if(col == 0)
                    {
                        tableRow = new TableRow(this);
                        tableRow.setLayoutParams(new TableLayout.LayoutParams(
                                TableLayout.LayoutParams.WRAP_CONTENT,
                                TableLayout.LayoutParams.WRAP_CONTENT,
                                1.0f));
                        tableLayout.addView(tableRow);
                    }

                    final String specialities = resultSet.getString("specialty");
                    if(speciality.contains(specialities))
                    {
                        col--;
                        continue;
                    }
                    speciality.add(specialities);

                    ImageButton imageButton = new ImageButton(this);

                    if(specialities.equals("DENTIST"))
                        imageButton.setImageResource(R.drawable.dentist);
                    else if(specialities.equals("UROLOGY"))
                        imageButton.setImageResource(R.drawable.urology);
                    else if(specialities.equals("INTERNAL"))
                        imageButton.setImageResource(R.drawable.internal);
                    else if(specialities.equals("FAMILY"))
                        imageButton.setImageResource(R.drawable.family);
                    else if(specialities.equals("OPHTHALMOLOGY"))
                        imageButton.setImageResource(R.drawable.ophthalmology);
                    else if(specialities.equals("NEUROLOGY"))
                        imageButton.setImageResource(R.drawable.neurlogy);
                    else if(specialities.equals("SURGERY"))
                        imageButton.setImageResource(R.drawable.surgery);
                    else if(specialities.equals("PEDIATRICS"))
                        imageButton.setImageResource(R.drawable.pediatrics);

                    imageButton.setLayoutParams(new TableRow.LayoutParams(
                            TableRow.LayoutParams.WRAP_CONTENT,
                            TableRow.LayoutParams.MATCH_PARENT,
                            1.0f));
                    imageButton.setBackgroundColor(getResources().getColor(R.color.White));
                    tableRow.addView(imageButton);


                    imageButton.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            clickButton(specialities);
                        }
                    });
                }

                while(col < COL)
                {
                    ImageButton imageButton = new ImageButton(this);
                    imageButton.setLayoutParams(new TableRow.LayoutParams(
                            TableRow.LayoutParams.WRAP_CONTENT,
                            TableRow.LayoutParams.MATCH_PARENT,
                            1.0f));
                    imageButton.setPadding(0,0,100,0);
                    imageButton.setBackgroundColor(getResources().getColor(R.color.White));
                    tableRow.addView(imageButton);
                    col++;
                }
            } catch (SQLException e) {
                e.printStackTrace();
                textView.setText(e.getMessage());
            }

        }
        else
        {
            textView.setText("No Connection");
        }
    }

    public void clickButton(String speciality)
    {
        Intent intent = new Intent(this,DoctorsActivity.class);
        intent.putExtra("speciality",speciality);
        startActivity(intent);
    }
}