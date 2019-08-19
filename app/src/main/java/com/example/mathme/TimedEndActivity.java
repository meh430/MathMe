package com.example.mathme;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.sql.Time;
import java.util.ArrayList;
import java.util.HashMap;

public class TimedEndActivity extends AppCompatActivity {
    SharedPreferences mSharedPreferences;
    //map to hold all the questions
    private ArrayList<String> questions;
    //map to hold all the answers, and the user answers
    private ArrayList<Integer> answers, userAnswers;
    //maximum number of questions
    private int intQAnswered, score;
    //array list that stores the corrections to wrong answers
    private final ArrayList<String> resultInfoList = new ArrayList<>();
    private TextView scoreTv, accuracyTv, scoreHeaderTv;
    public static final String TIMED_RESULT_LIST = "TimedResultInfo";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timed_end);

        mSharedPreferences = getSharedPreferences(MainActivity.SharedPrefFile, MODE_PRIVATE);
        scoreTv = findViewById(R.id.time_score);
        accuracyTv = findViewById(R.id.accuracy);
        scoreHeaderTv = findViewById(R.id.time_score_header);

        Intent timedMode = getIntent();
        intQAnswered = timedMode.getIntExtra(TimedMode.Q_ANS, 0);
        score = timedMode.getIntExtra(TimedMode.SCORE, 0);
        questions = timedMode.getStringArrayListExtra(TimedMode.Q_LIST);
        answers = timedMode.getIntegerArrayListExtra(TimedMode.A_LIST);
        userAnswers = timedMode.getIntegerArrayListExtra(TimedMode.UA_LIST);

        checkAnswers();
    }

    private void checkAnswers() {
        String resultInfo;

        for (int i = 0; i < intQAnswered; i++) {
            int intActualAnswer = answers.get(i);
            int intUserAnswer = userAnswers.get(i);

            if (intActualAnswer != intUserAnswer) {
                resultInfo = questions.get(i) + " is " + intActualAnswer + ", not " + intUserAnswer;
                resultInfoList.add(resultInfo);
            }
        }

        double dblPercent = (double) score / intQAnswered;
        String resultPercent = (Math.round(dblPercent * 100)) + "%";
        Button viewResButt = findViewById(R.id.result_button);
        //set view results button visibility depending on the result
        if (score == intQAnswered) {
            viewResButt.setVisibility(View.INVISIBLE);
        } else {
            viewResButt.setVisibility(View.VISIBLE);
        }

        accuracyTv.setText(resultPercent);

        if (score > MainActivity.getHighScoreTime()) {
            scoreHeaderTv.setText("New High Score!");
            MainActivity.setHighScoreTime(score);
            SharedPreferences.Editor preferenceEditor = mSharedPreferences.edit();
            preferenceEditor.putInt(MainActivity.HIGH_TIME, MainActivity.getHighScoreTime());
            preferenceEditor.apply();
        } else {
            scoreHeaderTv.setText("Score: ");
        }
        scoreTv.setText(score + "");
    }

    public void onViewResults(View view) {
        Intent timeResults = new Intent(this, TestResults.class);
        timeResults.putExtra(TIMED_RESULT_LIST, resultInfoList);
        timeResults.putExtra(TestResults.TEST_TIME, false);
        startActivity(timeResults);
    }

    public void onTakeMeHome(View view) {
        Intent takeMeHome = new Intent(this, MainActivity.class);
        startActivity(takeMeHome);
    }
}
