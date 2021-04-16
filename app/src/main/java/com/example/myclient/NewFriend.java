package com.example.myclient;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myclient.Models.Master;
import com.example.myclient.Models.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class NewFriend extends AppCompatActivity {

    Button add_new_friend;
    TextView UidField;
    User client = new User();
    DatabaseReference mDb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_friend);

        init();
    }

    void init() {

        mDb = FirebaseDatabase.getInstance().getReference("Clients").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("friends");
        UidField = findViewById(R.id.UidField);
        add_new_friend = (Button) findViewById(R.id.add_new_friend);

    }

    public void loadData(View view) {

        DatabaseReference dbRef = FirebaseDatabase.getInstance().getReference("Masters");
        Query dataQuery = dbRef.orderByChild("uid").equalTo(UidField.getText().toString());

        dataQuery.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    mDb.push().setValue(UidField.getText().toString());
                } else {
                    Toast.makeText(NewFriend.this, "Пользователь не найден", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });
    }
}
