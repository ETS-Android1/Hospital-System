package com.example.hospital;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.Toast;

import javax.xml.datatype.Duration;

public class addTest extends AppCompatActivity {

    private static int NUM_ROWS = 1;
    private static final int NUM_COL = 3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_test);

        addText();




    }

    private void addText() {
        Button addBTN = (Button)findViewById(R.id.addBTN);
        final EditText testName = (EditText)findViewById(R.id.testName);
        final EditText testResult = (EditText)findViewById(R.id.testResult);
        final EditText testDate = (EditText)findViewById(R.id.testDate);

        addBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!(testName.getText().toString().equals(""))&&!(testResult.getText().toString().equals(""))&&!(testDate.getText().toString().equals("")))
                {
                    String newTest = testName.getText().toString();
                    newTest+= "\t";
                    newTest += testResult.getText().toString();
                    String newDate = testDate.getText().toString();
                    //addhom fe el database b2a
                    testName.getText().clear();
                    testResult.getText().clear();
                    testDate.getText().clear();
                    Toast.makeText(getApplicationContext(),"The new test added successfully",Toast.LENGTH_LONG).show();
                }
                else
                {
                    Toast.makeText(getApplicationContext(),"Please enter the values",Toast.LENGTH_LONG).show();
                }


            }
        });
    }


}