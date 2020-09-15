package com.example.hospital;

import android.content.Context;

import java.util.Calendar;
import java.util.Date;

public class Appointment {
    private String ID;
    private String doctorID;
    private String patientID;
    private Date date;
    private int numberInQueue;

    public Appointment(String ID, String doctorID, String patientID, Date date) {
        this.ID = ID;
        this.doctorID = doctorID;
        this.patientID = patientID;
        this.date = date;
    }

    public Appointment(String ID, String doctorID, String patientID, Date date, int numberInQueue) {
        this(ID,doctorID,patientID,date);
        this.numberInQueue = numberInQueue;
    }

    public static int numberInQueue(Date date , Doctor doctor , Context context)
    {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);

        int size =  0;
        size = DataBase.resultSize("select * from Appiontment where doctor_id = '" + doctor.getID() + "'"
                + " and date = '" + calendar.get(Calendar.YEAR) + "-" + (calendar.get(Calendar.MONTH) + 1) + "-" +calendar.get(Calendar.DAY_OF_MONTH) +"'", context);
        return size;
    }

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public String getDoctorID() {
        return doctorID;
    }

    public void setDoctorID(String doctorID) {
        this.doctorID = doctorID;
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

    public int getNumberInQueue() {
        return numberInQueue;
    }

    public void setNumberInQueue(int numberInQueue) {
        this.numberInQueue = numberInQueue;
    }
}
