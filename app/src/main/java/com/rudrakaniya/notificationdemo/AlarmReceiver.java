package com.rudrakaniya.notificationdemo;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TaskStackBuilder;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.util.Log;

import androidx.core.app.NotificationCompat;

import static android.app.NotificationManager.IMPORTANCE_DEFAULT;

public class AlarmReceiver extends BroadcastReceiver {

    private static final String TAG = "AlarmReceiver";
    private static final String CHANNEL_ID = "com.rudrakaniya.notificationDemo.channelId";

    @Override
    public void onReceive(Context context, Intent intent) {
        Intent myIntent = intent;

        Uri uri = Uri.parse(myIntent.getStringExtra("link"));
        Intent linkIntent = new Intent(Intent.ACTION_VIEW, uri);

        Log.d(TAG, "onReceive: " + myIntent.getStringExtra("hero"));

//        Intent notificationIntent = new Intent(context, NotificationActivity.class);

        TaskStackBuilder stackBuilder = TaskStackBuilder.create(context);
//        stackBuilder.addParentStack(NotificationActivity.class);
        stackBuilder.addNextIntent(linkIntent);

        PendingIntent pendingIntent = stackBuilder.getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT);

        Notification.Builder builder = new Notification.Builder(context,CHANNEL_ID);
//                .setTimeoutAfter(1000000)

        Notification notification = builder.setContentTitle(myIntent.getStringExtra("text"))
                .setContentText("New Notification From Demo App..")
                .setTicker("New Message Alert!")
                .setSmallIcon(R.mipmap.ic_launcher)
                .setTimeoutAfter(1000000)
                .setContentIntent(pendingIntent).build();


        // Will show lights and make the notification disappear when the presses it
        notification.flags = Notification.FLAG_AUTO_CANCEL;

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            builder.setChannelId(CHANNEL_ID);
        }

        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(
                    CHANNEL_ID,
                    "NotificationDemo",
                    IMPORTANCE_DEFAULT
            );
            notificationManager.createNotificationChannel(channel);
        }

        notificationManager.notify((int) System.currentTimeMillis(), notification);
    }
}
