package com.example.mathme;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.HashMap;

@SuppressWarnings({"ConstantConditions"})
public class TestMode extends AppCompatActivity {
    private boolean end = false;
    //stores inputted num limit, inputted question num, count of correct answers, keep track of current question
    private int intNumLimit, intMaxQuestion, intNumCorrect = 1, intCurrentQuestion = 1, intUserAnswer = 0, intActualAnswer = 0, intNum1, intNum2;
    private String chosenOperation, strQuestion;
    private TextView questionTv, questionNumTv;
    @SuppressLint("UseSparseArrays")
    HashMap<Integer, String> questionMap = new HashMap<>();
    @SuppressLint("UseSparseArrays")
    HashMap<Integer, Integer> answerMap = new HashMap<>();
    @SuppressLint("UseSparseArrays")
    HashMap<Integer, Integer> userAnswerMap = new HashMap<>();
    EditText userAnswerEdit;

    public static final String MAXQ = "MAXQ", Q_MAP = "QMAP", A_MAP = "AMAP", UA_MAP = "UAMAP";
    private boolean firsTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_mode);

        questionTv = findViewById(R.id.question_window);
        questionNumTv = findViewById(R.id.current_question_num);
        userAnswerEdit = findViewById(R.id.answer_edit_text);

        Intent testSettings = getIntent();
        chosenOperation = testSettings.getStringExtra(TestModeSettings.OPERATIONS);
        intNumLimit = testSettings.getIntExtra(TestModeSettings.MAX_NUM, 100);
        intMaxQuestion = testSettings.getIntExtra(TestModeSettings.NUM_Q, 100);
    }

    public void onYes(View view) {
        view.setVisibility(View.INVISIBLE);
        LinearLayout buttonBar = findViewById(R.id.test_buttons);
        buttonBar.setVisibility(View.VISIBLE);
        userAnswerEdit.setVisibility(View.VISIBLE);
        driver();
        firsTime = true;
        //checkOperationChoicesShow();
    }

    public void onNext(View view) {
        if (firsTime) {
            String temp = userAnswerEdit.getText().toString();
            if (temp.equalsIgnoreCase("")) {
                Toast.makeText(TestMode.this, "Just Try", Toast.LENGTH_SHORT).show();
            } else {
                firsTime = false;
                intUserAnswer = Integer.parseInt(temp);
                userAnswerMap.put(intCurrentQuestion, intUserAnswer);
                intCurrentQuestion++;
                driver();
            }
        } else {
            //if (!nothingEntered) {
            //  driver();
            //}
            String temp = userAnswerEdit.getText().toString();
            if (temp.equalsIgnoreCase("")) {
                Toast.makeText(TestMode.this, "Just Try", Toast.LENGTH_SHORT).show();
            } else {
                intUserAnswer = Integer.parseInt(temp);
                userAnswerMap.put(intCurrentQuestion, intUserAnswer);
                intCurrentQuestion++;
                driver();

            }
        }
    }

    private void driver() {
        if (intCurrentQuestion == intMaxQuestion + 1) {
            end = true;
        }
        if (!end) {
            checkOperationChoicesShow();
        }

        userAnswerEdit.setText("");

        if (intCurrentQuestion > intMaxQuestion) {
            Intent launchTestEnd = new Intent(this, TestEndActivity.class);
            launchTestEnd.putExtra(MAXQ, intMaxQuestion)
                    .putExtra(Q_MAP, questionMap)
                    .putExtra(A_MAP, answerMap)
                    .putExtra(UA_MAP, userAnswerMap);

            startActivity(launchTestEnd);
            userAnswerEdit.setVisibility(View.INVISIBLE);
        }
    }//close driver method























    /*
    @SuppressLint("SetTextI18n")
    public void onBack(View view) {

        if (intCurrentQuestion > 1) {
            intCurrentQuestion--;
            if (answerMap.get(intCurrentQuestion) != null) {
                intActualAnswer = answerMap.get(intCurrentQuestion);
            }
            if (userAnswerMap.get(intCurrentQuestion) != null) {
                intUserAnswer = userAnswerMap.get(intCurrentQuestion);
            }
            if (questionMap.get(intCurrentQuestion) != null) {
                strQuestion = questionMap.get(intCurrentQuestion);
            }

            String strCurrentQ = getString(R.string.current_question_label) + " " + intCurrentQuestion;
            questionNumTv.setText(strCurrentQ);
            questionTv.setText(strQuestion);
            //userAnswerEdit.setText(intUserAnswer + "");
        } else {
            Toast.makeText(this, "OI, there's no more", Toast.LENGTH_SHORT).show();
        }
    }
     */
