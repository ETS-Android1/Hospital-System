package com.example.hospital;

import androidx.fragment.app.Fragment;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;

import android.text.InputType;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;


import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;

import java.util.Date;
import java.util.regex.Pattern;

public class ProfileFrag extends Fragment {

    DatePickerDialog picker;
    private Person user;

    public DataBase dataBase;
    boolean found;


    public RadioGroup gender;
    public RadioButton selecrgender;
    public int userid;
    public ResultSet resultSet;
    public String useremail;/////////////////////////////////////////
    public LinearLayout layy, calen, genderlayy;
    public EditText email, phone, description, pass, npass, confirmnpass, name, birthday, max;
    public Button submitbutton,showDoctorCalendar;
    public TextView temail, tgender, tphone, tdes, tpass, tname, tbirthday, tspecil, firstname, tmax;
    private String emaill, genderr, phonee, des, spass, snpass, sncpass, sname, sbirthday, realpass, smax;
    public LinearLayout.LayoutParams params, pram;
    private ImageView editImage,patientApp,logout;

    Calendar date;
    private static final Pattern PASSWORD_PATTERN =
            Pattern.compile("^" +
                    "(?=.*[a-zA-Z])" +      //any letter
                    "(?=.*[@#$%^&+=])" +    //at least 1 special character
                    "(?=\\S+$)" +           //no white spaces
                    ".{4,}" +               //at least 4 characters
                    "$");
    private View view;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_profile, container, false);

        calen = view.findViewById(R.id.calendervis_id);
        tmax = view.findViewById(R.id.numofmax);
        gender = view.findViewById(R.id.radioGroup);
        firstname = view.findViewById(R.id.nameid);
        tbirthday = view.findViewById(R.id.birthdayid);
        temail = view.findViewById(R.id.email_text);
        tspecil = view.findViewById(R.id.speciality_id);
        tgender = view.findViewById(R.id.gender);
        tphone = view.findViewById(R.id.phone);
        tdes = view.findViewById(R.id.des);
        tpass = view.findViewById(R.id.password_text);
        tname = view.findViewById(R.id.name_text);
        layy = view.findViewById(R.id.lay_id);
        genderlayy = view.findViewById(R.id.genderlay);
        editImage = view.findViewById(R.id.editImage);
        birthday = view.findViewById(R.id.editText1);
        showDoctorCalendar = view.findViewById(R.id.calenderid);
        patientApp = view.findViewById(R.id.patient_appointments);
        logout = view.findViewById(R.id.logout);


        user = (Person) getActivity().getIntent().getSerializableExtra("Person");

        useremail = "basma@gmail.com";


        userid = 1;/////////////////////////////////////////////da ely bt7km be any id yd5ol
        loadinformation();
        dr_or_user();

        editImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openeiditing();
            }
        });
        birthday.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                opencalender();
            }
        });
        showDoctorCalendar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openappocalen();
            }
        });
        patientApp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(),patientAppointments.class);
                intent.putExtra("Person",user);
                startActivity(intent);
            }
        });
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(),login.class);
                intent.putExtra("deleteTable" ,1);
                startActivity(intent);
            }
        });

        return view;
    }

    public void loadinformation() {

        if (user instanceof Doctor) {
            firstname.setText(user.getName());
            tname.setText(user.getName());
            temail.setText(user.getEmail());
            tphone.setText(user.getPhone());
            tpass.setText(user.getPassword());
            realpass = user.getPassword();
            tbirthday.setText(user.getDateOfBirth());
            tgender.setText(user.getGender());
            tspecil.setText(((Doctor) user).getSpeciality());
            tdes.setText(((Doctor) user).getDescription());
            tmax.setText(String.valueOf(((Doctor) user).getMaxAppointmentsPerDay()));
        } else {
            firstname.setText(user.getName());
            tname.setText(user.getName());
            temail.setText(user.getEmail());
            tphone.setText(user.getPhone());
            tpass.setText(user.getPassword());
            realpass = user.getPassword();
            tbirthday.setText(user.getDateOfBirth());
            tgender.setText(user.getGender());
        }
    }


    public void updateinformation() {


        if (user instanceof Doctor) {
            if (dataBase.connection != null) {

                String sql = "update Doctor Set name=" + "'" + tname.getText().toString() + "'" + ", phone=" + "'" + tphone.getText().toString() + "'" + ", gender=" + "'" + tgender.getText().toString() + "'" + ", description=" + "'" + tdes.getText().toString() + "'" + ", e_mail=" + "'" + temail.getText().toString() + "'" +
                        ",password= " + "'" + tpass.getText().toString() + "'" + ",specialty=" + "'" + tspecil.getText().toString() + "'" + ", birth_date=" + "'" + tbirthday.getText().toString() + "'" + ", max_app_per_day=" + "'" + Integer.parseInt(tmax.getText().toString()) + "'" + " where id=" + "'" + userid + "'";
                System.out.println(sql);
                dataBase.excutQuery(sql, getActivity());
            } else {
                tname.setText("No Connection");
            }
        } else {

            if (dataBase.connection != null) {
                String sql = "update Patient Set name=" + "'" + tname.getText().toString() + "'" + ", phone=" + "'" + tphone.getText().toString() + "'" + ", gender=" + "'" + tgender.getText().toString() + "'" + ", e_mail=" + "'" + temail.getText().toString() + "'" +
                        ",password= " + "'" + tpass.getText().toString() + "'" + ", birth_date=" + "'" + tbirthday.getText().toString() + "'" + " where id=" + "'" + userid + "'";
                System.out.println(sql);
                dataBase.excutQuery(sql, getActivity());

            } else {
                tname.setText("No Connection");
            }

        }
    }


    public void dr_or_user() {
        if (user instanceof Doctor) {
            tspecil.setVisibility(View.VISIBLE);
        } else {
            params = (LinearLayout.LayoutParams) layy.getLayoutParams();
            params.height = 0;
            params.width = 0;
            layy.setLayoutParams(params);

        }
    }

    public void reset_edit_page() {
        if (user instanceof Doctor) {
            pram = (LinearLayout.LayoutParams) genderlayy.getLayoutParams();
            params = (LinearLayout.LayoutParams) calen.getLayoutParams();


            params.height = pram.height;
            params.width = pram.width;


            calen.setLayoutParams(params);
        }

        if (smax.isEmpty() == false) {
            tmax.setText(smax);
        }
        if (sname.isEmpty() == false) {
            tname.setText(sname);
        }
        if ((tname.getText().toString()).isEmpty() == false) {
            firstname.setText(tname.getText().toString());
        }
        if (sbirthday.isEmpty() == false)
            tbirthday.setText(sbirthday);

        if (sncpass.isEmpty() == false)
            tpass.setText(sncpass);
        if (emaill.isEmpty() == false)

            temail.setText(emaill);

        if (phonee.isEmpty() == false)
            tphone.setText(phonee);
        if (des.isEmpty() == false)
            tdes.setText(des);

        max.getText().clear();
        max.setHint(max.getHint());


        birthday.getText().clear();
        birthday.setHint(birthday.getHint());

        name.getText().clear();
        name.setHint(name.getHint());

        email.getText().clear();
        email.setHint(email.getHint());


        phone.getText().clear();
        phone.setHint(phone.getHint());

        description.getText().clear();
        description.setHint(description.getHint());

        pass.getText().clear();
        pass.setHint(pass.getHint());

        npass.getText().clear();
        npass.setHint(npass.getHint());

        confirmnpass.getText().clear();
        confirmnpass.setHint(confirmnpass.getHint());

        calen.setVisibility(View.VISIBLE);
        max.setVisibility(View.INVISIBLE);
        email.setVisibility(View.INVISIBLE);
        gender.setVisibility(View.INVISIBLE);
        phone.setVisibility(View.INVISIBLE);
        description.setVisibility(View.INVISIBLE);
        pass.setVisibility(View.INVISIBLE);
        npass.setVisibility(View.INVISIBLE);
        confirmnpass.setVisibility(View.INVISIBLE);
        submitbutton.setVisibility(View.INVISIBLE);
        name.setVisibility(View.INVISIBLE);
        birthday.setVisibility(View.INVISIBLE);
    }


    public void openeiditing() {


        email = view.findViewById(R.id.editemail);

        phone = view.findViewById(R.id.eidtphone);
        max = view.findViewById(R.id.editnum);


        name = view.findViewById(R.id.editname);

        birthday = view.findViewById(R.id.editText1);
        birthday.setInputType(InputType.TYPE_NULL);

        description = view.findViewById(R.id.editdescr);
        pass = view.findViewById(R.id.editpassword);
        npass = view.findViewById(R.id.newpassword);
        confirmnpass = view.findViewById(R.id.confirmnewpassword);

        submitbutton = view.findViewById(R.id.submitedit);


        params = (LinearLayout.LayoutParams) calen.getLayoutParams();

        params.height = 0;
        params.width = 0;
        calen.setLayoutParams(params);


        max.setVisibility(view.getVisibility());
        name.setVisibility(view.getVisibility());
        email.setVisibility(view.getVisibility());
        phone.setVisibility(view.getVisibility());
        gender.setVisibility(view.getVisibility());
        submitbutton.setVisibility(view.getVisibility());
        description.setVisibility(view.getVisibility());
        pass.setVisibility(view.getVisibility());
        npass.setVisibility(view.getVisibility());
        confirmnpass.setVisibility(view.getVisibility());
        birthday.setVisibility(view.getVisibility());

        submitbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (validation()) {
                    reset_edit_page();
                    updateinformation();
                }
            }
        });

    }


    public void opencalender() {
        tbirthday = view.findViewById(R.id.birthdayid);
        birthday.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar cldr = Calendar.getInstance();
                int day = cldr.get(Calendar.DAY_OF_MONTH);
                int month = cldr.get(Calendar.MONTH);
                int year = cldr.get(Calendar.YEAR);
                // date picker dialog
                picker = new DatePickerDialog(getActivity(),
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                                birthday.setText(year + "-" + (month + 1) + "-" + dayOfMonth);
                            }
                        }, year, month, day);
                picker.show();
            }
        });
    }


    public void openappocalen() {//forappointment
        startActivity(new Intent(getActivity(), Appointment_calen.class));
    }


    public boolean validation() {
        sname = name.getText().toString();
        smax = max.getText().toString();
        emaill = email.getText().toString();
// get text from EditText password view
        phonee = phone.getText().toString();

        des = description.getText().toString();

        sbirthday = birthday.getText().toString();
        spass = pass.getText().toString();
        snpass = npass.getText().toString();
        sncpass = confirmnpass.getText().toString();

        if (!validateEmail() | !validateUsername() | !validatePassword() | !validatephonenumber()) {
            return false;
        }
        return true;
    }


    private boolean validateEmail() {
        if (emaill.isEmpty()) {
            return true;
        }
        if (!Patterns.EMAIL_ADDRESS.matcher(emaill).matches()) {
            email.setError("Please enter a valid email address");
            return false;
        } else {
            email.setError(null);
            return true;
        }
    }

    private boolean validateUsername() {
        if (sname.isEmpty()) {
            return true;
        }
        if (sname.length() > 15) {
            name.setError("Username too long");
            return false;
        } else {
            name.setError(null);
            return true;
        }
    }


    private boolean validatePassword() {
        if (spass.isEmpty()) {
            return true;
        }
        if (!(spass.equals(realpass))) {
            pass.setError("Enter correct password");
            return false;
        } else if (!PASSWORD_PATTERN.matcher(snpass).matches()) {
            npass.setError("Password too weak");
            return false;
        } else if (!(snpass.equals(sncpass))) {
            confirmnpass.setError("The confirm password does not match ");
            return false;
        } else {
            pass.setError(null);
            return true;
        }
    }

    private boolean validatephonenumber() {
        if (phonee.isEmpty()) {
            return true;
        }
        if (phonee.length() < 9 || phonee.length() > 11) {
            phone.setError("not valid number");
            return false;
        } else {
            phone.setError(null);
            return true;
        }
    }

    public void checkButton(View view) {
        int radio = gender.getCheckedRadioButtonId();
        selecrgender = view.findViewById(radio);
        tgender.setText(selecrgender.getText());

    }

    public void back(View view) {

    }

}
