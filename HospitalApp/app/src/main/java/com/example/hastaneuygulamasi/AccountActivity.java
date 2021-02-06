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
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.inappmessaging.MessagesProto;

public class AccountActivity extends AppCompatActivity {
    Button saveBtn;
    EditText userNameText,userSurnameText,userAgeText;
    DatabaseReference reference;
    private FirebaseDatabase firebaseDatabase;
    FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
    String email = firebaseUser.getEmail();
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account);
        userNameText=findViewById(R.id.userNameTextView);
        userSurnameText=findViewById(R.id.userSurnameTextView);
        userAgeText=findViewById(R.id.userAgeTextView);
        firebaseDatabase = FirebaseDatabase.getInstance();
        reference = firebaseDatabase.getReference();
    }
    public void SaveInfo(View view){
        String userName = userNameText.getText().toString();
        String userSurname = userSurnameText.getText().toString();
        String userAge = userAgeText.getText().toString();

        String user="";
        for(char c : email.toCharArray()){

            if(c!='@'){
                user+=c;
            }
            else {
                break;
            }
        }
        final String userId=user;
        reference.child("userinfo").child("userid").setValue(userId).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    reference.child("userinfo").child(userId).child("username").setValue(userName);
                    reference.child("userinfo").child(userId).child("usersurname").setValue(userSurname);
                    reference.child("userinfo").child(userId).child("userage").setValue(userAge);
                    Toast.makeText(AccountActivity.this,"Account Updated!",Toast.LENGTH_LONG).show();

                }
            }
        });
        Intent intent = new Intent(AccountActivity.this,MainActivity.class);
        startActivity(intent);
        finish();

    }
}