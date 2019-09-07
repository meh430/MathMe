package com.example.mathme.mode.utility;

import android.annotation.SuppressLint;
import android.widget.EditText;
import android.widget.TextView;

import java.util.HashMap;

public class TestUtility extends ModeUtility {
    public HashMap<Integer, String> mQuestionMap;
    public HashMap<Integer, Integer> mAnswerMap;
    public HashMap<Integer, Integer> mUserAnswerMap;

    @SuppressLint("UseSparseArrays")
    public TestUtility(TextView qTv, TextView qNumTv, EditText userEdit, String operator, int numLim, int maxQ) {
        this.mQuestionTv = qTv;
        this.mQuestionNumTv = qNumTv;
        this.mUserAnswerEdit = userEdit;
        this.strChosenOperation = operator;
        this.intNumLimit = numLim;
        this.intMaxQuestion = maxQ;
        this.intCurrentQuestion = 1;
        this.mQuestionMap = new HashMap<>();
        this.mAnswerMap = new HashMap<>();
        this.mUserAnswerMap = new HashMap<>();
    }

    public void lastQuestionNum() {
        this.intCurrentQuestion--;
    }

    public void showQuestion() {
        String strCurrentQ = "Question Number " + intCurrentQuestion;
        mQuestionNumTv.setText(strCurrentQ);
        int random;
        //Addition: a, Subtraction: s, Multiplication: m, Division: d
        //one checked
        if (strChosenOperation.equalsIgnoreCase("a")) {
            add();
        } else if (strChosenOperation.equalsIgnoreCase("s")) {
            subtract();
        } else if (strChosenOperation.equalsIgnoreCase("m")) {
            multiply();
        } else if (strChosenOperation.equalsIgnoreCase("d")) {
            divide();
        } else if (strChosenOperation.equalsIgnoreCase("as")) {
            random = (int) (Math.random() * 2) + 1;
            if (random == 1) {
                add();
            } else {
                subtract();
            }
        } else if (strChosenOperation.equalsIgnoreCase("am")) {
            random = (int) (Math.random() * 2) + 1;
            if (random == 1) {
                add();
            } else {
                multiply();
            }
        }
        if (strChosenOperation.equalsIgnoreCase("ad")) {
            random = (int) (Math.random() * 2) + 1;
            if (random == 1) {
                add();
            } else {
                divide();
            }
        }
        if (strChosenOperation.equalsIgnoreCase("sm")) {
            random = (int) (Math.random() * 2) + 1;
            if (random == 1) {
                subtract();
            } else {
                multiply();
            }
        }
        if (strChosenOperation.equalsIgnoreCase("sd")) {
            random = (int) (Math.random() * 2) + 1;
            if (random == 1) {
                subtract();
            } else {
                divide();
            }
        }
        if (strChosenOperation.equalsIgnoreCase("md")) {
            random = (int) (Math.random() * 2) + 1;
            if (random == 1) {
                multiply();
            } else {
                divide();
            }
        }
        if (strChosenOperation.equalsIgnoreCase("asm")) {
            random = (int) (Math.random() * 3) + 1;
            if (random == 1) {
                add();
            } else if (random == 2) {
                subtract();
            } else {
                multiply();
            }
        }
        if (strChosenOperation.equalsIgnoreCase("amd")) {
            random = (int) (Math.random() * 3) + 1;
            if (random == 1) {
                add();
            } else if (random == 2) {
                multiply();
            } else {
                divide();
            }
        }
        if (strChosenOperation.equalsIgnoreCase("asd")) {
            random = (int) (Math.random() * 3) + 1;
            if (random == 1) {
                add();
            } else if (random == 2) {
                subtract();
            } else {
                divide();
            }
        }
        if (strChosenOperation.equalsIgnoreCase("smd")) {
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
        if (strChosenOperation.equalsIgnoreCase("asmd")) {
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

        this.intActualAnswer = intNum1 + intNum2;

        this.strQuestion = (intNum1) + " + " + (intNum2);

        mQuestionTv.setText(strQuestion);

        //map time
        this.mQuestionMap.put(intCurrentQuestion, strQuestion);
        this.mAnswerMap.put(intCurrentQuestion, intActualAnswer);
    }

    private void subtract() {
        intNum1 = (int) (Math.random() * intNumLimit) + 1;
        intNum2 = (int) (Math.random() * intNumLimit) + 1;

        this.intActualAnswer = intNum1 - intNum2;

        this.strQuestion = (intNum1) + " - " + (intNum2);

        mQuestionTv.setText(strQuestion);

        //map time
        this.mQuestionMap.put(intCurrentQuestion, strQuestion);
        this.mAnswerMap.put(intCurrentQuestion, intActualAnswer);
    }

    private void multiply() {
        intNum1 = (int) (Math.random() * intNumLimit) + 1;
        intNum2 = (int) (Math.random() * intNumLimit) + 1;

        this.intActualAnswer = intNum1 * intNum2;

        this.strQuestion = (intNum1) + " * " + (intNum2);

        mQuestionTv.setText(strQuestion);

        //map time
        this.mQuestionMap.put(intCurrentQuestion, strQuestion);
        this.mAnswerMap.put(intCurrentQuestion, intActualAnswer);
    }

    private void divide() {
        intNum1 = (int) (Math.random() * intNumLimit) + 1;
        intNum2 = (int) (Math.random() * intNumLimit) + 1;
        intActualAnswer = Math.round((float) intNum1 / intNum2);
        this.intActualAnswer = intNum1 / intNum2;

        this.strQuestion = (intNum1) + " / " + (intNum2);

        mQuestionTv.setText(strQuestion);

        //map time
        this.mQuestionMap.put(intCurrentQuestion, strQuestion);
        this.mAnswerMap.put(intCurrentQuestion, intActualAnswer);
    }
}
