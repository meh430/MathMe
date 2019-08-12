package com.example.mathme;

import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.View;

import java.util.ArrayList;

public class TestResults extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_results);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent launchMain = new Intent(TestResults.this, MainActivity.class);
                startActivity(launchMain);
            }
        });

        Intent testEnd = getIntent();
        ArrayList<String> resultList = testEnd.getStringArrayListExtra(TestEndActivity.RESULT_LIST);

        RecyclerView recycle = findViewById(R.id.recyclerview);
        ResultListAdapter adapter = new ResultListAdapter(this, resultList);
        recycle.setAdapter(adapter);
        recycle.setLayoutManager(new LinearLayoutManager(this));
    }
}
