package com.example.mathme.mode;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.mathme.R;
import com.example.mathme.ends.TimedEndActivity;
import com.example.mathme.mode.utility.ModeUtility;
import com.example.mathme.mode.utility.TimeUtility;
import com.example.mathme.settings.TimeModeSettings;

import java.util.TimerTask;

public class TimedMode extends AppCompatActivity {
    public static final String SCORE = "Score", Q_ANS = "QuestionsAns",
            Q_LIST = "QuestionList", A_LIST = "AnswerList", UA_LIST = "UserAnswerList";

    private ModeUtility timeUtility;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timed_mode);

        Intent timeSettings = getIntent();

        timeUtility = new TimeUtility((TextView) findViewById(R.id.current_score), (TextView) findViewById(R.id.time_left),
                (TextView) findViewById(R.id.question), (TextView) findViewById(R.id.answer),
                timeSettings.getIntExtra(TimeModeSettings.MAX_NUM_TIME, 100),
                timeSettings.getIntExtra(TimeModeSettings.TIME_TIME, 30),
                timeSettings.getStringExtra(TimeModeSettings.OPERATIONS_TIME));

        final String strTime = "Time: ";
        ((TimeUtility) timeUtility).timeTv.setText(strTime + ((TimeUtility) timeUtility).intTime);

        final int delay = 1000;
        final int period = 1000;
        ((TimeUtility) timeUtility).timeTv.setText("Time: " + ((TimeUtility) timeUtility).intTime + "s");
        ((TimeUtility) timeUtility).timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        ((TimeUtility) timeUtility).timeTv.setText("Time: " + setInterval() + "s");
                    }
                });
            }
        }, delay, period);
        timeUtility.showQuestion();
    }

    private int setInterval() {
        if (((TimeUtility) timeUtility).intTime == 1) {
            ((TimeUtility) timeUtility).timer.cancel();

            Vibrator v = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                assert v != null;
                v.vibrate(VibrationEffect.createOneShot(1000, VibrationEffect.DEFAULT_AMPLITUDE));
            } else {
                //deprecated in API 26
                assert v != null;
                v.vibrate(1000);
            }

            Intent launchTimeEnd = new Intent(TimedMode.this, TimedEndActivity.class);
            launchTimeEnd.putExtra(Q_ANS, ((TimeUtility) timeUtility).intQuestionsAnswered)
                    .putExtra(SCORE, ((TimeUtility) timeUtility).intScore)
                    .putExtra(A_LIST, timeUtility.mAnswerList)
                    .putExtra(UA_LIST, timeUtility.mUserAnswerList)
                    .putExtra(Q_LIST, timeUtility.mQuestionList);

            startActivity(launchTimeEnd);
            //call another method later
        }
        return --((TimeUtility) timeUtility).intTime;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (((TimeUtility) timeUtility).timer != null) {
            ((TimeUtility) timeUtility).timer.cancel();
        }
    }

    public void onNextTime(View view) {

        if (((TimeUtility) timeUtility).sbAnswer == null ||
                ((TimeUtility) timeUtility).sbAnswer.toString().equalsIgnoreCase("")) {
            ((TimeUtility) timeUtility).sbAnswer = new StringBuilder("0");
        }

        if (timeUtility.intActualAnswer == Integer.parseInt(((TimeUtility) timeUtility).sbAnswer.toString())) {
            timeUtility.increaseScore();
            ((TimeUtility) timeUtility).increaseQuestionsAns();
        } else {
            ((TimeUtility) timeUtility).increaseQuestionsAns();
            Vibrator v = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                assert v != null;
                v.vibrate(VibrationEffect.createOneShot(100, VibrationEffect.DEFAULT_AMPLITUDE));
            } else {
                //deprecated in API 26
                assert v != null;
                v.vibrate(100);
            }
        }
        String strScore = "Score: " + ((TimeUtility) timeUtility).intScore;
        ((TimeUtility) timeUtility).scoreTv.setText(strScore);

        timeUtility.mUserAnswerList.add(Integer.parseInt(((TimeUtility) timeUtility).sbAnswer.toString()));

        ((TimeUtility) timeUtility).answerTv.setText("");
        ((TimeUtility) timeUtility).sbAnswer = new StringBuilder();
        timeUtility.showQuestion();
    }

    public void onZero(View view) {
        ((TimeUtility) timeUtility).sbAnswer.append("0");
        ((TimeUtility) timeUtility).answerTv.setText(((TimeUtility) timeUtility).sbAnswer);
    }

    public void onClear(View view) {
        ((TimeUtility) timeUtility).sbAnswer = new StringBuilder();
        ((TimeUtility) timeUtility).answerTv.setText("");
    }

    public void onThree(View view) {
        ((TimeUtility) timeUtility).sbAnswer.append("3");
        ((TimeUtility) timeUtility).answerTv.setText(((TimeUtility) timeUtility).sbAnswer);
    }

    public void onTwo(View view) {
        ((TimeUtility) timeUtility).sbAnswer.append("2");
        ((TimeUtility) timeUtility).answerTv.setText(((TimeUtility) timeUtility).sbAnswer);
    }

    public void onOne(View view) {
        ((TimeUtility) timeUtility).sbAnswer.append("1");
        ((TimeUtility) timeUtility).answerTv.setText(((TimeUtility) timeUtility).sbAnswer);
    }

    public void onSix(View view) {
        ((TimeUtility) timeUtility).sbAnswer.append("6");
        ((TimeUtility) timeUtility).answerTv.setText(((TimeUtility) timeUtility).sbAnswer);
    }

    public void onFive(View view) {
        ((TimeUtility) timeUtility).sbAnswer.append("5");
        ((TimeUtility) timeUtility).answerTv.setText(((TimeUtility) timeUtility).sbAnswer);
    }

    public void onFour(View view) {
        ((TimeUtility) timeUtility).sbAnswer.append("4");
        ((TimeUtility) timeUtility).answerTv.setText(((TimeUtility) timeUtility).sbAnswer);
    }

    public void onNine(View view) {
        ((TimeUtility) timeUtility).sbAnswer.append("9");
        ((TimeUtility) timeUtility).answerTv.setText(((TimeUtility) timeUtility).sbAnswer);
    }

    public void onEight(View view) {
        ((TimeUtility) timeUtility).sbAnswer.append("8");
        ((TimeUtility) timeUtility).answerTv.setText(((TimeUtility) timeUtility).sbAnswer);
    }

    public void onSeven(View view) {
        ((TimeUtility) timeUtility).sbAnswer.append("7");
        ((TimeUtility) timeUtility).answerTv.setText(((TimeUtility) timeUtility).sbAnswer);
    }
}
