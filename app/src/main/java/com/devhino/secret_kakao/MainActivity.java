package com.devhino.secret_kakao;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.widget.Button;
import android.widget.Toast;

import com.google.gson.Gson;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private ArrayList<MainData> arrayList;
    private MainAdapter adapter;
    private RecyclerView recyclerView;
    private LinearLayoutManager linearLayoutManager;

    String name;
    String msg;
    String user_data;
    private ArrayList<String> list;

    String lastname = "";
    String lastmsg = "";

    private static final int NOTIFICATION_LISTENER_SETTINGS_REQUEST_CODE = 123;

    private BroadcastReceiver notificationReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if ("my_notification".equals(intent.getAction())) {
                name = intent.getStringExtra("title");
                msg = intent.getStringExtra("text");
                user_data = intent.getStringExtra("user_data");


                if (lastname.equals(name) && lastmsg.equals(msg)) {

                } else {
                    int abs = -1;
                    for (int i = 0; i < arrayList.size(); i++) {
                        if (user_data.equals(arrayList.get(i).getPl_id())) {
                            abs = i + 0;
                            break;
                        }
                    }

                    lastname = name;
                    lastmsg = msg;
                    if (abs == -1) {
                        // 데이터가 없으므로 추가
                        MainData mainData = new MainData(R.drawable.ic_launcher_foreground, name, msg, user_data, new ArrayList<>());
                        mainData.getList().add(msg);
                        arrayList.add(0, mainData);
                        adapter.notifyDataSetChanged();
                    } else {
                        arrayList.get(abs).setPl_name(name);
                        arrayList.get(abs).setPl_text(msg);
                        arrayList.get(abs).getList().add(msg);
                        adapter.notifyDataSetChanged();
                        // Toast.makeText(context, arrayList.get(abs).getList().get(0), Toast.LENGTH_SHORT).show();

                    }
                    Intent i = new Intent("execute_pending_intent");
                    sendBroadcast(i);


                    // Save File
                    SharedPreferences sharedPreferences = getSharedPreferences("userdata", Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    Gson gson = new Gson();
                    String s = gson.toJson(arrayList);
                    editor.putString("datafile", s);
                    editor.apply();
                }
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        recyclerView = findViewById(R.id.chatting);
        linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);

        arrayList = new ArrayList<>();
        adapter = new MainAdapter(arrayList);
        recyclerView.setAdapter(adapter);

        checkSysyem();
    }

    public void checkSysyem() {
        ComponentName cn = new ComponentName(this, ShowNotificationListenerService.class);
        String flat = Settings.Secure.getString(this.getContentResolver(), "enabled_notification_listeners");
        final boolean enabled = flat != null && flat.contains(cn.flattenToString());

        if (!enabled) {
            // 권한이 거부된 경우 설정 화면으로 이동

            Intent intent = new Intent(Settings.ACTION_NOTIFICATION_LISTENER_SETTINGS);

            startActivityForResult(intent, NOTIFICATION_LISTENER_SETTINGS_REQUEST_CODE);

        } else {
            // 권한이 승인된 경우 NotificationListenerService 시작
            startService(new Intent(this, ShowNotificationListenerService.class));

            IntentFilter filter = new IntentFilter("my_notification");
            registerReceiver(notificationReceiver, filter);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == NOTIFICATION_LISTENER_SETTINGS_REQUEST_CODE) {
            checkSysyem();
        }
    }
}