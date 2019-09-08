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
import com.example.mathme.mode.TestMode;
import com.example.mathme.other.MainActivity;

public class TestModeSettings extends AppCompatActivity {
    private SharedPreferences mPreferences;
    private static final String ADD_TEST = "addTest", SUB_TEST = "subTest", MULT_TEST = "multTest", DIV_TEST = "divTest";
    public static final String MAX_NUM = "maxNum", NUM_Q = "numQ", OPERATIONS = "operations";
    private SettingsUtility testSettings;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_mode_settings);

        testSettings = new SettingsUtility((CheckBox) findViewById(R.id.checkBox_plus), (CheckBox) findViewById(R.id.checkBox_minus),
                (CheckBox) findViewById(R.id.checkbox_multiply), (CheckBox) findViewById(R.id.checkBox_divide),
                (SeekBar) findViewById(R.id.seek_max_num), (SeekBar) findViewById(R.id.seek_max_questions),
                (TextView) findViewById(R.id.num_limit), (TextView) findViewById(R.id.q_limit));

        //seekbar to choose max number
        testSettings.seekMaxNumber.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b){
                //i comes from seek bar and determines the max number
                if (i > 0) {
                    testSettings.maxNumProgress.setText((i * 5) + "");
                } else {
                    testSettings.maxNumProgress.setText("0");
                }
            }
            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {}
            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {}
        });

        //seekbar to choose question number
        testSettings.seekMaxQuestions.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                if (i > 0) {
                    testSettings.maxQuestionProgress.setText((i * 5) + "");
                } else {
                    testSettings.maxQuestionProgress.setText("0");
                }
            }
            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {}
            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {}
        });

        mPreferences = getSharedPreferences(MainActivity.SharedPrefFile, MODE_PRIVATE);
        testSettings.addChosen = mPreferences.getBoolean(ADD_TEST, false);
        testSettings.subChosen = mPreferences.getBoolean(SUB_TEST, false);
        testSettings.multChosen = mPreferences.getBoolean(MULT_TEST, false);
        testSettings.divChosen = mPreferences.getBoolean(DIV_TEST, false);

        if (testSettings.addChosen) {
            testSettings.addCheck.setChecked(true);
        }

        if (testSettings.subChosen) {
            testSettings.subCheck.setChecked(true);
        }

        if (testSettings.multChosen) {
            testSettings.multCheck.setChecked(true);
        }

        if (testSettings.divChosen) {
            testSettings.divCheck.setChecked(true);
        }
        testSettings.seekMaxNumber.setProgress(mPreferences.getInt(MAX_NUM, 0));
        testSettings.seekMaxQuestions.setProgress(mPreferences.getInt(NUM_Q, 0));
    }

    //check to see which checkbox was checked out of all the checkboxes
    public void onOperationChosen(View view) {
        boolean isChecked = ((CheckBox) view).isChecked();

        switch (view.getId()) {
            case R.id.checkBox_plus:
                testSettings.addChosen = isChecked;
                break;
            case R.id.checkBox_minus:
                testSettings.subChosen = isChecked;
                break;
            case R.id.checkBox_divide:
                testSettings.divChosen = isChecked;
                break;

            case R.id.checkbox_multiply:
                testSettings.multChosen = isChecked;
                break;

            default:
                testSettings.addChosen = false;
                testSettings.subChosen = false;
                testSettings.multChosen = false;
                testSettings.divChosen = false;
        }
    }


    public void onTestLaunch(View view) {
        int intNumLimit = testSettings.seekMaxNumber.getProgress() * 5;
        int intMaxQ = testSettings.seekMaxQuestions.getProgress() * 5;
        Toast setSetting = Toast.makeText(this, "Please set all values", Toast.LENGTH_SHORT);

        if (intNumLimit == 0 || intMaxQ == 0) {
            setSetting.show();
        } else if (!testSettings.addChosen && !testSettings.subChosen && !testSettings.multChosen && !testSettings.divChosen) {
            setSetting.show();
        } else {
            testSettings.setOperations();
            Intent launchTest = new Intent(TestModeSettings.this, TestMode.class);
            //put chosen operator, max num and questions
            launchTest.putExtra(OPERATIONS, testSettings.strChosenOperations);
            launchTest.putExtra(MAX_NUM, intNumLimit);
            launchTest.putExtra(NUM_Q, intMaxQ);

            startActivity(launchTest);
        }
    }

    public void onSaveDefaults(View view) {
        SharedPreferences.Editor preferenceEditor = mPreferences.edit();
        preferenceEditor.putBoolean(ADD_TEST, testSettings.addCheck.isChecked());
        preferenceEditor.putBoolean(SUB_TEST, testSettings.subCheck.isChecked());
        preferenceEditor.putBoolean(MULT_TEST, testSettings.multCheck.isChecked());
        preferenceEditor.putBoolean(DIV_TEST, testSettings.divCheck.isChecked());
        preferenceEditor.putInt(MAX_NUM, testSettings.seekMaxNumber.getProgress());
        preferenceEditor.putInt(NUM_Q, testSettings.seekMaxQuestions.getProgress());
        preferenceEditor.apply();
        Toast.makeText(this, "Saved", Toast.LENGTH_SHORT).show();
    }
}
