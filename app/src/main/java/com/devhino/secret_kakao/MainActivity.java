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
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private ArrayList<MainData> arrayList;
    private MainAdapter adapter;
    private RecyclerView recyclerView;
    private LinearLayoutManager linearLayoutManager;

    private Button btn;

    private static final int NOTIFICATION_LISTENER_SETTINGS_REQUEST_CODE = 123;

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

        ArrayList<MainData> ms;
        String json = sharedPreferences.getString("datafile", "");

        Gson gson = new Gson();
        Type type = new TypeToken<ArrayList<MainData>>() {}.getType();
        ms = gson.fromJson(json, type);

        arrayList.clear();
        if (ms != null) {
            if (ms.size() > 0) {
                for (int i = 0; i < ms.size(); i++) {
                    MainData m = new MainData(R.drawable.ic_launcher_foreground,
                            ms.get(i).getPl_name(), ms.get(i).getPl_text(),
                            ms.get(i).getPl_id(), ms.get(i).getList()
                    );
                    arrayList.add(m);
                }
            }
            adapter.notifyDataSetChanged();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        recyclerView = findViewById(R.id.chatting);
        linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);

        arrayList = new ArrayList<>();
        adapter = new MainAdapter(arrayList, this);
        recyclerView.setAdapter(adapter);

        btn = findViewById(R.id.btn_0);
        btn.setOnClickListener(v -> {
            check();
        });

        checkSysyem();
    }

    public void check() {
        Intent intent = new Intent(Settings.ACTION_NOTIFICATION_LISTENER_SETTINGS);

        startActivityForResult(intent, NOTIFICATION_LISTENER_SETTINGS_REQUEST_CODE);
    }

    public void checkSysyem() {
        ComponentName cn = new ComponentName(this, ShowNotificationListenerService.class);
        String flat = Settings.Secure.getString(this.getContentResolver(), "enabled_notification_listeners");
        final boolean enabled = flat != null && flat.contains(cn.flattenToString());

        if (!enabled) {
            // 권한이 거부된 경우 알림
            btn.setVisibility(View.VISIBLE);
            Toast.makeText(this, "앱 사용을 위해 권한을 활성화 해주세요", Toast.LENGTH_SHORT).show();

        } else {
            // 권한이 승인된 경우 NotificationListenerService 시작
            startService(new Intent(this, ShowNotificationListenerService.class));

            btn.setVisibility(View.GONE);

            IntentFilter filter = new IntentFilter("my_notification");
            registerReceiver(notificationReceiver, filter);
            data_load();
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