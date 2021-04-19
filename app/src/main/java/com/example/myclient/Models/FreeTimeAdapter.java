package com.example.myclient.Models;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myclient.R;

import java.util.List;

public class FreeTimeAdapter extends RecyclerView.Adapter<FreeTimeAdapter.timeViewHolder>{
    private final List<Free_time> free_times;

    public FreeTimeAdapter(List<Free_time> free_times) {
        this.free_times = free_times;
    }

    @NonNull
    @Override
    public timeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        int layoutIdforLisstItem = R.layout.free_time_item;
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(layoutIdforLisstItem, parent, false);
        timeViewHolder viewHolder = new timeViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull timeViewHolder holder, int position) {
        holder.bind(free_times.get(position));
    }

    @Override
    public int getItemCount() {
        return free_times.size();
    }

    class timeViewHolder extends RecyclerView.ViewHolder {

        TextView start_free_time;


        public timeViewHolder(@NonNull View itemView) {
            super(itemView);
            start_free_time = itemView.findViewById(R.id.free_time);
        }

        void bind (Free_time free_time){
            start_free_time.setText(free_time.get_time());

        }
    }

}
