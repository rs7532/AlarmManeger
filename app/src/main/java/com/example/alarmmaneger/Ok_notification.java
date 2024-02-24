package com.example.alarmmaneger;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.TextView;

import org.w3c.dom.Text;

public class Ok_notification extends AppCompatActivity {

    AlertDialog.Builder adb;
    static boolean flag;
    static boolean snooze_clicked;
    @SuppressLint({"ApplySharedPref", "SetTextI18n"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ok_notification);

        SharedPreferences settings=getSharedPreferences("PREFS_NAME",MODE_PRIVATE);
        SharedPreferences.Editor editor=settings.edit();


        adb = new AlertDialog.Builder(this);

        adb.setTitle("Alarm clock");

        if (settings.getInt("counter", 0) != 0){
            TextView tv = new TextView(Ok_notification.this);
            adb.setView(tv);
            tv.setText("\t\t\t\tsnooze number: "+settings.getInt("counter", 0));
        }

        adb.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                flag = true;
                finish();
            }
        });
        adb.setNeutralButton("Snooze", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                flag = false;
                editor.putInt("counter", settings.getInt("counter", 0) + 1);
                editor.commit();
                finish();
            }
        });

        AlertDialog ad = adb.create();
        ad.show();
    }
}