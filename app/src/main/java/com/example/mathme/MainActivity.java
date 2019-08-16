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
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    public static final String SharedPrefFile = "com.example.mathme";
    //constant values for sharedPref keys
    public static final String DARK = "isDark", NOTIF = "notificationSet", NOTIF_TIME = "notificationTime", HOUR = "hour", MINUTE = "minute", HIGH = "highScore";
    private static int highScore;
    //force dark mode if set by user on first session
    public static boolean firstDark = false;
    //textview to store display the higscore
    TextView highScoreTv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        highScoreTv = findViewById(R.id.main_high_score);

        //get any variables from previous sessions
        SharedPreferences mPreferences = getSharedPreferences(SharedPrefFile, MODE_PRIVATE);
        boolean isDark = mPreferences.getBoolean(DARK, firstDark);
        highScore = mPreferences.getInt(HIGH, 0);

        //set theme
        if (isDark) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
            getDelegate().applyDayNight();
        } else {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
            getDelegate().applyDayNight();
        }

        String strHighScore = "HighScore: " + getHighScore();
        highScoreTv.setText(strHighScore);
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
                String[] urlArray = {"http://eelslap.com/", "https://en.wikipedia.org/wiki/Donald_Trump",
                        "https://myanimelist.net/anime/10087/Fate_Zero?q=fate%20z",
                        "https://en.wikipedia.org/wiki/The_Office_(American_TV_series)",
                        "https://www.youtube.com/watch?v=3M_5oYU-IsU",
                        "https://en.wikipedia.org/wiki/Red_Dead_Redemption_2",
                        "https://en.wikipedia.org/wiki/The_Empire_Strikes_Back",
                        "https://www.youtube.com/watch?v=cPJUBQd-PNM",
                        "https://www.youtube.com/watch?v=iw2FAJcQbWM",
                        "https://www.youtube.com/watch?v=-yvMqox7c6A",
                        "https://www.youtube.com/watch?v=caM2hEV69ac",
                        "https://www.youtube.com/watch?v=jsRchR-jrf4"};
                int randomUrl = (int) (Math.random() * urlArray.length);
                Uri contact = Uri.parse(urlArray[randomUrl]);
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

    //start the test settings activity
    public void onTestModeClicked(View view) {
        Intent launchTest = new Intent(MainActivity.this, TestModeSettings.class);
        startActivity(launchTest);
    }

    //start the time settings activity
    public void onTimedModeClicked(View view) {
        Intent launchTime = new Intent(MainActivity.this, TimeModeSettings.class);
        startActivity(launchTime);
    }

    //setter for private variable highScore
    public static void setHighScore(int hS) {
        highScore = hS;
    }

    //getter for private variable highScore
    public static int getHighScore() {
        return highScore;
    }
}
