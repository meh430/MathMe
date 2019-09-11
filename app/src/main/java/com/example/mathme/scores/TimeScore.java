package com.example.mathme.scores;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "time_table")
public class TimeScore {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "time")
    private int intNumLim, intScore, intTime;
    private String strOperators;

    public TimeScore(int numLim, int accuracy, int timeChosen, String operators) {
        this.intNumLim = numLim;
        this.intScore = accuracy;
        this.intTime = timeChosen;
        this.strOperators = operators;
    }

    public int getNumberLimit() {
        return this.intNumLim;
    }

    public int getTime() {
        return this.intTime;
    }

    public int getScore() {
        return this.intScore;
    }

    public String getOperations() {
        return this.strOperators;
    }
}
