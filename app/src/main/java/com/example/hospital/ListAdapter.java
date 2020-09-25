package com.example.hospital;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class ListAdapter extends RecyclerView.Adapter {

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }
    private  class ListViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{


        private Button b;
        public ListViewHolder(View itemview){
            super(itemview);
            b=(Button)itemview.findViewById(R.id.calenderid);
            itemview.setOnClickListener(this);
        }
        public void onClick(View view){

        }
    }
}
