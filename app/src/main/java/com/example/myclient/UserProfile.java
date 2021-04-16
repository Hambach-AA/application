package com.example.myclient;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.myclient.Models.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
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
    FirebaseUser user;

    TextView pSecond_name, pName, pPhone, pEmail;
    Button pSave;
    User client;
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
        pSecond_name = (TextView) findViewById(R.id.pSecond_name);
        pName = (TextView) findViewById(R.id.pName);
        pPhone = (TextView) findViewById(R.id.pPhone);
        pEmail = (TextView) findViewById(R.id.pEmail);
        pSave = (Button) findViewById(R.id.pSave);
    }
    private void info(){
        mDatabase.child(user.getUid()).child("email").get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if(task.isSuccessful()){
                    pEmail.setText(task.getResult().getValue().toString());
                }
            }
            });
        mDatabase.child(user.getUid()).child("first_name").get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if(task.isSuccessful()){
                    pName.setText(task.getResult().getValue().toString());
                }
            }
        });
        mDatabase.child(user.getUid()).child("phone").get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if(task.isSuccessful()){
                    pPhone.setText(task.getResult().getValue().toString());
                }
            }
        });
        mDatabase.child(user.getUid()).child("second_name").get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if(task.isSuccessful()){
                    pSecond_name.setText(task.getResult().getValue().toString());
                }
            }
        });
    }

    public void onClick_save_friends(View view){
        mDatabase.child(user.getUid()).child("first_name").setValue(pName.getText().toString());
        mDatabase.child(user.getUid()).child("phone").setValue(pPhone.getText().toString());
        mDatabase.child(user.getUid()).child("second_name").setValue(pSecond_name.getText().toString());
    }
}
