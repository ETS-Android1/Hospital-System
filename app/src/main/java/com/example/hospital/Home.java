package com.example.hospital;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ZoomButtonsController;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class Home extends AppCompatActivity {
    public static String ip = "192.168.43.50";
    public  static String port = "1433";
    public static String Classes = "net.sourceforge.jtds.jdbc.Driver";
    public static String dataBase = "TheHospital";
    public static String userName = "mustafa";
    public static String passWord = "123456";
    public static String url = "jdbc:jtds:sqlserver://" + ip + ":" + port + "/" + dataBase;
    public Set<String> speciality = new HashSet<>();
    public final int COL = 2;
    public int width = 150,height = 150;
    public ArrayList<ImageButton> buttonArrayList = new ArrayList<>();

    private Connection connection = null;

    private TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        textView = (TextView) findViewById(R.id.connectView);

        ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.INTERNET}, PackageManager.PERMISSION_GRANTED);
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        setConnection();
        testDynamic();
    }
    public void testDynamic()
    {
        if(connection != null)
        {
            try {
                int col = -1;
                Statement statement = connection.createStatement();
                ResultSet resultSet = statement.executeQuery("select specialty from Doctor");
                TableLayout tableLayout = (TableLayout)findViewById(R.id.buttons);
                TableRow tableRow = new TableRow(this);

                while (resultSet.next())
                {
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

    public void setConnection()
    {
        try {
            Class.forName(Classes);
            connection = DriverManager.getConnection(url,userName,passWord);
            textView.setText("Connected Successfully");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            textView.setText("Error");
        } catch (SQLException e) {
            e.printStackTrace();
            textView.setText(e.getMessage());
        }
    }

}