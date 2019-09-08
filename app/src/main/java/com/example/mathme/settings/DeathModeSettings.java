package com.example.mathme.settings;

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
import com.example.mathme.mode.DeathMode;
import com.example.mathme.other.MainActivity;

public class DeathModeSettings extends AppCompatActivity {
    private SharedPreferences mPreferences;
    private static final String ADD_DEATH = "addDeath", SUB_DEATH = "subDeath", MULT_DEATH = "multDeath", DIV_DEATH = "divDeath";
    public static final String MAX_NUM_DEATH = "maxNumDeath", OPERATIONS_DEATH = "operationsDeath";
    private SettingsUtility deathSettings;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_death_mode_settings);

        deathSettings = new SettingsUtility((CheckBox) findViewById(R.id.checkBox_plus), (CheckBox) findViewById(R.id.checkBox_minus),
                (CheckBox) findViewById(R.id.checkbox_multiply), (CheckBox) findViewById(R.id.checkBox_divide),
                (SeekBar) findViewById(R.id.seek_max_num), (TextView) findViewById(R.id.num_limit));

        deathSettings.seekMaxNumber.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                if (i > 0) {
                    String progress = i * 5 + "";
                    deathSettings.maxNumProgress.setText(progress);
                } else {
                    deathSettings.maxNumProgress.setText("0");
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
        deathSettings.addChosen = mPreferences.getBoolean(ADD_DEATH, false);
        deathSettings.subChosen = mPreferences.getBoolean(SUB_DEATH, false);
        deathSettings.multChosen = mPreferences.getBoolean(MULT_DEATH, false);
        deathSettings.divChosen = mPreferences.getBoolean(DIV_DEATH, false);

        if (deathSettings.addChosen) {
            deathSettings.addCheck.setChecked(true);
        }

        if (deathSettings.subChosen) {
            deathSettings.subCheck.setChecked(true);
        }

        if (deathSettings.multChosen) {
            deathSettings.multCheck.setChecked(true);
        }

        if (deathSettings.divChosen) {
            deathSettings.divCheck.setChecked(true);
        }

        deathSettings.seekMaxNumber.setProgress(mPreferences.getInt(MAX_NUM_DEATH, 0));
    }

    public void onOperationChosen(View view) {
        boolean isChecked = ((CheckBox) view).isChecked();

        switch (view.getId()) {
            case R.id.checkBox_plus:
                deathSettings.addChosen = isChecked;
                break;
            case R.id.checkBox_minus:
                deathSettings.subChosen = isChecked;
                break;
            case R.id.checkBox_divide:
                deathSettings.divChosen = isChecked;
                break;

            case R.id.checkbox_multiply:
                deathSettings.multChosen = isChecked;
                break;

            default:
                deathSettings.addChosen = false;
                deathSettings.subChosen = false;
                deathSettings.multChosen = false;
                deathSettings.divChosen = false;
        }
    }

    public void onSaveDefaults(View view) {
        SharedPreferences.Editor preferenceEditor = mPreferences.edit();
        preferenceEditor.putBoolean(ADD_DEATH, deathSettings.addCheck.isChecked());
        preferenceEditor.putBoolean(SUB_DEATH, deathSettings.subCheck.isChecked());
        preferenceEditor.putBoolean(MULT_DEATH, deathSettings.multCheck.isChecked());
        preferenceEditor.putBoolean(DIV_DEATH, deathSettings.divCheck.isChecked());
        preferenceEditor.putInt(MAX_NUM_DEATH, deathSettings.seekMaxNumber.getProgress());
        preferenceEditor.apply();
        Toast.makeText(this, "Saved", Toast.LENGTH_SHORT).show();
    }

    public void onStartDeath(View view) {
        int intNumLimit = deathSettings.seekMaxNumber.getProgress() * 5;
        Toast setSetting = Toast.makeText(this, "Please set all values", Toast.LENGTH_SHORT);

        if (intNumLimit == 0) {
            setSetting.show();
        } else if (!deathSettings.addChosen && !deathSettings.subChosen && !deathSettings.multChosen && !deathSettings.divChosen) {
            setSetting.show();
        } else {
            deathSettings.setOperations();
            Intent launchDeath = new Intent(DeathModeSettings.this, DeathMode.class);
            //put chosen operator, max num and questions
            launchDeath.putExtra(OPERATIONS_DEATH, deathSettings.strChosenOperations);
            launchDeath.putExtra(MAX_NUM_DEATH, intNumLimit);

            startActivity(launchDeath);
        }
    }
}
