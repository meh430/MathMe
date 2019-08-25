//TODO: Make a superclass with all the commonly used methods and variables, one called Mode, one called Settings, one called End
package com.example.mathme.mode;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.mathme.R;
import com.example.mathme.ends.DeathEndActivity;
import com.example.mathme.settings.DeathModeSettings;


public class DeathMode extends AppCompatActivity {
    private int intNumLimit;
    private int intActualAnswer = 0;
    private int intNum1;
    private int intNum2;
    private int intScore = 0;
    private String strQuestion, strChosenOperations;
    public static final String SCORE = "Score";
    private TextView scoreTv, questionTv;
    private EditText userAnswerEditText;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_death_mode);

        scoreTv = findViewById(R.id.current_score);
        questionTv = findViewById(R.id.question_window);
        userAnswerEditText = findViewById(R.id.answer_edit_text);

        Intent deathSettings = getIntent();
        strChosenOperations = deathSettings.getStringExtra(DeathModeSettings.OPERATIONS_DEATH);
        intNumLimit = deathSettings.getIntExtra(DeathModeSettings.MAX_NUM_DEATH, 10);
    }

    public void onYes(View view) {
        scoreTv.setVisibility(View.VISIBLE);
        view.setVisibility(View.INVISIBLE);
        Button next = findViewById(R.id.next_button);
        next.setVisibility(View.VISIBLE);
        userAnswerEditText.setVisibility(View.VISIBLE);
        showQuestion();
    }

    public void onNext(View view) {
        String temp = userAnswerEditText.getText().toString();
        if (temp.equalsIgnoreCase("")) {
            Toast.makeText(this, "No harm in guessing!", Toast.LENGTH_SHORT).show();
        } else {
            int intUserAnswer = Integer.parseInt(temp);
            if (intUserAnswer == intActualAnswer) {
                intScore++;
                showQuestion();
            } else {
                Vibrator v = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    assert v != null;
                    v.vibrate(VibrationEffect.createOneShot(1000, VibrationEffect.DEFAULT_AMPLITUDE));
                } else {
                    //deprecated in API 26
                    assert v != null;
                    v.vibrate(1000);
                }
                //launch end activity
                Intent launchDeathEnd = new Intent(this, DeathEndActivity.class);
                launchDeathEnd.putExtra(SCORE, intScore);
                startActivity(launchDeathEnd);
            }
        }

        String strScore = "Score: " + intScore;
        scoreTv.setText(strScore);
        userAnswerEditText.setText("");
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

    }

    private void subtract() {
        intNum1 = (int) (Math.random() * intNumLimit) + 1;
        intNum2 = (int) (Math.random() * intNumLimit) + 1;

        intActualAnswer = intNum1 - intNum2;

        strQuestion = (intNum1) + " - " + (intNum2);

        questionTv.setText(strQuestion);

    }

    private void multiply() {
        intNum1 = (int) (Math.random() * intNumLimit) + 1;
        intNum2 = (int) (Math.random() * intNumLimit) + 1;

        intActualAnswer = intNum1 * intNum2;

        strQuestion = (intNum1) + " * " + (intNum2);

        questionTv.setText(strQuestion);
    }

    private void divide() {
        intNum1 = (int) (Math.random() * intNumLimit) + 1;
        intNum2 = (int) (Math.random() * intNumLimit) + 1;
        intActualAnswer = Math.round((float) intNum1 / intNum2);
        strQuestion = intNum1 + " / " + intNum2;

        questionTv.setText(strQuestion);
    }

}
