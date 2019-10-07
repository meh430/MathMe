package com.example.mathme.scores.databases.death;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.mathme.scores.DeathScores;

@Database(entities = {DeathScores.class}, version = 1, exportSchema = false)
public abstract class DeathRoomDatabase extends RoomDatabase {
    private static DeathRoomDatabase INSTANCE;

    static DeathRoomDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (DeathRoomDatabase.class) {
                if (INSTANCE == null) {
                    // Create database here
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            DeathRoomDatabase.class, "death_database")
                            // Wipes and rebuilds instead of migrating
                            // if no Migration object.
                            .fallbackToDestructiveMigration()
                            .build();
                }
            }
        }
        return INSTANCE;
    }

    public abstract DeathDao deathDao();
}
