package com.example.mathme.result;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mathme.R;
import com.example.mathme.ends.TestEndActivity;
import com.example.mathme.ends.TimedEndActivity;
import com.example.mathme.other.MainActivity;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

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
