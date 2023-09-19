package com.devhino.secret_kakao;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class InChatting extends AppCompatActivity {
    ArrayList<MainData> arrayList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_in_chatting);

        SharedPreferences sharedPreferences = getSharedPreferences("userdata", Context.MODE_PRIVATE);

        String json = sharedPreferences.getString("datafile", "");

        Gson gson = new Gson();
        Type type = new TypeToken<ArrayList<MainData>>() {}.getType();
        arrayList = gson.fromJson(json, type);
    }
}