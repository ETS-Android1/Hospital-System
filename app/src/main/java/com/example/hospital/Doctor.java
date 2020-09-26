package com.example.hospital;

import android.content.Context;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Time;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class Doctor extends Person {
    private String Speciality;
    private String Description;
    private int maxAppointmentsPerDay;
    private Time[] availableDays = new Time[7];

    public Doctor(String ID, String name, String phone, String email, String gender, String dateOfBirth) {
        super(ID, name, phone, email, gender, dateOfBirth);
    }

    public Doctor(String ID, String name, String phone, String email, String gender, String dateOfBirth, String speciality, String description) {
        super(ID, name, phone, email, gender, dateOfBirth);
        Speciality = speciality;
        Description = description;
    }

    public Doctor(String ID, String name, String phone, String email, String gender, String dateOfBirth, String speciality, String description, int maxAppointmentsPerDay,String password) {
        super(ID, name, phone, email, gender, dateOfBirth,password);
        Speciality = speciality;
        Description = description;
        this.maxAppointmentsPerDay = maxAppointmentsPerDay;
    }

    public String getSpeciality() {
        return Speciality;
    }

    public String getDescription() {
        return Description;
    }

    public boolean isAvailable(Date date, Context context) {
        if (availableDays[date.getDay()] == null)
            return false;

        int prevAppointments = Appointment.numberInQueue(date, this, context);

        if (prevAppointments == maxAppointmentsPerDay) {
            return false;
        }
        return true;
    }

    public void setAvailableDays(ResultSet availableDays) {
        try {
            this.availableDays[0] = availableDays.getTime("sunday");
            this.availableDays[1] = availableDays.getTime("monday");
            this.availableDays[2] = availableDays.getTime("tuesday");
            this.availableDays[3] = availableDays.getTime("wednesday");
            this.availableDays[4] = availableDays.getTime("thursday");
            this.availableDays[5] = availableDays.getTime("friday");
            this.availableDays[6] = availableDays.getTime("saturday");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static ArrayList<Doctor> getDoctors(String value, String attribute, Context context) {
        ResultSet resultSet = null;
        ArrayList<Doctor> doctors = new ArrayList<>();
        resultSet = DataBase.excutQuery("select * from Doctor where " + attribute + " = '" + value + "'", context);
        try {
            while (resultSet.next()) {
                Doctor doctor = new Doctor(resultSet.getString("id"), resultSet.getString("name"), resultSet.getString("phone"),
                        resultSet.getString("e_mail"), resultSet.getString("gender"), resultSet.getString("birth_date")
                        , resultSet.getString("specialty"), resultSet.getString("description"),
                        Integer.parseInt(resultSet.getString("max_app_per_day")),resultSet.getString("password"));
                doctor.setAvailableDays(resultSet);
                doctors.add(doctor);
            }
        } catch (SQLException e) {
        }
        return doctors;
    }

    public static Doctor getDoctor(String value, String attribute, Context context) {
        ResultSet resultSet = null;
        resultSet = DataBase.excutQuery("select * from Doctor where " + attribute + " = '" + value + "'", context);
        try {
            resultSet.next();
            Doctor doctor = new Doctor(resultSet.getString("id"), resultSet.getString("name"), resultSet.getString("phone"),
                    resultSet.getString("e_mail"), resultSet.getString("gender"), resultSet.getString("birth_date")
                    , resultSet.getString("specialty"), resultSet.getString("description"),
                    Integer.parseInt(resultSet.getString("max_app_per_day")),resultSet.getString("password"));
            doctor.setAvailableDays(resultSet);
            return doctor;
        } catch (SQLException e) {
        }
        return null;
    }

    public int getMaxAppointmentsPerDay() {
        return maxAppointmentsPerDay;
    }

    public void setMaxAppointmentsPerDay(int maxAppointmentsPerDay) {
        this.maxAppointmentsPerDay = maxAppointmentsPerDay;
    }

    public Time getTime (int day)
    {
        return availableDays[day];
    }

    public Time[] getAvailableDays() {
        return availableDays;
    }
}
