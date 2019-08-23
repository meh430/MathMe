package com.example.mathme.settings;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.widget.CheckBox;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.mathme.R;
import com.example.mathme.mode.TestMode;
import com.example.mathme.other.MainActivity;

public class TestModeSettings extends AppCompatActivity {
    private AlphaAnimation buttonClick = new AlphaAnimation(1F, 0.4F);
    private CheckBox addCheck, subCheck, multCheck, divCheck;
    private SharedPreferences mPreferences;
    private SeekBar seekMaxNumber, seekMaxQuestions;
    private boolean addChosen, subChosen, multChosen, divChosen;
    private static final String ADD_TEST = "addTest", SUB_TEST = "subTest", MULT_TEST = "multTest", DIV_TEST = "divTest";

    //key values
    public static final String MAX_NUM = "maxNum", NUM_Q = "numQ", OPERATIONS = "operations";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_mode_settings);

        //initialize seekBar
        seekMaxNumber = findViewById(R.id.seek_max_num);
        seekMaxQuestions = findViewById(R.id.seek_max_questions);
        addCheck = findViewById(R.id.checkBox_plus);
        subCheck = findViewById(R.id.checkBox_minus);
        multCheck = findViewById(R.id.checkbox_multiply);
        divCheck = findViewById(R.id.checkBox_divide);

        final TextView maxNumProgress, maxQuestionProgress;

        //initialize textView
        maxNumProgress = findViewById(R.id.num_limit);
        maxQuestionProgress = findViewById(R.id.q_limit);

        //seekbar to choose max number
        seekMaxNumber.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b){
                //i comes from seek bar and determines the max number
                if (i > 0) {
                    maxNumProgress.setText((i*5) + "");
                } else {
                    maxNumProgress.setText("0");
                }
            }
            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {}
            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {}
        });

        //seekbar to choose question number
        seekMaxQuestions.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                if (i > 0) {
                    maxQuestionProgress.setText((i*5) + "");
                } else {
                    maxQuestionProgress.setText("0");
                }
            }
            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {}
            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {}
        });

        mPreferences = getSharedPreferences(MainActivity.SharedPrefFile, MODE_PRIVATE);
        addChosen = mPreferences.getBoolean(ADD_TEST, false);
        subChosen = mPreferences.getBoolean(SUB_TEST, false);
        multChosen = mPreferences.getBoolean(MULT_TEST, false);
        divChosen = mPreferences.getBoolean(DIV_TEST, false);

        if (addChosen) {
            addCheck.setChecked(true);
        }

        if (subChosen) {
            subCheck.setChecked(true);
        }

        if (multChosen) {
            multCheck.setChecked(true);
        }

        if (divChosen) {
            divCheck.setChecked(true);
        }
        seekMaxNumber.setProgress(mPreferences.getInt(MAX_NUM, 0));
        seekMaxQuestions.setProgress(mPreferences.getInt(NUM_Q, 0));
    }

    //check to see which checkbox was checked out of all the checkboxes
    public void onOperationChosen(View view) {
        boolean isChecked = ((CheckBox) view).isChecked();

        switch (view.getId()) {
            case R.id.checkBox_plus:
                addChosen = isChecked;
                break;
            case R.id.checkBox_minus:
                subChosen = isChecked;
                break;
            case R.id.checkBox_divide:
                divChosen = isChecked;
                break;

            case R.id.checkbox_multiply:
                multChosen = isChecked;
                break;

            default:
                addChosen = false;
                subChosen = false;
                multChosen = false;
                divChosen = false;
        }
    }


    public void onTestLaunch(View view) {
        view.startAnimation(buttonClick);
        int intNumLimit = seekMaxNumber.getProgress() * 5;
        int intMaxQ = seekMaxQuestions.getProgress() * 5;
        String strChosenOperations = "";
        Toast setSetting = Toast.makeText(this, "Please set all values", Toast.LENGTH_SHORT);

        if (intNumLimit == 0 || intMaxQ == 0) {
            setSetting.show();
        } else if (!addChosen && !subChosen && !multChosen && !divChosen) {
            setSetting.show();
        } else {
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

            Intent launchTest = new Intent(TestModeSettings.this, TestMode.class);
            //put chosen operator, max num and questions
            launchTest.putExtra(OPERATIONS, strChosenOperations);
            launchTest.putExtra(MAX_NUM, intNumLimit);
            launchTest.putExtra(NUM_Q, intMaxQ);

            startActivity(launchTest);
        }
    }

    public void onSaveDefaults(View view) {
        view.startAnimation(buttonClick);
        SharedPreferences.Editor preferenceEditor = mPreferences.edit();
        preferenceEditor.putBoolean(ADD_TEST, addCheck.isChecked());
        preferenceEditor.putBoolean(SUB_TEST, subCheck.isChecked());
        preferenceEditor.putBoolean(MULT_TEST, multCheck.isChecked());
        preferenceEditor.putBoolean(DIV_TEST, divCheck.isChecked());
        preferenceEditor.putInt(MAX_NUM, seekMaxNumber.getProgress());
        preferenceEditor.putInt(NUM_Q, seekMaxQuestions.getProgress());
        preferenceEditor.apply();
        Toast.makeText(this, "Saved", Toast.LENGTH_SHORT).show();
    }
}
