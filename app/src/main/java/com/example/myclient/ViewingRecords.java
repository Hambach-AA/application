package com.example.myclient;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.example.myclient.Models.RecordAdapter;
import com.example.myclient.Models.RecordingSession;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;

public class ViewingRecords extends AppCompatActivity {

    DatabaseReference mDatabase; //????????????????
    FirebaseUser user;
    ArrayList<RecordingSession> recordingSessions = new ArrayList<>();

    RecyclerView recyclerview_reg;
    RecordAdapter recordAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_viewing_records);


        init();
        user_records();
    }

    void init(){

        recyclerview_reg = (RecyclerView) findViewById(R.id.recyclerview_reg);
        recordAdapter = new RecordAdapter(recordingSessions);
        recyclerview_reg.setLayoutManager(new LinearLayoutManager(this));
        recyclerview_reg.setAdapter(recordAdapter);

        mDatabase = FirebaseDatabase.getInstance().getReference("Recording_Session"); //????????????????
        user = FirebaseAuth.getInstance().getCurrentUser();
    }
    private void  user_records(){
        mDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot rsm : snapshot.getChildren()){
                    RecordingSession recordingSession = rsm.getValue(RecordingSession.class);
                    if (recordingSession.getId_client().equals(user.getUid())){
                        recordingSessions.add(recordingSession);
                    }
                }
                add_recyclerview();
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
    }
    private String timeUnParse(int temp) {
        int minute = 0;
        int hours = 0;

        String sminute;
        String shours;

        hours = temp / 60;
        minute = temp - (hours * 60);

        if (minute == 0) {
            sminute = "00";
        } else if (minute < 10) {
            sminute = "0" + String.valueOf(minute);
        } else {
            sminute = String.valueOf(minute);
        }

        if (hours == 0) {
            shours = "00";
        } else if (hours < 10) {
            shours = "0" + String.valueOf(hours);
        } else {
            shours = String.valueOf(hours);
        }

        return shours+":"+sminute;
    }

    private void add_recyclerview(){
        Collections.sort(recordingSessions, new Comparator<RecordingSession>() {
            @Override
            public int compare(RecordingSession o1, RecordingSession o2)
            {
                String day_01 = o1.getDate().substring(0,o1.getDate().indexOf("."));
                String month_01 = o1.getDate().substring(o1.getDate().indexOf(".")+1,o1.getDate().indexOf(".",3));
                String year_o1 = o1.getDate().substring(o1.getDate().indexOf(".",3)+1,o1.getDate().indexOf(".",3)+5);

                String day_02 = o2.getDate().substring(0,o2.getDate().indexOf("."));
                String month_02 = o2.getDate().substring(o2.getDate().indexOf(".")+1,o2.getDate().indexOf(".",3));
                String year_o2 = o2.getDate().substring(o2.getDate().indexOf(".",3)+1,o2.getDate().indexOf(".",3)+5);

                if(Integer.parseInt(day_01)<10){
                    day_01 = "0"+day_01;
                }
                if(Integer.parseInt(day_02)<10){
                    day_02 = "0"+day_02;
                }
                if(Integer.parseInt(month_01)<10){
                    month_01 = "0"+month_01;
                }
                if(Integer.parseInt(month_02)<10){
                    month_02 = "0"+month_02;
                }
                return (year_o1+"."+month_01+"."+day_01+"."+o1.getStart_service()).compareTo( (year_o2+"."+month_02+"."+day_02+"."+o2.getStart_service()));
            };
        });
        for ( int i = 0; i<recordingSessions.size(); i++){
            recordingSessions.get(i).setStart_service(timeUnParse(Integer.valueOf(recordingSessions.get(i).getStart_service())));
            recordingSessions.get(i).setEnd_service(timeUnParse(Integer.valueOf(recordingSessions.get(i).getEnd_service())));
        }
        recordAdapter.notifyDataSetChanged();

    }
}