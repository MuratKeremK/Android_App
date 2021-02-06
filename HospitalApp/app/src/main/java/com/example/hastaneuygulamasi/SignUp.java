package com.example.hastaneuygulamasi;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class SignUp extends AppCompatActivity {

    EditText userNameTextView, EmailText, Password;
    Button BackButton, RegisterButton;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference reference;
    String email, userID, password;
    private FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        Define();
    }
    private void Define(){
        userNameTextView=findViewById(R.id.userNameText);
        EmailText = findViewById(R.id.signUpDoctorEmailTextView);
        Password = findViewById(R.id.signUpDoctorPasswordTextView);
        BackButton = findViewById(R.id.backButton);
        RegisterButton = findViewById(R.id.registerButton);

        firebaseDatabase= FirebaseDatabase.getInstance();
        reference=firebaseDatabase.getReference();
        mAuth = FirebaseAuth.getInstance();
    }
    public void Register(View view) {
        System.out.println("kerem");
        userID=userNameTextView.getText().toString();
        email=EmailText.getText().toString();
        password = Password.getText().toString();
        String userName = "";
        for (char a:email.toCharArray()){
            if (a!='@'){
                userName+=a;
            }else{
                break;
            }
        }
        final String username = userName;
        mAuth.createUserWithEmailAndPassword(email,password).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
            @Override
            public void onSuccess(AuthResult authResult) {
                reference.child("users").child("username").setValue(username).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            System.out.println("Fatih");
                           // reference.child("users").child("citizenID").child(userID).child("email").setValue(email);
                            Toast.makeText(getApplicationContext(), "User Created", Toast.LENGTH_LONG).show();
                            Intent intent = new Intent(SignUp.this, MainActivity.class);
                            intent.putExtra("email", email);
                            startActivity(intent);
                            finish();
                        }
                    }
                });
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(SignUp.this,e.getLocalizedMessage().toString(),Toast.LENGTH_LONG).show();
            }
        });
    }
    public void Back(View view){
        Intent intent = new Intent(SignUp.this, LogInScreen.class);
        startActivity(intent);
        finish();

    }
}
