package com.example.mathme.mode;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.mathme.R;
import com.example.mathme.ends.SpeedEndActivity;
import com.example.mathme.mode.utility.ModeUtility;
import com.example.mathme.mode.utility.SpeedUtility;
import com.example.mathme.settings.SpeedModeSettings;

import java.util.Timer;
import java.util.TimerTask;

public class SpeedMode extends AppCompatActivity {

    public static final String Q_LIST = "QuestionList", A_LIST = "AnswerList",
            UA_LIST = "UserAnswerList", BT = "BestTimes", CORRECT_ANSWERS = "CorrectAnswers";

    private ModeUtility speedUtility;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_speed_mode);

        Intent speedSettings = getIntent();

        speedUtility = new SpeedUtility((TextView) findViewById(R.id.current_question_num),
                (TextView) findViewById(R.id.stop_watch), (TextView) findViewById(R.id.question_window),
                (EditText) findViewById(R.id.answer_edit_text),
                speedSettings.getIntExtra(SpeedModeSettings.MAX_NUM_SPEED, 10),
                speedSettings.getIntExtra(SpeedModeSettings.NUM_Q_SPEED, 10),
                speedSettings.getStringExtra(SpeedModeSettings.OPERATIONS_SPEED));

        ((SpeedUtility) speedUtility).strCurrentQuestionNum = "Question Number " + speedUtility.intCurrentQuestion;
        speedUtility.mQuestionNumTv.setText(((SpeedUtility) speedUtility).strCurrentQuestionNum);

        final int delay = 1000;
        final int period = 1000;
        speedUtility.timer = new Timer();
        ((SpeedUtility) speedUtility).stopWatchTv.setText("Time: " + ((SpeedUtility) speedUtility).intStopWatch + "s");
        speedUtility.timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        ((SpeedUtility) speedUtility).stopWatchTv.setText("Time: " + setInterval() + "s");
                    }
                });
            }
        }, delay, period);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        speedUtility.timer.cancel();
    }

    private int setInterval() {
        return ++((SpeedUtility) speedUtility).intStopWatch;
    }

    public void onYes(View view) {
        ((SpeedUtility) speedUtility).stopWatchTv.setVisibility(View.VISIBLE);
        speedUtility.mQuestionNumTv.setVisibility(View.VISIBLE);
        view.setVisibility(View.INVISIBLE);
        Button next = findViewById(R.id.next_button);
        next.setVisibility(View.VISIBLE);
        speedUtility.mUserAnswerEdit.setVisibility(View.VISIBLE);
        speedUtility.showQuestion();
    }

    public void onNext(View view) {
        String temp = speedUtility.mUserAnswerEdit.getText().toString();
        if (temp.equalsIgnoreCase("")) {
            Toast.makeText(this, "No harm in guessing!", Toast.LENGTH_SHORT).show();
        } else {
            int intUserAnswer = Integer.parseInt(temp);
            speedUtility.mUserAnswerList.add(intUserAnswer);
            speedUtility.nextQuestionNum();

            if (speedUtility.intCurrentQuestion > speedUtility.intMaxQuestion) {
                //launch end
                speedUtility.timer.cancel();
                Intent launchSpeedEnd = new Intent(this, SpeedEndActivity.class);
                launchSpeedEnd.putExtra(SpeedModeSettings.NUM_Q_SPEED, speedUtility.intMaxQuestion);
                launchSpeedEnd.putExtra(CORRECT_ANSWERS, speedUtility.intScore);
                launchSpeedEnd.putExtra(Q_LIST, speedUtility.mQuestionList);
                launchSpeedEnd.putExtra(A_LIST, speedUtility.mAnswerList);
                launchSpeedEnd.putExtra(UA_LIST, speedUtility.mUserAnswerList);
                launchSpeedEnd.putExtra(BT, ((SpeedUtility) speedUtility).intStopWatch);

                startActivity(launchSpeedEnd);
            } else {
                if (intUserAnswer == speedUtility.intActualAnswer) {
                    speedUtility.increaseScore();
                    speedUtility.showQuestion();
                } else {
                    speedUtility.showQuestion();
                }
            }
        }

        ((SpeedUtility) speedUtility).strCurrentQuestionNum = "Question Number " + speedUtility.intCurrentQuestion;
        speedUtility.mQuestionNumTv.setText(((SpeedUtility) speedUtility).strCurrentQuestionNum);
        speedUtility.mUserAnswerEdit.setText("");
    }
}
