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
    private SharedPreferences mPreferences;
    private static final String ADD_SPEED = "addSpeed", SUB_SPEED = "subSpeed", MULT_SPEED = "multSpeed", DIV_SPEED = "divSpeed";
    public static final String MAX_NUM_SPEED = "maxNumSpeed", NUM_Q_SPEED = "numQSpeed", OPERATIONS_SPEED = "operationsSpeed";
    private SettingsUtility speedSettings;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_speed_mode_settings);

        speedSettings = new SettingsUtility((CheckBox) findViewById(R.id.checkBox_plus), (CheckBox) findViewById(R.id.checkBox_minus),
                (CheckBox) findViewById(R.id.checkbox_multiply), (CheckBox) findViewById(R.id.checkBox_divide),
                (SeekBar) findViewById(R.id.seek_max_num), (SeekBar) findViewById(R.id.seek_max_questions),
                (TextView) findViewById(R.id.num_limit), (TextView) findViewById(R.id.q_limit));

        //seekbar to choose max number
        speedSettings.seekMaxNumber.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                //i comes from seek bar and determines the max number
                if (i > 0) {
                    speedSettings.maxNumProgress.setText((i * 5) + "");
                } else {
                    speedSettings.maxNumProgress.setText("0");
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
        speedSettings.seekMaxQuestions.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                if (i > 0) {
                    speedSettings.maxQuestionProgress.setText((i * 5) + "");
                } else {
                    speedSettings.maxQuestionProgress.setText("0");
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
        speedSettings.addChosen = mPreferences.getBoolean(ADD_SPEED, false);
        speedSettings.subChosen = mPreferences.getBoolean(SUB_SPEED, false);
        speedSettings.multChosen = mPreferences.getBoolean(MULT_SPEED, false);
        speedSettings.divChosen = mPreferences.getBoolean(DIV_SPEED, false);

        if (speedSettings.addChosen) {
            speedSettings.addCheck.setChecked(true);
        }

        if (speedSettings.subChosen) {
            speedSettings.subCheck.setChecked(true);
        }

        if (speedSettings.multChosen) {
            speedSettings.multCheck.setChecked(true);
        }

        if (speedSettings.divChosen) {
            speedSettings.divCheck.setChecked(true);
        }
        speedSettings.seekMaxNumber.setProgress(mPreferences.getInt(MAX_NUM_SPEED, 0));
        speedSettings.seekMaxQuestions.setProgress(mPreferences.getInt(NUM_Q_SPEED, 0));
    }

    public void onOperationChosen(View view) {
        boolean isChecked = ((CheckBox) view).isChecked();

        switch (view.getId()) {
            case R.id.checkBox_plus:
                speedSettings.addChosen = isChecked;
                break;
            case R.id.checkBox_minus:
                speedSettings.subChosen = isChecked;
                break;
            case R.id.checkBox_divide:
                speedSettings.divChosen = isChecked;
                break;

            case R.id.checkbox_multiply:
                speedSettings.multChosen = isChecked;
                break;

            default:
                speedSettings.addChosen = false;
                speedSettings.subChosen = false;
                speedSettings.multChosen = false;
                speedSettings.divChosen = false;
        }
    }

    public void onSaveDefaults(View view) {
        SharedPreferences.Editor preferenceEditor = mPreferences.edit();
        preferenceEditor.putBoolean(ADD_SPEED, speedSettings.addCheck.isChecked());
        preferenceEditor.putBoolean(SUB_SPEED, speedSettings.subCheck.isChecked());
        preferenceEditor.putBoolean(MULT_SPEED, speedSettings.multCheck.isChecked());
        preferenceEditor.putBoolean(DIV_SPEED, speedSettings.divCheck.isChecked());
        preferenceEditor.putInt(MAX_NUM_SPEED, speedSettings.seekMaxNumber.getProgress());
        preferenceEditor.putInt(NUM_Q_SPEED, speedSettings.seekMaxQuestions.getProgress());
        preferenceEditor.apply();
        Toast.makeText(this, "Saved", Toast.LENGTH_SHORT).show();
    }

    public void onSpeedLaunch(View view) {
        int intNumLimit = speedSettings.seekMaxNumber.getProgress() * 5;
        int intMaxQ = speedSettings.seekMaxQuestions.getProgress() * 5;
        Toast setSetting = Toast.makeText(this, "Please set all values", Toast.LENGTH_SHORT);

        if (intNumLimit == 0 || intMaxQ == 0) {
            setSetting.show();
        } else if (!speedSettings.addChosen && !speedSettings.subChosen && !speedSettings.multChosen && !speedSettings.divChosen) {
            setSetting.show();
        } else {

            speedSettings.setOperations();

            Intent launchSpeed = new Intent(SpeedModeSettings.this, SpeedMode.class);
            //put chosen operator, max num and questions
            launchSpeed.putExtra(OPERATIONS_SPEED, speedSettings.strChosenOperations);
            launchSpeed.putExtra(MAX_NUM_SPEED, intNumLimit);
            launchSpeed.putExtra(NUM_Q_SPEED, intMaxQ);

            startActivity(launchSpeed);
        }
    }
}
