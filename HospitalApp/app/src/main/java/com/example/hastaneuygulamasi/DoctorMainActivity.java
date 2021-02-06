package com.example.hastaneuygulamasi;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class DoctorMainActivity extends AppCompatActivity {
    Button doctorAppointmentBtn,doctorQuestionBtn,doctorExitBtn;
    Intent intentA ;
    String doctorname;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor_main);
        doctorAppointmentBtn=findViewById(R.id.doctorAppointmentsTextView);
        doctorQuestionBtn=findViewById(R.id.doctorQuestionsTextView);
        doctorExitBtn=findViewById(R.id.doctorExitButton);
        intentA=getIntent();
        doctorname=intentA.getStringExtra("doctorname");
        System.out.println("doc:"+doctorname);


    }

    public void MyAppointments(View view){

        Intent intent = new Intent(DoctorMainActivity.this,DoctorAppointments.class);
        intent.putExtra("doctorname",doctorname);
        startActivity(intent);

    }

    public void AskQuestion(View view){
        Intent intent = new Intent(DoctorMainActivity.this, PickPatient.class);
        intent.putExtra("doctorname",doctorname);
        startActivity(intent);
    }

    public void Exit(View view){
        Intent intent = new Intent(DoctorMainActivity.this, LogInScreen.class);
        startActivity(intent);
        finish();
    }


}