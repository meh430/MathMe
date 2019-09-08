package com.example.mathme.mode;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.mathme.R;
import com.example.mathme.ends.TestEndActivity;
import com.example.mathme.mode.utility.ModeUtility;
import com.example.mathme.mode.utility.TestUtility;
import com.example.mathme.settings.TestModeSettings;

//do not change anything, pls, it works. I don't know why but it works. Pls don't mess it up
@SuppressWarnings("ConstantConditions")
public class TestMode extends AppCompatActivity {
    private ModeUtility testUtility;
    private boolean savedAnswer = false, end = false;
    public static final String Q_MAP = "QMAP", A_MAP = "AMAP", UA_MAP = "UAMAP";
    private boolean firsTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_mode);
        Intent testSettings = getIntent();

        testUtility = new TestUtility((TextView) findViewById(R.id.question_window),
                (TextView) findViewById(R.id.current_question_num),
                (EditText) findViewById(R.id.answer_edit_text),
                testSettings.getStringExtra(TestModeSettings.OPERATIONS),
                testSettings.getIntExtra(TestModeSettings.MAX_NUM, 100),
                testSettings.getIntExtra(TestModeSettings.NUM_Q, 100));
    }

    public void onYes(View view) {
        ((TestUtility) testUtility).mQuestionNumTv.setVisibility(View.VISIBLE);
        view.setVisibility(View.INVISIBLE);
        LinearLayout buttonBar = findViewById(R.id.test_buttons);
        buttonBar.setVisibility(View.VISIBLE);
        Button saveButton = findViewById(R.id.save_button);
        saveButton.setVisibility(View.VISIBLE);
        testUtility.mUserAnswerEdit.setVisibility(View.VISIBLE);
        driver();
        firsTime = true;
    }

    public void onSave(View view) {
        savedAnswer = true;
        String temp = testUtility.mUserAnswerEdit.getText().toString();
        if (temp.equalsIgnoreCase("")) {
            Toast.makeText(this, "Nothing to save here", Toast.LENGTH_SHORT).show();
        } else {
            testUtility.intUserAnswer = Integer.parseInt(temp);
            ((TestUtility) testUtility).mUserAnswerMap.put(((TestUtility) testUtility).intCurrentQuestion, testUtility.intUserAnswer);
            Toast.makeText(this, "saved", Toast.LENGTH_SHORT).show();
        }
    }

    public void onNext(View view) {
        if (savedAnswer && ((TestUtility) testUtility).mUserAnswerMap.get(((TestUtility) testUtility).intCurrentQuestion) != null) {
            testUtility.mUserAnswerEdit.setText("");
            if (firsTime) {
                firsTime = false;
                testUtility.nextQuestionNum();
                driver();
                savedAnswer = false;
            } else if (((TestUtility) testUtility).intCurrentQuestion < ((TestUtility) testUtility).mQuestionMap.size()) {
                testUtility.nextQuestionNum();
                if (((TestUtility) testUtility).mAnswerMap.get(((TestUtility) testUtility).intCurrentQuestion) != null) {
                    testUtility.intActualAnswer =
                            ((TestUtility) testUtility).mAnswerMap.get(((TestUtility) testUtility).intCurrentQuestion);
                }
                if (((TestUtility) testUtility).mUserAnswerMap.get(((TestUtility) testUtility).intCurrentQuestion) != null) {
                    testUtility.intUserAnswer =
                            ((TestUtility) testUtility).mUserAnswerMap.get(((TestUtility) testUtility).intCurrentQuestion);
                    String strCurrentUA = testUtility.intUserAnswer + "";
                    testUtility.mUserAnswerEdit.setText(strCurrentUA);
                }
                if (((TestUtility) testUtility).mQuestionMap.get(((TestUtility) testUtility).intCurrentQuestion) != null) {
                    testUtility.strQuestion =
                            ((TestUtility) testUtility).mQuestionMap.get(((TestUtility) testUtility).intCurrentQuestion);
                }

                String strCurrentQ = getString(R.string.current_question_label) + " " + ((TestUtility) testUtility).intCurrentQuestion;
                ((TestUtility) testUtility).mQuestionNumTv.setText(strCurrentQ);
                testUtility.mQuestionTv.setText(testUtility.strQuestion);
                //questionNumTv.setText(strCurrentQ);
            } else {
                testUtility.nextQuestionNum();
                driver();
                savedAnswer = false;
            }
        } else {
            Toast.makeText(this, "No harm in guessing!", Toast.LENGTH_SHORT).show();
        }
    }

    @SuppressLint("SetTextI18n")
    public void onBack(View view) {
        end = false;
        String strCurrentQ;
        if (((TestUtility) testUtility).intCurrentQuestion <= 1) {
            Toast.makeText(this, "Oi, you can't go back no mo", Toast.LENGTH_SHORT).show();
        } else {
            ((TestUtility) testUtility).lastQuestionNum();
            if (((TestUtility) testUtility).mAnswerMap.get(((TestUtility) testUtility).intCurrentQuestion) != null) {
                testUtility.intActualAnswer =
                        ((TestUtility) testUtility).mAnswerMap.get(((TestUtility) testUtility).intCurrentQuestion);
            }
            if (((TestUtility) testUtility).mUserAnswerMap.get(((TestUtility) testUtility).intCurrentQuestion) != null) {
                testUtility.intUserAnswer =
                        ((TestUtility) testUtility).mUserAnswerMap.get(((TestUtility) testUtility).intCurrentQuestion);
                String strCurrentUA = testUtility.intUserAnswer + "";
                testUtility.mUserAnswerEdit.setText(strCurrentUA);
            }
            if (((TestUtility) testUtility).mQuestionMap.get(((TestUtility) testUtility).intCurrentQuestion) != null) {
                testUtility.strQuestion =
                        ((TestUtility) testUtility).mQuestionMap.get(((TestUtility) testUtility).intCurrentQuestion);
            }

            strCurrentQ = getString(R.string.current_question_label) + " " + ((TestUtility) testUtility).intCurrentQuestion;
            ((TestUtility) testUtility).mQuestionNumTv.setText(strCurrentQ);
            testUtility.mQuestionTv.setText(testUtility.strQuestion);
        }
    }

    private void driver() {
        if (((TestUtility) testUtility).intCurrentQuestion == (testUtility.intMaxQuestion + 1)) {
            end = true;
        }
        if (!end) {
            testUtility.showQuestion();
        }
        testUtility.mUserAnswerEdit.setText("");
        if (((TestUtility) testUtility).intCurrentQuestion > testUtility.intMaxQuestion) {
            //launch the test end activity
            Intent launchTestEnd = new Intent(this, TestEndActivity.class);
            launchTestEnd.putExtra(TestModeSettings.NUM_Q, testUtility.intMaxQuestion)
                    .putExtra(Q_MAP, ((TestUtility) testUtility).mQuestionMap)
                    .putExtra(A_MAP, ((TestUtility) testUtility).mAnswerMap)
                    .putExtra(UA_MAP, ((TestUtility) testUtility).mUserAnswerMap);

            startActivity(launchTestEnd);
            testUtility.mUserAnswerEdit.setVisibility(View.INVISIBLE);
            finish();
        }
    }//close driver met
}//close class