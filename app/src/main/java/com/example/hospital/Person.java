package com.example.hospital;

import java.io.Serializable;
import java.time.Year;
import java.util.Calendar;
import java.util.Date;

public abstract class Person implements Serializable {
    private String ID;
    private String Name;
    private String Phone;
    private String Email;
    private String Gender;
    private String dateOfBirth;
    private int Age;
    private String Password;

    public Person(String name, String phone, String email, String gender, String dateOfBirth) {
        Name = name;
        Phone = phone;
        Email = email;
        Gender = gender;
        this.dateOfBirth = dateOfBirth;
        calculateAge(dateOfBirth);
    }

    public Person(String ID, String name, String phone, String email, String gender, String dateOfBirth) {
        this.ID = ID;
        Name = name;
        Phone = phone;
        Email = email;
        Gender = gender;
        this.dateOfBirth = dateOfBirth;
        calculateAge(dateOfBirth);
    }

    public Person(String ID, String name, String phone, String email, String gender, String dateOfBirth, String password) {
       this(ID,name,phone,email,gender,dateOfBirth);
        Password = password;
    }

    public void calculateAge(String date)
    {
        Date today = new Date();
        Calendar todayCalendar = Calendar.getInstance();
        todayCalendar.setTime(today);

        String[]dateBirth = date.split("-");
        Date birthDate = new Date(Integer.parseInt(dateBirth[0])-1900 , Integer.parseInt(dateBirth[1])-1 ,Integer.parseInt(dateBirth[2]));
        Calendar birthCalendar = Calendar.getInstance();
        birthCalendar.setTime(birthDate);

        this.Age  = todayCalendar.get(Calendar.YEAR) - birthCalendar.get(Calendar.YEAR);
    }

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getPhone() {
        return Phone;
    }

    public void setPhone(String phone) {
        Phone = phone;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public String getGender() {
        return Gender;
    }

    public int getAge() {
        return Age;
    }

    public void setGender(String gender) {
        Gender = gender;
    }

    public String getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(String dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String password) {
        Password = password;
    }
}
