package com.example.mathme;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class DeathEndActivity extends AppCompatActivity {
    SharedPreferences mSharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_death_end);
        mSharedPreferences = getSharedPreferences(MainActivity.SharedPrefFile, MODE_PRIVATE);

        Intent deathMode = getIntent();
        int intScore = deathMode.getIntExtra(DeathMode.SCORE, 0);
        TextView scoreResultTv = findViewById(R.id.death_Score);
        TextView scoreHeaderTv = findViewById(R.id.death_score_header);

        String strScore = Integer.toString(intScore);
        scoreResultTv.setText(strScore);

        String strResultMessage;

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
        }
    }

    public void onTakeMeHome(View view) {
        Intent takeMeHome = new Intent(this, MainActivity.class);
        startActivity(takeMeHome);
    }
}
