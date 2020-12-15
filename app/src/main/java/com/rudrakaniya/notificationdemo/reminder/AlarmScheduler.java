package com.rudrakaniya.notificationdemo.reminder;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.net.Uri;
import android.util.Log;

public class AlarmScheduler {

    /**
     * Schedule a reminder alarm at the specified time for the given task.
     *
     * @param context Local application or activity context
     * @param alarmTime Alarm start time
     */
    public void scheduleAlarm(Context context, long alarmTime) {
        //Schedule the alarm. Will update an existing item for the same task.
        AlarmManager manager = AlarmManagerProvider.getAlarmManager(context);

        PendingIntent operation =
                ReminderAlarmService.getReminderPendingIntent(context);
        Log.d("TAG", "onHandleWork: " + "WE are here!!!!    inside alarmScheduler");

        manager.setExact(AlarmManager.RTC, alarmTime, operation);
    }
}
