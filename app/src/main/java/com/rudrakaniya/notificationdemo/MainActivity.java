package com.rudrakaniya.notificationdemo;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlarmManager;
import android.app.DatePickerDialog;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.icu.util.Calendar;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.sql.Date;
import java.time.LocalDateTime;

public class MainActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener, TimePickerDialog.OnTimeSetListener {

    Button mButton;

    int day, month, year, hour, minute;
    int myday, myMonth, myYear, myHour, myMinute;
        
    TextView mTextView;
    private static final String TAG = "MainActivity";
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mButton = findViewById(R.id.button);
        mTextView = findViewById(R.id.textView);





        Calendar calendar = Calendar.getInstance();

        myday = calendar.get(Calendar.DAY_OF_MONTH);
        myMonth = calendar.get(Calendar.MONTH) + 1;
        myYear = calendar.get(Calendar.YEAR);

        myHour = calendar.get(Calendar.HOUR);
        myMinute = calendar.get(Calendar.MINUTE);


        mTextView.setText(myHour + ":" + myMinute + "  " + myday + "/" + myMonth + "/" + myYear);
        Log.d(TAG, "onTimeSet: From Intent " + "    Year: " + myYear + "  " +
                "Month: " + myMonth + "  " +
                "Day: " + myday + "  " +
                "Hour: " + myHour + "  " +
                "Minute: " + myMinute);


        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar calendar = Calendar.getInstance();
//                year = calendar.get(Calendar.YEAR);
//                month = calendar.get(Calendar.MONTH);
//                day = calendar.get(Calendar.DAY_OF_MONTH);
                DatePickerDialog datePickerDialog = new DatePickerDialog(MainActivity.this, (DatePickerDialog.OnDateSetListener) MainActivity.this, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
                datePickerDialog.show();
            }
        });

    }

    @Override
    public void onDateSet(DatePicker view, int yearC, int monthOfYear, int dayOfMonth) {
        myYear = yearC;
        myday = dayOfMonth;
        myMonth = monthOfYear + 1;
        Calendar c = Calendar.getInstance();
        hour = c.get(Calendar.HOUR);
        minute = c.get(Calendar.MINUTE);
        TimePickerDialog timePickerDialog = new TimePickerDialog(MainActivity.this, (TimePickerDialog.OnTimeSetListener) MainActivity.this, hour, minute, DateFormat.is24HourFormat(this));
        timePickerDialog.show();

        Log.d("TAG 1", "onTimeSet: " + "    Year: " + myYear + "  " +
                "Month: " + myMonth + "  " +
                "Day: " + myday + "  " +
                "Hour: " + myHour + "  " +
                "Minute: " + myMinute);
    }

    @Override
    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
        myHour = hourOfDay;
        myMinute = minute;
        mTextView.setText(myHour + ":" + myMinute + "  " + myday + "/" + myMonth + "/" + myYear);

        Log.d("TAG", "onTimeSet: " + "    Year: " + myYear + "  " +
                "Month: " + myMonth + "  " +
                "Day: " + myday + "  " +
                "Hour: " + myHour + "  " +
                "Minute: " + myMinute);


        inTime();
    }

    private void inTime(){
        Calendar calendar = Calendar.getInstance();
        calendar.clear();
        calendar.set(myYear, myMonth-1, myday, myHour, myMinute-2, 1);

        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);

        Intent notificationIntent = new Intent(this, AlarmReceiver.class);
        notificationIntent.putExtra("text", "7:42 zoom meeting is about to start in 2 minuts");
        notificationIntent.putExtra("link", "https://us04web.zoom.us/j/79068474728?pwd=QkRtd3lrNE5Da3JSdm1SNXdMU0h5Zz09");

        PendingIntent broadcast = PendingIntent.getBroadcast(this, (int) System.currentTimeMillis(), notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT);

//        Calendar cal = Calendar.getInstance();
//        cal.add(Calendar.SECOND, 5);
//        cal.set(Calendar.DATE, 17);
//        cal.set(Calendar.MONTH, 12);
//        cal.set(Calendar.YEAR, 2020);
//        cal.set(Calendar.HOUR, 12);
//        cal.set(Calendar.MINUTE, 29);
//        alarmManager.setExact(AlarmManager.RTC_WAKEUP, cal.getTimeInMillis(), broadcast);
        alarmManager.setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), broadcast);

        Toast.makeText(MainActivity.this, "Alarm set!!", Toast.LENGTH_SHORT).show();

        Log.d(TAG, "inTime: Alarm set!! with id  =  " +  (int) System.currentTimeMillis());


//        https://android.jlelse.eu/using-alarmmanager-like-a-pro-20f89f4ca720

        AlarmManager alarmManager2 = (AlarmManager) getSystemService(Context.ALARM_SERVICE);

        calendar.set(myYear, myMonth-1, myday, myHour, myMinute, 1);

        Intent notificationIntent2nd = new Intent(this, AlarmReceiver.class);
        notificationIntent2nd.putExtra("text", "7:43 zoom meeting started");
        notificationIntent2nd.putExtra("link", "https://us04web.zoom.us/j/79068474728?pwd=QkRtd3lrNE5Da3JSdm1SNXdMU0h5Zz09");

        PendingIntent broadcast2 = PendingIntent.getBroadcast(this, (int) System.currentTimeMillis(), notificationIntent2nd, PendingIntent.FLAG_UPDATE_CURRENT);

//        Calendar cal = Calendar.getInstance();
//        cal.add(Calendar.SECOND, 5);
//        cal.set(Calendar.DATE, 17);
//        cal.set(Calendar.MONTH, 12);
//        cal.set(Calendar.YEAR, 2020);
//        cal.set(Calendar.HOUR, 12);
//        cal.set(Calendar.MINUTE, 29);
//        alarmManager.setExact(AlarmManager.RTC_WAKEUP, cal.getTimeInMillis(), broadcast);
        alarmManager2.setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), broadcast2);

        Toast.makeText(MainActivity.this, "Alarm set!!", Toast.LENGTH_SHORT).show();

        Log.d(TAG, "inTime: Alarm set!! with id  =  " +  (int) System.currentTimeMillis());

    }
}