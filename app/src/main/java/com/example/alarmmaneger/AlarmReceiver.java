package com.example.alarmmaneger;

import static android.widget.Toast.LENGTH_LONG;

import static androidx.core.content.ContextCompat.getSystemService;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.widget.TextView;
import android.widget.Toast;

import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

public class AlarmReceiver extends BroadcastReceiver {

    @SuppressLint({"MissingPermission", "SetTextI18n"})
    @Override
    public void onReceive(Context context, Intent intent) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            NotificationChannel channel = new NotificationChannel("Alarm", "notification", NotificationManager.IMPORTANCE_DEFAULT);
            NotificationManager manager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
            manager.createNotificationChannel(channel);
        }

        Intent Ok_notification_intent = new Intent(context, Ok_notification.class);
        intent.putExtra("ok_clicked", true);
        PendingIntent OKpendingIntent = PendingIntent.getActivity(context,
                0, Ok_notification_intent, PendingIntent.FLAG_UPDATE_CURRENT);

        Intent Snooze_intent = new Intent(context, Snooze_notification.class);
        intent.putExtra("snooze_clicked", true);
        PendingIntent Snooze_pendingIntent = PendingIntent.getActivity(context,
                1, Snooze_intent, PendingIntent.FLAG_UPDATE_CURRENT);


        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, "Alarm")
                .setContentTitle("Alarm Manager app")
                .setSmallIcon(R.drawable.ic_notification)
                .addAction(android.R.drawable.ic_menu_close_clear_cancel, "OK", OKpendingIntent)
                .addAction(android.R.drawable.ic_dialog_info, "Snooze", Snooze_pendingIntent)
                .setAutoCancel(true);

        NotificationManagerCompat notificationManagerCompat = androidx.core.app.NotificationManagerCompat.from(context);
        notificationManagerCompat.notify(123, builder.build());


        System.out.println("sent notification");
    }
}