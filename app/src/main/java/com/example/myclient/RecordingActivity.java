package com.example.myclient;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import com.example.myclient.Models.FreeTimeAdapter;
import com.example.myclient.Models.Free_time;
import com.example.myclient.Models.RecordingSession;
import com.example.myclient.Models.Services;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.List;


//TODO сделать ресайклер вью и промежутки свободного времени (использовать слушателей в полях)
public class RecordingActivity extends AppCompatActivity {

    Calendar cal = Calendar.getInstance();
    int mHour = cal.get(Calendar.HOUR_OF_DAY);
    int mMinute = cal.get(Calendar.MINUTE);
    DatabaseReference mDatabase;
    DatabaseReference mscDatabase;
    DatabaseReference rsDatabase;
    List<String> services = new ArrayList<>();

    RecyclerView recyclerView;
    FreeTimeAdapter adapter_free_time;
    ArrayList<Free_time> free_times = new ArrayList<>();

    Spinner spinner;
    TextView date;
    TextView time;
    TextView weekend;
    Button btn;
    String Master_Uid;
    ArrayList<Services> service = new ArrayList<>();
    ArrayAdapter<String> adapter;

    ArrayList<Integer> recordingSessions = new ArrayList<>();

    String service_name, price, service_time, day_week;

    boolean confirmation;

