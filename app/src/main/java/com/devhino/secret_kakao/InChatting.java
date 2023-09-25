package com.devhino.secret_kakao;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class InChatting extends AppCompatActivity {
    ArrayList<inChatData> arrayList;
    ArrayList<MainData> ms;
    private String userId;
    TextView username;


    private ChattingAdapter adapter;
    private RecyclerView recyclerView;
    private LinearLayoutManager linearLayoutManager;


    private BroadcastReceiver notificationReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if ("my_notification".equals(intent.getAction())) {
                data_load();
            }
        }
    };

    public void data_load() {
        SharedPreferences sharedPreferences = getSharedPreferences("userdata", Context.MODE_PRIVATE);

        String json = sharedPreferences.getString("datafile", "");

        Gson gson = new Gson();
        Type type = new TypeToken<ArrayList<MainData>>() {}.getType();
        ms = gson.fromJson(json, type);

        arrayList.clear();
        for (int i = 0; i < ms.size(); i++) {
            if (userId.equals(ms.get(i).getPl_id())) {
                username.setText(ms.get(i).getPl_name());

                for (int j = 0; j < ms.get(i).getList().size(); j++) {
                        String name = ms.get(i).getList().get(j).getname();
                        String text = ms.get(i).getList().get(j).getChat();
                        String time = ms.get(i).getList().get(j).getTime();
                      inChatData chatData = new inChatData(name, text, time);
                      arrayList.add(0, chatData);
                }

                adapter.notifyDataSetChanged();
                break;
            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_in_chatting);

        username = findViewById(R.id.user_name);

        Intent i = getIntent();
        userId = i.getStringExtra("user_id");

        recyclerView = findViewById(R.id.inchat_ui);
        linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);

        arrayList = new ArrayList<>();
        adapter = new ChattingAdapter(arrayList);
        recyclerView.setAdapter(adapter);

        IntentFilter filter = new IntentFilter("my_notification");
        registerReceiver(notificationReceiver, filter);

        data_load();

        ImageView back_key = findViewById(R.id.back_key);
        back_key.setOnClickListener(view -> {
            finish();
        });
    }
}