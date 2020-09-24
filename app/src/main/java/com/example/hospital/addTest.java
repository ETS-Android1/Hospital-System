package com.example.hospital;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListPopupWindow;
import android.widget.Spinner;
import android.widget.Toast;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Date;

import static com.example.hospital.AdminRemoveFrag.spinner;

public class addTest extends AppCompatActivity {

    private static int NUM_ROWS = 1;
    private static final int NUM_COL = 3;
    private Patient patient;
    private Spinner spinner_day;
    private Spinner spinner_month;
    private Spinner spinner_year;
    private EditText testName;
    private EditText testResult;
    private Button addBTN;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_test);

        patient = (Patient) getIntent().getSerializableExtra("Patient");

        addBTN = (Button) findViewById(R.id.addBTN);
        testName = (EditText) findViewById(R.id.testName);
        testResult = (EditText) findViewById(R.id.testResult);
        spinner_day = (Spinner) findViewById(R.id.spinner_day_test);
        spinner_month = (Spinner) findViewById(R.id.spinner_month_test);
        spinner_year = (Spinner) findViewById(R.id.spinner_year_test);
        initializeSpinners();
    }

    public void addText(View v) {
        String newTest = testName.getText().toString();
        if (!(newTest.equals("")) && !(testResult.getText().toString().equals(""))) {
            newTest += "\t";
            newTest += testResult.getText().toString();
            if (validateDate()) {
                String year = spinner_year.getSelectedItem().toString();
                String month = spinner_month.getSelectedItem().toString();
                String day = spinner_day.getSelectedItem().toString();
                addTest(patient.getID(), year + "-" + month + "-" + day, newTest, this);
                testName.getText().clear();
                testResult.getText().clear();
                spinner_day.setSelection(0);
                spinner_month.setSelection(0);
                spinner_year.setSelection(0);
                Toast.makeText(this, "The new test added successfully", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(this,NavigateActivity.class);
                intent.putExtra("Patient",patient);
                intent.putExtra("pageIndex",2);
                startActivity(intent);
            } else
                Toast.makeText(this, "Please select the date", Toast.LENGTH_SHORT).show();
        } else
            Toast.makeText(getApplicationContext(), "Please enter the values", Toast.LENGTH_SHORT).show();
    }

    public void addTest(String patientID, String date, String text, Context context) {
        String query = "INSERT INTO Test VALUES('" + patientID + "','" + date + "','" + text + "')";
        DataBase.excutQuery(query, context);
    }

    public void initializeSpinners() {
        ArrayList<String> arrayList = new ArrayList<>();
        for (int i = 1; i <= 31; i++)
            arrayList.add(String.valueOf(i));
        arrayList.add(0, "Day");
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, arrayList);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_day.setAdapter(adapter);

        arrayList = new ArrayList<>();
        for (int i = 1; i <= 12; i++)
            arrayList.add(String.valueOf(i));
        arrayList.add(0, "Month");
        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, arrayList);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_month.setAdapter(adapter);

        arrayList = new ArrayList<>();
        int year = new Date().getYear() + 1900;
        for (int i = year - 2; i <= year ; i++)
            arrayList.add(String.valueOf(i));
        arrayList.add(0, "Year");
        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, arrayList);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_year.setAdapter(adapter);

        try {
            Field popup = Spinner.class.getDeclaredField("mPopup");
            popup.setAccessible(true);
            // Get private mPopup member variable and try cast to ListPopupWindow
            android.widget.ListPopupWindow popupWindow = (android.widget.ListPopupWindow) popup.get(spinner_day);
            popupWindow.setHeight(5);

            // Get private mPopup member variable and try cast to ListPopupWindow
            popupWindow = (android.widget.ListPopupWindow) popup.get(spinner_month);
            popupWindow.setHeight(5);

            // Get private mPopup member variable and try cast to ListPopupWindow
            popupWindow = (android.widget.ListPopupWindow) popup.get(spinner_year);
            popupWindow.setHeight(5);
        }
        catch (Exception e){
            System.out.println("Erorrrrrrrrrrrrrrrrrrrrr");
        }
    }

    public boolean validateDate() {
        if (spinner_year.getSelectedItemPosition() == 0 || spinner_month.getSelectedItemPosition() == 0 ||
                spinner_day.getSelectedItemPosition() == 0) {
            Toast.makeText(this, "Please select your birth date", Toast.LENGTH_LONG).show();
            return false;
        }
        return true;
    }

}