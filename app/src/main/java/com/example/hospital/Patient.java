package com.example.hospital;

import android.content.Context;

import java.sql.ResultSet;
import java.sql.SQLException;

public class Patient extends Person {

    public Patient(String name, String phone, String email, String gender, String dateOfBirth) {
        super(name, phone, email, gender, dateOfBirth);
    }

    public Patient(String ID, String name, String phone, String email, String gender, String dateOfBirth) {
        super(ID, name, phone, email, gender, dateOfBirth);
    }


    public static Patient getPatient(String attribute ,String value, Context context)
    {
        ResultSet resultSet = DataBase.excutQuery("select * from Patient where "+attribute+" = '" + value +"'",context);
        try{
            resultSet.next();
            Patient patient = new Patient(resultSet.getString("id"),resultSet.getString("name"),resultSet.getString("phone"),
                    resultSet.getString("e_mail"),resultSet.getString("gender"),resultSet.getString("birth_date"));
            return patient;
        }catch (SQLException e)
        {
        }
        return null;
    }
}
