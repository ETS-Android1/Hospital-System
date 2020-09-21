package com.example.hospital;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Pair;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.Toast;

import org.xml.sax.Parser;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import javax.xml.datatype.Duration;

public class addTest extends AppCompatActivity {

    private static int NUM_ROWS = 1;
    private static final int NUM_COL = 3;
    private Patient patient;
    final String DATE_FORMAT = "yyyy-mm-dd";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_test);
    }

    public void addText(View  v) {
        Button addBTN = (Button)findViewById(R.id.addBTN);


        final EditText testName = (EditText)findViewById(R.id.testName);
        final EditText testResult = (EditText)findViewById(R.id.testResult);
        final EditText testDate = (EditText)findViewById(R.id.testDate);

                    String newTest = testName.getText().toString();
                    String newDate = testDate.getText().toString();
                if (!(newTest.equals(""))&&!(testResult.getText().toString().equals(""))&&!(newDate.equals("")))
                {
                    newTest+= "\t";
                    newTest += testResult.getText().toString();
                    if (validationDate(newDate))
                    {
                        addTest(Integer.parseInt(patient.getID()),newDate,newTest,this);
                        testName.getText().clear();
                        testResult.getText().clear();
                        testDate.getText().clear();
                        Toast.makeText(getApplicationContext(),"The new test added successfully",Toast.LENGTH_SHORT).show();
                    }
                }
                else
                {
                    Toast.makeText(getApplicationContext(),"Please enter the values",Toast.LENGTH_SHORT).show();
                }



    }

    public void addTest (int patientID,String date,String text, Context context)
    {
        String query = "INSERT INTO Tests(patientId,date,text)"+"VALUES('"+patientID+"','"+date+"','"+text+"')";
        DataBase.excutQuery(query,context);
    }

    private boolean validationDate(String date) {
        boolean b = true;
        if (date.length()!=10)
            b= false;
        for (int i = 0; i < date.length()&&b;i++)
        {
            if (i==4||i==7)
            {
                if(date.charAt(i)!='-')
                    b=false;
            }
            else if (!isInt(date.charAt(i)))
            {
                b=false;
            }
        }
        if (!b)
        {
            Toast.makeText(getApplicationContext(),"please enter the date in yyyy-mm-dd",Toast.LENGTH_SHORT).show();
            return false;
        }
        int year = Integer.parseInt(date.substring(0,3));
        int month = Integer.parseInt(date.substring(5,6));
        int day = Integer.parseInt(date.substring(8,9));
        if (year>1900&&year<2020&&month<13&&month>0&&day>0&&day<32)
            return true;
        else
        {
            Toast.makeText(getApplicationContext(),"please enter your test date",Toast.LENGTH_SHORT).show();
            return false;
        }

    }

    private boolean isInt(char index) {
        if (index >'0'&& index<='9')
        {
            return true;
        }
        else
        {
            return false;
        }

    }


}