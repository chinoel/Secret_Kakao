package com.devhino.secret_kakao;

import android.app.Notification;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.service.notification.NotificationListenerService;
import android.service.notification.StatusBarNotification;
import android.util.Base64;
import android.util.Log;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

public class ShowNotificationListenerService extends NotificationListenerService {

    private ArrayList<MainData> arrayList;

    private BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if ("execute_pending_intent".equals(intent.getAction())) {
            }
            if ("execute_pending_call".equals(intent.getAction())) {

            }
        }
    };

    @Override
    public void onCreate() {
        super.onCreate();

        IntentFilter filter = new IntentFilter("execute_pending_intent");
        registerReceiver(broadcastReceiver, filter);
    }


    @Override
    public void onNotificationPosted(StatusBarNotification sbn) {
        super.onNotificationPosted(sbn);

        Notification notification = sbn.getNotification();

        // Kakao Start Data - Low_bobo
        PendingIntent pendingIntent = sbn.getNotification().contentIntent;

        String user_data = sbn.getKey().toString();
        Bundle extra = notification.extras;
        String title = extra.getString(Notification.EXTRA_TITLE);
        CharSequence text = extra.getCharSequence(Notification.EXTRA_TEXT);

        if (sbn.getPackageName().equals("com.kakao.talk") && title != null) {
            Intent i = new Intent("my_notification");
            i.putExtra("title", title);
            i.putExtra("text", text);
            i.putExtra("user_data", user_data);

            sendBroadcast(i);
        }


    }

    @Override
    public void onNotificationRemoved(StatusBarNotification sbn) {
        super.onNotificationRemoved(sbn);
    }
}