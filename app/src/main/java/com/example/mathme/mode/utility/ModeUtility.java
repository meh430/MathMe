//TODO: Get rid of the magic strings

package com.example.mathme.mode.utility;

import android.widget.EditText;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Timer;

public class ModeUtility {
    int intNumLimit, intNum1, intNum2;
    public int intUserAnswer = 0, intCurrentQuestion, intActualAnswer = 0, intMaxQuestion, intScore = 0;
    String strChosenOperation;
    public String strQuestion;
    public TextView mQuestionTv, scoreTv, mQuestionNumTv;
    public EditText mUserAnswerEdit;
    public ArrayList<String> mQuestionList;
    public ArrayList<Integer> mAnswerList;
    public ArrayList<Integer> mUserAnswerList;
    public Timer timer;

    ModeUtility() {
    }

    public void showQuestion() {
    }

    public void nextQuestionNum() {
        this.intCurrentQuestion++;
    }

    public void increaseScore() {
        this.intScore++;
    }

}
