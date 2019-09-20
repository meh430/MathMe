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
import com.example.mathme.scores.TimeScore;
import com.example.mathme.scores.databases.time.TimeScoreAdapter;
import com.example.mathme.scores.databases.time.TimeViewModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

public class TimeScoreList extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_time_score_list);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        RecyclerView recyclerView = findViewById(R.id.timeScoreRecycler);
        final TimeScoreAdapter adapter = new TimeScoreAdapter(this);
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
                        TimeScore tScore = adapter.getTimeScoreAtPosition(position);
                        // Delete the word
                        MainActivity.mTimeViewModel.deleteTimeScore(tScore);
                    }
                });

        helper.attachToRecyclerView(recyclerView);
        MainActivity.mTimeViewModel = ViewModelProviders.of(this).get(TimeViewModel.class);
        MainActivity.mTimeViewModel.getAllTimeScores().observe(this, new Observer<List<TimeScore>>() {
            @Override
            public void onChanged(@Nullable final List<TimeScore> tScores) {
                adapter.setTimeScores(tScores);
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
