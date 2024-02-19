package com.example.alarmmaneger;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;

import android.app.AlarmManager;
import android.app.Notification;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.SystemClock;
import android.view.View;
import android.widget.Button;
import android.widget.TimePicker;

import java.util.Calendar;

public class MainActivity extends AppCompatActivity {
    Button time_btn;
    TimePickerDialog.OnTimeSetListener onTimeSetListener;
    private final int ALARM_REQUEST_CODE = 0;
    Calendar calSet;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        time_btn = findViewById(R.id.time_button);
    }

    public void setAlarm(){
        AlarmManager alarmMgr;
        PendingIntent alarmIntent;
        Intent intent = new Intent(this, AlarmReceiver.class);
        alarmIntent = PendingIntent.getBroadcast(this,
                ALARM_REQUEST_CODE, intent, PendingIntent.FLAG_IMMUTABLE);
        alarmMgr = (AlarmManager)this.getSystemService(Context.ALARM_SERVICE);
        if (calSet != null){
            alarmMgr.set(AlarmManager.RTC_WAKEUP, calSet.getTimeInMillis(), alarmIntent);
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
}