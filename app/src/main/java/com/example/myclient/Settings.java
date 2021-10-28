package com.example.myclient;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.view.View;

public class Settings extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().setTitle(Html.fromHtml("<font color='#ffffff'>Настройки</font>"));
        setContentView(R.layout.activity_settings);

    }
    public void onClick_exit(View view){
        Intent intent = new Intent(Settings.this, MainActivity.class);
        startActivity(intent);
    }
}