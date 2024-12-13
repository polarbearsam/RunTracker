package com.example.runtracker;

import android.app.Notification;
import android.content.Context;
import android.content.pm.PackageManager;
import android.Manifest;
import android.util.Log;

import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

public class NotificationHandler {
    private String textTitle = "Timer Finished!";
    private String textContent = "You've finished your run!";
    private Context context;

    public NotificationHandler(Context context) {
        this.context = context;
    }


    public Notification createNotification() {
        return new NotificationCompat.Builder(context, "my_channel_id")
                .setSmallIcon(R.drawable.timer_icon)
                .setContentTitle(textTitle)
                .setContentText(textContent)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .build();
    }

    public void sendNotification(Notification notification) {
        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(context);
        if (ActivityCompat.checkSelfPermission(context,
        Manifest.permission.POST_NOTIFICATIONS) != PackageManager.PERMISSION_GRANTED) {
            Log.w("NotificationHandler", "Notification permission not granted!");
        } else {
            Log.d("NotificationHandler", "Sending notification!");
            notificationManager.notify(1, notification);
            Log.d("NotificationHandler", "Notification sent!");
        }
    }

    public String getCHANNEL_ID() {
        return "my_channel_id";
    }

}
