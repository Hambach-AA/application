package com.example.myclient.Models;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myclient.List_masters;
import com.example.myclient.MasterInfo;
import com.example.myclient.R;

import java.util.ArrayList;
import java.util.List;

public class StateAdapter extends RecyclerView.Adapter<StateAdapter.NumberViewHolder>{

    private final List<Master> masters;
    private  Context parent;
    public StateAdapter(List<Master> masters,Context parent){
        this.masters = masters;
        this.parent = parent;
    }
    @NonNull
    @Override
    public NumberViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        Context context = parent.getContext();
        int layoutIdforLisstItem = R.layout.list_item;
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(layoutIdforLisstItem, parent, false);

        NumberViewHolder viewHolder = new NumberViewHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull NumberViewHolder holder, int position) {
        holder.bind(masters.get(position));
    }

    @Override
    public int getItemCount() {
        return masters.size();
    }

    class NumberViewHolder extends RecyclerView.ViewHolder {

        TextView first_name;
        TextView second_name;
        String Uid="";

        public NumberViewHolder(@NonNull View itemView) {
            super(itemView);

            first_name = itemView.findViewById(R.id.first_name_master);
            second_name = itemView.findViewById(R.id.second_name_master);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(parent, MasterInfo.class);
                    intent.putExtra("Uid",Uid);
                    parent.startActivity(intent);
                }
            });
        }

        void bind (Master master){
            first_name.setText(master.getFirst_name());
            second_name.setText(master.getSecond_name());
            Uid = master.getUid();
        }


    }
}