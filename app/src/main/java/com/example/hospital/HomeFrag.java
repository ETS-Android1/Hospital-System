package com.example.hospital;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;

import android.os.StrictMode;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.Toast;
import android.widget.ViewFlipper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

/**
 * A simple {@link Fragment} subclass.
 * create an instance of this fragment.
 */
public class HomeFrag extends Fragment {
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private String mParam1;
    private String mParam2;


    public Set<String> speciality = new HashSet<>();
    public final int COL = 2;
    public int width = 150,height = 150;
    public ArrayList<ImageButton> buttonArrayList = new ArrayList<>();
    public int images[] = {R.drawable.get_well,R.drawable.use_app,R.drawable.care,R.drawable.lab};
    public ViewFlipper imageSlider;
    public Patient patient;
    public View view;


    public HomeFrag() {

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
    public static HomeFrag newInstance(String param1, String param2) {
        HomeFrag fragment = new HomeFrag();
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
    public View onCreateView(LayoutInflater inflater,  ViewGroup container,  Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_home,container,false);


        imageSlider = view.findViewById(R.id.imageSlider_frag);
        patient = (Patient) getActivity().getIntent().getSerializableExtra("Person");
        viewSpecialities();
        slider(images);
        return view;
    }

    public void viewSpecialities()
    {
        if(DataBase.connection != null)
        {
            try {
                int col = -1;
                ResultSet resultSet = DataBase.excutQuery("select specialty from Doctor",getActivity());
                TableLayout tableLayout = view.findViewById(R.id.buttons_frag);
                TableRow tableRow = new TableRow(getActivity());

                while (resultSet.next()){
        System.out.println("hnaaaaaaaaaaaaaaaaaaaaaaa");
                    col =  ++col % COL;
                    if(col == 0)
                    {
                        tableRow = new TableRow(getActivity());
                        tableRow.setLayoutParams(new TableLayout.LayoutParams(
                                TableLayout.LayoutParams.WRAP_CONTENT,
                                TableLayout.LayoutParams.WRAP_CONTENT,
                                1.0f));
                        tableLayout.addView(tableRow);
                    }

                    final String specialities = resultSet.getString("specialty");
                    if(speciality.contains(specialities))
                    {
                        col--;
                        continue;
                    }
                    speciality.add(specialities);

                    ImageButton imageButton = new ImageButton(getActivity());

                    if(specialities.equalsIgnoreCase("DENTIST"))
                        imageButton.setImageResource(R.drawable.dentist);
                    else if(specialities.equalsIgnoreCase("UROLOGY"))
                        imageButton.setImageResource(R.drawable.urology);
                    else if(specialities.equalsIgnoreCase("INTERNAL"))
                        imageButton.setImageResource(R.drawable.internal);
                    else if(specialities.equalsIgnoreCase("FAMILY"))
                        imageButton.setImageResource(R.drawable.family);
                    else if(specialities.equalsIgnoreCase("OPHTHALMOLOGY"))
                        imageButton.setImageResource(R.drawable.ophthalmology);
                    else if(specialities.equalsIgnoreCase("NEUROLOGY"))
                        imageButton.setImageResource(R.drawable.neurlogy);
                    else if(specialities.equalsIgnoreCase("SURGERY"))
                        imageButton.setImageResource(R.drawable.surgery);
                    else if(specialities.equalsIgnoreCase("PEDIATRICS"))
                        imageButton.setImageResource(R.drawable.pediatrics);

                    imageButton.setLayoutParams(new TableRow.LayoutParams(
                            TableRow.LayoutParams.WRAP_CONTENT,
                            TableRow.LayoutParams.MATCH_PARENT,
                            1.0f));
                    imageButton.setBackgroundColor(getResources().getColor(R.color.White));
                    tableRow.addView(imageButton);


                    imageButton.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            clickButton(specialities);
                        }
                    });
                }

                while(col < COL)
                {
                    ImageButton imageButton = new ImageButton(getActivity());
                    imageButton.setLayoutParams(new TableRow.LayoutParams(
                            TableRow.LayoutParams.WRAP_CONTENT,
                            TableRow.LayoutParams.MATCH_PARENT,
                            1.0f));
                    imageButton.setPadding(0,0,100,0);
                    imageButton.setBackgroundColor(getResources().getColor(R.color.White));
                    tableRow.addView(imageButton);
                    col++;
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }

        }
    }

    public void clickButton(String speciality)
    {
        Intent intent = new Intent(getActivity(),DoctorsActivity.class);
        intent.putExtra("speciality",speciality);
        intent.putExtra("Patient",patient);
        startActivity(intent);
    }

    public void slider(int[] images)
    {
        for(int image : images)
        {
            ImageView imageView = new ImageView(getActivity());
            imageView.setBackgroundResource(image);
            imageSlider.addView(imageView);
            imageSlider.setFlipInterval(4000);
            imageSlider.setAutoStart(true);
            imageSlider.setInAnimation(getActivity(),android.R.anim.slide_in_left);
            imageSlider.setOutAnimation(getActivity(),android.R.anim.slide_out_right);
        }
    }
}