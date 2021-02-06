package com.example.hastaneuygulamasi;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class DoctorAppointments extends AppCompatActivity {
    private FirebaseDatabase firebaseDatabase;
    DatabaseReference reference;
    ArrayList<String> appointmentList;
    ArrayList<String> datesList,hoursList,patientNameList;
    DoctorRecyclerAdapter recyclerAdapter;
    RecyclerView recyclerView;
    Intent intent;
    String doctorname;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor_appointments);
        recyclerView=findViewById(R.id.doctorAppointmentsRecyclerView);
        firebaseDatabase = FirebaseDatabase.getInstance();
        reference = firebaseDatabase.getReference();
        appointmentList = new ArrayList<>();
        datesList=new ArrayList<>();
        hoursList=new ArrayList<>();
        patientNameList=new ArrayList<>();
        intent=getIntent();
        doctorname=intent.getStringExtra("doctorname");
        System.out.println("doctor:"+doctorname);


        RecyclerView.LayoutManager layoutManager=new LinearLayoutManager(DoctorAppointments.this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerAdapter = new DoctorRecyclerAdapter(appointmentList,datesList,hoursList,patientNameList ,DoctorAppointments.this, DoctorAppointments.this);
        recyclerView.setAdapter(recyclerAdapter);
        GetData();
    }
    public void GetData() {
        reference.child("doctors").child(doctorname).child("Appointments").child("Date").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                final String date = dataSnapshot.getKey();
                datesList.add(dataSnapshot.getKey());
                reference.child("doctors").child(doctorname).child("Appointments").child("Date").child(dataSnapshot.getKey()).addChildEventListener(new ChildEventListener() {
                    @Override
                    public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                        String hour=snapshot.getKey();
                        hoursList.add(snapshot.getKey());
                        patientNameList.add(snapshot.getValue().toString());
                        String time = date +" / "+ hour+"\nPatient : "+snapshot.getValue();
                        appointmentList.add(time);
                        recyclerAdapter.notifyDataSetChanged();

                    }

                    @Override
                    public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

                    }

                    @Override
                    public void onChildRemoved(@NonNull DataSnapshot snapshot) {

                    }

                    @Override
                    public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });

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