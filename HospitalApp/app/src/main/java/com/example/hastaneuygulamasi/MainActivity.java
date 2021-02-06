package com.example.hastaneuygulamasi;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {
    FirebaseAuth firebaseAuth;
    Dialog epicDialog;

    private FirebaseDatabase firebaseDatabase;
    DatabaseReference reference;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        firebaseAuth = FirebaseAuth.getInstance();
        epicDialog = new Dialog(this);



        firebaseDatabase = FirebaseDatabase.getInstance();
        reference = firebaseDatabase.getReference();

    }
    public void MakeAnAppointment(View view){
        Intent intent = new Intent(MainActivity.this,FeedActivity.class);
        startActivity(intent);

    }
    public void MyAppointments(View view){
        Intent intent = new Intent(MainActivity.this,MyAppointmentsActivity.class);
        startActivity(intent);

    }

    public void AskQuestion(View view){
        Intent intent = new Intent(MainActivity.this, PickDoctor.class);
        startActivity(intent);
    }

    public void Exit(View view){
        firebaseAuth.signOut();
        Intent intent = new Intent(MainActivity.this, LogInScreen.class);
        startActivity(intent);
        finish();
    }

    public void GoAccountInfo(View view){
        Intent intent = new Intent(MainActivity.this, AccountActivity.class);
        startActivity(intent);
    }
}