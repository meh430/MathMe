package com.example.mathme.lists;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
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
import com.example.mathme.scores.TimeScore;
import com.example.mathme.scores.databases.time.TimeScoreAdapter;
import com.example.mathme.scores.databases.time.TimeViewModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

public class TimeScoreList extends AppCompatActivity {
    TimeScoreAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_time_score_list);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        RecyclerView recyclerView = findViewById(R.id.timeScoreRecycler);
        mAdapter = new TimeScoreAdapter(this);
        recyclerView.setAdapter(mAdapter);
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
                        TimeScore tScore = mAdapter.getTimeScoreAtPosition(position);
                        // Delete the word
                        MainActivity.mTimeViewModel.deleteTimeScore(tScore);
                    }
                });

        helper.attachToRecyclerView(recyclerView);
        MainActivity.mTimeViewModel = ViewModelProviders.of(this).get(TimeViewModel.class);
        MainActivity.mTimeViewModel.getAllTimeScores().observe(this, new Observer<List<TimeScore>>() {
            @Override
            public void onChanged(@Nullable final List<TimeScore> tScores) {
                mAdapter.setTimeScores(tScores);
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.sort_test, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml-v25.
        int id = item.getItemId();

        switch (id) {
            //launch settings
            case R.id.action_sort_asc:
                MainActivity.mTimeViewModel.getAllTimeScores().observe(this, new Observer<List<TimeScore>>() {
                    @Override
                    public void onChanged(@Nullable final List<TimeScore> tScores) {
                        // Update the cached copy of the words in the adapter.
                        mAdapter.setTimeScores(tScores);
                    }
                });
                return true;
            case R.id.action_sort_desc:
                MainActivity.mTimeViewModel.getTimeScoresD().observe(this, new Observer<List<TimeScore>>() {
                    @Override
                    public void onChanged(@Nullable final List<TimeScore> tScores) {
                        // Update the cached copy of the words in the adapter.
                        mAdapter.setTimeScores(tScores);
                    }
                });
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

}
