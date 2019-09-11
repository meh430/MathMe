package com.example.mathme.ends;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.mathme.R;
import com.example.mathme.mode.SpeedMode;
import com.example.mathme.other.MainActivity;
import com.example.mathme.result.TestResults;
import com.example.mathme.settings.SpeedModeSettings;

import java.util.ArrayList;

public class SpeedEndActivity extends AppCompatActivity {
    SharedPreferences mSharedPreferences;
    //map to hold all the questions
    private ArrayList<String> questions;
    //map to hold all the answers, and the user answers
    private ArrayList<Integer> answers, userAnswers;
    private int intMaxQ, intCorrectAns = 0, intTimeResult;
    //array list that stores the corrections to wrong answers
    private final ArrayList<String> resultInfoList = new ArrayList<>();
    private TextView accuracyTv, timeResultHeadTv, timeResultTv;
    public static final String SPEED_RESULT_LIST = "SpeedResultsInfo";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_speed_end);

        accuracyTv = findViewById(R.id.speed_accuracy);
        timeResultHeadTv = findViewById(R.id.speed_result_header);
        timeResultTv = findViewById(R.id.speed_result_time);

        mSharedPreferences = getSharedPreferences(MainActivity.SharedPrefFile, MODE_PRIVATE);

        Intent speedMode = getIntent();
        intMaxQ = speedMode.getIntExtra(SpeedModeSettings.NUM_Q_SPEED, 0);
        questions = speedMode.getStringArrayListExtra(SpeedMode.Q_LIST);
        answers = speedMode.getIntegerArrayListExtra(SpeedMode.A_LIST);
        userAnswers = speedMode.getIntegerArrayListExtra(SpeedMode.UA_LIST);
        intTimeResult = speedMode.getIntExtra(SpeedMode.BT, 0);

        checkAnswers();
    }

    @SuppressLint("SetTextI18n")
    private void checkAnswers() {
        String resultInfo;

        for (int i = 0; i < intMaxQ; i++) {
            int intActualAnswer = answers.get(i);
            int intUserAnswer = userAnswers.get(i);

            if (intActualAnswer != intUserAnswer) {
                resultInfo = questions.get(i) + " is " + intActualAnswer + ", not " + intUserAnswer;
                resultInfoList.add(resultInfo);
            } else {
                intCorrectAns++;
            }
        }

        double dblPercent = (double) intCorrectAns / intMaxQ;
        String resultPercent = (Math.round(dblPercent * 100)) + "%";
        Button viewResButt = findViewById(R.id.result_button);
        //set view results button visibility depending on the result
        if (intCorrectAns == intMaxQ) {
            viewResButt.setVisibility(View.INVISIBLE);
        } else {
            viewResButt.setVisibility(View.VISIBLE);
        }

        accuracyTv.setText(resultPercent);

        String endMessage;
        /*
        if (intTimeResult < MainActivity.getBestTime() || MainActivity.getBestTime() == 0) {
            endMessage = "New Best Time!";
            timeResultHeadTv.setText(endMessage);
            MainActivity.setBestTime(intTimeResult);
            SharedPreferences.Editor preferenceEditor = mSharedPreferences.edit();
            preferenceEditor.putInt(MainActivity.BEST_TIME, MainActivity.getBestTime());
            preferenceEditor.apply();
        } else {
            endMessage = "Time: ";
            timeResultHeadTv.setText(endMessage);
        }*/
        endMessage = "Time: ";
        timeResultHeadTv.setText(endMessage);
        timeResultTv.setText(intTimeResult + "s");
        MainActivity.setBestTime(intTimeResult);
        SharedPreferences.Editor preferenceEditor = mSharedPreferences.edit();
        preferenceEditor.putInt(MainActivity.BEST_TIME, MainActivity.getBestTime());
        preferenceEditor.apply();
    }

    public void onViewResults(View view) {
        Intent speedResults = new Intent(this, TestResults.class);
        speedResults.putExtra(SPEED_RESULT_LIST, resultInfoList);
        speedResults.putExtra(TestResults.TEST_TIME, "speed");
        startActivity(speedResults);
    }

    public void onTakeMeHome(View view) {
        Intent takeMeHome = new Intent(this, MainActivity.class);
        startActivity(takeMeHome);
        finish();
    }
}
