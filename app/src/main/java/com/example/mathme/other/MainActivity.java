package com.example.mathme.other;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.ViewModelProviders;

import com.example.mathme.R;
import com.example.mathme.lists.Scores;
import com.example.mathme.scores.databases.death.DeathViewModel;
import com.example.mathme.scores.databases.test.TestViewModel;
import com.example.mathme.scores.databases.time.TimeViewModel;
import com.example.mathme.settings.DeathModeSettings;
import com.example.mathme.settings.SpeedModeSettings;
import com.example.mathme.settings.TestModeSettings;
import com.example.mathme.settings.TimeModeSettings;
import com.example.mathme.web.Relax;

public class MainActivity extends AppCompatActivity {
    public static TestViewModel mTestViewModel;
    public static TimeViewModel mTimeViewModel;
    public static DeathViewModel mDeathViewModel;
    public static final String SharedPrefFile = "com.example.mathme";
    //constant values for sharedPref keys
    public static final String DARK = "isDark", NOTIF = "notificationSet", NOTIF_TIME = "notificationTime", HOUR = "hour",
            MINUTE = "minute", HIGH_TIME = "highScoreTime",
            HIGH_DEATH = "highScoreDeath", BEST_TIME = "bestTime", LAST_MARK = "lastMark";
    private static int highScoreTime, highScoreDeath, bestTime;
    //force dark mode if set by user on first session
    public static boolean firstDark = false;
    //textview to store display the higscore
    TextView highScoreTimeTv, highScoreDeathTv, bestTimeTv, lastMarkTv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        highScoreTimeTv = findViewById(R.id.main_high_score);
        highScoreDeathTv = findViewById(R.id.death_high_score);
        bestTimeTv = findViewById(R.id.speed_time);
        lastMarkTv = findViewById(R.id.test_mark);

        mTestViewModel = ViewModelProviders.of(this).get(TestViewModel.class);
        mTimeViewModel = ViewModelProviders.of(this).get(TimeViewModel.class);
        mDeathViewModel = ViewModelProviders.of(this).get(DeathViewModel.class);

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

        String strHighScoreTime = "Last Score: " + getHighScoreTime();
        highScoreTimeTv.setText(strHighScoreTime);

        String strHighScoreDeath = "Last Score: " + getHighScoreDeath();
        highScoreDeathTv.setText(strHighScoreDeath);

        String strBestTime = "Last Time: " + getBestTime() + "s";
        bestTimeTv.setText(strBestTime);

        String strLastMark = "Last Mark: " + mPreferences.getString(LAST_MARK, "0%");
        lastMarkTv.setText(strLastMark);
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
        // as you specify a parent activity in AndroidManifest.xml-v25.
        int id = item.getItemId();

        switch (id) {
            //launch settings
            case R.id.action_settings:
                Intent launchSettings = new Intent(MainActivity.this, SettingActivity.class);
                startActivity(launchSettings);
                return true;
            //TODO:add picker for result lists
            case R.id.action_scores:
                Intent launchScores = new Intent(MainActivity.this, Scores.class);
                startActivity(launchScores);
                return true;
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
                //int randomUrl = (int) (Math.random() * urlArray.length);
                Uri contact = Uri.parse(urlArray[0]);
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
                Intent launchAnime = new Intent(MainActivity.this, Relax.class);
                startActivity(launchAnime);
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

    public static void setHighScoreTime(int hs) {
        highScoreTime = hs;
    }

    public static int getHighScoreTime() {
        return highScoreTime;
    }

    public static void setHighScoreDeath(int hs) {
        highScoreDeath = hs;
    }

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
