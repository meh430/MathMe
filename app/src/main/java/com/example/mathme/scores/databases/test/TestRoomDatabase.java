package com.example.mathme.scores.databases.test;


import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.mathme.scores.TestScore;

@Database(entities = {TestScore.class}, version = 3, exportSchema = false)
public abstract class TestRoomDatabase extends RoomDatabase {
    public abstract TestDao testDao();

    private static TestRoomDatabase INSTANCE;

    static TestRoomDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (TestRoomDatabase.class) {
                if (INSTANCE == null) {
                    // Create database here
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            TestRoomDatabase.class, "test_database")
                            // Wipes and rebuilds instead of migrating
                            // if no Migration object.
                            .fallbackToDestructiveMigration()
                            .build();
                }
            }
        }
        return INSTANCE;
    }
}