package com.rudrakaniya.notificationdemo.reminder;

import android.app.IntentService;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TaskStackBuilder;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.core.app.JobIntentService;
import androidx.core.app.NotificationCompat;

import com.rudrakaniya.notificationdemo.R;

import static android.app.NotificationManager.IMPORTANCE_DEFAULT;

public class ReminderAlarmService extends JobIntentService {

    private static final String TAG = ReminderAlarmService.class.getSimpleName();
    private static Context contextApp;
    private static final int NOTIFICATION_ID = 42;
    private static final String CHANNEL_ID = "com.rudrakaniya.notificationDemo.channelId";

    //This is a deep link intent, and needs the task stack
    public static PendingIntent getReminderPendingIntent(Context context) {
        Intent action = new Intent(context, ReminderAlarmService.class);
        contextApp = context;
        return PendingIntent.getService(context, 0, action, PendingIntent.FLAG_UPDATE_CURRENT);
    }

    public ReminderAlarmService() {
        super();
    }

    @Override
    protected void onHandleWork(@NonNull Intent intent) {
        Uri uri = Uri.parse("https://meet.google.com/hzt-jnbt-jkw");
        Intent linkIntent = new Intent(Intent.ACTION_VIEW, uri);

        Log.d("TAG", "onHandleWork: " + "WE are here!!!!");

//        Intent notificationIntent = new Intent(context, NotificationActivity.class);

        TaskStackBuilder stackBuilder = TaskStackBuilder.create(contextApp);
//        stackBuilder.addParentStack(NotificationActivity.class);
        stackBuilder.addNextIntent(linkIntent);

        PendingIntent pendingIntent = stackBuilder.getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT);

        Notification.Builder builder = new Notification.Builder(contextApp,CHANNEL_ID);

        Notification notification = builder.setContentTitle("Demo App Notification")
                .setContentText("New Notification From Demo App..")
                .setTicker("New Message Alert!")
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentIntent(pendingIntent).build();

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            builder.setChannelId(CHANNEL_ID);
        }

        NotificationManager notificationManager = (NotificationManager) contextApp.getSystemService(Context.NOTIFICATION_SERVICE);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(
                    CHANNEL_ID,
                    "NotificationDemo",
                    IMPORTANCE_DEFAULT
            );
            notificationManager.createNotificationChannel(channel);
        }

        notificationManager.notify(0, notification);
    }
}
