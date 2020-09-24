package com.example.hospital;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.FragmentTransaction;

import android.Manifest;
import android.app.DatePickerDialog;
import android.app.usage.UsageEvents;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.StrictMode;
import com.github.sundeepk.compactcalendarview.CompactCalendarView;
import com.github.sundeepk.compactcalendarview.domain.Event;
import android.provider.CalendarContract;
import android.service.autofill.FillEventHistory;
import android.text.InputType;
import android.text.Layout;
import android.util.EventLog;
import android.util.Patterns;
import android.widget.DatePicker;
import android.widget.ImageView;


import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.github.sundeepk.compactcalendarview.CompactCalendarView;
import com.riontech.calendar.CustomCalendar;
import com.riontech.calendar.dao.EventData;
import com.riontech.calendar.dao.dataAboutDate;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;

import java.util.Date;
import java.util.regex.Pattern;

public class Profile extends AppCompatActivity {

    DatePickerDialog picker;

    public DataBase dataBase;
    boolean found;


   public RadioGroup gender;
    public RadioButton selecrgender;
   public  int userid;
   public  ResultSet resultSet;
    public   String useremail,user;/////////////////////////////////////////
    public LinearLayout layy,calen,genderlayy;
    public EditText email, phone, description, pass, npass, confirmnpass, name, birthday,max;
    public Button submitbutton;
    public TextView temail, tgender, tphone, tdes, tpass, tname, tbirthday,tspecil,firstname,tmax;
    String emaill, genderr, phonee, des, spass, snpass, sncpass, sname, sbirthday,realpass,smax;
    public LinearLayout.LayoutParams params,pram;

    Calendar date;
    private static final Pattern PASSWORD_PATTERN =
            Pattern.compile("^" +
                    "(?=.*[a-zA-Z])" +      //any letter
                    "(?=.*[@#$%^&+=])" +    //at least 1 special character
                    "(?=\\S+$)" +           //no white spaces
                    ".{4,}" +               //at least 4 characters
                    "$");
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    //Intent intent = new Intent(this,Home.class);
        //startActivity(intent);

        setContentView(R.layout.activity_profile);

        ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.INTERNET}, PackageManager.PERMISSION_GRANTED);
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);


        dataBase =new DataBase(this);
        found =false;
       useremail= "basma@gmail.com";
        calen=(LinearLayout)findViewById(R.id.calendervis_id);
       tmax=(TextView)findViewById(R.id.numofmax);
        gender=(RadioGroup)findViewById(R.id.radioGroup);
          firstname=(TextView)findViewById(R.id.nameid);
        tbirthday=(TextView)findViewById(R.id.birthdayid) ;
        temail = (TextView) findViewById(R.id.email_text);
        tspecil=(TextView)findViewById(R.id.speciality_id);
        tgender = (TextView) findViewById(R.id.gender);
        tphone = (TextView) findViewById(R.id.phone);
        tdes = (TextView) findViewById(R.id.des);
        tpass = (TextView) findViewById(R.id.password_text);
        tname = (TextView) findViewById(R.id.name_text);
        layy = (LinearLayout) findViewById(R.id.lay_id);
        genderlayy= (LinearLayout) findViewById(R.id.genderlay);

        userid=9;/////////////////////////////////////////////da ely bt7km be any id yd5ol
        user="walawa7d";
        loadinformation();
        dr_or_user(user);
    }

public  void loadinformation(){

    if(dataBase.connection != null)
    {
        try {

          resultSet = dataBase.excutQuery("select * from Doctor where id=" + userid, this);
           if (!(resultSet==null)) {
               user = "Doctor";
               while (resultSet.next()) {
                   found=true;
                   firstname.setText(resultSet.getString("name"));
                   tname.setText(resultSet.getString("name"));
                   temail.setText(resultSet.getString("e_mail"));
                   tphone.setText(resultSet.getString("phone"));
                   tpass.setText(resultSet.getString("password"));
                   realpass = resultSet.getString("password");
                   tbirthday.setText(resultSet.getString("birth_date"));
                   tgender.setText(resultSet.getString("gender"));
                   tspecil.setText(resultSet.getString("specialty"));
                   tdes.setText(resultSet.getString("description"));
                   tmax.setText(String.valueOf(resultSet.getInt("max_app_per_day")));
               }

           }

        }
        catch (SQLException e) {
            e.printStackTrace();
            tname.setText(e.getMessage());
        }
if (found==false){
    try {

        resultSet = dataBase.excutQuery("select * from Patient where id=" + userid, this);
        if (!(resultSet==null)) {
            user = "patient";
            while (resultSet.next()) {
                found=true;
                firstname.setText(resultSet.getString("name"));
                tname.setText(resultSet.getString("name"));
                temail.setText(resultSet.getString("e_mail"));
                tphone.setText(resultSet.getString("phone"));
                tpass.setText(resultSet.getString("password"));
                realpass = resultSet.getString("password");
                tbirthday.setText(resultSet.getString("birth_date"));
                tgender.setText(resultSet.getString("gender"));

            }

        }

    }
    catch (SQLException e) {
        e.printStackTrace();
        tname.setText(e.getMessage());
    }
}
    }
    else
    {
        tname.setText("No Connection");
    }

}


