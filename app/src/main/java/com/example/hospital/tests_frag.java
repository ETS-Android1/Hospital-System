package com.example.hospital;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link tests_frag#newInstance} factory method to
 * create an instance of this fragment.
 */
public class tests_frag extends Fragment {
    private Patient patient ;
    private Test test;
    private int Curid = 0;
    private ArrayList<String>list;
    private ArrayList<Test> testArrayList;
    private ArrayAdapter testsData;
    private ListView tests;


    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private View view;
    private ImageButton addButton;

    public tests_frag() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment tests_frag.
     */
    // TODO: Rename and change types and number of parameters
    public static tests_frag newInstance(String param1, String param2) {
        tests_frag fragment = new tests_frag();
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
        view = inflater.inflate(R.layout.fragment_tests_frag,container,false);
        patient = (Patient) getActivity().getIntent().getSerializableExtra("Person");
        tests = view.findViewById(R.id.viewTests);
        addButton = view.findViewById(R.id.AddTest);
        list = loadData();
        testsData = new ArrayAdapter(getActivity(),R.layout.white_list_view ,list);
        tests.setAdapter(testsData);

        tests.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //message box asking to confirm the deleting
                Curid = position;
                openMessageBox();

            }
        });

        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(),addTest.class);
                intent.putExtra("Person",patient);
                startActivity(intent);
            }
        });
        return view;
    }

    public ArrayList<String> loadData()
    {
        ArrayList<String> list = new ArrayList<>();
        testArrayList = Test.getTests(patient.getID(),getActivity());
        for(Test test : testArrayList)
        {
            list.add(test.getDate() + "\n" + test.getText());
        }
        return list;
    }

    public void deleteTest ()
    {
        list.remove(Curid);
        tests.setAdapter(testsData);
        String query = "DELETE FROM Test WHERE id ='" + testArrayList.get(Curid).getID() + "'";
        DataBase.excutQuery(query,getActivity());
    }




    public void openMessageBox() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Confirm Delete!");
        builder.setMessage("Are you already want to delete this test?");
        builder.setPositiveButton("yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                deleteTest();
            }
        });
        builder.setNegativeButton("no", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();

    }
}