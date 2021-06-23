package com.example.myclient.Models;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myclient.MasterInfo;
import com.example.myclient.R;
import com.example.myclient.ViewingRecords;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.List;

public class RecordAdapter extends RecyclerView.Adapter<RecordAdapter.RecordViewHolder>{

    private final List<RecordingSession> recordingSessions;
    private Context parent;
    public RecordAdapter(List<RecordingSession> recordingSessions, Context parent) {
        this.recordingSessions = recordingSessions;
        this.parent = parent;
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
        DatabaseReference mRec = FirebaseDatabase.getInstance().getReference("Recording_Session");
        String client_UID;



        public RecordViewHolder(@NonNull View itemView) {
            super(itemView);

            service_rec = itemView.findViewById(R.id.service_rec);
            date_rec = itemView.findViewById(R.id. date_rec);
            time_rec = itemView.findViewById(R.id.time_rec);
            price_rec = itemView.findViewById(R.id.price_rec);
            address_rec = itemView.findViewById(R.id.address_rec);

            itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    mRec.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            for(DataSnapshot rsm : snapshot.getChildren()) {
                                String date_rec_ = rsm.child("date").getValue(String.class);
                                String id = rsm.child("id_client").getValue(String.class);
                                String service_ = rsm.child("service").getValue(String.class);

                                if (date_rec_.equals(date_rec.getText().toString()) && id.equals(client_UID) && service_.equals(service_rec.getText().toString())){

                                    rsm.getRef().removeValue();
                                    Intent intent = new Intent(parent, ViewingRecords.class);
                                    parent.startActivity(intent);

                                }
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });
                    return true;
                }
            });

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
                    client_UID = recordingSession.getId_client();


                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
        }
    }
}
