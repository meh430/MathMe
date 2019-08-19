package com.example.mathme;

import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.View;

import java.util.ArrayList;

public class TestResults extends AppCompatActivity {
    public static final String TEST_TIME = "TestOrTime";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_results);

        ArrayList<String> resultList;

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent launchMain = new Intent(TestResults.this, MainActivity.class);
                startActivity(launchMain);
            }
        });
        Intent modeFin = getIntent();
        boolean testResults = modeFin.getBooleanExtra(TEST_TIME, true);
        if (testResults) {
            resultList = modeFin.getStringArrayListExtra(TestEndActivity.RESULT_LIST);
        } else {
            resultList = modeFin.getStringArrayListExtra(TimedEndActivity.TIMED_RESULT_LIST);
        }

        //attach adapter to RecyclerView
        RecyclerView recycle = findViewById(R.id.recyclerview);
        ResultListAdapter adapter = new ResultListAdapter(this, resultList);
        recycle.setAdapter(adapter);
        recycle.setLayoutManager(new LinearLayoutManager(this));
    }
}
