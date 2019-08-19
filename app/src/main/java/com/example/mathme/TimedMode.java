package com.example.mathme;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

public class TimedMode extends AppCompatActivity {
    private int intNumLimit, intQuestionsAnswered = 0, intActualAnswer = 0, intNum1, intNum2,
            intTime, intScore = 0;
    private StringBuilder sbAnswer = new StringBuilder("");
    private ArrayList<String> questionList;
    private ArrayList<Integer> answerList;
    private ArrayList<Integer> userAnswerList;
    private String strQuestion, strChosenOperations;
    public static final String SCORE = "Score", Q_ANS = "QuestionsAns", Q_LIST = "QuestionList", A_LIST = "AnswerList",
            UA_LIST = "UserAnswerList";

    private TextView scoreTv, questionTv, answerTv, timeTv;
    private AlphaAnimation buttonClick = new AlphaAnimation(1F, 0.4F);
    private Timer timer;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timed_mode);

        scoreTv = findViewById(R.id.current_score);
        timeTv = findViewById(R.id.time_left);
        questionTv = findViewById(R.id.question);
        answerTv = findViewById(R.id.answer);

        questionList = new ArrayList<>();
        answerList = new ArrayList<>();
        userAnswerList = new ArrayList<>();

        Intent timeSettings = getIntent();
        intNumLimit = timeSettings.getIntExtra(TimeModeSettings.MAX_NUM_TIME, 100);
        intTime = timeSettings.getIntExtra(TimeModeSettings.TIME_TIME, 30);
        strChosenOperations = timeSettings.getStringExtra(TimeModeSettings.OPERATIONS_TIME);
        timer = new Timer();
        final String strTime = "Time: ";
        timeTv.setText(strTime + intTime);

        final int delay = 1000;
        final int period = 1000;
        timer = new Timer();
        timeTv.setText("Time: " + intTime + "s");
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        timeTv.setText("Time: " + setInterval() + "s");
                    }
                });
            }
        }, delay, period);
        showQuestion();
    }

    private int setInterval() {
        if (intTime == 1) {
            timer.cancel();
            Intent launchTimeEnd = new Intent(TimedMode.this, TimedEndActivity.class);
            launchTimeEnd.putExtra(Q_ANS, intQuestionsAnswered)
                    .putExtra(SCORE, intScore)
                    .putExtra(A_LIST, answerList)
                    .putExtra(UA_LIST, userAnswerList)
                    .putExtra(Q_LIST, questionList);

            startActivity(launchTimeEnd);
            //call another method later
        }
        return --intTime;
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

    public void onNextTime(View view) {
        view.startAnimation(buttonClick);

        if (sbAnswer == null || sbAnswer.toString().equalsIgnoreCase("")) {
            sbAnswer = new StringBuilder("0");
        }

        if (intActualAnswer == Integer.parseInt(sbAnswer.toString())) {
            intScore++;
            intQuestionsAnswered++;
        } else {
            intQuestionsAnswered++;
            Vibrator v = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                v.vibrate(VibrationEffect.createOneShot(100, VibrationEffect.DEFAULT_AMPLITUDE));
            } else {
                //deprecated in API 26
                v.vibrate(100);
            }
        }
        String strScore = "Score: " + intScore;
        scoreTv.setText(strScore);

        userAnswerList.add(Integer.parseInt(sbAnswer.toString()));

        answerTv.setText("");
        sbAnswer = new StringBuilder("");
        showQuestion();
    }

    public void onZero(View view) {
        view.startAnimation(buttonClick);
        sbAnswer.append("0");
        answerTv.setText(sbAnswer);
    }

    public void onClear(View view) {
        view.startAnimation(buttonClick);
        sbAnswer = new StringBuilder();
        answerTv.setText("");
    }

    public void onThree(View view) {
        view.startAnimation(buttonClick);
        sbAnswer.append("3");
        answerTv.setText(sbAnswer);
    }

    public void onTwo(View view) {
        view.startAnimation(buttonClick);
        sbAnswer.append("2");
        answerTv.setText(sbAnswer);
    }

    public void onOne(View view) {
        view.startAnimation(buttonClick);
        sbAnswer.append("1");
        answerTv.setText(sbAnswer);
    }

    public void onSix(View view) {
        view.startAnimation(buttonClick);
        sbAnswer.append("6");
        answerTv.setText(sbAnswer);
    }

    public void onFive(View view) {
        view.startAnimation(buttonClick);
        sbAnswer.append("5");
        answerTv.setText(sbAnswer);
    }

    public void onFour(View view) {
        view.startAnimation(buttonClick);
        sbAnswer.append("4");
        answerTv.setText(sbAnswer);
    }

    public void onNine(View view) {
        view.startAnimation(buttonClick);
        sbAnswer.append("9");
        answerTv.setText(sbAnswer);
    }

    public void onEight(View view) {
        view.startAnimation(buttonClick);
        sbAnswer.append("8");
        answerTv.setText(sbAnswer);
    }

    public void onSeven(View view) {
        view.startAnimation(buttonClick);
        sbAnswer.append("7");
        answerTv.setText(sbAnswer);
    }
}
