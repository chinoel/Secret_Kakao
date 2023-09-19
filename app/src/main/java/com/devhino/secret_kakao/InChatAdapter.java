package com.devhino.secret_kakao;

import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class InChatAdapter extends RecyclerView.Adapter<InChatAdapter.CustomViewHolder> {

    private ArrayList<MainData> arrayList;

    public InChatAdapter(ArrayList<MainData> arrayList) {
        this.arrayList = arrayList;
    }

    @NonNull
    @Override
    public InChatAdapter.CustomViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull InChatAdapter.CustomViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public class CustomViewHolder extends RecyclerView.ViewHolder {
        public CustomViewHolder(@NonNull View itemView) {
            super(itemView);

        }
    }
}
