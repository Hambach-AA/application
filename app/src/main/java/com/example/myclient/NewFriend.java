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
import com.example.myclient.Models.Master_new;
import com.example.myclient.Models.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
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
    DatabaseReference mDatabase;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_friend);

        init();
    }

    void init() {
        mDatabase = FirebaseDatabase.getInstance().getReference("Masters");
        mDb = FirebaseDatabase.getInstance().getReference("Clients").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("friends");
        UidField = findViewById(R.id.UidField);
        add_new_friend = (Button) findViewById(R.id.add_new_friend);

    }

    public void loadData(View view) {
        mDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot rsm : snapshot.getChildren()) {

                    String email = rsm.child("email").getValue(String.class);
                    String uid_m = rsm.child("uid").getValue(String.class);
                    if (email.equals(UidField.getText().toString())){
                        mDb.push().setValue(uid_m);
                        Intent intent = new Intent(NewFriend.this, List_masters.class);
                        startActivity(intent);
                    }

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}
