package com.example.mathme.scores.databases.time;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.example.mathme.scores.TimeScore;

import java.util.List;

@Dao
public interface TimeDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(TimeScore tScore);

    @Query("DELETE FROM time_table")
    void deleteAll();

    @Query("SELECT * from time_table ORDER BY time_score ASC")
    LiveData<List<TimeScore>> getAllTimeScores();

    @Query("SELECT * from time_table ORDER BY time_score DESC")
    LiveData<List<TimeScore>> getTimeScoresD();

    @Query("SELECT * from time_table LIMIT 1")
    TimeScore[] getAnyTimeScore();

    @Delete
    void deleteTimeScore(TimeScore tScore);
}
