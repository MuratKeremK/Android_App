package com.example.hastaneuygulamasi;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.List;

public class MessageDoctorAdapter extends RecyclerView.Adapter<MessageDoctorAdapter.RowHolder> {

    List<String> doctorNameList;
    Context context;
    Activity activity;
    private DatabaseReference reference;
    private  FirebaseDatabase firebaseDatabase;
    FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
    Intent intent;
    String email="";

    public MessageDoctorAdapter(List<String> doctorNameList, Context context, Activity activity) {
        this.doctorNameList = doctorNameList;
        this.context = context;
        this.activity = activity;
        firebaseDatabase = FirebaseDatabase.getInstance();
        reference = firebaseDatabase.getReference();
        intent=activity.getIntent();
        if(firebaseUser!=null){
            email= firebaseUser.getEmail();
        }
    }


    @NonNull
    @Override
    public MessageDoctorAdapter.RowHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.recycler_row, parent, false);
        return new RowHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MessageDoctorAdapter.RowHolder holder, int position) {
        holder.doctorName.setText(doctorNameList.get(position).toString());
        holder.userAnaLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String userName = "";
                final String username;
                if(!email.equals("")){
                    for (char a:email.toCharArray()){
                        if (a!='@'){
                            userName+=a;
                        }else{
                            break;
                        }
                    }
                     username= userName;
                }
                else{
                    username=intent.getStringExtra("doctorname");
                }


                Intent intent=new Intent(activity, ChatActivity.class);
                intent.putExtra("username",username);
                intent.putExtra("othername",doctorNameList.get(position).toString());
                activity.startActivity(intent);

            }
        });
    }
    @Override
    public int getItemCount() {
        return doctorNameList.size();
    }

    public class RowHolder extends RecyclerView.ViewHolder{
        TextView doctorName;
        LinearLayout userAnaLayout;
        public RowHolder(View itemView) {
            super(itemView);
            doctorName=itemView.findViewById(R.id.doctorNameTextView);
            userAnaLayout=itemView.findViewById(R.id.ana_layout);
        }
    }
}
