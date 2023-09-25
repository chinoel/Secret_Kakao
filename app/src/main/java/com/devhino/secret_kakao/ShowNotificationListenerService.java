package com.devhino.secret_kakao;

import android.app.Notification;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Parcelable;
import android.service.notification.NotificationListenerService;
import android.service.notification.StatusBarNotification;
import android.util.Log;

import android.app.Notification.MessagingStyle;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Set;

public class ShowNotificationListenerService extends NotificationListenerService {

    private BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if ("update_data".equals(intent.getAction())) {
                changeCheck(intent.getIntExtra("position", -1));
            }
        }
    };

    @Override
    public void onCreate() {
        super.onCreate();

        IntentFilter filter = new IntentFilter("update_data");
        registerReceiver(broadcastReceiver, filter);
    }

    public void changeCheck(int position) {
        if (position == -1) {
        }
        else {
            SharedPreferences sharedPreferences = getSharedPreferences("userdata", Context.MODE_PRIVATE);
            ArrayList<MainData> arrayList;

            String json = sharedPreferences.getString("datafile", "");

            Gson gson = new Gson();
            Type type = new TypeToken<ArrayList<MainData>>() {}.getType();
            arrayList = gson.fromJson(json, type);

            arrayList.remove(position);

            SharedPreferences.Editor editor = sharedPreferences.edit();
            String s = gson.toJson(arrayList);
            editor.putString("datafile", s);
            editor.apply();

            Intent intent = new Intent("my_notification");
            sendBroadcast(intent);

            Toast.makeText(this, "해당 메시지가 삭제되었습니다", Toast.LENGTH_SHORT).show();
        }
    }


    @Override
    public void onNotificationPosted(StatusBarNotification sbn) {
        super.onNotificationPosted(sbn);

        SharedPreferences sharedPreferences = getSharedPreferences("userdata", Context.MODE_PRIVATE);
        ArrayList<MainData> arrayList;

        String json = sharedPreferences.getString("datafile", "");

        Gson gson = new Gson();
        Type type = new TypeToken<ArrayList<MainData>>() {}.getType();
        arrayList = gson.fromJson(json, type);

        // Data getting & Change ---------------------------------
        Notification notification = sbn.getNotification();

        // Kakao Start Data - Low_bobo
        PendingIntent pendingIntent = sbn.getNotification().contentIntent;

        String user_data = sbn.getKey().toString();
        Bundle extra = notification.extras;
        String name = extra.getString(Notification.EXTRA_TITLE);
        CharSequence text = extra.getCharSequence(Notification.EXTRA_TEXT);

        if (sbn.getPackageName().equals("com.kakao.talk") && name != null) {
            // time format
            Date currentDate = new Date();

            // 출력 형식을 정의할 SimpleDateFormat 객체를 생성합니다.
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd\nHH:mm");

            // SimpleDateFormat을 사용하여 날짜와 시간을 문자열로 변환합니다.
            String formattedDate = dateFormat.format(currentDate);
            // ------------------------------------------------ //

            String msg = text.toString();
            Intent intent = new Intent("my_notification");

            int abs = -1;
            if (arrayList == null) {

            }
            else {
                for (int i = 0; i < arrayList.size(); i++) {
                    if (user_data.equals(arrayList.get(i).getPl_id())) {
                        abs = i + 0;
                        break;
                    }
                }
            }

            if (abs == -1) {
                // 데이터가 없으므로 추가
                MainData mainData = new MainData(R.drawable.ic_launcher_foreground, name, msg, user_data, new ArrayList<>());
                mainData.getList().add(new MainChatList(name, msg, formattedDate));
                if (arrayList == null) {
                    arrayList = new ArrayList<>();
                }
                arrayList.add(0, mainData);
            } else {
                arrayList.get(abs).setPl_name(name);
                arrayList.get(abs).setPl_text(msg);
                arrayList.get(abs).getList().add(new MainChatList(name, msg, formattedDate));

                MainData move = arrayList.get(abs);
                arrayList.remove(abs);
                arrayList.add(0, move);
            }
            // Data getting & Change ---------------------------------

            SharedPreferences.Editor editor = sharedPreferences.edit();
            String s = gson.toJson(arrayList);
            editor.putString("datafile", s);
            editor.apply();

            sendBroadcast(intent);
        }


    }

    @Override
    public void onNotificationRemoved(StatusBarNotification sbn) {
        super.onNotificationRemoved(sbn);
    }
}