public void updateinformation(){


if (user.equals("Doctor")) {
    if(dataBase.connection != null)
    {

    String sql="update Doctor Set name="+"'"+tname.getText().toString()+"'"+", phone="+"'"+tphone.getText().toString()+"'"+ ", gender="+"'"+ tgender.getText().toString()+"'"+", description="+"'"+tdes.getText().toString()+"'" +", e_mail="+"'"+temail.getText().toString()+"'"+
        ",password= "+"'"+tpass.getText().toString()+"'"+",specialty="+"'"+tspecil.getText().toString()+"'" + ", birth_date="+"'"+tbirthday.getText().toString()+"'"+  ", max_app_per_day=" +"'"+Integer.parseInt(tmax.getText().toString())+"'"  + " where id="+"'"+userid+"'";
       System.out.println(sql);
    dataBase.excutQuery(sql, this);
}
    else
    {
        tname.setText("No Connection");
    }
    }

else{

    if(dataBase.connection != null)
    {
        String sql="update Patient Set name="+"'"+tname.getText().toString()+"'"+", phone="+"'"+tphone.getText().toString()+"'"+ ", gender="+"'"+ tgender.getText().toString()+"'"+", e_mail="+"'"+temail.getText().toString()+"'"+
                ",password= "+"'"+tpass.getText().toString()+"'"+", birth_date="+"'"+tbirthday.getText().toString()+"'"+" where id="+"'"+userid+"'";
        System.out.println(sql);
        dataBase.excutQuery(sql, this);


    }
    else
    {
        tname.setText("No Connection");
    }



}
}





    public void dr_or_user(String dr) {
        if (dr.equals("Doctor")) {
            tspecil.setVisibility(View.VISIBLE);
        }
        else{
            params = (LinearLayout.LayoutParams) layy.getLayoutParams();

            params.height = 0;
            params.width = 0;
            layy.setLayoutParams(params);

        }
    }

    public void reset_edit_page() {
        if (user.equals("Doctor")){
            pram = (LinearLayout.LayoutParams) genderlayy.getLayoutParams();
            params = (LinearLayout.LayoutParams) calen.getLayoutParams();


            params.height =pram.height;
            params.width = pram.width;


            calen.setLayoutParams(params);

        }

        if (smax.isEmpty() == false) {
            tmax.setText(smax);
        }
        if (sname.isEmpty() == false) {
            tname.setText(sname);
        }
      if((tname.getText().toString()).isEmpty()==false){
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


    public void openeiditing(View view) {


        email = (EditText) findViewById(R.id.editemail);

        phone = (EditText) findViewById(R.id.eidtphone);
         max=(EditText)findViewById(R.id.editnum);


        name = (EditText) findViewById(R.id.editname);

        birthday = (EditText) findViewById(R.id.editText1);

        description = (EditText) findViewById(R.id.editdescr);
        pass = (EditText) findViewById(R.id.editpassword);
        npass = (EditText) findViewById(R.id.newpassword);
        confirmnpass = (EditText) findViewById(R.id.confirmnewpassword);

        submitbutton = (Button) findViewById(R.id.submitedit);



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



    public void opencalender(View view) {
        tbirthday = (TextView) findViewById(R.id.birthdayid);


        birthday.setInputType(InputType.TYPE_NULL);
        birthday.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final  Calendar cldr = Calendar.getInstance();
                int day = cldr.get(Calendar.DAY_OF_MONTH);
                int month = cldr.get(Calendar.MONTH);
                int year = cldr.get(Calendar.YEAR);
                // date picker dialog
                picker = new DatePickerDialog(Profile.this,
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


public  void openappocalen(View view){//forappointment
  startActivity(new Intent(Profile.this,Appointment_calen.class));
}


public  boolean validation(){
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

    if (!validateEmail() | !validateUsername() | !validatePassword()| !validatephonenumber()) {
        return false;
    }
    else {
        String input = "Email: " + email.getText().toString();
        input += "\n";
        input += "Username: " + name.getText().toString();
        input += "\n";
        input += "Password: " + pass.getText().toString();
        Toast.makeText(this, input, Toast.LENGTH_SHORT).show();
        return  true;
    }
}


    private boolean validateEmail() {
        if (emaill.isEmpty()){
            return true;
        }
        if (!Patterns.EMAIL_ADDRESS.matcher(emaill).matches()) {
            email.setError("Please enter a valid email address");
            return false;
        }
        else {
           email.setError(null);
            return true;
        }
    }

    private boolean validateUsername() {
        if (sname.isEmpty()){
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
        if (spass.isEmpty()){
            return true;
        }
        if (!(spass.equals(realpass))) {
            pass.setError("Enter correct password");
            return false;
        }
      else  if (!PASSWORD_PATTERN.matcher(snpass).matches()) {
            npass.setError("Password too weak");
            return false;
        }

        else if (!(snpass.equals(sncpass))) {
            confirmnpass.setError("The confirm password does not match ");
            return false;
        }

        else {
           pass.setError(null);
            return true;
        }
    }

    private boolean validatephonenumber() {
        if (phonee.isEmpty()){
            return true;
        }
        if (phonee.length() <9||phonee.length() > 11) {
            phone.setError("not valid number");
            return false;
        } else {
            phone.setError(null);
            return true;
        }
    }

    public  void checkButton(View view){
    int radio=gender.getCheckedRadioButtonId();
   selecrgender=findViewById(radio);
   tgender.setText(selecrgender.getText());

    }

public void back(View view){

}
}
