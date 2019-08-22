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
    public static final String DARK = "isDark", NOTIF = "notificationSet", NOTIF_TIME = "notificationTime", HOUR = "hour", MINUTE = "minute", HIGH_TIME = "highScoreTime", HIGH_DEATH = "highScoreDeath", BEST_TIME = "bestTime";
    private static int highScoreTime, highScoreDeath, bestTime;
    //force dark mode if set by user on first session
    public static boolean firstDark = false;
    //textview to store display the higscore
    TextView highScoreTimeTv, highScoreDeathTv, bestTimeTv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        highScoreTimeTv = findViewById(R.id.main_high_score);
        highScoreDeathTv = findViewById(R.id.death_high_score);
        bestTimeTv = findViewById(R.id.speed_time);

        //get any variables from previous sessions
        SharedPreferences mPreferences = getSharedPreferences(SharedPrefFile, MODE_PRIVATE);
        boolean isDark = mPreferences.getBoolean(DARK, firstDark);
        highScoreTime = mPreferences.getInt(HIGH_TIME, 0);
        highScoreDeath = mPreferences.getInt(HIGH_DEATH, 0);
        bestTime = mPreferences.getInt(BEST_TIME, 0);

        //set theme
        if (isDark) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
            getDelegate().applyDayNight();
        } else {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
            getDelegate().applyDayNight();
        }

        String strHighScoreTime = "HighScore: " + getHighScoreTime();
        highScoreTimeTv.setText(strHighScoreTime);

        String strHighScoreDeath = "HighScore: " + getHighScoreDeath();
        highScoreDeathTv.setText(strHighScoreDeath);

        String strBestTime = "Best Time: " + getBestTime();
        bestTimeTv.setText(strBestTime);

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
            case R.id.action_anime:
                //launch anime WebView
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

    public void onSuddenDeathClicked(View view) {
        Intent launchDeath = new Intent(MainActivity.this, DeathModeSettings.class);
        startActivity(launchDeath);
    }

    public void onSpeedRun(View view) {
        Intent launchSpeed = new Intent(MainActivity.this, SpeedModeSettings.class);
        startActivity(launchSpeed);
    }

    //setter for private variable highScoreTime
    public static void setHighScoreTime(int hs) {
        highScoreTime = hs;
    }

    //getter for private variable highScoreTime
    public static int getHighScoreTime() {
        return highScoreTime;
    }

    //setter for private variable highScoreDeath
    public static void setHighScoreDeath(int hs) {
        highScoreDeath = hs;
    }

    //setter for private variable highScoreDeath
    public static int getHighScoreDeath() {
        return highScoreDeath;
    }

    public static void setBestTime(int bt) {
        bestTime = bt;
    }

    public static int getBestTime() {
        return bestTime;
    }
}
