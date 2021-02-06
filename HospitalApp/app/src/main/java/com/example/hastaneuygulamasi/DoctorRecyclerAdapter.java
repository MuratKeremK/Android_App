package com.example.hastaneuygulamasi;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
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

public class DoctorRecyclerAdapter extends RecyclerView.Adapter<DoctorRecyclerAdapter.RowHolder>{
    List<String> appointmentList,dateList,hourList,patientNameList;
    Context context;
    Activity activity;
    private FirebaseDatabase firebaseDatabase;
    DatabaseReference reference;


    public DoctorRecyclerAdapter(List<String> appointmentList, List<String> dateList, List<String> hourList, List<String> patientNameList, Context context, Activity activity) {
        this.appointmentList = appointmentList;
        this.context = context;
        this.activity = activity;
        this.dateList=dateList;
        this.hourList=hourList;
        this.patientNameList=patientNameList;
        firebaseDatabase = FirebaseDatabase.getInstance();
        reference = firebaseDatabase.getReference();
    }


    @NonNull
    @Override
    public DoctorRecyclerAdapter.RowHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.doctor_appointments_row, parent, false);
        return new DoctorRecyclerAdapter.RowHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DoctorRecyclerAdapter.RowHolder holder, int position) {
        holder.patientName.setText(appointmentList.get(position).toString());

    }
    @Override
    public int getItemCount() {
        return appointmentList.size();
    }

    public class RowHolder extends RecyclerView.ViewHolder{
        TextView patientName;

        public RowHolder(View itemView) {
            super(itemView);
            patientName=itemView.findViewById(R.id.myappointmentsTextView);

        }
    }
}
