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
    private CheckBox addCheck, subCheck, multCheck, divCheck;
    private SharedPreferences mPreferences;
    private SeekBar seekMaxNumber;
    private boolean addChosen, subChosen, multChosen, divChosen;
    private static final String ADD_DEATH = "addDeath", SUB_DEATH = "subDeath", MULT_DEATH = "multDeath", DIV_DEATH = "divDeath";
    public static final String MAX_NUM_DEATH = "maxNumDeath", OPERATIONS_DEATH = "operationsDeath";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_death_mode_settings);

        final TextView maxNumProgress = findViewById(R.id.num_limit);
        seekMaxNumber = findViewById(R.id.seek_max_num);
        addCheck = findViewById(R.id.checkBox_plus);
        subCheck = findViewById(R.id.checkBox_minus);
        multCheck = findViewById(R.id.checkbox_multiply);
        divCheck = findViewById(R.id.checkBox_divide);

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
        addChosen = mPreferences.getBoolean(ADD_DEATH, false);
        subChosen = mPreferences.getBoolean(SUB_DEATH, false);
        multChosen = mPreferences.getBoolean(MULT_DEATH, false);
        divChosen = mPreferences.getBoolean(DIV_DEATH, false);

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

        seekMaxNumber.setProgress(mPreferences.getInt(MAX_NUM_DEATH, 0));
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
        preferenceEditor.putBoolean(ADD_DEATH, addCheck.isChecked());
        preferenceEditor.putBoolean(SUB_DEATH, subCheck.isChecked());
        preferenceEditor.putBoolean(MULT_DEATH, multCheck.isChecked());
        preferenceEditor.putBoolean(DIV_DEATH, divCheck.isChecked());
        preferenceEditor.putInt(MAX_NUM_DEATH, seekMaxNumber.getProgress());
        preferenceEditor.apply();
        Toast.makeText(this, "Saved", Toast.LENGTH_SHORT).show();
    }

    public void onStartDeath(View view) {
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

            Intent launchDeath = new Intent(DeathModeSettings.this, DeathMode.class);
            //put chosen operator, max num and questions
            launchDeath.putExtra(OPERATIONS_DEATH, strChosenOperations);
            launchDeath.putExtra(MAX_NUM_DEATH, intNumLimit);

            startActivity(launchDeath);
        }
    }
}
