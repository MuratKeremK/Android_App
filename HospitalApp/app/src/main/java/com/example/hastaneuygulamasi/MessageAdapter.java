package com.example.hastaneuygulamasi;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class MessageAdapter extends RecyclerView.Adapter<MessageAdapter.ViewHolder> {
    Context context;
    List<MessageModel> messageModelList;
    Activity activity;
    String userName;
    Boolean state;
    int view_send=1;
    int view_received=2;
    public MessageAdapter(Context context, List<MessageModel>messageModelList, Activity activity, String userName) {
        this.context = context;
        this.messageModelList = messageModelList;
        this.activity = activity;
        this.userName=userName;
        state=false;
    }

    @NonNull
    @Override
    public MessageAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        if (viewType == view_send) {
            view  = LayoutInflater.from(context).inflate(R.layout.send_layout, parent, false);
            return new ViewHolder(view);
        }else{
            view  = LayoutInflater.from(context).inflate(R.layout.received_layout, parent, false);
            return new ViewHolder(view);
        }


    }

    @Override
    public void onBindViewHolder(@NonNull MessageAdapter.ViewHolder holder, final int position) {
        holder.textView.setText(messageModelList.get(position).getText().toString());

    }

    @Override
    public int getItemCount() {
        return messageModelList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView textView;

        public ViewHolder(View itemView) {
            super(itemView);
            if (state==true){
                textView=itemView.findViewById(R.id.sendTextView);
            }else{
                textView=itemView.findViewById(R.id.receivedTextView);
            }


        }
    }

    @Override
    public int getItemViewType(int position) {

        if (messageModelList.get(position).getFrom().equals(userName)) {
            state=true;
            return view_send;

        }else{
            state=false;
            return view_received;
        }
    }
}
