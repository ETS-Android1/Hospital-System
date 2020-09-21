package com.example.hospital;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Pair;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;



public class tests extends AppCompatActivity {
    Patient patient ;
    Test test;
    int Curid=0;
    ArrayList<Integer>IDs;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tests);


        ListView tests = (ListView) findViewById(R.id.viewTests);
         IDs=new ArrayList<>();
        ArrayList<String>list=loadData();
        ArrayAdapter testsData = new ArrayAdapter(this,android.R.layout.simple_list_item_1,list);
        tests.setAdapter(testsData);

        tests.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //message box asking to confirm the deleting
                Curid=IDs.get(position);
                openMessageBox();

            }
        });

    }



    public ArrayList<String> loadData()
    {
        ArrayList<String> list=new ArrayList<>();
        IDs.clear();
        String query = "SELECT id,date,text from Test WHERE patient_id ='"+patient.getID()+"'";
        ResultSet result = DataBase.excutQuery(query, this);
        try {
            while (result.next())
            {

             String data="Date: "+result.getDate("date")+"\n"+result.getString("text");
                list.add(data);
                IDs.add(result.getInt("id"));
            }
        } catch (SQLException e) {
        }
        return list;
    }

    public void deleteTest ()
    {
        String query = "DELETE FROM Tests WHERE test_id ='"+Curid+"'";
        DataBase.excutQuery(query,this);

    }




    public void openMessageBox() {
        AlertDialog.Builder builder = new AlertDialog.Builder(tests.this);
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