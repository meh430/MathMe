package com.example.mathme;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.fragment.app.DialogFragment;

import android.app.AlarmManager;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Calendar;

public class SettingActivity extends AppCompatActivity {
    //data to handle notification
    private NotificationManager mNotificationManager;
    private static final int NOTIFICATION_ID = 0;
    private static final String PRIMARY_CHANNEL_ID = "primary_notification_channel";

    //handle button click animations
    private AlphaAnimation buttonClick = new AlphaAnimation(1F, 0.4F);
    //member variable to get stored preferences
    private SharedPreferences mPreferences;
    //switch objects
    Switch darkSwitch, notifSwitch;
    //textView to show the time set for notifications
    TextView notificationStatus;
    private boolean notificationSet, isDark;
    private String strNotifTime;
    private AlarmManager alarmManager;
    private PendingIntent notifyPendingIntent;
    private int bootHours, bootMin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);

        //instantiate view objects
        darkSwitch = findViewById(R.id.dark_theme_switch);
        notifSwitch = findViewById(R.id.notification_switch);
        notificationStatus = findViewById(R.id.notification_status);

        //get previously saved values for dark mode flag and notification set flag
        mPreferences = getSharedPreferences(MainActivity.SharedPrefFile, MODE_PRIVATE);
        isDark = mPreferences.getBoolean(MainActivity.DARK, false);
        notificationSet = mPreferences.getBoolean(MainActivity.NOTIF, false);

        if (isDark) {
            darkSwitch.setChecked(true);
        } else {
            darkSwitch.setChecked(false);
        }

        if (notificationSet) {
            notifSwitch.setChecked(true);
            strNotifTime = mPreferences.getString(MainActivity.NOTIF_TIME, "");
            notificationStatus.setVisibility(View.VISIBLE);
            notificationStatus.setText(strNotifTime);
        } else {
            notifSwitch.setChecked(false);
            notificationStatus.setVisibility(View.INVISIBLE);
            if (alarmManager != null) {
                alarmManager.cancel(notifyPendingIntent);
            }
            if (mNotificationManager != null) {
                mNotificationManager.cancelAll();
            }
        }

        //dark switch change listener
        darkSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                if (isChecked) {
                    isDark = true;
                    MainActivity.firstDark = true;
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
                    getDelegate().applyDayNight();
                } else {
                    isDark = false;
                    MainActivity.firstDark = false;
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
                    getDelegate().applyDayNight();
                }
            }
        });

        //notification switch change listener
        notifSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                if (isChecked) {
                    //create time picker fragment
                    DialogFragment newFragment = new TimePickerFragment();
                    newFragment.show(getSupportFragmentManager(), "Pick Time");
                    notificationSet = true;
                } else {
                    notificationSet = false;
                    if (alarmManager != null) {
                        alarmManager.cancel(notifyPendingIntent);
                    }

                    if (mNotificationManager != null) {
                        mNotificationManager.cancelAll();
                    }
                    notificationStatus.setText("");
                }
            }
        });

        createNotificationChannel();
    }

    //reset all preferences and variables
    public void onReset(View view) {
        if (alarmManager != null) {
            alarmManager.cancel(notifyPendingIntent);
        }

        if (mNotificationManager != null) {
            mNotificationManager.cancelAll();
        }

        view.startAnimation(buttonClick);
        darkSwitch.setChecked(false);
        notifSwitch.setChecked(false);
        notificationStatus.setText("");
        strNotifTime = "";
        notificationSet = false;
        isDark = false;
        MainActivity.firstDark = false;
        //change high score later

        SharedPreferences.Editor preferencesEditor = mPreferences.edit();
        preferencesEditor.clear();
        preferencesEditor.apply();

        Toast.makeText(this, "Reset preferences", Toast.LENGTH_SHORT).show();
    }

    //save all of the preferences
    @Override
    public void onPause() {
        super.onPause();
        SharedPreferences.Editor preferenceEditor = mPreferences.edit();
        preferenceEditor.putInt(MainActivity.HOUR, bootHours);
        preferenceEditor.putInt(MainActivity.MINUTE, bootMin);
        preferenceEditor.putBoolean(MainActivity.DARK, isDark);
        preferenceEditor.putBoolean(MainActivity.NOTIF, notificationSet);
        preferenceEditor.putString(MainActivity.NOTIF_TIME, strNotifTime);
        preferenceEditor.apply();
    }

    //called from time picker, sends a repeating broadcast
    public void processTimePicked(int hour, int minute) {
        //store int values in case the device is rebooted
        bootHours = hour;
        bootMin = minute;

        String strHour = Integer.toString(hour);
        String strMinute = Integer.toString(minute);

        strNotifTime = getString(R.string.notification_set_for) + " " + (strHour + ":" + strMinute);

        notificationStatus.setVisibility(View.VISIBLE);
        notificationStatus.setText(strNotifTime);


        alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
        mNotificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        //new intent pointing to receiver class
        Intent notifyIntent = new Intent(this, AlarmReceiver.class);
        //pending intent that wraps the intent
        notifyPendingIntent = PendingIntent.getBroadcast(this, NOTIFICATION_ID, notifyIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        //get the hour and minutes from Calender class
        Calendar c = Calendar.getInstance();
        c.setTimeInMillis(System.currentTimeMillis());
        c.set(Calendar.HOUR_OF_DAY, hour);
        c.set(Calendar.MINUTE, minute);
        //set a repeating alarm that uses real-time clock and repeats at specified time(hopefully) at intervals of one day
        //goes to onReceive in AlarmReceiver if alarm was broadcasted
        alarmManager.setInexactRepeating(AlarmManager.RTC_WAKEUP, c.getTimeInMillis(), AlarmManager.INTERVAL_DAY, notifyPendingIntent);
    }

    //create a separate notification channel for oreo and above, assign it to the notification manager member variable
    public void createNotificationChannel() {

        // Create a notification manager object.
        mNotificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);

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

    //called if the user chooses to exit out of the time picker dialog
    public void timeDialogCanceled() {
        notificationStatus.setVisibility(View.INVISIBLE);
        notifSwitch.setChecked(false);
        notificationSet = false;
        if (alarmManager != null) {
            alarmManager.cancel(notifyPendingIntent);
        }

        if (mNotificationManager != null) {
            mNotificationManager.cancelAll();
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}
