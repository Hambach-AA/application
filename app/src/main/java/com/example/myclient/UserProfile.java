package com.example.myclient;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.myclient.Models.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Map;

public class UserProfile extends AppCompatActivity {

    DatabaseReference mDatabase;
//    DatabaseReference usersRef;
//    final FirebaseDatabase database = FirebaseDatabase.getInstance();
//    DatabaseReference ref = database.getReference("");
    FirebaseUser user;

    TextView pSecond_name, pName, pPhone, pEmail;
    Button pSave;
    User client = new User();
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
//        usersRef =  ref.child("Clients");
        pSecond_name = (TextView) findViewById(R.id.pSecond_name);
        pName = (TextView) findViewById(R.id.pName);
        pPhone = (TextView) findViewById(R.id.pPhone);
        pEmail = (TextView) findViewById(R.id.pEmail);
        pSave = (Button) findViewById(R.id.pSave);
    }

    void info(){
        ValueEventListener postListener = new ValueEventListener(){
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                DataSnapshot info_user = snapshot.child(user.getUid());
                client = info_user.getValue(User.class);
                pSecond_name.setText(client.getSecond_name());
                pName.setText(client.getFirst_name());
                pEmail.setText(client.getEmail());
                pPhone.setText(client.getPhone());
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        };
        mDatabase.addValueEventListener(postListener);
    }
    public void onClick_save(View view){
        Map<String, Object> userNicknameUpdates = new HashMap<>();
        userNicknameUpdates.put(client.getUid(), new User(pName.getText().toString(), pSecond_name.getText().toString(), client.getEmail(), pPhone.getText().toString(),client.getUid()));
//        usersRef.updateChildren(userNicknameUpdates);
        mDatabase.updateChildren(userNicknameUpdates);
    }
}