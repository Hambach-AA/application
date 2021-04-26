package com.example.myclient.Models;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myclient.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.List;

public class RecordAdapter extends RecyclerView.Adapter<RecordAdapter.RecordViewHolder>{

    private final List<RecordingSession> recordingSessions;

    public RecordAdapter(List<RecordingSession> recordingSessions) {
        this.recordingSessions = recordingSessions;
    }

    @NonNull
    @Override
    public RecordViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        int layoutIdforLisstItem = R.layout.record_item;
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(layoutIdforLisstItem, parent, false);
        RecordViewHolder recordViewHolder = new RecordViewHolder(view);
        return recordViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecordViewHolder holder, int position) {
        holder.bind(recordingSessions.get(position));
    }

    @Override
    public int getItemCount() {
        return recordingSessions.size();
    }

    class RecordViewHolder extends RecyclerView.ViewHolder {

        TextView service_rec, date_rec, time_rec, price_rec, address_rec;
        DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference("Masters");

        public RecordViewHolder(@NonNull View itemView) {
            super(itemView);

            service_rec = itemView.findViewById(R.id.service_rec);
            date_rec = itemView.findViewById(R.id. date_rec);
            time_rec = itemView.findViewById(R.id.time_rec);
            price_rec = itemView.findViewById(R.id.price_rec);
            address_rec = itemView.findViewById(R.id.address_rec);

        }
        void bind (RecordingSession recordingSession){
            mDatabase.child(recordingSession.getId_master()).child("address").addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    service_rec.setText(recordingSession.getService());
                    date_rec.setText(recordingSession.getDate());
                    time_rec.setText(recordingSession.getStart_service()+" - "+recordingSession.getEnd_service());
                    price_rec.setText(recordingSession.getPrice());
                    address_rec.setText(snapshot.getValue().toString());
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
        }
    }
}
