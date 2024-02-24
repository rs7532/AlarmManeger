package com.example.alarmmaneger;

import static com.example.alarmmaneger.Ok_notification.flag;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;

import android.annotation.SuppressLint;
import android.app.AlarmManager;
import android.app.Notification;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.SystemClock;
import android.view.View;
import android.widget.Button;
import android.widget.TimePicker;
import android.widget.Toast;

import java.util.Calendar;

public class MainActivity extends AppCompatActivity {
    Button time_btn;
    TimePickerDialog.OnTimeSetListener onTimeSetListener;
    private final int ALARM_REQUEST_CODE = 0;
    Calendar calSet;
    AlarmManager alarmMgr;
    PendingIntent alarmIntent;
    @SuppressLint("ApplySharedPref")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        time_btn = findViewById(R.id.time_button);

        SharedPreferences settings=getSharedPreferences("PREFS_NAME",MODE_PRIVATE);
        SharedPreferences.Editor editor=settings.edit();
        editor.putInt("counter", 0);
        editor.commit();
    }

    @SuppressLint({"ApplySharedPref", "ShortAlarm"})
    public void setAlarm(){
        Intent intent = new Intent(this, AlarmReceiver.class);
        alarmIntent = PendingIntent.getBroadcast(this,
                ALARM_REQUEST_CODE, intent, PendingIntent.FLAG_IMMUTABLE);
        alarmMgr = (AlarmManager)this.getSystemService(Context.ALARM_SERVICE);
        if (calSet != null){
            alarmMgr.setRepeating(AlarmManager.RTC_WAKEUP, calSet.getTimeInMillis(),
                    (1000 * 60 * 5) ,alarmIntent);
             Toast.makeText(this, "clock set successfully!", Toast.LENGTH_LONG).show();
        }
        else{
            alarmMgr.setInexactRepeating(AlarmManager.RTC_WAKEUP, SystemClock.elapsedRealtime(),
                    AlarmManager.INTERVAL_HOUR, alarmIntent);

        }
    }

    private void openTimePickerDialog() {
        Calendar calendar = Calendar.getInstance();

        TimePickerDialog timePickerDialog = new TimePickerDialog(this, onTimeSetListener,
                calendar.get(Calendar.HOUR_OF_DAY),
                calendar.get(Calendar.MINUTE), true);
        timePickerDialog.setTitle("Choose time");
        timePickerDialog.show();
    }
    public void pick_time(View view) {
        onTimeSetListener = new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {

                Calendar calNow = Calendar.getInstance();
                calSet = (Calendar) calNow.clone();

                calSet.set(Calendar.HOUR_OF_DAY, hourOfDay);
                calSet.set(Calendar.MINUTE, minute);
                calSet.set(Calendar.SECOND, 0);
                calSet.set(Calendar.MILLISECOND, 0);

                if (calSet.compareTo(calNow) <= 0) {
                    calSet.add(Calendar.DATE, 1);
                }
                setAlarm();
            }
        };
        openTimePickerDialog();
    }

    public void exit_pressed(View view) {
        finish();
    }

    public void destroy_alarmManager(){
        alarmMgr.cancel(alarmIntent);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (flag) {
            destroy_alarmManager();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (flag) {
            destroy_alarmManager();
        }
    }
}