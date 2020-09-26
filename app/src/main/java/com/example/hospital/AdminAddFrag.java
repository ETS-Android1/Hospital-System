package com.example.hospital;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.util.Pair;
import android.util.Patterns;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputLayout;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Time;
import java.util.ArrayList;
import java.util.regex.Pattern;

public class AdminAddFrag extends Fragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";


    private String mParam1;
    private String mParam2;
    private TextInputLayout text_username;
    private TextInputLayout text_email;
    private TextInputLayout text_password;
    private TextInputLayout text_phone;
    private TextInputLayout text_description;
    private TextInputLayout text_maxApp;
    private EditText[] daystext;
    private CheckBox[] daysCheck;

    private Spinner spinner_day;
    private Spinner spinner_month;
    private Spinner spinner_year;
    private Spinner spinner_spical;
    private RadioGroup rdGroup;
    private RadioButton maleButton;
    private RadioButton femaleButton;

    private int curID = -1;

    private static final Pattern PASSWORD_PATTERN =
            Pattern.compile("^" +
                    //"(?=.*[0-9])" +         //at least 1 digit
                    //"(?=.*[a-z])" +         //at least 1 lower case letter
                    //"(?=.*[A-Z])" +         //at least 1 upper case letter
                    "(?=.*[a-zA-Z])" +      //any letter
                    "(?=.*[@#$%^_&+=])" +    //at least 1 special character
                    "(?=\\S+$)" +           //no white spaces
                    ".{4,}" +               //at least 4 characters
                    "$");


    public AdminAddFrag() {
        // Required empty public constructor
    }

    public static AdminAddFrag newInstance(String param1, String param2) {
        AdminAddFrag fragment = new AdminAddFrag();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_admin_add, container, false);



        text_username = rootView.findViewById(R.id.text_user_name);
        text_email = rootView.findViewById(R.id.text_email);
        text_password = rootView.findViewById(R.id.text_pass);
        text_phone = rootView.findViewById(R.id.text_phone);
        text_description = rootView.findViewById(R.id.text_description);
        text_maxApp = rootView.findViewById(R.id.maxApp);
        spinner_spical = rootView.findViewById(R.id.spin_specialty);
        spinner_day = rootView.findViewById(R.id.spinner_day);
        spinner_month = rootView.findViewById(R.id.spinner_month);
        spinner_year = rootView.findViewById(R.id.spinner_year);
        /////////////////////////////////
        //load days
        daysCheck=new CheckBox[7];
        daysCheck[0]=rootView.findViewById(R.id.check_sunday);
        daysCheck[1]=rootView.findViewById(R.id.check_monday);
        daysCheck[2]=rootView.findViewById(R.id.check_tuesday);
        daysCheck[3]=rootView.findViewById(R.id.check_wednesday);
        daysCheck[4]=rootView.findViewById(R.id.check_thursday);
        daysCheck[5]=rootView.findViewById(R.id.check_friday);
        daysCheck[6]=rootView.findViewById(R.id.check_saturday);

        daystext=new EditText[7];
        daystext[0]=rootView.findViewById(R.id.text_hour_sunday);
        daystext[1]=rootView.findViewById(R.id.text_hour_monday);
        daystext[2]=rootView.findViewById(R.id.text_hour_tuesday);
        daystext[3]=rootView.findViewById(R.id.text_hour_wednesday);
        daystext[4]=rootView.findViewById(R.id.text_hour_thursday);
        daystext[5]=rootView.findViewById(R.id.text_hour_friday);
        daystext[6]=rootView.findViewById(R.id.text_hour_saturday);
        ////////////////////////////////
        rdGroup=rootView.findViewById(R.id.rdgroup);
        maleButton=rootView.findViewById(R.id.rdmale);
        femaleButton=rootView.findViewById(R.id.rdfemale);



        initializeSpinners();
        Button ADD = rootView.findViewById(R.id.button_add);
        ADD.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                signUp(view);
            }
        });
        Button Edit = rootView.findViewById(R.id.button_edit);


       /// getActivity().setContentView(R.layout.fragment_admin_add);
        registerForContextMenu(Edit);
        return rootView;
    }


    private boolean validateEmail() {
        String emailInput = text_email.getEditText().getText().toString().trim();
        int prevDoctor = DataBase.resultSize("select * from Doctor where e_mail = '" + emailInput + "'",getActivity()),
                prevPatient = DataBase.resultSize("select * from Patient where e_mail = '" + emailInput + "'",getActivity());
        if (emailInput.isEmpty()) {
            text_email.setError("Field can't be empty");
            return false;
        } else if (!Patterns.EMAIL_ADDRESS.matcher(emailInput).matches()) {
            text_email.setError("Please enter a valid email address");
            return false;
        }else if(prevDoctor == 1 || prevPatient == 1){
            text_email.setError("This email is used before");
            return false;
        } else {
            text_email.setError(null);
            return true;
        }
    }

    private boolean validateUsername() {
        String userInput = text_username.getEditText().getText().toString().trim();
        if (userInput.isEmpty()) {
            text_username.setError("Field can't be empty");
            return false;
        } else if (userInput.length() > 15) {
            text_username.setError("Username too long");
            return false;
        } else {
            text_username.setError(null);
            return true;
        }
    }


    private boolean validateDescription() {
        String userInput = text_description.getEditText().getText().toString().trim();
        if (userInput.isEmpty()) {
            text_description.setError("Field can't be empty");
            return false;

        } else {
            text_description.setError(null);
            return true;
        }
    }

    private boolean validateBirthDate() {
     if(spinner_year.getSelectedItemPosition()==0 || spinner_day.getSelectedItemPosition()==0
             ||spinner_month.getSelectedItemPosition()==0)
     return false;

     return true;

    }

    private boolean validatePassword() {
        String passwordInput = text_password.getEditText().getText().toString().trim();
        if (passwordInput.isEmpty()) {
            text_password.setError("Field can't be empty");
            return false;
        } else if (!PASSWORD_PATTERN.matcher(passwordInput).matches()) {
            text_password.setError("Password too weak");
            return false;
        } else {
            text_password.setError(null);
            return true;
        }
    }

    private boolean validatePhoneNumber() {
        String phoneInput = text_phone.getEditText().getText().toString().trim();
        if (phoneInput.isEmpty()) {
            text_phone.setError("Field can't be empty");
            return false;
        } else if (phoneInput.length() > 11) {
            text_phone.setError("not valid number");
            return false;
        } else {
            text_phone.setError(null);
            return true;
        }
    }
    private boolean validateDays() {

        boolean good=true;
        for(int i=0;i<7;i++)
        {
            if(daysCheck[i].isChecked()&&!daystext[i].getText().toString().isEmpty())
            {
                Toast.makeText(getContext(),daystext[i].getText().toString(),Toast.LENGTH_SHORT);
                int hour=Integer.parseInt(daystext[i].getText().toString());
                if(hour<1||hour>24)
                {
                    daystext[i].setError("not valid");
                    good=false;
                }
                else
                    daystext[i].setError(null);
            }
        }
        return good;
    }




    public void signUp(View v) {

        if (!validateEmail() | !validateUsername() | !validatePassword() | !validatePhoneNumber()
                | !validateBirthDate() | !validateDescription()| !validateDays()) {
            return;
        }
        if(!maleButton.isChecked()&&!femaleButton.isChecked())
        {
            Toast.makeText(getContext(),"Invalid Gender",Toast.LENGTH_SHORT).show();
            return;
        }

        String name = text_username.getEditText().getText().toString();
        String email = text_email.getEditText().getText().toString();
        String pass = text_password.getEditText().getText().toString();
        String phone = text_phone.getEditText().getText().toString();
        String specialty = spinner_spical.getSelectedItem().toString();
        String description = text_description.getEditText().getText().toString();
        String birthDate = spinner_year.getSelectedItem().toString()+"-"+spinner_month.getSelectedItem().toString()+"-"+spinner_day.getSelectedItem().toString();
        String gender = rdGroup.getCheckedRadioButtonId() == R.id.rdmale ? "Male" : "Female";
        int mxApp= Integer.parseInt(text_maxApp.getEditText().getText().toString());

        Doctor doctor = new Doctor("-1", name, phone, email, gender, birthDate, specialty, description,mxApp,pass);
        for(int i=0;i<7;i++)
        {
            if(daysCheck[i].isChecked())
            doctor.getAvailableDays()[i]=new Time(Integer.parseInt(daystext[i].getText().toString()),0,0);
            else
                doctor.getAvailableDays()[i]=null;
        }

        if (curID == -1)
            addDoctor(doctor, pass, getContext());
        else
            EditDoctor(doctor, curID, pass, getContext());
    }

    private void EditDoctor(Doctor doctor, int ID, String password, Context context) {
        String query = "UPDATE Doctor SET name='" + doctor.getName() + "',e_mail='" + doctor.getEmail() + "',phone='" + doctor.getPhone() + "',password='" + password + "',birth_date='" + doctor.getDateOfBirth() + "',gender='" + doctor.getGender() + "',specialty='" + doctor.getSpeciality() + "',description='" + doctor.getDescription() + "',max_app_per_day='" + doctor.getMaxAppointmentsPerDay() + "' ";
        query+=" ,sunday="+((doctor.getTime(0)==null)?"NULL":"'"+doctor.getAvailableDays()[0].getHours()+":0:0'");
        query+=" ,monday="+((doctor.getTime(1)==null)?"NULL":"'"+doctor.getAvailableDays()[1].getHours()+":0:0'");
        query+=" ,tuesday="+((doctor.getTime(2)==null)?"NULL":"'"+doctor.getAvailableDays()[2].getHours()+":0:0'");
        query+=" ,wednesday="+((doctor.getTime(3)==null)?"NULL":"'"+doctor.getAvailableDays()[3].getHours()+":0:0'");
        query+=" ,thursday="+((doctor.getTime(4)==null)?"NULL":"'"+doctor.getAvailableDays()[4].getHours()+":0:0'");
        query+=" ,friday="+((doctor.getTime(5)==null)?"NULL":"'"+doctor.getAvailableDays()[5].getHours()+":0:0'");
        query+=" ,saturday="+((doctor.getTime(6)==null)?"NULL":"'"+doctor.getAvailableDays()[6].getHours()+":0:0'");
        query+=" Where id ='" + ID + "'";
        DataBase.excutQuery(query, context);

        Toast.makeText(getContext(),"Edit",Toast.LENGTH_SHORT).show();
    }

    private void addDoctor(Doctor doctor, String password, Context context) {
        String query = "INSERT INTO Doctor(name,e_mail,phone,password,birth_date,gender,specialty,description,max_app_per_day ,sunday,monday,tuesday,wednesday,thursday,friday,saturday)"
                + "VALUES ('" + doctor.getName() + "','" + doctor.getEmail() + "','" + doctor.getPhone() + "','" + password + "','" + doctor.getDateOfBirth() + "','" + doctor.getGender() + "','" + doctor.getSpeciality() + "','" + doctor.getDescription() + "','" + doctor.getMaxAppointmentsPerDay() ;
        for(int i=0;i<7;i++)
            query+="','" +((doctor.getTime(i)==null)?"NULL":doctor.getAvailableDays()[i].getHours()+":0:0");
        query+=  "');";
        DataBase.excutQuery(query, context);
        Toast.makeText(getContext(),"ADDED",Toast.LENGTH_SHORT).show();
    }

    public void initializeSpinners() {
        ArrayList<String> arrayList = new ArrayList<>();
        for (int i = 1; i <= 31; i++)
            arrayList.add(String.valueOf(i));
        arrayList.add(0, "Day");
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this.getContext(), android.R.layout.simple_spinner_dropdown_item, arrayList);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_day.setAdapter(adapter);

        arrayList = new ArrayList<>();
        for (int i = 1; i <= 12; i++)
            arrayList.add(String.valueOf(i));
        arrayList.add(0, "Month");
        adapter = new ArrayAdapter<String>(this.getContext(), android.R.layout.simple_spinner_dropdown_item, arrayList);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_month.setAdapter(adapter);

        arrayList = new ArrayList<>();


        int year = new java.util.Date().getYear() + 1900;
        for (int i = year - 18; i >= year - 100; i--)
            arrayList.add(String.valueOf(i));
        arrayList.add(0, "Year");
        adapter = new ArrayAdapter<String>(this.getContext(), android.R.layout.simple_spinner_dropdown_item, arrayList);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_year.setAdapter(adapter);

    }

    void fill_doctor_data(int id) {
        curID = id;
        String query = "SELECT * From Doctor where id='" + id + "';";
        ResultSet result = DataBase.excutQuery(query, getContext());

        try {
            if(result.next())
            {

            Toast.makeText(getContext(),result.getString("id"),Toast.LENGTH_SHORT).show();
            text_username.getEditText().setText(result.getString("name"));
            text_email.getEditText().setText(result.getString("e_mail"));
            text_password.getEditText().setText(result.getString("password"));
            text_phone.getEditText().setText(result.getString("phone"));
            //birth date
            String[] date=result.getString("birth_date").split("-");
            spinner_day.setSelection(Integer.parseInt(date[2]));
            spinner_month.setSelection(Integer.parseInt(date[1]));
            int year=-Integer.parseInt(date[0])+(new java.util.Date().getYear() + 1900-18+1);
            spinner_year.setSelection(year);

            //Spica
            String[] Allspecialty=getResources().getStringArray(R.array.specialtyArray);
            String specialty=result.getString("specialty");
            int i=0;
            for(String s:Allspecialty)
            {
                if(s.equals(specialty))
                    spinner_spical.setSelection(i);
                i++;
            }
            ///////////////////////////////////////////
            text_description.getEditText().setText(result.getString("description"));
            text_maxApp.getEditText().setText(result.getString("max_app_per_day"));
            //Days
                String[] days={"sunday","monday","tuesday","wednesday","thursday","friday","saturday"};
            for(i=0;i<7;i++)
            {
               Time t= result.getTime(days[i]);
                if(t==null)
                {
                    daysCheck[i].setChecked(false);
                    daystext[i].setText("");
                }
                else
                {
                    daysCheck[i].setChecked(true);
                    daystext[i].setText(String.valueOf(t.getHours()));
                }

            }

            ///gender
            if(result.getString("gender").equalsIgnoreCase("Male"))
                maleButton.setChecked(true);
            else
                femaleButton.setChecked(true);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void onCreateContextMenu(ContextMenu contextMenu, View view, ContextMenu.ContextMenuInfo contextMenuInfo) {
    ///    Toast.makeText(getContext(),"try",Toast.LENGTH_SHORT).show();

        ArrayList<Pair<Integer, String>> list=loadAll("Doctor",getContext());
        for(Pair<Integer, String> doc:list)
            contextMenu.add(0, Menu.FIRST,1,doc.first+"- "+doc.second);
        super.onCreateContextMenu(contextMenu, view, contextMenuInfo);
    }

    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo)item.getMenuInfo();
        curID=Integer.parseInt(item.getTitle().toString().split("-")[0]);
        fill_doctor_data(curID);
        return super.onContextItemSelected(item);
    }
    private ArrayList<Pair<Integer, String>> loadAll(String tableName, Context context) {
        ArrayList<Pair<Integer, String>> list = new ArrayList<>();
        String query = "select id,name from " + tableName;
        ResultSet result = DataBase.excutQuery(query, context);
        try {
            while (result.next())
                list.add(new Pair<Integer, String>(result.getInt("id"), result.getString("name")));
        } catch (SQLException e) {
        }
        return list;
    }
}