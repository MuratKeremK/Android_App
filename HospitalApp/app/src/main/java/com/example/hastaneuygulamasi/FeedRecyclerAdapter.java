package com.example.hastaneuygulamasi;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class FeedRecyclerAdapter extends RecyclerView.Adapter<FeedRecyclerAdapter.RowHolder> {

    List<String> doctorNameList;
    Context context;
    Activity activity;
    Dialog epicDialog;
    Button closeButton, appointmentBtn;
    CalendarView calendarView;
    String date;

    public FeedRecyclerAdapter(List<String> doctorNameList, Context context, Activity activity) {
        this.doctorNameList = doctorNameList;
        this.context = context;
        this.activity = activity;

        epicDialog = new Dialog(context);
    }

    @NonNull
    @Override
    public FeedRecyclerAdapter.RowHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.recycler_row, parent, false);
        return new RowHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FeedRecyclerAdapter.RowHolder holder, int position) {

        holder.doctorName.setText(doctorNameList.get(position).toString());
        holder.userAnaLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                epicDialog.setContentView(R.layout.level_interface);
                closeButton = epicDialog.findViewById(R.id.closeButton);
                appointmentBtn = epicDialog.findViewById(R.id.appointmentButton);
                calendarView = epicDialog.findViewById(R.id.calendarView);
                closeButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        epicDialog.cancel();
                    }
                });
                String currentDate = new SimpleDateFormat("dd:MM:yyyy", Locale.getDefault()).format(new Date());
                date = currentDate;
                calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
                    @Override
                    public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {
                        if(month<9){

                            date = dayOfMonth + ":" +0+""+(month+1) + ":" + year ;
                        }
                        else{
                            date = dayOfMonth + ":" + (month+1) + ":" + year ;

                        }
                    }

                });
                appointmentBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(activity, AppointmentAcivity.class);
                        intent.putExtra("date", date);
                        intent.putExtra("doctorname",doctorNameList.get(position));
                        activity.startActivity(intent);
                    }
                });

                epicDialog.setCancelable(false);
                epicDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                epicDialog.show();


                /* Intent intent=new Intent(activity, ChatActivity.class);
                intent.putExtra("username",userName);
                intent.putExtra("othername",list.get(position).toString());
                activity.startActivity(intent);

                 */
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