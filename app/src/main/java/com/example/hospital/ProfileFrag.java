package com.example.hospital;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ProfileFrag#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ProfileFrag extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private View view;

    public ProfileFrag() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ProfileFrag.
     */
    // TODO: Rename and change types and number of parameters
    public static ProfileFrag newInstance(String param1, String param2) {
        ProfileFrag fragment = new ProfileFrag();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }


    public ArrayList<Appointment> appointments = new ArrayList<>();
    public ListView patientAppointments;
    private AppointmentArrayAdapter adapter;
    public int selectedIndex;
    private Patient patient;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }
    @Override
    public View onCreateView(LayoutInflater inflater,  ViewGroup container,  Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_profile,container,false);
        patientAppointments = view.findViewById(R.id.patient_appointments_frag);
        patient = (Patient) getActivity().getIntent().getSerializableExtra("Patient");

        registerForContextMenu(patientAppointments);

        appointments = Appointment.getPatientAppointments("patient_id",patient.getID(), getActivity());
        adapter = new AppointmentArrayAdapter(getActivity(),R.layout.appointment_list,appointments);
        patientAppointments.setAdapter(adapter);
        return view;
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        getActivity().getMenuInflater().inflate(R.menu.delete_menu,menu);
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
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Delete Appointment");

        Doctor doctor = Doctor.getDoctor(appointment.getDoctorID(),"id",getActivity());
        builder.setMessage("\nDoctor: " + doctor.getName() +"\n\nSpecialty: " + doctor.getSpeciality()+
                "\n\nDate: " + appointment.getDate() + "\n\nYour number is: " + appointment.getNumberInQueue());

        builder.setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                DataBase.excutQuery("delete from Appiontment where id = '" +
                        appointments.get(selectedIndex).getID() +"'",getActivity());
                adapter.remove(appointments.get(selectedIndex));
                Toast.makeText(getActivity(),"Appointment deleted successfully",Toast.LENGTH_LONG).show();
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