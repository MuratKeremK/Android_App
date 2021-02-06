package com.example.hastaneuygulamasi;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class PickPatient extends AppCompatActivity {


    private FirebaseDatabase firebaseDatabase;
    DatabaseReference reference;
    ArrayList<String> doctorsNamesList;
    MessagePatientAdapter recyclerAdapter;
    RecyclerView recyclerView;
    String doctorname;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.doctor_activity_feed);

        recyclerView = (RecyclerView)findViewById(R.id.recyclerViewDoctor);
        firebaseDatabase = FirebaseDatabase.getInstance();
        reference = firebaseDatabase.getReference();
        doctorsNamesList = new ArrayList<>();
        doctorname=getIntent().getStringExtra("doctorname");


        RecyclerView.LayoutManager layoutManager=new LinearLayoutManager(PickPatient.this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerAdapter = new MessagePatientAdapter(doctorsNamesList, PickPatient.this, PickPatient.this);
        recyclerView.setAdapter(recyclerAdapter);

        GetData();
        System.out.println(doctorsNamesList.size());
    }

    public void GetData() {
        reference.child("messages").child(doctorname).addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

                doctorsNamesList.add(dataSnapshot.getKey());
                recyclerAdapter.notifyDataSetChanged();
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}