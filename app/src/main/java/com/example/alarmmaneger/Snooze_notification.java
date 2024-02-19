package com.example.alarmmaneger;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.SystemClock;
import android.widget.TextView;

public class Snooze_notification extends AppCompatActivity {
    private final int ALARM_REQUEST_CODE = 0;

    TextView tv;


    @SuppressLint({"MissingInflatedId", "ApplySharedPref", "SetTextI18n"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_snooze_notification);
        tv = findViewById(R.id.tv);

        SharedPreferences settings=getSharedPreferences("PREFS_NAME",MODE_PRIVATE);
        SharedPreferences.Editor editor=settings.edit();
        editor.putInt("counter", (settings.getInt("counter", 0)) + 1);
        editor.commit();

        tv.setText(tv.getText() + "\n Snooze act: "+settings.getInt("counter", 1)+"times");

        AlarmManager alarmMgr;
        PendingIntent alarmIntent;
        Intent intent = new Intent(this, AlarmReceiver.class);
        alarmIntent = PendingIntent.getBroadcast(this,
                ALARM_REQUEST_CODE, intent, PendingIntent.FLAG_IMMUTABLE);
        alarmMgr = (AlarmManager)this.getSystemService(Context.ALARM_SERVICE);
        alarmMgr.set(AlarmManager.RTC_WAKEUP, SystemClock.elapsedRealtime() + (1000 * 60 * 5),
                alarmIntent);
    }
}