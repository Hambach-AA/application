package com.example.myclient;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;
import android.text.Html;
import android.view.View;
import android.widget.TextView;

import com.example.myclient.Models.RecordingSession;
import com.example.myclient.Models.Schedule;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class MasterInfo extends AppCompatActivity {

    TextView MI_first_name, MI_second_name, MI_phone, MI_email, ML_address, MI_info;

    TextView MI_Monday, MI_Tuesday, MI_Wednesday, MI_Thursday, MI_Friday, MI_Saturday, MI_Sunday;
    DatabaseReference mDatabase;
    String Uid = "";
    ArrayList<TextView> textViews = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_master_info);
        getSupportActionBar().setTitle(Html.fromHtml("<font color='#ffffff'>Информация о мастере</font>"));

        Uid = getIntent().getStringExtra("Uid");

        init();

        info();

        Schedule_info();
    }

    void init(){

        MI_first_name = (TextView) findViewById(R.id.MI_first_name);
        MI_second_name = (TextView) findViewById(R.id.MI_second_name);
        MI_phone = (TextView) findViewById(R.id.MI_phone);
        MI_email = (TextView) findViewById(R.id.MI_email);
        ML_address = (TextView) findViewById(R.id.ML_address);
        MI_info = (TextView) findViewById(R.id.MI_info);

        MI_Monday = (TextView) findViewById(R.id.MI_Monday);
        MI_Tuesday = (TextView) findViewById(R.id.MI_Tuesday);
        MI_Wednesday = (TextView) findViewById(R.id.MI_Wednesday);
        MI_Thursday = (TextView) findViewById(R.id.MI_Thursday);
        MI_Friday = (TextView) findViewById(R.id.MI_Friday);
        MI_Saturday = (TextView) findViewById(R.id. MI_Saturday);
        MI_Sunday = (TextView) findViewById(R.id.MI_Sunday);

        textViews.add(MI_Monday);
        textViews.add(MI_Tuesday);
        textViews.add(MI_Wednesday);
        textViews.add(MI_Thursday);
        textViews.add(MI_Friday);
        textViews.add(MI_Saturday);
        textViews.add( MI_Sunday);

        mDatabase = FirebaseDatabase.getInstance().getReference("Masters");
    }

    void info(){
        mDatabase.child(Uid).child("first_name").get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if(task.isSuccessful()){
                    MI_first_name.setText(task.getResult().getValue().toString());
                }
            }
        });
        mDatabase.child(Uid).child("second_name").get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if(task.isSuccessful()){
                    MI_second_name.setText(task.getResult().getValue().toString());
                }
            }
        });
        mDatabase.child(Uid).child("phone").get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if(task.isSuccessful()){
                    MI_phone.setText(task.getResult().getValue().toString());
                }
            }
        });
        mDatabase.child(Uid).child("email").get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if(task.isSuccessful()){
                    MI_email.setText(task.getResult().getValue().toString());
                }
            }
        });
        mDatabase.child(Uid).child("address").get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if(task.isSuccessful()){
                    ML_address.setText(task.getResult().getValue().toString());
                }
            }
        });
        mDatabase.child(Uid).child("info").get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if(task.isSuccessful()){
                    MI_info.setText(task.getResult().getValue().toString());
                }
            }
        });
    }

    void Schedule_info(){
        for (int i = 0; i < 7; i++) {
            final int finalI = i;
            final int time[] = new int[2];
            final boolean[] flag = new boolean[1];
            String end = "";

            mDatabase.child(Uid).child("schedule").child(String.valueOf(finalI)).child("enable").addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    if ("true".equals(snapshot.getValue().toString())){
                        flag[0] = true;
                    }
                    else{
                        flag[0]=false;
                    }
                }
                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });

            mDatabase.child(Uid).child("schedule").child(String.valueOf(finalI)).child("time_start").addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                   time[0] = Integer.parseInt(snapshot.getValue().toString());
                }
                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });

            mDatabase.child(Uid).child("schedule").child(String.valueOf(finalI)).child("time_finish").addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    time[1] = Integer.parseInt(snapshot.getValue().toString());
                    if(flag[0]) {
                        textViews.get(finalI).setText(timeUnParse(time[0]) + " - " + timeUnParse(time[1]));
                    }
                    else{
                        textViews.get(finalI).setText("Выходной");
                    }
                }
                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
        }
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

    public void onClickRecord(View view){
        Intent intent = new Intent(MasterInfo.this, RecordingActivity.class);
        intent.putExtra("Uid_Master",Uid);
        startActivity(intent);
    }
}