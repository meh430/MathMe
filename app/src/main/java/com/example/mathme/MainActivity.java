package com.example.mathme;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.appcompat.widget.Toolbar;

import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

public class MainActivity extends AppCompatActivity {
    //variables to get stored preferences
    private SharedPreferences mPreferences;
    public static final String SharedPrefFile = "com.example.mathme";
    //force dark mode if set by user on first session
    public static boolean firstDark = false;

    //constant values for sharedPref keys
    public static final String DARK = "isDark", NOTIF = "notificationSet", NOTIF_TIME = "notificationTime", HOUR= "hour", MINUTE = "minute";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mPreferences = getSharedPreferences(SharedPrefFile, MODE_PRIVATE);
        boolean isDark = mPreferences.getBoolean(DARK, firstDark);

        if (isDark) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
            getDelegate().applyDayNight();
        } else {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
            getDelegate().applyDayNight();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        switch (id) {
            //launch settings
            case R.id.action_settings:
                Intent launchSettings = new Intent(MainActivity.this, SettingActivity.class);
                startActivity(launchSettings);
                return true;
            //launch contact implicit intent
            case R.id.action_contact:
                String url = "http://eelslap.com/";
                Uri contact = Uri.parse(url);
                Intent launchContact = new Intent(Intent.ACTION_VIEW, contact);

                if (launchContact.resolveActivity(getPackageManager()) != null) {
                    startActivity(launchContact);
                } else {
                    Log.d("IMPLICIT_CONTACT", "No intent receivers");
                }
                return true;
            //launch help
            case R.id.action_help:
                Intent launchHelp = new Intent(MainActivity.this, HelpActivity.class);
                startActivity(launchHelp);
                return true;
            default:
                //nothing
        }
        return super.onOptionsItemSelected(item);
    }

    //start the test setting activity
    public void onTestModeClicked(View view) {
        Intent launchTest = new Intent(MainActivity.this, TestModeSettings.class);
        startActivity(launchTest);
    }

    public void onTimedModeClicked(View view) {
    }
}
