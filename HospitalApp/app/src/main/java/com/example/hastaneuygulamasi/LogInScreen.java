package com.example.hastaneuygulamasi;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
public class LogInScreen extends AppCompatActivity {
    EditText emailTextView, passwordTextView;
    Button signInButton, signUpButton;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference reference;
    FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in_screen);
        define();

        /*
        reference.child("doctors").child("Fatih Aytar").child("Staff ID").setValue(100);
        reference.child("doctors").child("Burak Aksu").child("Staff ID").setValue(200);
        reference.child("doctors").child("Murat Kerem Kara").child("Staff ID").setValue(300);
        reference.child("doctors").child("Sümeyye Gürel").child("Staff ID").setValue(400);
        reference.child("doctors").child("Ali ").child("Staff ID").setValue(500);
         */
    }
    public void define(){
        emailTextView = findViewById(R.id.signUpDoctorEmailTextView);
        passwordTextView = findViewById(R.id.signUpDoctorPasswordTextView);
        signInButton=findViewById(R.id.signInButton);
        signUpButton = findViewById(R.id.signUpButton);
        firebaseDatabase= FirebaseDatabase.getInstance();
        reference=firebaseDatabase.getReference();
        firebaseAuth = FirebaseAuth.getInstance();
            FirebaseUser currentUser = firebaseAuth.getCurrentUser();
            if(currentUser != null){
                Intent intent = new Intent(LogInScreen.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
    }
    public void DoctorLogIn(View view){
    Intent intent = new Intent(LogInScreen.this,DoctorLogInActivity.class);
    startActivity(intent);


    }
    public void SignIn(View view){
        String email=emailTextView.getText().toString();
        String password=passwordTextView.getText().toString();
        firebaseAuth.signInWithEmailAndPassword(email,password).addOnSuccessListener(new OnSuccessListener<AuthResult>() {//email ve şifre ile kullanıcı oluşturuldu
            @Override
            public void onSuccess(AuthResult authResult) {// başarılı olursa
                Intent intent = new Intent(LogInScreen.this, MainActivity.class);
                startActivity(intent);
                finish();// intentten çıkış yapmayı sağlıyor
            }
        }).addOnFailureListener(new OnFailureListener() {// başarısız olursa
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(LogInScreen.this,e.getLocalizedMessage().toString(),Toast.LENGTH_LONG).show();
            }
        });
    }
    public void SignUp(View view){
        Intent intent = new Intent(LogInScreen.this, SignUp.class);
        startActivity(intent);
    }
}

