package com.example.hospital;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.DatePicker;
import android.widget.ImageButton;
import android.widget.TextView;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.util.Calendar;
import java.util.Date;

public class MakeAppointment extends AppCompatActivity {
    public TextInputEditText editText;
    public TextInputLayout textInputLayout;
    public DatePickerDialog pickerDialog;
    public Doctor doctor;
    public String ID;
    public TextView appDetail;
    public TextView timeLabel;
    public TextView numberLabel;
    public TextView time;
    public TextView number;
    public ImageButton confirmButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_make_appointment);

        ID = getIntent().getExtras().get("id").toString();
        doctor = Doctor.getDoctor(ID, "id", this);

        editText = (TextInputEditText) findViewById(R.id.date);
        editText.setInputType(InputType.TYPE_NULL);
        textInputLayout = (TextInputLayout) findViewById(R.id.textInputLayout);
        appDetail = (TextView) findViewById(R.id.appDetail);
        numberLabel = (TextView) findViewById(R.id.numberLabel);
        timeLabel = (TextView) findViewById(R.id.timeLabel);
        time = (TextView) findViewById(R.id.time);
        number = (TextView) findViewById(R.id.number);
        confirmButton = (ImageButton) findViewById(R.id.confirmButton);
        hideDetails();


        editText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Calendar calendar = Calendar.getInstance();
                int day = calendar.get(Calendar.DAY_OF_MONTH);
                int month = calendar.get(Calendar.MONTH);
                int year = calendar.get(Calendar.YEAR);

                editText.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        final Calendar cldr = Calendar.getInstance();
                        int day = cldr.get(Calendar.DAY_OF_MONTH);
                        int month = cldr.get(Calendar.MONTH);
                        int year = cldr.get(Calendar.YEAR);

                        pickerDialog = new DatePickerDialog(MakeAppointment.this,
                                new DatePickerDialog.OnDateSetListener() {
                                    @Override
                                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                                        editText.setText(dayOfMonth + "/" + (monthOfYear + 1) + "/" + year);
                                        selectDate(new Date(year - 1900, monthOfYear, dayOfMonth));
                                    }
                                }, year, month, day);
                        pickerDialog.show();
                    }
                });
            }
        });
    }

    public void selectDate(Date date) {
        Date today = new Date();
        if (today.after(date) && !(today.getDate() == date.getDate())) {
            textInputLayout.setErrorEnabled(true);
            editText.setError("Invalid Date");
            hideDetails();
            return;
        }

        if (!doctor.isAvailable(date, this)) {
            textInputLayout.setErrorEnabled(true);
            editText.setError("No available appointments for D." + doctor.getName() + " on this date");
            hideDetails();
            return;
        }

        editText.setError(null);
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
        number.setText(String.valueOf(Appointment.numberInQueue(date, doctor, this) + 1));
    }

    public void hideDetails() {
        appDetail.setVisibility(View.INVISIBLE);
        numberLabel.setVisibility(View.INVISIBLE);
        number.setVisibility(View.INVISIBLE);
        time.setVisibility(View.INVISIBLE);
        timeLabel.setVisibility(View.INVISIBLE);
        confirmButton.setVisibility(View.INVISIBLE);
    }
}