/*
    @SuppressLint("SetTextI18n")
    public void onNext(View view) {

        if (intCurrentQuestion < intMaxQuestion && !(intCurrentQuestion < questionMap.size())) {
            String temp = userAnswerEdit.getText().toString();
            if (!temp.equalsIgnoreCase("")) {
                intUserAnswer = Integer.parseInt(temp);
                checkOperationChoicesCheck();
                checkOperationChoicesShow();
            } else {
                Toast.makeText(this, "Just try it", Toast.LENGTH_SHORT).show();
            }
            userAnswerEdit.setText("");
        } else if (intCurrentQuestion < questionMap.size()) {
            intCurrentQuestion++;
            if (answerMap.get(intCurrentQuestion) != null) {
                intActualAnswer = answerMap.get(intCurrentQuestion);
            }
            if (userAnswerMap.get(intCurrentQuestion) != null) {
                intUserAnswer = userAnswerMap.get(intCurrentQuestion);
                //userAnswerEdit.setText(intUserAnswer + "");
            }
            if (questionMap.get(intCurrentQuestion) != null) {
                strQuestion = questionMap.get(intCurrentQuestion);
                questionTv.setText(strQuestion);
            }

            String temp = userAnswerEdit.getText().toString();

            if (!temp.equalsIgnoreCase("")) {
                intUserAnswer = Integer.parseInt(temp);
                userAnswerMap.put(intCurrentQuestion, intUserAnswer);
            } else {
                Toast.makeText(this, "Just try it", Toast.LENGTH_SHORT).show();
            }

            userAnswerEdit.setText("");
            String strCurrentQ = getString(R.string.current_question_label) + " " + intCurrentQuestion;
            questionNumTv.setText(strCurrentQ);
        } else if (intCurrentQuestion >= intMaxQuestion) {
            String temp = userAnswerEdit.getText().toString();

            if (!temp.equalsIgnoreCase("")) {
                intUserAnswer = Integer.parseInt(temp);
                userAnswerMap.put(intCurrentQuestion, intUserAnswer);
                Intent launchTestEnd = new Intent(this, TestEndActivity.class);
                launchTestEnd.putExtra(MAXQ, intMaxQuestion)
                        .putExtra(Q_MAP, questionMap)
                        .putExtra(A_MAP, answerMap)
                        .putExtra(UA_MAP, userAnswerMap);

                startActivity(launchTestEnd);
                userAnswerEdit.setVisibility(View.INVISIBLE);
                //checkAnswers();
            } else {
                Toast.makeText(this, "Just try it", Toast.LENGTH_SHORT).show();
            }
        }
    }


    private void checkOperationChoicesCheck() {
        userAnswerMap.put(intCurrentQuestion, intUserAnswer);
        intCurrentQuestion++;
    }
    */

    private void checkOperationChoicesShow() {
        String strCurrentQ = getString(R.string.current_question_label) + " " + intCurrentQuestion;
        questionNumTv.setText(strCurrentQ);
        int random;
        //Addition: a, Subtraction: s, Multiplication: m, Division: d
        //one checked
        if (chosenOperation.equalsIgnoreCase("a")) {
            add();
        } else if (chosenOperation.equalsIgnoreCase("s")) {
            subtract();
        } else if (chosenOperation.equalsIgnoreCase("m")) {
            multiply();
        } else if (chosenOperation.equalsIgnoreCase("d")) {
            divide();
        } else if (chosenOperation.equalsIgnoreCase("as")) {
            random = (int) (Math.random() * 2) + 1;
            if (random == 1) {
                add();
            } else {
                subtract();
            }
        } else if (chosenOperation.equalsIgnoreCase("am")) {
            random = (int) (Math.random() * 2) + 1;
            if (random == 1) {
                add();
            } else {
                multiply();
            }
        }
        if (chosenOperation.equalsIgnoreCase("ad")) {
            random = (int) (Math.random() * 2) + 1;
            if (random == 1) {
                add();
            } else {
                divide();
            }
        }
        if (chosenOperation.equalsIgnoreCase("sm")) {
            random = (int) (Math.random() * 2) + 1;
            if (random == 1) {
                subtract();
            } else {
                multiply();
            }
        }
        if (chosenOperation.equalsIgnoreCase("sd")) {
            random = (int) (Math.random() * 2) + 1;
            if (random == 1) {
                subtract();
            } else {
                divide();
            }
        }
        if (chosenOperation.equalsIgnoreCase("md")) {
            random = (int) (Math.random() * 2) + 1;
            if (random == 1) {
                multiply();
            } else {
                divide();
            }
        }
        if (chosenOperation.equalsIgnoreCase("asm")) {
            random = (int) (Math.random() * 3) + 1;
            if (random == 1) {
                add();
            } else if (random == 2) {
                subtract();
            } else {
                multiply();
            }
        }
        if (chosenOperation.equalsIgnoreCase("amd")) {
            random = (int) (Math.random() * 3) + 1;
            if (random == 1) {
                add();
            } else if (random == 2) {
                multiply();
            } else {
                divide();
            }
        }
        if (chosenOperation.equalsIgnoreCase("asd")) {
            random = (int) (Math.random() * 3) + 1;
            if (random == 1) {
                add();
            } else if (random == 2) {
                subtract();
            } else {
                divide();
            }
        }
        if (chosenOperation.equalsIgnoreCase("smd")) {
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
        if (chosenOperation.equalsIgnoreCase("asmd")) {
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
    }

    private void add() {
        intNum1 = (int) (Math.random() * intNumLimit) + 1;
        intNum2 = (int) (Math.random() * intNumLimit) + 1;

        intActualAnswer = intNum1 + intNum2;

        strQuestion = (intNum1) + " + " + (intNum2);

        questionTv.setText(strQuestion);

        //map time
        questionMap.put(intCurrentQuestion, strQuestion);
        answerMap.put(intCurrentQuestion, intActualAnswer);
    }

    private void subtract() {
        intNum1 = (int) (Math.random() * intNumLimit) + 1;
        intNum2 = (int) (Math.random() * intNumLimit) + 1;

        intActualAnswer = intNum1 - intNum2;

        strQuestion = (intNum1) + " - " + (intNum2);

        questionTv.setText(strQuestion);

        //map time
        questionMap.put(intCurrentQuestion, strQuestion);
        answerMap.put(intCurrentQuestion, intActualAnswer);
    }

    private void multiply() {
        intNum1 = (int) (Math.random() * intNumLimit) + 1;
        intNum2 = (int) (Math.random() * intNumLimit) + 1;

        intActualAnswer = intNum1 * intNum2;

        strQuestion = (intNum1) + " * " + (intNum2);

        questionTv.setText(strQuestion);

        //map time
        questionMap.put(intCurrentQuestion, strQuestion);
        answerMap.put(intCurrentQuestion, intActualAnswer);
    }

    private void divide() {
        boolean answerIsWhole = true;
        double dblAnswer = 0;
        intNum1 = (int) (Math.random() * intNumLimit) + 1;
        intNum2 = (int) (Math.random() * intNumLimit) + 1;
        intActualAnswer = Math.round((float) intNum1 / intNum2);
        strQuestion = intNum1 + " / " + intNum2;

        questionTv.setText(strQuestion);

        //map time
        questionMap.put(intCurrentQuestion, strQuestion);
        answerMap.put(intCurrentQuestion, intActualAnswer);
    }
}