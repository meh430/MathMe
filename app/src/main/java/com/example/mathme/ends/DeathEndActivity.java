package com.example.mathme.ends;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.mathme.R;
import com.example.mathme.mode.DeathMode;
import com.example.mathme.other.MainActivity;
import com.example.mathme.scores.DeathScores;
import com.example.mathme.settings.DeathModeSettings;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class DeathEndActivity extends AppCompatActivity {
    SharedPreferences mSharedPreferences;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_death_end);
        mSharedPreferences = getSharedPreferences(MainActivity.SharedPrefFile, MODE_PRIVATE);

        Intent deathMode = getIntent();
        int intScore = deathMode.getIntExtra(DeathMode.SCORE, 0);
        int intNumLim = deathMode.getIntExtra(DeathModeSettings.MAX_NUM_DEATH, 0);
        String mStrOperators = deathMode.getStringExtra(DeathModeSettings.OPERATIONS_DEATH);
        TextView scoreResultTv = findViewById(R.id.death_Score);
        TextView scoreHeaderTv = findViewById(R.id.death_score_header);

        String strScore = Integer.toString(intScore);
        scoreResultTv.setText(strScore);

        String strResultMessage;
        /*
        if (intScore > MainActivity.getHighScoreDeath()) {
            strResultMessage = "New High Score!";
            scoreHeaderTv.setText(strResultMessage);
            MainActivity.setHighScoreDeath(intScore);
            SharedPreferences.Editor preferenceEditor = mSharedPreferences.edit();
            preferenceEditor.putInt(MainActivity.HIGH_DEATH, MainActivity.getHighScoreDeath());
            preferenceEditor.apply();
        } else {
            strResultMessage = "Score";
            scoreHeaderTv.setText(strResultMessage);
        }*/

        strResultMessage = "Score";
        scoreHeaderTv.setText(strResultMessage);
        MainActivity.setHighScoreDeath(intScore);
        SharedPreferences.Editor preferenceEditor = mSharedPreferences.edit();
        preferenceEditor.putInt(MainActivity.HIGH_DEATH, MainActivity.getHighScoreDeath());
        preferenceEditor.apply();
        MainActivity.mDeathViewModel.insert(new DeathScores(intNumLim, intScore,
                new EndUtility().chosenOperators(mStrOperators), (int) (Math.random() * 10000) + 1,
                new SimpleDateFormat("MM-dd-yyyy 'at' hh:mm:ss", Locale.CANADA).format(new Date())));
    }

    public void onTakeMeHome(View view) {
        Intent takeMeHome = new Intent(this, MainActivity.class);
        startActivity(takeMeHome);
        finish();
    }
}
