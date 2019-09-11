package com.example.mathme.lists;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mathme.R;
import com.example.mathme.other.MainActivity;
import com.example.mathme.scores.TestScore;
import com.example.mathme.scores.databases.test.TestScoreAdapter;
import com.example.mathme.scores.databases.test.TestViewModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

public class TestScoreList extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_score_list);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        RecyclerView recyclerView = findViewById(R.id.testScoreRecycler);
        final TestScoreAdapter adapter = new TestScoreAdapter(this);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        ItemTouchHelper helper = new ItemTouchHelper(
                new ItemTouchHelper.SimpleCallback(0,
                        ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
                    @Override
                    public boolean onMove(@NonNull RecyclerView recyclerView,
                                          @NonNull RecyclerView.ViewHolder viewHolder,
                                          @NonNull RecyclerView.ViewHolder target) {
                        return false;
                    }

                    @Override
                    public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                        int position = viewHolder.getAdapterPosition();
                        TestScore tScore = adapter.getTestScoreAtPosition(position);
                        // Delete the word
                        MainActivity.mTestViewModel.deleteTestScore(tScore);
                    }
                });

        helper.attachToRecyclerView(recyclerView);
        MainActivity.mTestViewModel = ViewModelProviders.of(this).get(TestViewModel.class);
        MainActivity.mTestViewModel.getAllTestScores().observe(this, new Observer<List<TestScore>>() {
            @Override
            public void onChanged(@Nullable final List<TestScore> tScores) {
                // Update the cached copy of the words in the adapter.
                adapter.setTestScores(tScores);
            }
        });

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MainActivity.mTestViewModel.deleteAll();
            }
        });
    }

}
