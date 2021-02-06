package com.example.hastaneuygulamasi;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.List;

public class AppointmentAdapter extends RecyclerView.Adapter<AppointmentAdapter.RowHolder>{
    List<String> appointmentList,dateList,hourList,doctorNameList;
    Context context;
    Activity activity;
    private FirebaseDatabase firebaseDatabase;
    DatabaseReference reference;
    FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
    String email = firebaseUser.getEmail();
    String username="";
    public AppointmentAdapter(List<String> appointmentList,List<String> dateList,List<String> hourList,List<String> doctorNameList, Context context, Activity activity) {
        this.appointmentList = appointmentList;
        this.context = context;
        this.activity = activity;
        this.dateList=dateList;
        this.hourList=hourList;
        this.doctorNameList=doctorNameList;
        firebaseDatabase = FirebaseDatabase.getInstance();
        reference = firebaseDatabase.getReference();
    }


    @NonNull
    @Override
    public AppointmentAdapter.RowHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.appointment_frame_layout, parent, false);
        return new AppointmentAdapter.RowHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AppointmentAdapter.RowHolder holder, int position) {
        holder.doctorName.setText(appointmentList.get(position).toString());
        holder.cancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for(char c:email.toCharArray()){
                    if(c!='@'){

                        username+=c;
                    }
                    else{
                        break;
                    }

                }

                String date = dateList.get(position);
                String hour = hourList.get(position);
                String doctor=doctorNameList.get(position);

                reference.child("doctors").child(doctor).child("Appointments").child("Date").child(date).child(hour).removeValue(new DatabaseReference.CompletionListener() {
                    @Override
                    public void onComplete(@Nullable DatabaseError error, @NonNull DatabaseReference ref) {
                        reference.child("users").child(username).child("Appointments").child("Date").child(date).child(hour).removeValue();
                        Toast.makeText(context,"Appointment removed",Toast.LENGTH_SHORT);
                    }
                });

                Intent intent=new Intent(activity, MainActivity.class);

                activity.startActivity(intent);



            }
        });
    }
    @Override
    public int getItemCount() {
        return appointmentList.size();
    }

    public class RowHolder extends RecyclerView.ViewHolder{
        TextView doctorName;
        Button cancelBtn;
        public RowHolder(View itemView) {
            super(itemView);
            doctorName=itemView.findViewById(R.id.myappointmentsTextView);
            cancelBtn=itemView.findViewById(R.id.cancelAppointmentBtn);
        }
    }
}
