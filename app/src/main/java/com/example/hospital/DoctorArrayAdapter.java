package com.example.hospital;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import java.util.ArrayList;

public class DoctorArrayAdapter extends ArrayAdapter<Doctor> {

    private static final String TAG = "DoctorArrayAdapter";

    private Context mContext;
    private int mResource;
    private int lastPosition = -1;
    /**
     * Holds variables in a View
     */
    private static class ViewHolder {

        private static final String TAG = "DoctorArrayAdapter";

        TextView DoctorName;
        TextView Age;
        TextView Gender;
    }

    /**
     * Default constructor for the PersonListAdapter
     * @param context
     * @param resource
     * @param objects
     */
    public DoctorArrayAdapter(Context context, int resource, ArrayList<Doctor> objects) {
        super(context, resource, objects);
        mContext = context;
        mResource = resource;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        //get the Doctor information
        String id = getItem(position).getID();
        String name = getItem(position).getName();
        String specialty = getItem(position).getSpeciality();
        String phone = getItem(position).getPhone();
        String email = getItem(position).getEmail();
        String gender = getItem(position).getGender();
        String birthdate = getItem(position).getDateOfBirth();

        //Create the appointment object with the information
        Doctor doctor = new Doctor(id,name,phone,email,gender,birthdate);

        //create the view result for showing the animation
        final View result;

        //ViewHolder object
        DoctorArrayAdapter.ViewHolder holder;


        if(convertView == null){
            LayoutInflater inflater = LayoutInflater.from(mContext);
            convertView = inflater.inflate(mResource, parent, false);
            holder = new DoctorArrayAdapter.ViewHolder();
            holder.DoctorName = (TextView) convertView.findViewById(R.id.doctor_name);
            holder.Age = (TextView) convertView.findViewById(R.id.age_text);
            holder.Gender = (TextView) convertView.findViewById(R.id.gender_text);

            result = convertView;

            convertView.setTag(holder);
        }
        else{
            holder = (DoctorArrayAdapter.ViewHolder) convertView.getTag();
            result = convertView;
        }


        Animation animation = AnimationUtils.loadAnimation(mContext,
                (position > lastPosition) ? R.anim.load_down_anim : R.anim.load_up_anim);
        result.startAnimation(animation);
        lastPosition = position;

        holder.DoctorName.setText( "D. " + doctor.getName());
        holder.Age.setText(String.valueOf(doctor.getAge()));
        holder.Gender.setText(doctor.getGender());

        return convertView;
    }
}
