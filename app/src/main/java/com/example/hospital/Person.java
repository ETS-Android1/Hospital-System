package com.example.hospital;

import java.util.Date;

public abstract class Person {
    private String ID;
    private String Name;
    private String Phone;
    private String Email;
    private String Gender;
    private String dateOfBirth;
    private int Age;

    public Person(String ID, String name, String phone, String email, String gender, String dateOfBirth) {
        this.ID = ID;
        Name = name;
        Phone = phone;
        Email = email;
        Gender = gender;
        this.dateOfBirth = dateOfBirth;
        calculateAge(dateOfBirth);
    }

    public void calculateAge(String date)
    {
        Date today = new Date();
        String[]dateBirth = date.split("-");
        this.Age = today.getYear() - (Integer.parseInt(dateBirth[0])-1900);
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
}
