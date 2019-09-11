package com.example.mathme.mode.utility;

import android.widget.EditText;
import android.widget.TextView;

public class DeathUtility extends ModeUtility {
    public DeathUtility(TextView scoreT, TextView qT, EditText uEdit, int numLim, String chosen) {
        this.scoreTv = scoreT;
        this.mQuestionTv = qT;
        this.mUserAnswerEdit = uEdit;
        this.intNumLimit = numLim;
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

        this.intActualAnswer = intNum1 + intNum2;

        this.strQuestion = (intNum1) + " + " + (intNum2);

        mQuestionTv.setText(strQuestion);

    }

    private void subtract() {
        intNum1 = (int) (Math.random() * intNumLimit) + 1;
        intNum2 = (int) (Math.random() * intNumLimit) + 1;

        this.intActualAnswer = intNum1 - intNum2;

        this.strQuestion = (intNum1) + " - " + (intNum2);

        mQuestionTv.setText(strQuestion);

    }

    private void multiply() {
        intNum1 = (int) (Math.random() * intNumLimit) + 1;
        intNum2 = (int) (Math.random() * intNumLimit) + 1;

        this.intActualAnswer = intNum1 * intNum2;

        this.strQuestion = (intNum1) + " * " + (intNum2);

        mQuestionTv.setText(strQuestion);
    }

    private void divide() {
        intNum1 = (int) (Math.random() * intNumLimit) + 1;
        intNum2 = (int) (Math.random() * intNumLimit) + 1;

        while (intNum1 % intNum2 != 0) {
            intNum1 = (int) (Math.random() * intNumLimit) + 1;
            intNum2 = (int) (Math.random() * intNumLimit) + 1;
        }
        //this.intActualAnswer = Math.round((float) intNum1 / intNum2);
        this.intActualAnswer = intNum1 / intNum2;

        this.strQuestion = intNum1 + " / " + intNum2;

        mQuestionTv.setText(strQuestion);
    }
}
