package com.example.hastaneuygulamasi;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.lang.ref.Reference;
import java.util.ArrayList;
import java.util.LinkedList;

public class AppointmentAcivity extends AppCompatActivity {
    Intent intent;
    String date,doctorname;
    TextView textView,doctorNameText;
    Spinner appointmentHoursText;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference reference;
    LinkedList<String> appointmentsHours;
    ArrayList<String> reservedHours;
    ArrayAdapter<String> arrayAdapter;
    String selectedHour,userName="";

    FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();

    String email = firebaseUser.getEmail();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        firebaseDatabase = FirebaseDatabase.getInstance();
        reference = firebaseDatabase.getReference();
        setContentView(R.layout.activity_appointment_acivity);
        appointmentsHours=new LinkedList<>();
        appointmentHoursText=findViewById(R.id.appointmentsTextView);
        reservedHours=new ArrayList<>();
        doctorNameText=findViewById(R.id.doctornameTextViewInAppointment);
        intent=getIntent();
        doctorname=intent.getStringExtra("doctorname");
        doctorNameText.setText(doctorname);
        date = intent.getStringExtra("date");
        FillArray();


        for(char c:email.toCharArray()){
            if(c!='@'){
                userName+=c;
            }
            else{
                break;
            }
        }





         arrayAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, appointmentsHours);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        appointmentHoursText.setAdapter(arrayAdapter);
        appointmentHoursText.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectedHour = parent.getItemAtPosition(position).toString();
                Toast.makeText(parent.getContext(), "Selected: " + selectedHour,Toast.LENGTH_LONG).show();
            }
            @Override
            public void onNothingSelected(AdapterView <?> parent) {
            }
        });

        textView = findViewById(R.id.dateTextView);
        textView.setText(date);
    }
    private void FillArray(){

        for(int i=9;i<17;i++) {
            for (int j = 0; j < 46; j += 15) {
                String s;
                if (i == 9) {
                    if(j==0){
                        s = "0" + i + ":" + j+""+j;
                    }
                    else{
                        s = "0" + i + ":" + j;
                    }

                } else {
                    if(j==0){
                        s =  i + ":" + j+""+j;
                    }
                    else{
                        s = i + ":" + j;
                    }
                }
                appointmentsHours.add(s);

            }
        }
        reference.child("doctors").child(doctorname).child("Appointments").child("Date").child(date).addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                String k = dataSnapshot.getKey();
                    for(int j=0;j<appointmentsHours.size();j++){
                        if (k.equals(appointmentsHours.get(j))){
                            appointmentsHours.remove(j);
                            arrayAdapter.notifyDataSetChanged();

                        }

                    }


                //recyclerAdapter.notifyDataSetChanged();
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
    public void Apply(View view){
        reference.child("doctors").child(doctorname).child("Appointments").child("Date").child(date).child(selectedHour).setValue(userName).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                reference.child("users").child(userName).child("Appointments").child("Date").child(date).child(selectedHour).setValue(doctorname);



                Toast.makeText(getApplicationContext(),"Appointment made successfully",Toast.LENGTH_LONG).show();
                Intent intent = new Intent(AppointmentAcivity.this,MainActivity.class);
                startActivity(intent);
                finish();
            }
        });


    }

}