    @Override
    protected void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recording);

        recyclerView = (RecyclerView) findViewById(R.id.rec_list);
        adapter_free_time = new FreeTimeAdapter(free_times);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter_free_time);

        weekend = (TextView) findViewById(R.id.weekend);

        adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, services);

        Master_Uid = getIntent().getStringExtra("Uid_Master");

        mDatabase = FirebaseDatabase.getInstance().getReference("Masters").child(Master_Uid);

        mscDatabase = FirebaseDatabase.getInstance().getReference("Masters").child(Master_Uid).child("schedule");

        rsDatabase = FirebaseDatabase.getInstance().getReference("Recording_Session");

        spinner = findViewById(R.id.rec_spinner);
        date = findViewById(R.id.rec_date);
        time = findViewById(R.id.rec_time);
        btn = findViewById(R.id.rec_btn);

        // Создаем адаптер ArrayAdapter с помощью массива строк и стандартной разметки элемета spinner

        // Определяем разметку для использования при выборе элемента
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Применяем адаптер к элементу spinner
        spinner.setAdapter(adapter);

        onClickDate(date);
        onClickTime(time);

        info_services_class();

        onClick_spinner();
        //TODO дописать логику

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(confirmation){
                    if(сhecking_time(timeParse(time.getText().toString()))) {
                        RecordingSession rs = new RecordingSession();
                        rs.setId_master(Master_Uid);
                        rs.setId_client(FirebaseAuth.getInstance().getCurrentUser().getUid());
                        rs.setDate(date.getText().toString());
                        rs.setDay_week(day_week);

                        //TODO получение выбраного в спинере значения
                        rs.setService(service_name);
                        rs.setPrice(price);
                        rs.setStart_service(String.valueOf(timeParse(time.getText().toString())));
                        rs.setEnd_service(String.valueOf(timeParse(time.getText().toString()) + Integer.valueOf(service_time))); //+++++++++++++++++++++++++++++++++++
                        rsDatabase.push().setValue(rs);

                        Intent intent= new Intent(RecordingActivity.this, MapActivity.class);
                        startActivity(intent);

                    }
                    else {
                        Toast toast= Toast.makeText(RecordingActivity.this,"Выберите другой день или время",Toast.LENGTH_SHORT);
                        toast.show();
                    }
                }
                else {
                    Toast toast= Toast.makeText(RecordingActivity.this,"Выберите другой день",Toast.LENGTH_SHORT);
                    toast.show();
                }
            }
        });




    }

    private void onClick_spinner(){
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                service_name = service.get(position).getName();
                price =  service.get(position).getPrice();
                service_time = service.get(position).getTime();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private void info_services_class(){
        mDatabase.child("list_services").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot ds: snapshot.getChildren())
                {
                    Services s = ds.getValue(Services.class);
                    assert s != null;
                    service.add(s);
                }
                add_spinner_services();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void add_spinner_services(){
        for (int i = 0; i<service.size(); i++){
            adapter.add(service.get(i).getName());
        }
        adapter.notifyDataSetChanged();
    }

    private void onClickDate(final TextView v) {
        v.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar cal = Calendar.getInstance();
                int year = cal.get(Calendar.YEAR);
                int month = cal.get(Calendar.MONTH);
                int day = cal.get(Calendar.DAY_OF_MONTH);
                weekend.setText("");
                confirmation = true;

                DatePickerDialog datePickerDialog = new DatePickerDialog(RecordingActivity.this,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                                String editTextDateParam = dayOfMonth + "." + (monthOfYear + 1) + "." + year;
                                Calendar c = Calendar.getInstance();
                                c.setTime(new Date(year,monthOfYear+1,dayOfMonth));
                                day_week = day_week_converter(c.get(Calendar.DAY_OF_WEEK));

                                v.setText(editTextDateParam);

                                mDatabase.child("schedule").child(day_week).child("enable").addListenerForSingleValueEvent(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                                        if ("true".equals(snapshot.getValue().toString())){

                                            adapter_free_time.notifyDataSetChanged();
                                            free_times.clear();
                                            recordingSessions = new ArrayList<>();
                                            working_hours_master();
                                            master_records();
                                        }
                                        else{
                                            weekend.setText("Выходной день");
                                            free_times.clear();
                                            adapter_free_time.notifyDataSetChanged();
                                            confirmation = false;
                                        }
                                    }
                                    @Override
                                    public void onCancelled(@NonNull DatabaseError error) {

                                    }
                                });


//                                working_hours_master();
//                                master_records();
                            }
                        }, year, month, day);
                datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis()-1000);
                datePickerDialog.show();
            }
        });
    }

    private  String day_week_converter(int day) {

        String week_normal;

//        System.out.println("________________________________________");
//        System.out.println("________________________________________");
//        System.out.println("________________________________________");
//        System.out.println(day);
//        System.out.println("________________________________________");
//        System.out.println("________________________________________");
//        System.out.println("________________________________________");

        if (day == 1){ week_normal = "2"; }
        else if (day == 2) {week_normal = "3";}
        else if (day == 3) {week_normal = "4";}
        else if (day == 4) {week_normal = "5";}
        else if (day == 5) {week_normal = "6";}
        else if (day == 6) {week_normal = "0";}
        else {week_normal = "1";}
        return week_normal;
    }

    private void onClickTime(final TextView v) {
        v.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                TimePickerDialog timePickerDialog = new TimePickerDialog(RecordingActivity.this,
                        new TimePickerDialog.OnTimeSetListener() {
                            @Override
                            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                                String textViewTimeParam;
                                if (minute < 10) {
                                    textViewTimeParam = hourOfDay + ":0" + minute;
                                } else {
                                    textViewTimeParam = hourOfDay + ":" + minute;

                                }
                                v.setText(textViewTimeParam);
                            }
                        },
                        mHour,
                        mMinute,
                        true);
                timePickerDialog.show();
            }
        });
    }

    private int timeParse(String temp) {
        int time = 0;
        if (temp.indexOf(':') == 1) {
            time = Integer.parseInt(temp.substring(0, 1)) * 60;
            time = time + Integer.parseInt(temp.substring(2, 4));
        } else if (temp.indexOf(':') == 2) {
            time = Integer.parseInt(temp.substring(0, 2)) * 60;
            time = time + Integer.parseInt(temp.substring(3, 5));
        }
        return time;
    }

    private void working_hours_master() {
        mDatabase.child("schedule").child(day_week).child("time_start").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                recordingSessions.add(Integer.valueOf(snapshot.getValue().toString()));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        mDatabase.child("schedule").child(day_week).child("time_finish").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                recordingSessions.add((Integer.valueOf(snapshot.getValue().toString())-Integer.valueOf(service_time)));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void  master_records(){
        rsDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot rsm : snapshot.getChildren()){
                    RecordingSession recordingSession = rsm.getValue(RecordingSession.class);
                    if (recordingSession.getId_master().equals(Master_Uid) && recordingSession.getDate().equals(date.getText().toString())){
                        recordingSessions.add(Integer.valueOf(recordingSession.getStart_service()));
                        recordingSessions.add(Integer.valueOf(recordingSession.getEnd_service()));
                    }
                }
                time_intervals();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void time_intervals(){

        Collections.sort(recordingSessions);
        for( int i = 0; i<recordingSessions.size(); i+=2){
            if(!recordingSessions.get(i).equals(recordingSessions.get(i + 1))){

                free_times.add(new Free_time(timeUnParse(recordingSessions.get(i)),timeUnParse(recordingSessions.get(i+1))));
            }
        }
        adapter_free_time.notifyDataSetChanged();
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

    private boolean сhecking_time(int time){
        boolean flag = false;
        for(int i = 0; i<recordingSessions.size(); i+=2){
            if(recordingSessions.get(i) <= time && recordingSessions.get(i+1) > time){
                flag = true;
                break;
            }
        }
        return flag;
    }
}