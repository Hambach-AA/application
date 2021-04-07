package com.example.myclient;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import com.example.myclient.Models.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class UserProfile extends AppCompatActivity {

    DatabaseReference mDatabase;
    FirebaseUser user;

    TextView text_Name, text_Email, text_Phone_number;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);
        init();
        info();
    }

    private void init(){

        user = FirebaseAuth.getInstance().getCurrentUser();
        mDatabase = FirebaseDatabase.getInstance().getReference("Clients");

        text_Name = (TextView) findViewById(R.id.text_Name);
        text_Email = (TextView) findViewById(R.id.text_Email);
        text_Phone_number = (TextView) findViewById(R.id.text_Phone_number);
    }

    void info(){
        ValueEventListener postListener = new ValueEventListener(){
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                DataSnapshot info_user = snapshot.child(user.getUid());
                User client = info_user.getValue(User.class);

                text_Name.setText(client.getName());
                text_Email.setText(client.getEmail());
                text_Phone_number.setText(client.getPhone());

            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        };
        mDatabase.addValueEventListener(postListener);

    }
}