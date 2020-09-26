package com.example.hospital;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Pair;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.riontech.calendar.dao.EventData;
import com.riontech.calendar.CustomCalendar;

import com.riontech.calendar.dao.dataAboutDate;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Arrays;
import java.util.Map;

public class Appointment_calen extends AppCompatActivity {
    private HashMap<String, ArrayList<Pair<String, Integer>>> arrofdates;

    private String nameofpatient;
    private String[] arrofnames;
    private String[] dates;
    private int ind = 0;
    private int idp;
    private CustomCalendar customCalendarr;
    public EventData eventdata;
    public Pair<String, Integer> tmp;
    public ArrayList<Pair<String, Integer>> enterdate;
    public ArrayList<EventData> arrayofevent;
    public ArrayList<dataAboutDate> arrayofdata;
    public dataAboutDate dataabout;
    private Button bbb;
    private Doctor doctor;

    private Connection connection = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_appointment_calen);

        bbb = findViewById(R.id.okid);
        doctor = (Doctor) getIntent().getSerializableExtra("Person");

        customCalendarr = (CustomCalendar) findViewById(R.id.customCalendar);
        arrofnames = new String[100];
        dates = new String[100];
        arrofdates = new HashMap<String, ArrayList<Pair<String, Integer>>>();
        getappointments();
    }

    public void getappointments() {
        ArrayList<Appointment> appointments = Appointment.getPatientAppointments("doctor_id", doctor.getID(), this);
        for (Appointment appointment : appointments) {
            //  int idd=resultSet.getInt("doctor_id");
            idp = Integer.parseInt(appointment.getPatientID());
            String num = String.valueOf(appointment.getNumberInQueue());
            String datee = appointment.getDate();


            tmp = new Pair<>(num, idp);
            if (arrofdates.containsKey(datee) == false) {
                enterdate = new ArrayList<Pair<String, Integer>>();
                enterdate.add(tmp);
                arrofdates.put(datee, enterdate);
                dates[ind] = datee;
                ind++;
            } else {
                arrofdates.get(datee).add(tmp);
            }
            //  System.out.println("idd " +idd);
            System.out.println("idp " + idp);
            System.out.println("num " + num);
            System.out.println(" dete" + datee);

        }

        Arrays.sort(dates, 0, ind);
        for (int i = 0; i < ind; ++i) {
            System.out.println(arrofdates.get(dates[i]));
            int eventCount = arrofdates.get(dates[i]).size();
            if (eventCount > 3)
                eventCount = 3;

            customCalendarr.addAnEvent(dates[i], eventCount, getEventDataList(dates[i]));
        }
    }


    private ArrayList<EventData> getEventDataList(String date) {
        arrayofevent = new ArrayList<EventData>();
        for (int i = 0; i < arrofdates.get(date).size(); ++i) {
            idp = arrofdates.get(date).get(i).second;

            Patient patient = Patient.getPatient("id", String.valueOf(idp), this);
            nameofpatient = patient.getName();
            System.out.println(" name" + nameofpatient);


            arrayofdata = new ArrayList<dataAboutDate>(5);
            dataabout = new dataAboutDate();
            eventdata = new EventData();


            eventdata.setSection(arrofdates.get(date).get(i).first);//num inque
            dataabout.setTitle("Name of patient:");//name of patient
            dataabout.setSubmissionDate("");

            dataabout.setSubject(nameofpatient);//name
            dataabout.setRemarks("His/Her turn : " + arrofdates.get(date).get(i).first);
            arrayofdata.add(dataabout);
            eventdata.setData(arrayofdata);
            arrayofevent.add(eventdata);
        }
        return arrayofevent;
    }

    public void back(View view) {
        finish();
    }
}