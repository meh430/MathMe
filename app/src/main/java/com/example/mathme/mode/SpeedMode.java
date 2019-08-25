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
import com.example.mathme.settings.SpeedModeSettings;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

public class SpeedMode extends AppCompatActivity {
    private int intMaxQuestions, intNumLimit, intNum1, intNum2, intStopWatch = 0, intCorrectAnswers, intQuestionNum = 1,
            intActualAnswer;
    private ArrayList<String> questionList;
    private ArrayList<Integer> answerList;
    private ArrayList<Integer> userAnswerList;
    private String strQuestion, strChosenOperations, strCurrentQuestionNum;

    public static final String Q_LIST = "QuestionList", A_LIST = "AnswerList",
            UA_LIST = "UserAnswerList", BT = "BestTimes", CORRECT_ANSWERS = "CorrectAnswers";

    private TextView questionNumTv, stopWatchTv, questionTv;
    private EditText userAnswerEdit;
    private Timer timer;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_speed_mode);

        questionNumTv = findViewById(R.id.current_question_num);
        stopWatchTv = findViewById(R.id.stop_watch);
        questionTv = findViewById(R.id.question_window);
        userAnswerEdit = findViewById(R.id.answer_edit_text);

        questionList = new ArrayList<>();
        answerList = new ArrayList<>();
        userAnswerList = new ArrayList<>();

        Intent speedSettings = getIntent();
        strChosenOperations = speedSettings.getStringExtra(SpeedModeSettings.OPERATIONS_SPEED);
        intMaxQuestions = speedSettings.getIntExtra(SpeedModeSettings.NUM_Q_SPEED, 10);
        intNumLimit = speedSettings.getIntExtra(SpeedModeSettings.MAX_NUM_SPEED, 10);

        strCurrentQuestionNum = "Question Number " + intQuestionNum;
        questionNumTv.setText(strCurrentQuestionNum);

        final int delay = 1000;
        final int period = 1000;
        timer = new Timer();
        stopWatchTv.setText("Time: " + intStopWatch + "s");
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        stopWatchTv.setText("Time: " + setInterval() + "s");
                    }
                });
            }
        }, delay, period);
    }

    @Override
    protected void onPause() {
        super.onPause();

        timer.cancel();
    }

    private int setInterval() {
        return ++intStopWatch;
    }

    public void onYes(View view) {
        stopWatchTv.setVisibility(View.VISIBLE);
        questionNumTv.setVisibility(View.VISIBLE);
        view.setVisibility(View.INVISIBLE);
        Button next = findViewById(R.id.next_button);
        next.setVisibility(View.VISIBLE);
        userAnswerEdit.setVisibility(View.VISIBLE);
        showQuestion();
    }

    public void onNext(View view) {
        String temp = userAnswerEdit.getText().toString();
        if (temp.equalsIgnoreCase("")) {
            Toast.makeText(this, "No harm in guessing!", Toast.LENGTH_SHORT).show();
        } else {
            int intUserAnswer = Integer.parseInt(temp);
            userAnswerList.add(intUserAnswer);
            intQuestionNum++;

            if (intQuestionNum > intMaxQuestions) {
                //launch end
                timer.cancel();
                Intent launchSpeedEnd = new Intent(this, SpeedEndActivity.class);
                launchSpeedEnd.putExtra(SpeedModeSettings.NUM_Q_SPEED, intMaxQuestions);
                launchSpeedEnd.putExtra(CORRECT_ANSWERS, intCorrectAnswers);
                launchSpeedEnd.putExtra(Q_LIST, questionList);
                launchSpeedEnd.putExtra(A_LIST, answerList);
                launchSpeedEnd.putExtra(UA_LIST, userAnswerList);
                launchSpeedEnd.putExtra(BT, intStopWatch);

                startActivity(launchSpeedEnd);
            } else {
                if (intUserAnswer == intActualAnswer) {
                    intCorrectAnswers++;
                    showQuestion();
                } else {
                    showQuestion();
                }
            }
        }

        strCurrentQuestionNum = "Question Number " + intQuestionNum;
        questionNumTv.setText(strCurrentQuestionNum);
        userAnswerEdit.setText("");
    }

    private void showQuestion() {
        int random;
        //Addition: a, Subtraction: s, Multiplication: m, Division: d
        //one checked
        if (strChosenOperations.equalsIgnoreCase("a")) {
            add();
        } else if (strChosenOperations.equalsIgnoreCase("s")) {
            subtract();
        } else if (strChosenOperations.equalsIgnoreCase("m")) {
            multiply();
        } else if (strChosenOperations.equalsIgnoreCase("d")) {
            divide();
        } else if (strChosenOperations.equalsIgnoreCase("as")) {
            random = (int) (Math.random() * 2) + 1;
            if (random == 1) {
                add();
            } else {
                subtract();
            }
        } else if (strChosenOperations.equalsIgnoreCase("am")) {
            random = (int) (Math.random() * 2) + 1;
            if (random == 1) {
                add();
            } else {
                multiply();
            }
        }
        if (strChosenOperations.equalsIgnoreCase("ad")) {
            random = (int) (Math.random() * 2) + 1;
            if (random == 1) {
                add();
            } else {
                divide();
            }
        }
        if (strChosenOperations.equalsIgnoreCase("sm")) {
            random = (int) (Math.random() * 2) + 1;
            if (random == 1) {
                subtract();
            } else {
                multiply();
            }
        }
        if (strChosenOperations.equalsIgnoreCase("sd")) {
            random = (int) (Math.random() * 2) + 1;
            if (random == 1) {
                subtract();
            } else {
                divide();
            }
        }
        if (strChosenOperations.equalsIgnoreCase("md")) {
            random = (int) (Math.random() * 2) + 1;
            if (random == 1) {
                multiply();
            } else {
                divide();
            }
        }
        if (strChosenOperations.equalsIgnoreCase("asm")) {
            random = (int) (Math.random() * 3) + 1;
            if (random == 1) {
                add();
            } else if (random == 2) {
                subtract();
            } else {
                multiply();
            }
        }
        if (strChosenOperations.equalsIgnoreCase("amd")) {
            random = (int) (Math.random() * 3) + 1;
            if (random == 1) {
                add();
            } else if (random == 2) {
                multiply();
            } else {
                divide();
            }
        }
        if (strChosenOperations.equalsIgnoreCase("asd")) {
            random = (int) (Math.random() * 3) + 1;
            if (random == 1) {
                add();
            } else if (random == 2) {
                subtract();
            } else {
                divide();
            }
        }
        if (strChosenOperations.equalsIgnoreCase("smd")) {
            random = (int) (Math.random() * 3) + 1;
            if (random == 1) {
                subtract();
            } else if (random == 2) {
                multiply();
            } else {
                divide();
            }
        }
        //four checked
        if (strChosenOperations.equalsIgnoreCase("asmd")) {
            random = (int) (Math.random() * 4) + 1;
            if (random == 1) {
                add();
            } else if (random == 2) {
                subtract();
            } else if (random == 3) {
                multiply();
            } else {
                divide();
            }
        }
    }//close showQuestion

    private void add() {
        intNum1 = (int) (Math.random() * intNumLimit) + 1;
        intNum2 = (int) (Math.random() * intNumLimit) + 1;

        intActualAnswer = intNum1 + intNum2;

        strQuestion = (intNum1) + " + " + (intNum2);

        questionTv.setText(strQuestion);

        questionList.add(strQuestion);
        answerList.add(intActualAnswer);
    }

    private void subtract() {
        intNum1 = (int) (Math.random() * intNumLimit) + 1;
        intNum2 = (int) (Math.random() * intNumLimit) + 1;

        intActualAnswer = intNum1 - intNum2;

        strQuestion = (intNum1) + " - " + (intNum2);

        questionTv.setText(strQuestion);

        questionList.add(strQuestion);
        answerList.add(intActualAnswer);
    }

    private void multiply() {
        intNum1 = (int) (Math.random() * intNumLimit) + 1;
        intNum2 = (int) (Math.random() * intNumLimit) + 1;

        intActualAnswer = intNum1 * intNum2;

        strQuestion = (intNum1) + " * " + (intNum2);

        questionTv.setText(strQuestion);

        questionList.add(strQuestion);
        answerList.add(intActualAnswer);
    }

    private void divide() {
        intNum1 = (int) (Math.random() * intNumLimit) + 1;
        intNum2 = (int) (Math.random() * intNumLimit) + 1;
        intActualAnswer = Math.round((float) intNum1 / intNum2);
        strQuestion = intNum1 + " / " + intNum2;

        questionTv.setText(strQuestion);

        questionList.add(strQuestion);
        answerList.add(intActualAnswer);
    }
}
