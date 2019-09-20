package com.example.mathme.ends;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.mathme.R;
import com.example.mathme.mode.TimedMode;
import com.example.mathme.other.MainActivity;
import com.example.mathme.result.TestResults;
import com.example.mathme.scores.TimeScore;
import com.example.mathme.settings.TimeModeSettings;

import java.util.ArrayList;

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
    private String mStrOperators;
    private int intNumLim, intTime;
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
        intTime = timedMode.getIntExtra(TimeModeSettings.TIME_TIME, 30);
        intNumLim = timedMode.getIntExtra(TimeModeSettings.MAX_NUM_TIME, 10);
        mStrOperators = timedMode.getStringExtra(TimeModeSettings.OPERATIONS_TIME);

        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                Button home = findViewById(R.id.country_roads);
                home.setVisibility(View.VISIBLE);
            }
        }, 1500);

        checkAnswers();
    }

    @SuppressLint("SetTextI18n")
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

        String endMessage;

        endMessage = "Score: ";
        scoreHeaderTv.setText(endMessage);
        scoreTv.setText(Integer.toString(score));
        MainActivity.setHighScoreTime(score);
        SharedPreferences.Editor preferenceEditor = mSharedPreferences.edit();
        preferenceEditor.putInt(MainActivity.HIGH_TIME, MainActivity.getHighScoreTime());
        preferenceEditor.apply();
        MainActivity.mTimeViewModel.insert(new TimeScore(intNumLim, score, intTime, new EndUtility().chosenOperators(mStrOperators),
                (int) (Math.random() * 10000) + 1));
    }

    public void onViewResults(View view) {
        Intent timeResults = new Intent(this, TestResults.class);
        timeResults.putExtra(TIMED_RESULT_LIST, resultInfoList);
        timeResults.putExtra(TestResults.TEST_TIME, "time");
        startActivity(timeResults);
    }

    public void onTakeMeHome(View view) {
        Intent takeMeHome = new Intent(this, MainActivity.class);
        startActivity(takeMeHome);
        finish();
    }
}
