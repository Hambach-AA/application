package com.example.myclient;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.myclient.Models.Master;
import com.example.myclient.Models.StateAdapter;
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


public class List_masters extends AppCompatActivity {

    DatabaseReference cDatabase;
    FirebaseUser user;

    DatabaseReference mDatabase;

    ArrayList<Master> masters  = new ArrayList<Master>();

    RecyclerView recyclerView;
    StateAdapter adapter;

    ArrayList<String> friends = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_masters);
        init();
        info_friends();
    }

    void init(){

        user = FirebaseAuth.getInstance().getCurrentUser();
        cDatabase = FirebaseDatabase.getInstance().getReference("Clients");

        mDatabase = FirebaseDatabase.getInstance().getReference("Masters");

        recyclerView = (RecyclerView) findViewById(R.id.sch_list);
        adapter = new StateAdapter(masters,this);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);

    }
    void ListMasters(){
        info();
        System.out.println("____");
    }

    void info_friends() {
        cDatabase.child(user.getUid()).child("friends").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot ds : snapshot.getChildren() ){
                    friends.add(ds.getValue().toString());
                }
                ListMasters();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
    private void info(){

        for(int i = 0; i<friends.size(); i++){
            Master master = new Master();
            mDatabase.child(friends.get(i)).child("first_name").addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    master.setFirst_name(snapshot.getValue().toString());
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
            mDatabase.child(friends.get(i)).child("second_name").addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    master.setSecond_name(snapshot.getValue().toString());
                }
                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
            mDatabase.child(friends.get(i)).child("uid").addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    master.setUid(snapshot.getValue().toString());
                }
                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
            mDatabase.child(friends.get(i)).child("info").addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    master.setInfo(snapshot.getValue().toString());
                    masters.add(master);
                    adapter.notifyDataSetChanged();
                }
                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
        }
    }

    public void onClick_new_friend(View view){
        Intent intent = new Intent(List_masters.this, NewFriend.class);
        startActivity(intent);
    }
}