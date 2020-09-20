package com.example.hospital;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.nostra13.universalimageloader.cache.memory.impl.WeakMemoryCache;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;
import java.util.ArrayList;

/**
 * Created by Mustafa Elshamy.
 */

public class AppointmentArrayAdapter extends ArrayAdapter<Appointment> {

    private static final String TAG = "AppointmentArrayAdapter";

    private Context mContext;
    private int mResource;
    private int lastPosition = -1;

    /**
     * Holds variables in a View
     */
    private static class ViewHolder {
        TextView DoctorName;
        TextView Date;
        TextView Number;
        ImageView image;
    }

    /**
     * Default constructor for the PersonListAdapter
     * @param context
     * @param resource
     * @param objects
     */
    public AppointmentArrayAdapter(Context context, int resource, ArrayList<Appointment> objects) {
        super(context, resource, objects);
        mContext = context;
        mResource = resource;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        //sets up the image loader library
        setupImageLoader();

        //get the appointment information
        String id = getItem(position).getID();
        String patient_ID = getItem(position).getPatientID();
        String doctor_ID = getItem(position).getDoctorID();
        Doctor doctor = Doctor.getDoctor(doctor_ID,"id",mContext);
        String date = getItem(position).getDate();
        int number = getItem(position).getNumberInQueue();
        String imageURL = "";

        if(doctor.getSpeciality().equalsIgnoreCase("dentist"))
            imageURL = "drawable://" + R.drawable.dentist;
        else if(doctor.getSpeciality().equalsIgnoreCase("urology"))
            imageURL = "drawable://" + R.drawable.urology;
        else if(doctor.getSpeciality().equalsIgnoreCase("NEUROLOGY"))
            imageURL = "drawable://" + R.drawable.neurlogy;
        else if(doctor.getSpeciality().equalsIgnoreCase("family"))
            imageURL = "drawable://" + R.drawable.family;
        else if(doctor.getSpeciality().equalsIgnoreCase("pediatrics"))
            imageURL = "drawable://" + R.drawable.pediatrics;
        else if(doctor.getSpeciality().equalsIgnoreCase("internal"))
            imageURL = "drawable://" + R.drawable.internal;
        else if(doctor.getSpeciality().equalsIgnoreCase("ophthalmology"))
            imageURL = "drawable://" + R.drawable.ophthalmology;
        else if(doctor.getSpeciality().equalsIgnoreCase("surgery"))
            imageURL = "drawable://" + R.drawable.surgery;

        //Create the appointment object with the information
        Appointment appointment = new Appointment(id,doctor_ID,patient_ID,
                Appointment.convertDate(date),number);

        //create the view result for showing the animation
        final View result;

        //ViewHolder object
        ViewHolder holder;


        if(convertView == null){
            LayoutInflater inflater = LayoutInflater.from(mContext);
            convertView = inflater.inflate(mResource, parent, false);
            holder = new ViewHolder();
            holder.DoctorName = (TextView) convertView.findViewById(R.id.doctorName);
            holder.Date = (TextView) convertView.findViewById(R.id.date_text);
            holder.Number = (TextView) convertView.findViewById(R.id.number_text);
            holder.image = (ImageView)convertView.findViewById(R.id.image);

            result = convertView;

            convertView.setTag(holder);
        }
        else{
            holder = (ViewHolder) convertView.getTag();
            result = convertView;
        }


        Animation animation = AnimationUtils.loadAnimation(mContext,
                (position > lastPosition) ? R.anim.load_down_anim : R.anim.load_up_anim);
        result.startAnimation(animation);
        lastPosition = position;

        holder.DoctorName.setText( "D. " + doctor.getName());
        holder.Date.setText(appointment.getDate());
        holder.Number.setText(String.valueOf(appointment.getNumberInQueue()));

        //create the imageloader object
        ImageLoader imageLoader = ImageLoader.getInstance();

        //create display options
        DisplayImageOptions options = new DisplayImageOptions.Builder().cacheInMemory(true)
                .cacheOnDisc(true).resetViewBeforeLoading(true).build();

        //download and display image from url
        imageLoader.displayImage(imageURL, holder.image, options);

        return convertView;
    }
    /**
     * Required for setting up the Universal Image loader Library
     */
    private void setupImageLoader(){
        // UNIVERSAL IMAGE LOADER SETUP
        DisplayImageOptions defaultOptions = new DisplayImageOptions.Builder()
                .cacheOnDisc(true).cacheInMemory(true)
                .imageScaleType(ImageScaleType.EXACTLY_STRETCHED)
                .displayer(new FadeInBitmapDisplayer(300)).build();

        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(
                mContext)
                .defaultDisplayImageOptions(defaultOptions)
                .memoryCache(new WeakMemoryCache())
                .discCacheSize(100 * 1024 * 1024).build();

        ImageLoader.getInstance().init(config);
        // END - UNIVERSAL IMAGE LOADER SETUP
    }
}