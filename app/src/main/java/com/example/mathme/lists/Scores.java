package com.example.mathme.lists;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.example.mathme.R;

public class Scores extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scores);
    }

    public void testScores(View view) {
        Intent testScores = new Intent(this, TestScoreList.class);
        startActivity(testScores);
    }

    public void TimeScores(View view) {
        Intent timeScores = new Intent(this, TimeScoreList.class);
        startActivity(timeScores);
    }

    public void deathScores(View view) {
        Intent deathScores = new Intent(this, DeathScoreList.class);
        startActivity(deathScores);
    }
}
