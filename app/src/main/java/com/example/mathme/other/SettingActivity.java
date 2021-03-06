package com.example.mathme.other;

import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.fragment.app.DialogFragment;

import com.example.mathme.R;
import com.example.mathme.receivers.AlarmReceiver;

import java.util.Calendar;

public class SettingActivity extends AppCompatActivity {
    //data to handle notification
    private NotificationManager mNotificationManager;
    private static final int NOTIFICATION_ID = 0;
    private static final String PRIMARY_CHANNEL_ID = "primary_notification_channel";

    //member variable to get stored preferences
    private SharedPreferences mPreferences;
    //switch objects
    Switch darkSwitch, notifSwitch;
    //textView to show the time set for notifications
    TextView notificationStatus;

    private boolean notificationSet, isDark;
    //string that stores the time of notification
    private String strNotifTime;
    //AlarmManager object to set a repeating alarm
    private AlarmManager alarmManager;
    //pending intent to wrap the notification's intent
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

        //set switches on dark setting depending on if it is
        if (isDark) {
            darkSwitch.setChecked(true);
        } else {
            darkSwitch.setChecked(false);
        }

        //checks to see if notification is set
        if (notificationSet) {
            notifSwitch.setChecked(true);
            strNotifTime = mPreferences.getString(MainActivity.NOTIF_TIME, "");
            //sets notification time on screen
            notificationStatus.setVisibility(View.VISIBLE);
            notificationStatus.setText(strNotifTime);
        } else {
            notifSwitch.setChecked(false);
            notificationStatus.setVisibility(View.GONE);
            //cancels any notifications and alarms
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

        //assign a notification channel to the notification manager object
        createNotificationChannel();
    }

    //reset all preferences and variables
    public void onReset(View view) {

        AlertDialog.Builder confDialog = new AlertDialog.Builder(this);
        confDialog.setTitle("Are You Sure?");
        confDialog.setMessage("This will delete everything! EVERYTHING! Well, actually just info in this app.");
        confDialog.setPositiveButton("I Know", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                //cancel alarms
                if (alarmManager != null) {
                    alarmManager.cancel(notifyPendingIntent);
                }

                //cancels any pending notifications
                if (mNotificationManager != null) {
                    mNotificationManager.cancelAll();
                }

                darkSwitch.setChecked(false);
                notifSwitch.setChecked(false);
                notificationStatus.setText("");
                strNotifTime = "";
                notificationSet = false;
                isDark = false;
                MainActivity.firstDark = false;
                MainActivity.setHighScoreTime(0);
                MainActivity.setHighScoreDeath(0);
                MainActivity.setBestTime(0);
                SharedPreferences.Editor preferencesEditor = mPreferences.edit();
                preferencesEditor.clear();
                preferencesEditor.apply();
                MainActivity.mTimeViewModel.deleteAll();
                MainActivity.mTestViewModel.deleteAll();
                MainActivity.mDeathViewModel.deleteAll();

                Toast.makeText(SettingActivity.this, "Reset preferences", Toast.LENGTH_SHORT).show();
            }
        });

        confDialog.setNegativeButton("NVM", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Toast.makeText(SettingActivity.this, "Phew, dodged a bullet there", Toast.LENGTH_SHORT).show();
            }
        });

        confDialog.show();
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
