package com.example.hospital;

import android.content.Context;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;


public class AdminRemoveFrag extends Fragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    ArrayList<Pair<Integer, String>> list;
    ListView listView;
    Spinner spinner;
    String Selected;

    public AdminRemoveFrag() {

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


    private void removeDoctor(int id, Context context) {

        String query = "DELETE FROM Appiontment WHERE doctor_id= " + id + ";";
        DataBase.excutQuery(query, context);

        query = "DELETE FROM Doctor WHERE id= " + id + ";";
        DataBase.excutQuery(query, context);

    }

    private void removePatient(int id, Context context) {
        String query = "DELETE FROM Appiontment WHERE patient_id= " + id + ";";
        DataBase.excutQuery(query, context);

        query = "DELETE FROM Test WHERE patient_id= " + id + ";";
        DataBase.excutQuery(query, context);

        query = "DELETE FROM Patient WHERE id= " + id + ";";
        DataBase.excutQuery(query, context);

    }

    public void updateListView() {

        ArrayList<String> nameList = new ArrayList<>();
        for (Pair<Integer, String> item : list)
            nameList.add(item.first + "- " + item.second);
        ArrayAdapter adapter = new ArrayAdapter(getContext(), android.R.layout.simple_list_item_1, nameList);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
               if(Selected.equals("Doctor"))
                   removeDoctor(list.get(i).first,getContext());
               else
                   removePatient(list.get(i).first,getContext());
                list=loadAll(Selected,getContext());
               updateListView();
            }
        });
    }

    public static AdminRemoveFrag newInstance(String param1, String param2) {
        AdminRemoveFrag fragment = new AdminRemoveFrag();
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
        View rootView = inflater.inflate(R.layout.fragment_admin_remove, container, false);
        spinner = rootView.findViewById(R.id.spinner);
        listView = rootView.findViewById(R.id.theList);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                              @Override
                                              public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                                                   Selected=adapterView.getSelectedItem().toString();
                                                   list=loadAll(Selected,getContext());
                                                   updateListView();
                                              }

                                              @Override
                                              public void onNothingSelected(AdapterView<?> adapterView) {

                                              }
                                          }
        );


        return rootView;
    }
}