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

    public Date getDate() {
        return date;
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
}
