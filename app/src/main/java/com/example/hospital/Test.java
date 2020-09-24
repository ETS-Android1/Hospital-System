package com.example.hospital;

import android.content.Context;
import android.provider.ContactsContract;
import android.util.Pair;
import android.widget.ArrayAdapter;

import java.lang.reflect.Array;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;

public class Test {
    private String ID;
    private String patientID;
    private Date date;
    private String text;

    public Test(String ID, String patientID, Date date, String text) {
        this.ID = ID;
        this.patientID = patientID;
        this.date = date;
        this.text = text;
    }


    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public String getPatientID() {
        return patientID;
    }

    public void setPatientID(String patientID) {
        this.patientID = patientID;
    }

    public String getDate() {
        return ( (date.getYear() + 1900) + "-" + (date.getMonth() + 1) + "-" + date.getDay());
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public static ArrayList<Test> getTests(String patientID, Context context)
    {
        ResultSet resultSet = DataBase.excutQuery("select * from Test where patient_id = '" + patientID + "'",context);
        ArrayList<Test> tests = new ArrayList<>();
        try{
            while (resultSet.next())
            {
                Test test = new Test(resultSet.getString("id"),resultSet.getString("patient_id"),
                        Appointment.convertDate(resultSet.getString("date")),resultSet.getString("text"));
                tests.add(test);
            }
        }catch (SQLException e)
        {
        }
        return tests;
    }
}
