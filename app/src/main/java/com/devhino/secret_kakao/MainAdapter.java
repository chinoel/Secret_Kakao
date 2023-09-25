package com.devhino.secret_kakao;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class MainAdapter extends RecyclerView.Adapter<MainAdapter.CustomViewHolder> {

    private ArrayList<MainData> arrayList;

    public MainAdapter(ArrayList<MainData> arrayList, Context context) {
        this.arrayList = arrayList;
        this.mContext = context;
    }
    Context mContext;

    @NonNull
    @Override
    public MainAdapter.CustomViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.chat_ui, parent, false);
        CustomViewHolder holder = new CustomViewHolder(view);

        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull MainAdapter.CustomViewHolder holder, int position) {
        holder.player_name.setText(arrayList.get(position).getPl_name());
        holder.player_text.setText(arrayList.get(position).getPl_text());

        holder.itemView.setTag(position);
        holder.itemView.setOnClickListener(view -> {
            Intent i = new Intent(view.getContext(), InChatting.class);
            i.putExtra("user_id", arrayList.get(position).getPl_id());
            view.getContext().startActivity(i);
        });

        holder.itemView.setOnLongClickListener(view -> {
            Intent i = new Intent("update_data");
            i.putExtra("position", position);
            mContext.sendBroadcast(i);

            return false;
        });
    }

    @Override
    public int getItemCount() {
        return (null != arrayList ? arrayList.size() : 0);
    }

    public class CustomViewHolder extends RecyclerView.ViewHolder {

        protected ImageView iv_profile;
        protected TextView player_name;
        protected TextView player_text;


        public CustomViewHolder(@NonNull View itemView) {
            super(itemView);

            this.iv_profile = itemView.findViewById(R.id.player_icon);
            this.player_name = itemView.findViewById(R.id.player_name);
            this.player_text = itemView.findViewById(R.id.player_text);
        }
    }
}
