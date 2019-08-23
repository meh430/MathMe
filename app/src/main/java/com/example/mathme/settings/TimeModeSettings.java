package com.example.mathme.settings;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.widget.CheckBox;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.mathme.R;
import com.example.mathme.mode.TimedMode;
import com.example.mathme.other.MainActivity;

public class TimeModeSettings extends AppCompatActivity {
    private AlphaAnimation buttonClick = new AlphaAnimation(1F, 0.4F);
    private CheckBox addCheck, subCheck, multCheck, divCheck;
    private SharedPreferences mPreferences;
    private SeekBar seekMaxNumber;
    RadioGroup timeOptions;
    private boolean addChosen, subChosen, multChosen, divChosen;
    private static final String ADD_TIME = "addTime", SUB_TIME = "subTime", MULT_TIME = "multTime", DIV_TIME = "divTime";
    public static final String MAX_NUM_TIME = "maxNumTime", OPERATIONS_TIME = "operationsTime", TIME_TIME = "TimeTime";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_time_mode_settings);
        final TextView maxNumProgress;

        maxNumProgress = findViewById(R.id.num_limit);
        seekMaxNumber = findViewById(R.id.seek_max_num);
        addCheck = findViewById(R.id.checkBox_plus);
        subCheck = findViewById(R.id.checkBox_minus);
        multCheck = findViewById(R.id.checkbox_multiply);
        divCheck = findViewById(R.id.checkBox_divide);
        timeOptions = findViewById(R.id.timeOptions);
        RadioButton thirty = findViewById(R.id.thirty);
        RadioButton sixty = findViewById(R.id.sixty);
        RadioButton ninety = findViewById(R.id.ninety);
        RadioButton one_twenty = findViewById(R.id.one_twenty);

        seekMaxNumber.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                if (i > 0) {
                    String progress = i * 5 + "";
                    maxNumProgress.setText(progress);
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

        mPreferences = getSharedPreferences(MainActivity.SharedPrefFile, MODE_PRIVATE);
        addChosen = mPreferences.getBoolean(ADD_TIME, false);
        subChosen = mPreferences.getBoolean(SUB_TIME, false);
        multChosen = mPreferences.getBoolean(MULT_TIME, false);
        divChosen = mPreferences.getBoolean(DIV_TIME, false);
        int timeSelected = mPreferences.getInt(TIME_TIME, R.id.thirty);

        switch (timeSelected) {
            case R.id.thirty:
                thirty.setChecked(true);
                break;
            case R.id.sixty:
                sixty.setChecked(true);
                break;
            case R.id.ninety:
                ninety.setChecked(true);
                break;
            case R.id.one_twenty:
                one_twenty.setChecked(true);
        }

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

        seekMaxNumber.setProgress(mPreferences.getInt(MAX_NUM_TIME, 0));

    }//close onCreate

    public void onSaveDefaults(View view) {
        view.startAnimation(buttonClick);
        SharedPreferences.Editor preferenceEditor = mPreferences.edit();
        preferenceEditor.putBoolean(ADD_TIME, addCheck.isChecked());
        preferenceEditor.putBoolean(SUB_TIME, subCheck.isChecked());
        preferenceEditor.putBoolean(MULT_TIME, multCheck.isChecked());
        preferenceEditor.putBoolean(DIV_TIME, divCheck.isChecked());
        preferenceEditor.putInt(MAX_NUM_TIME, seekMaxNumber.getProgress());
        preferenceEditor.putInt(TIME_TIME, timeOptions.getCheckedRadioButtonId());
        preferenceEditor.apply();
        Toast.makeText(this, "Saved", Toast.LENGTH_SHORT).show();
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

    public void onTestLaunch(View view) {
        view.startAnimation(buttonClick);
        int timeSet = 30;
        int timeSelected = timeOptions.getCheckedRadioButtonId();

        switch (timeSelected) {
            case R.id.thirty:
                timeSet = 30;
                break;
            case R.id.sixty:
                timeSet = 60;
                break;
            case R.id.ninety:
                timeSet = 90;
                break;
            case R.id.one_twenty:
                timeSet = 120;
        }

        int intNumLimit = seekMaxNumber.getProgress() * 5;
        String strChosenOperations = "";
        Toast setSetting = Toast.makeText(this, "Please set all values", Toast.LENGTH_SHORT);

        if (intNumLimit == 0) {
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

            Intent launchTime = new Intent(TimeModeSettings.this, TimedMode.class);
            //put chosen operator, max num and questions
            launchTime.putExtra(OPERATIONS_TIME, strChosenOperations);
            launchTime.putExtra(MAX_NUM_TIME, intNumLimit);
            launchTime.putExtra(TIME_TIME, timeSet);

            startActivity(launchTime);
        }
    }
}
