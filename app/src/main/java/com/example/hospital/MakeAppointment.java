package com.example.hospital;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.util.Calendar;
import java.util.Date;

public class MakeAppointment extends AppCompatActivity {
    public TextInputEditText dateText;
    public TextInputLayout textInputLayout;
    public DatePickerDialog pickerDialog;
    public Doctor doctor;
    public String queryDate;
    public TextView appDetail;
    public TextView timeLabel;
    public TextView numberLabel;
    public TextView time;
    public TextView number;
    public ImageButton confirmButton;
    public String numberInQueue;
    public Date date;

    public String patientID;
    public EditText pID;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_make_appointment);

         doctor = (Doctor) getIntent().getSerializableExtra("Doctor");

        dateText = (TextInputEditText) findViewById(R.id.date);
        dateText.setInputType(InputType.TYPE_NULL);
        textInputLayout = (TextInputLayout) findViewById(R.id.textInputLayout);
        appDetail = (TextView) findViewById(R.id.appDetail);
        numberLabel = (TextView) findViewById(R.id.numberLabel);
        timeLabel = (TextView) findViewById(R.id.timeLabel);
        time = (TextView) findViewById(R.id.time);
        number = (TextView) findViewById(R.id.number);
        confirmButton = (ImageButton) findViewById(R.id.confirmButton);
        hideDetails();

        // temp
        pID = (EditText) findViewById(R.id.id_p);

        dateText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showCalendar();
            }
        });

        confirmButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                confirmAppointment(date);
            }
        });
    }

    public void showCalendar() {
        dateText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar calendar = Calendar.getInstance();
                final int day = calendar.get(Calendar.DAY_OF_MONTH);
                int month = calendar.get(Calendar.MONTH);
                int year = calendar.get(Calendar.YEAR);

                pickerDialog = new DatePickerDialog(MakeAppointment.this,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                                dateText.setText(dayOfMonth + " - " + (monthOfYear + 1) + " - " + year);
                                queryDate = year + "-" + (monthOfYear + 1) + "-" + dayOfMonth;
                                date = new Date(year - 1900, monthOfYear, dayOfMonth);
                                selectDate(date);
                            }
                        }, year, month, day);
                pickerDialog.show();
            }
        });
    }

    public void selectDate(Date date) {
        Date today = new Date();
        if (today.after(date) && !(today.getDate() == date.getDate())) {
            textInputLayout.setErrorEnabled(true);
            dateText.setError("Invalid Date");
            hideDetails();
            return;
        }

        if (!doctor.isAvailable(date, this)) {
            textInputLayout.setErrorEnabled(true);
            dateText.setError("No available appointments for D." + doctor.getName() + " on this date");
            hideDetails();
            return;
        }

        dateText.setError(null);
        textInputLayout.setErrorEnabled(false);
        showDetails(date);
    }

    public void showDetails(Date date) {
        appDetail.setVisibility(View.VISIBLE);
        numberLabel.setVisibility(View.VISIBLE);
        number.setVisibility(View.VISIBLE);
        time.setVisibility(View.VISIBLE);
        timeLabel.setVisibility(View.VISIBLE);
        confirmButton.setVisibility(View.VISIBLE);

        String doctorTime = String.valueOf(doctor.getTime(date.getDay()).getHours() % 12);
        if (doctor.getTime(date.getDay()).getHours() == 0)
            doctorTime = "12 AM";
        else if (doctor.getTime(date.getDay()).getHours() == 12)
            doctorTime = "12 PM";
        else if (doctor.getTime(date.getDay()).getHours() / 12 == 1)
            doctorTime += " PM";
        else
            doctorTime += " AM";

        time.setText(doctorTime);
        numberInQueue = String.valueOf(Appointment.numberInQueue(date, doctor, this) + 1);
        number.setText(numberInQueue);
    }

    public void hideDetails() {
        appDetail.setVisibility(View.INVISIBLE);
        numberLabel.setVisibility(View.INVISIBLE);
        number.setVisibility(View.INVISIBLE);
        time.setVisibility(View.INVISIBLE);
        timeLabel.setVisibility(View.INVISIBLE);
        confirmButton.setVisibility(View.INVISIBLE);
    }

    public void confirmAppointment(Date date) {
        //temp
        try {
            patientID = pID.getText().toString();
        } catch (Exception e) {
        }

        int prevApps = DataBase.resultSize("select * from Appiontment where patient_id = '" + patientID + "'" +
                "and date = '" + queryDate + "'", this);

        if (prevApps == 1) {
            Toast.makeText(this, "You have already booked an appointment in this Date", Toast.LENGTH_LONG).show();
            return;
        }


        Appointment appointment = new Appointment(doctor.getID(), patientID, date, Integer.parseInt(numberInQueue));
        System.out.println("insert into Appiontment values ( '" + appointment.getDoctorID() + "'" + " , '" + appointment.getPatientID()
                + "' , '" + appointment.getDate() + "' )");
        DataBase.excutQuery("insert into Appiontment values ('" + appointment.getDoctorID() + "','" + appointment.getPatientID()
                + "','" + appointment.getDate() + "','" + appointment.getNumberInQueue() + "')", this);
        Toast.makeText(this, "Booked Successfully", Toast.LENGTH_LONG).show();

        Intent intent = new Intent(this, Home.class);
        // get ID when user login
        //intent.putExtra("ID",patientID);
        startActivity(intent);
    }
}