package com.example.mathme.receivers;

import android.app.AlarmManager;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;

import com.example.mathme.other.MainActivity;

import java.util.Calendar;

import static android.content.Context.NOTIFICATION_SERVICE;

//resets alarm if device reboots
public class DeviceBootReceiver extends BroadcastReceiver {
    private AlarmManager alarmManager;
    private static final int NOTIFICATION_ID = 0;
    private static final String PRIMARY_CHANNEL_ID = "primary_notification_channel";
    private NotificationManager mNotificationManager;
    private Context mContext;

    @SuppressWarnings("ConstantConditions")
    @Override
    public void onReceive(Context context, Intent intent) {
        createNotificationChannel();
        mContext = context;
        SharedPreferences mSharedPreferences = context.getSharedPreferences(MainActivity.SharedPrefFile, Context.MODE_PRIVATE);
        int hour =  mSharedPreferences.getInt(MainActivity.HOUR, 69), minute = mSharedPreferences.getInt(MainActivity.MINUTE, 69);
        if (intent.getAction().equalsIgnoreCase("android.intent.action.BOOT_COMPLETED")) {
            if (alarmManager != null && hour == 69 && minute == 69) {
                alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
                mNotificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

                Intent notifyIntent = new Intent(context, AlarmReceiver.class);

                PendingIntent notifyPendingIntent = PendingIntent.getBroadcast(context, NOTIFICATION_ID, notifyIntent, PendingIntent.FLAG_UPDATE_CURRENT);

                Calendar c = Calendar.getInstance();
                c.setTimeInMillis(System.currentTimeMillis());
                c.set(Calendar.HOUR_OF_DAY, hour);
                c.set(Calendar.MINUTE, minute);

                alarmManager.setInexactRepeating(AlarmManager.RTC_WAKEUP, c.getTimeInMillis(), AlarmManager.INTERVAL_DAY, notifyPendingIntent);
            }
        }
    }

    //create a separate notification channel for oreo and above
    public void createNotificationChannel() {

        // Create a notification manager object.
        mNotificationManager = (NotificationManager) mContext.getSystemService(NOTIFICATION_SERVICE);

        // Notification channels are only available in OREO and higher.
        // So, add a check on SDK version.
        if (android.os.Build.VERSION.SDK_INT >=
                android.os.Build.VERSION_CODES.O) {

            // Create the NotificationChannel with all the parameters.
            NotificationChannel notificationChannel = new NotificationChannel(PRIMARY_CHANNEL_ID, "Math Reminder",
                    NotificationManager.IMPORTANCE_HIGH);

            notificationChannel.enableLights(true);
            notificationChannel.setLightColor(Color.RED);
            notificationChannel.enableVibration(true);
            notificationChannel.setDescription("Reminder to practice math");
            mNotificationManager.createNotificationChannel(notificationChannel);
        }
    }
}