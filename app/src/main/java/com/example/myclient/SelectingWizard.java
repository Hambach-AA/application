package com.example.myclient;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.example.myclient.Models.Master;
import com.example.myclient.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class SelectingWizard extends AppCompatActivity {

    DatabaseReference mDatabase;
    FirebaseUser master;

    ArrayList<Master> masters  = new ArrayList<Master>();

//    ArrayList<User> masters  = new ArrayList<User>();

    RecyclerView recyclerView;
    StateAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_selecting_wizard);

        init();
        info();
    }

    public class StateAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{
        private final List<Master> masters;

        public StateAdapter(List<Master> masters) {
            this.masters = masters;
        }

        @NonNull
        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            return new RecyclerView.ViewHolder(
                    LayoutInflater.from(parent.getContext())
                            .inflate(R.layout.list_item, parent, false)
            ) {};
        }

        @Override
        public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
            TextView first_name = holder.itemView.findViewById(R.id.first_name_master);
            TextView second_name = holder.itemView.findViewById(R.id.second_name_master);
            first_name.setText(String.format("%s. %s", position, this.masters.get(position).getFirst_name()));
            second_name.setText(String.format("%s. %s", position, this.masters.get(position).getSecond_name()));
        }

        @Override
        public int getItemCount() {
            return masters.size();
        }

    }

//    public class StateAdapter extends RecyclerView.Adapter<StateAdapter.ViewHolder>{
//        private final LayoutInflater inflater;
//        private final List<User> masters;
//
//        StateAdapter(Context context, List<User> masters) {
//            this.masters = masters;
//            this.inflater = LayoutInflater.from(context);
//        }
//        @Override
//        public StateAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
//
//            View view = inflater.inflate(R.layout.list_item, parent, false);
//            return new ViewHolder(view);
//        }
//
//        @Override
//        public void onBindViewHolder(StateAdapter.ViewHolder holder, int position) {
//            User master = masters.get(position);
//            holder.first_name_master.setText(master.getEmail());
//            holder.second_name_master.setText(master.getName());
//
//        }
//
//        @Override
//        public int getItemCount() {
//            return masters.size();
//        }
//
//        public class ViewHolder extends RecyclerView.ViewHolder {
//            final TextView first_name_master, second_name_master;
//            ViewHolder(View view){
//                super(view);
//                first_name_master = (TextView) view.findViewById(R.id.first_name_master);
//                second_name_master = (TextView) view.findViewById(R.id.second_name_master);
//            }
//        }
//    }



    private void init(){
        recyclerView = (RecyclerView) findViewById(R.id.sch_list);
        adapter = new StateAdapter(masters);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);

        master = FirebaseAuth.getInstance().getCurrentUser();
        mDatabase = FirebaseDatabase.getInstance().getReference("Masters");
//        mDatabase = FirebaseDatabase.getInstance().getReference("Clients");
    }


    private void info(){
        ValueEventListener postListener = new ValueEventListener(){
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot ds : snapshot.getChildren() ){
                    Master master = ds.getValue(Master.class);
                    masters.add(master);
                }
                adapter.notifyDataSetChanged();
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        };
        mDatabase.addValueEventListener(postListener);
    }

}