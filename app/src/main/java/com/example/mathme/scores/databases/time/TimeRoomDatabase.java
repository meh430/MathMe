package com.example.mathme.scores.databases.time;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.mathme.scores.TimeScore;
import com.example.mathme.scores.databases.test.TestRoomDatabase;

@Database(entities = {TimeScore.class}, version = 1, exportSchema = false)
public abstract class TimeRoomDatabase extends RoomDatabase {
    public abstract TimeDao timeDao();

    private static TimeRoomDatabase INSTANCE;

    static TimeRoomDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (TestRoomDatabase.class) {
                if (INSTANCE == null) {
                    // Create database here
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            TimeRoomDatabase.class, "time_database")
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
