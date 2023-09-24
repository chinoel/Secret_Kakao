package com.devhino.secret_kakao;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class ChattingAdapter extends RecyclerView.Adapter<ChattingAdapter.CustomViewHolder> {

    private ArrayList<inChatData> arrayList;

    public ChattingAdapter(ArrayList<inChatData> arrayList) {
        this.arrayList = arrayList;
    }

    @NonNull
    @Override
    public ChattingAdapter.CustomViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.inchat, parent, false);
        ChattingAdapter.CustomViewHolder holder = new ChattingAdapter.CustomViewHolder(view);

        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ChattingAdapter.CustomViewHolder holder, int position) {
        holder.player_name.setText(arrayList.get(position).getName());
        holder.player_text.setText(arrayList.get(position).getText());
        holder.inchat_times.setText(arrayList.get(position).getTimes());
    }

    @Override
    public int getItemCount() {
        return (null != arrayList ? arrayList.size() : 0);
    }

    public class CustomViewHolder extends RecyclerView.ViewHolder {

        protected ImageView iv_profile;
        protected TextView player_name;
        protected TextView player_text;

        protected TextView inchat_times;


        public CustomViewHolder(@NonNull View itemView) {
            super(itemView);

            this.iv_profile = itemView.findViewById(R.id.inchat_usericon);
            this.player_name = itemView.findViewById(R.id.inchat_username);
            this.player_text = itemView.findViewById(R.id.inchat_usertext);
            this.inchat_times = itemView.findViewById(R.id.inchat_times);
        }
    }
}
