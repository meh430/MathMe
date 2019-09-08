package com.example.mathme.settings;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
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
    private SharedPreferences mPreferences;
    RadioGroup timeOptions;
    private static final String ADD_TIME = "addTime", SUB_TIME = "subTime", MULT_TIME = "multTime", DIV_TIME = "divTime";
    public static final String MAX_NUM_TIME = "maxNumTime", OPERATIONS_TIME = "operationsTime", TIME_TIME = "TimeTime";
    private SettingsUtility timeSetting;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_time_mode_settings);

        timeOptions = findViewById(R.id.timeOptions);
        RadioButton thirty = findViewById(R.id.thirty);
        RadioButton sixty = findViewById(R.id.sixty);
        RadioButton ninety = findViewById(R.id.ninety);
        RadioButton one_twenty = findViewById(R.id.one_twenty);

        timeSetting = new SettingsUtility((CheckBox) findViewById(R.id.checkBox_plus), (CheckBox) findViewById(R.id.checkBox_minus),
                (CheckBox) findViewById(R.id.checkbox_multiply), (CheckBox) findViewById(R.id.checkBox_divide),
                (SeekBar) findViewById(R.id.seek_max_num), (TextView) findViewById(R.id.num_limit));

        timeSetting.seekMaxNumber.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                if (i > 0) {
                    String progress = i * 5 + "";
                    timeSetting.maxNumProgress.setText(progress);
                } else {
                    timeSetting.maxNumProgress.setText("0");
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
        timeSetting.addChosen = mPreferences.getBoolean(ADD_TIME, false);
        timeSetting.subChosen = mPreferences.getBoolean(SUB_TIME, false);
        timeSetting.multChosen = mPreferences.getBoolean(MULT_TIME, false);
        timeSetting.divChosen = mPreferences.getBoolean(DIV_TIME, false);
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

        if (timeSetting.addChosen) {
            timeSetting.addCheck.setChecked(true);
        }

        if (timeSetting.subChosen) {
            timeSetting.subCheck.setChecked(true);
        }

        if (timeSetting.multChosen) {
            timeSetting.multCheck.setChecked(true);
        }

        if (timeSetting.divChosen) {
            timeSetting.divCheck.setChecked(true);
        }

        timeSetting.seekMaxNumber.setProgress(mPreferences.getInt(MAX_NUM_TIME, 0));

    }//close onCreate

    public void onSaveDefaults(View view) {
        SharedPreferences.Editor preferenceEditor = mPreferences.edit();
        preferenceEditor.putBoolean(ADD_TIME, timeSetting.addCheck.isChecked());
        preferenceEditor.putBoolean(SUB_TIME, timeSetting.subCheck.isChecked());
        preferenceEditor.putBoolean(MULT_TIME, timeSetting.multCheck.isChecked());
        preferenceEditor.putBoolean(DIV_TIME, timeSetting.divCheck.isChecked());
        preferenceEditor.putInt(MAX_NUM_TIME, timeSetting.seekMaxNumber.getProgress());
        preferenceEditor.putInt(TIME_TIME, timeOptions.getCheckedRadioButtonId());
        preferenceEditor.apply();
        Toast.makeText(this, "Saved", Toast.LENGTH_SHORT).show();
    }

    public void onOperationChosen(View view) {
        boolean isChecked = ((CheckBox) view).isChecked();

        switch (view.getId()) {
            case R.id.checkBox_plus:
                timeSetting.addChosen = isChecked;
                break;
            case R.id.checkBox_minus:
                timeSetting.subChosen = isChecked;
                break;
            case R.id.checkBox_divide:
                timeSetting.divChosen = isChecked;
                break;

            case R.id.checkbox_multiply:
                timeSetting.multChosen = isChecked;
                break;

            default:
                timeSetting.addChosen = false;
                timeSetting.subChosen = false;
                timeSetting.multChosen = false;
                timeSetting.divChosen = false;
        }
    }

    public void onTestLaunch(View view) {
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

        int intNumLimit = timeSetting.seekMaxNumber.getProgress() * 5;
        Toast setSetting = Toast.makeText(this, "Please set all values", Toast.LENGTH_SHORT);

        if (intNumLimit == 0) {
            setSetting.show();
        } else if (!timeSetting.addChosen && !timeSetting.subChosen && !timeSetting.multChosen && !timeSetting.divChosen) {
            setSetting.show();
        } else {

            timeSetting.setOperations();

            Intent launchTime = new Intent(TimeModeSettings.this, TimedMode.class);
            //put chosen operator, max num and questions
            launchTime.putExtra(OPERATIONS_TIME, timeSetting.strChosenOperations);
            launchTime.putExtra(MAX_NUM_TIME, intNumLimit);
            launchTime.putExtra(TIME_TIME, timeSet);

            startActivity(launchTime);
        }
    }
}
