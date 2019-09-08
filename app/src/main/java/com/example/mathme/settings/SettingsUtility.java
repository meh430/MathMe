package com.example.mathme.settings;

import android.widget.CheckBox;
import android.widget.SeekBar;
import android.widget.TextView;

class SettingsUtility {
    String strChosenOperations;
    CheckBox addCheck, subCheck, multCheck, divCheck;
    SeekBar seekMaxNumber, seekMaxQuestions;
    TextView maxNumProgress, maxQuestionProgress;
    boolean addChosen, subChosen, multChosen, divChosen;

    SettingsUtility(CheckBox add, CheckBox sub, CheckBox mult, CheckBox div,
                    SeekBar seekMaxNum, SeekBar seekMaxQ, TextView maxNum, TextView maxQ) {
        this.addCheck = add;
        this.subCheck = sub;
        this.multCheck = mult;
        this.divCheck = div;
        this.seekMaxNumber = seekMaxNum;
        this.seekMaxQuestions = seekMaxQ;
        this.maxNumProgress = maxNum;
        this.maxQuestionProgress = maxQ;
    }

    SettingsUtility(CheckBox add, CheckBox sub, CheckBox mult, CheckBox div,
                    SeekBar seekMaxNum, TextView maxNum) {
        this.addCheck = add;
        this.subCheck = sub;
        this.multCheck = mult;
        this.divCheck = div;
        this.seekMaxNumber = seekMaxNum;
        this.maxNumProgress = maxNum;
    }

    void setOperations() {
        //Addition: a, Subtraction: s, Multiplication: m, Division: d
        //one checked
        if (addChosen) {
            strChosenOperations = "a";
        }
        if (subChosen) {
            strChosenOperations = "s";
        }
        if (multChosen) {
            strChosenOperations = "m";
        }
        if (divChosen) {
            strChosenOperations = "d";
        }
        //two checked
        if (addChosen && subChosen) {
            strChosenOperations = "as";
        }
        if (addChosen && multChosen) {
            strChosenOperations = "am";
        }
        if (addChosen && divChosen) {
            strChosenOperations = "ad";
        }
        if (subChosen && multChosen) {
            strChosenOperations = "sm";
        }
        if (subChosen && divChosen) {
            strChosenOperations = "sd";
        }
        if (multChosen && divChosen) {
            strChosenOperations = "md";
        }
        //three checked
        if (addChosen && subChosen && multChosen) {
            //div left out
            strChosenOperations = "asm";
        }
        if (addChosen && multChosen && divChosen) {
            //sub left out
            strChosenOperations = "amd";
        }
        if (addChosen && subChosen && divChosen) {
            //mult left out
            strChosenOperations = "asd";
        }
        if (subChosen && multChosen && divChosen) {
            //add left out
            strChosenOperations = "smd;";
        }
        //four checked
        if (addChosen && subChosen && multChosen && divChosen) {
            strChosenOperations = "asmd";
        }
    }
}
