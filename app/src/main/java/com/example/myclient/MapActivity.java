package com.example.myclient;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MapActivity extends AppCompatActivity {

    Button btn_selecting_wizard, btn_viewing_records, btn_settings, btn_user_profile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);

        init();
    }

    private void init(){

        btn_selecting_wizard = (Button) findViewById(R.id.btn_selecting_wizard);
        btn_viewing_records = (Button) findViewById(R.id.btn_viewing_records);
        btn_settings = (Button) findViewById(R.id.btn_settings);
        btn_user_profile = (Button) findViewById(R.id.btn_user_profile);

    }

    public void onClick_user_profile(View view){
        Intent intent = new Intent(MapActivity.this,UserProfile.class);
        startActivity(intent);
    }

    public void onClick_selecting_wizard(View view){
        Intent intent = new Intent(MapActivity.this,List_masters.class);
        startActivity(intent);
    }
    public void onClick_viewing_records(View view){
        Intent intent = new Intent(MapActivity.this, ViewingRecords.class);
        startActivity(intent);
    }
    public void onClick_settings(View view){
        Intent intent = new Intent(MapActivity.this, Settings.class);
        startActivity(intent);
    }
}