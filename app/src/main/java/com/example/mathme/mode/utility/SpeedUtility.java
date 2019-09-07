package com.example.mathme.mode.utility;

import android.widget.EditText;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Timer;

public class SpeedUtility extends ModeUtility {
    public int intStopWatch = 0;
    public String strCurrentQuestionNum;
    public TextView stopWatchTv;

    public SpeedUtility(TextView qNumT, TextView swT, TextView qT, EditText uaE, int numLim, int maxQ, String chosen) {
        this.mQuestionList = new ArrayList<>();
        this.mAnswerList = new ArrayList<>();
        this.mUserAnswerList = new ArrayList<>();
        this.timer = new Timer();
        this.mQuestionNumTv = qNumT;
        this.stopWatchTv = swT;
        this.mQuestionTv = qT;
        this.mUserAnswerEdit = uaE;
        this.intNumLimit = numLim;
        this.intMaxQuestion = maxQ;
        this.strChosenOperation = chosen;
    }

    public void showQuestion() {
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

        intActualAnswer = intNum1 + intNum2;

        strQuestion = (intNum1) + " + " + (intNum2);

        mQuestionTv.setText(strQuestion);

        this.mQuestionList.add(strQuestion);
        this.mAnswerList.add(intActualAnswer);
    }

    private void subtract() {
        intNum1 = (int) (Math.random() * intNumLimit) + 1;
        intNum2 = (int) (Math.random() * intNumLimit) + 1;

        intActualAnswer = intNum1 - intNum2;

        strQuestion = (intNum1) + " - " + (intNum2);

        mQuestionTv.setText(strQuestion);

        this.mQuestionList.add(strQuestion);
        this.mAnswerList.add(intActualAnswer);
    }

    private void multiply() {
        intNum1 = (int) (Math.random() * intNumLimit) + 1;
        intNum2 = (int) (Math.random() * intNumLimit) + 1;

        intActualAnswer = intNum1 * intNum2;

        strQuestion = (intNum1) + " * " + (intNum2);

        mQuestionTv.setText(strQuestion);

        this.mQuestionList.add(strQuestion);
        this.mAnswerList.add(intActualAnswer);
    }

    private void divide() {
        intNum1 = (int) (Math.random() * intNumLimit) + 1;
        intNum2 = (int) (Math.random() * intNumLimit) + 1;
        intActualAnswer = Math.round((float) intNum1 / intNum2);
        strQuestion = intNum1 + " / " + intNum2;

        mQuestionTv.setText(strQuestion);

        this.mQuestionList.add(strQuestion);
        this.mAnswerList.add(intActualAnswer);
    }
}
