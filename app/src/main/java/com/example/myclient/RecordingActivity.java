package com.example.myclient;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;


import com.example.myclient.Models.RecordingSession;
import com.example.myclient.Models.Services;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;


//TODO сделать ресайклер вью и промежутки свободного времени (использовать слушателей в полях)
public class RecordingActivity extends AppCompatActivity {

    Calendar cal = Calendar.getInstance();
    int mHour = cal.get(Calendar.HOUR_OF_DAY);
    int mMinute = cal.get(Calendar.MINUTE);
    DatabaseReference mDatabase;
    DatabaseReference rsDatabase;
    List<String> services = new ArrayList<>();
    RecyclerView recyclerView;
    Spinner spinner;
    TextView date;
    TextView time;
    Button btn;
    String Master_Uid;

    ArrayList<Services> service = new ArrayList<>();
    ArrayAdapter<String> adapter;

    String service_end, price;
    @Override
    protected void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recording);

        adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, services);

        Master_Uid = getIntent().getStringExtra("Uid_Master");

        mDatabase = FirebaseDatabase.getInstance().getReference("Masters").child(Master_Uid).child("list_services");
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
                RecordingSession rs = new RecordingSession();
                rs.setId_master(Master_Uid);
                rs.setId_client(FirebaseAuth.getInstance().getCurrentUser().getUid());
                rs.setDate(date.getText().toString());
                //TODO получение выбраного в спинере значения
                rs.setService(service_end);
                rs.setPrice(price);
                rsDatabase.push().setValue(rs);
            }
        });




    }

    private void onClick_spinner(){
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                service_end = service.get(position).getName();
                price =  service.get(position).getPrice();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private void info_services_class(){
        mDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
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

                DatePickerDialog datePickerDialog = new DatePickerDialog(RecordingActivity.this,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                                String editTextDateParam = dayOfMonth + "." + (monthOfYear + 1) + "." + year;
                                v.setText(editTextDateParam);
                            }
                        }, year, month, day);
                datePickerDialog.show();
            }
        });
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



}