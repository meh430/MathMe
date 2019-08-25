package com.example.mathme.settings;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.mathme.R;
import com.example.mathme.mode.SpeedMode;
import com.example.mathme.other.MainActivity;

public class SpeedModeSettings extends AppCompatActivity {
    private CheckBox addCheck, subCheck, multCheck, divCheck;
    private SharedPreferences mPreferences;
    private SeekBar seekMaxNumber, seekMaxQuestions;
    private boolean addChosen, subChosen, multChosen, divChosen;
    private static final String ADD_SPEED = "addSpeed", SUB_SPEED = "subSpeed", MULT_SPEED = "multSpeed", DIV_SPEED = "divSpeed";
    //key values
    public static final String MAX_NUM_SPEED = "maxNumSpeed", NUM_Q_SPEED = "numQSpeed", OPERATIONS_SPEED = "operationsSpeed";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_speed_mode_settings);

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
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                //i comes from seek bar and determines the max number
                if (i > 0) {
                    maxNumProgress.setText((i * 5) + "");
                } else {
                    maxNumProgress.setText("0");
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
            }
        });

        //seekbar to choose question number
        seekMaxQuestions.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                if (i > 0) {
                    maxQuestionProgress.setText((i * 5) + "");
                } else {
                    maxQuestionProgress.setText("0");
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
            }
        });

        mPreferences = getSharedPreferences(MainActivity.SharedPrefFile, MODE_PRIVATE);
        addChosen = mPreferences.getBoolean(ADD_SPEED, false);
        subChosen = mPreferences.getBoolean(SUB_SPEED, false);
        multChosen = mPreferences.getBoolean(MULT_SPEED, false);
        divChosen = mPreferences.getBoolean(DIV_SPEED, false);

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
        seekMaxNumber.setProgress(mPreferences.getInt(MAX_NUM_SPEED, 0));
        seekMaxQuestions.setProgress(mPreferences.getInt(NUM_Q_SPEED, 0));
    }

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

    public void onSaveDefaults(View view) {
        SharedPreferences.Editor preferenceEditor = mPreferences.edit();
        preferenceEditor.putBoolean(ADD_SPEED, addCheck.isChecked());
        preferenceEditor.putBoolean(SUB_SPEED, subCheck.isChecked());
        preferenceEditor.putBoolean(MULT_SPEED, multCheck.isChecked());
        preferenceEditor.putBoolean(DIV_SPEED, divCheck.isChecked());
        preferenceEditor.putInt(MAX_NUM_SPEED, seekMaxNumber.getProgress());
        preferenceEditor.putInt(NUM_Q_SPEED, seekMaxQuestions.getProgress());
        preferenceEditor.apply();
        Toast.makeText(this, "Saved", Toast.LENGTH_SHORT).show();
    }

    public void onSpeedLaunch(View view) {
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

            Intent launchSpeed = new Intent(SpeedModeSettings.this, SpeedMode.class);
            //put chosen operator, max num and questions
            launchSpeed.putExtra(OPERATIONS_SPEED, strChosenOperations);
            launchSpeed.putExtra(MAX_NUM_SPEED, intNumLimit);
            launchSpeed.putExtra(NUM_Q_SPEED, intMaxQ);

            startActivity(launchSpeed);
        }
    }
}
