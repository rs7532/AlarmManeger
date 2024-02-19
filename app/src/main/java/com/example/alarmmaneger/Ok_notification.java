package com.example.alarmmaneger;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.SharedPreferences;
import android.os.Bundle;

public class Ok_notification extends AppCompatActivity {

    @SuppressLint("ApplySharedPref")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ok_notification);


        SharedPreferences settings=getSharedPreferences("PREFS_NAME",MODE_PRIVATE);
        SharedPreferences.Editor editor=settings.edit();
        editor.putInt("counter", 0);
        editor.commit();
    }
}