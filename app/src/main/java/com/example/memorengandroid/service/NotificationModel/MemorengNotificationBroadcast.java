package com.example.memorengandroid.service.NotificationModel;

import android.Manifest;
import android.app.Notification;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;

import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import com.example.memorengandroid.R;
import com.example.memorengandroid.view.Pages.NotificationActivity;

public class MemorengNotificationBroadcast extends BroadcastReceiver {
    public String message;

    public MemorengNotificationBroadcast(String message) {
        this.message = message;
    }

    public MemorengNotificationBroadcast() {}

    @Override
    public void onReceive(Context context, Intent intent) {
        try {
            Intent repeating_Intent = new Intent(context, NotificationActivity.class);
            repeating_Intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

            Bundle bundle = intent.getExtras();
            if (bundle != null && bundle.containsKey("message")) {
                message = bundle.getString("message");

                PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, repeating_Intent, PendingIntent.FLAG_UPDATE_CURRENT);
                NotificationCompat.Builder builder = new NotificationCompat.Builder(context, "Notification")
                        .setContentIntent(pendingIntent)
                        .setSmallIcon(R.drawable.logo)
                        .setLargeIcon(Bitmap.createScaledBitmap(BitmapFactory.decodeResource(context.getResources(), R.drawable.logo), 128, 128, false))
                        .setContentTitle("MEMORENG")
                        .setContentText(message)
                        .setPriority(Notification.PRIORITY_DEFAULT)
                        .setAutoCancel(true);

                NotificationManagerCompat notificationManager = NotificationManagerCompat.from(context);

                if (ActivityCompat.checkSelfPermission(context, Manifest.permission.POST_NOTIFICATIONS) != PackageManager.PERMISSION_GRANTED) {
                    Log.i("NOTIFICATION", "Permission denied");

                    notificationManager.notify(200, builder.build());
                } else {
                    Log.i("NOTIFICATION", "Permission not denied");

                    notificationManager.notify(200, builder.build());
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}