//TODO: Make a superclass with all the commonly used methods and variables, one called Mode, one called Settings, one called End
package com.example.mathme.mode;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.mathme.R;
import com.example.mathme.ends.DeathEndActivity;
import com.example.mathme.mode.utility.DeathUtility;
import com.example.mathme.mode.utility.ModeUtility;
import com.example.mathme.settings.DeathModeSettings;


public class DeathMode extends AppCompatActivity {
    public static final String SCORE = "Score";
    private ModeUtility deathUtility;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_death_mode);
        Intent deathSettings = getIntent();
        deathUtility = new DeathUtility((TextView) findViewById(R.id.current_score), (TextView) findViewById(R.id.question_window),
                (EditText) findViewById(R.id.answer_edit_text), deathSettings.getIntExtra(DeathModeSettings.MAX_NUM_DEATH, 10),
                deathSettings.getStringExtra(DeathModeSettings.OPERATIONS_DEATH));
    }

    public void onYes(View view) {
        deathUtility.scoreTv.setVisibility(View.VISIBLE);
        view.setVisibility(View.INVISIBLE);
        Button next = findViewById(R.id.next_button);
        next.setVisibility(View.VISIBLE);
        deathUtility.mUserAnswerEdit.setVisibility(View.VISIBLE);
        deathUtility.showQuestion();
    }

    public void onNext(View view) {
        String temp = deathUtility.mUserAnswerEdit.getText().toString();
        if (temp.equalsIgnoreCase("")) {
            Toast.makeText(this, "No harm in guessing!", Toast.LENGTH_SHORT).show();
        } else {
            int intUserAnswer = Integer.parseInt(temp);
            if (intUserAnswer == deathUtility.intActualAnswer) {
                deathUtility.increaseScore();
                deathUtility.showQuestion();
            } else {
                Vibrator v = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    assert v != null;
                    v.vibrate(VibrationEffect.createOneShot(1000, VibrationEffect.DEFAULT_AMPLITUDE));
                } else {
                    //deprecated in API 26
                    assert v != null;
                    v.vibrate(1000);
                }
                //launch end activity
                Intent launchDeathEnd = new Intent(this, DeathEndActivity.class);
                launchDeathEnd.putExtra(SCORE, deathUtility.intScore);
                startActivity(launchDeathEnd);
            }
        }

        String strScore = "Score: " + deathUtility.intScore;
        deathUtility.scoreTv.setText(strScore);
        deathUtility.mUserAnswerEdit.setText("");
    }

}
