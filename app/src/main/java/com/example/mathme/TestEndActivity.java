package com.example.mathme;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;


@SuppressWarnings({"ConstantConditions", "unchecked"})
public class TestEndActivity extends AppCompatActivity {
    private HashMap <Integer, String> questionMap;
    private HashMap <Integer, Integer> answerMap, userAnswerMap;
    private int intNumOfQ;
    private final ArrayList<String> resultInfoList = new ArrayList<>();
    TextView fractionResultTv, percentResultTv;
    public static final String RESULT_LIST = "resultInfoList";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_end);

        fractionResultTv = findViewById(R.id.test_result_fraction);
        percentResultTv = findViewById(R.id.test_result_percent);

        Intent testMode = getIntent();
        questionMap = (HashMap<Integer, String>) testMode.getSerializableExtra(TestMode.Q_MAP);
        answerMap = (HashMap<Integer, Integer>) testMode.getSerializableExtra(TestMode.A_MAP);
        userAnswerMap = (HashMap<Integer, Integer>) testMode.getSerializableExtra(TestMode.UA_MAP);
        intNumOfQ = testMode.getIntExtra(TestMode.MAXQ, 0);

        checkAnswers();
    }

    private void checkAnswers() {
        String resultInfo  = "";
        int intNumCorrect = 1;
        for (int i = 1; i < intNumOfQ; i++) {
            int intActualAnswer = answerMap.get(i);
            int intUserAnswer = userAnswerMap.get(i);

            if (intActualAnswer == intUserAnswer) {
                intNumCorrect++;
            } else {
                resultInfo = questionMap.get(i) + " is " + intActualAnswer + ", not " + intUserAnswer;
                resultInfoList.add(resultInfo);
            }
        }
        String resultFraction = intNumCorrect + " / " + intNumOfQ;
        double dblPercent = (double) intNumCorrect / intNumOfQ;
        String resultPercent = (dblPercent*100) + "%";

        Button viewResButt = findViewById(R.id.result_button);

        if (intNumCorrect == intNumOfQ) {
            viewResButt.setVisibility(View.INVISIBLE);
        } else {
            viewResButt.setVisibility(View.VISIBLE);
        }

        fractionResultTv.setText(resultFraction);
        percentResultTv.setText(resultPercent);
    }

    public void onTakeMeHome(View view) {
        Intent takeMeHome = new Intent(this, MainActivity.class);
        startActivity(takeMeHome);
    }

    public void onViewResults(View view) {
        Intent testResults = new Intent(this, TestResults.class);
        testResults.putExtra(RESULT_LIST, resultInfoList);
        startActivity(testResults);
    }
}
