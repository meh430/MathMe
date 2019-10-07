package com.example.mathme.scores.databases.death;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.example.mathme.scores.DeathScores;

import java.util.List;

@Dao
public interface DeathDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(DeathScores dScore);

    @Query("DELETE FROM death_table")
    void deleteAll();

    @Query("SELECT * from death_table ORDER BY death_score ASC")
    LiveData<List<DeathScores>> getAllDeathScores();

    @Query("SELECT * from death_table ORDER BY death_score DESC")
    LiveData<List<DeathScores>> getDeathScoreD();

    @Query("SELECT * from death_table LIMIT 1")
    DeathScores[] getAnyDeathScore();

    @Delete
    void deleteDeathScore(DeathScores dScores);
}
