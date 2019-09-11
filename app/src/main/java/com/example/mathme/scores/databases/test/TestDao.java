package com.example.mathme.scores.databases.test;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.example.mathme.scores.TestScore;

import java.util.List;

@Dao
public interface TestDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(TestScore tScore);

    @Query("DELETE FROM test_table")
    void deleteAll();

    @Query("SELECT * from test_table ORDER BY test_num_limit ASC")
    LiveData<List<TestScore>> getAllTestScores();

    @Query("SELECT * from test_table LIMIT 1")
    TestScore[] getAnyTestScore();

    @Delete
    void deleteTestScore(TestScore tScore);
}
