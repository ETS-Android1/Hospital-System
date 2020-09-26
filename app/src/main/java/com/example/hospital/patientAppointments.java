package com.example.hospital;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class patientAppointments extends AppCompatActivity {

    public ArrayList<Appointment> appointments = new ArrayList<>();
    public ListView patientAppointments;
    private AppointmentArrayAdapter adapter;
    public int selectedIndex;
    private Patient patient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient_appointments);

        patientAppointments = (ListView)findViewById(R.id.patient_appointments);
        patient = (Patient) getIntent().getSerializableExtra("Person");


        registerForContextMenu(patientAppointments);

        appointments = Appointment.getPatientAppointments("patient_id",patient.getID(), patientAppointments.this);
        adapter = new AppointmentArrayAdapter(patientAppointments.this,R.layout.appointment_list,appointments);
        patientAppointments.setAdapter(adapter);

    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        getMenuInflater().inflate(R.menu.delete_menu,menu);

    }

    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo)item.getMenuInfo();
        selectedIndex = info.position;

        deleteAppointment();

        return super.onContextItemSelected(item);
    }

    public void deleteAppointment()
    {
        Appointment appointment = appointments.get(selectedIndex);
        AlertDialog dialog;
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Delete Appointment");

        Doctor doctor = Doctor.getDoctor(appointment.getDoctorID(),"id",this);
        builder.setMessage("\nDoctor: " + doctor.getName() +"\n\nSpecialty: " + doctor.getSpeciality()+
                "\n\nDate: " + appointment.getDate() + "\n\nYour number is: " + appointment.getNumberInQueue());

        builder.setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                DataBase.excutQuery("delete from Appiontment where id = '" +
                        appointments.get(selectedIndex).getID() +"'",patientAppointments.this);
                adapter.remove(appointments.get(selectedIndex));
                Toast.makeText(patientAppointments.this,"Appointment deleted successfully",Toast.LENGTH_LONG).show();
             }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
            }
        });
        dialog = builder.create();
        dialog.show();
    }
}