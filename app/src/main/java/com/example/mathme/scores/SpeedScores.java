package com.example.mathme.scores;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "speed_table")
public class SpeedScores {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "speed")
    private int intNumLim, intNumOfQ, intAccuracy, intTimeTaken;
    private String strOperators;

    public SpeedScores(int numLim, int numq, int accuracy, int timeTaken, String operators) {
        this.intNumLim = numLim;
        this.intNumOfQ = numq;
        this.intAccuracy = accuracy;
        this.intTimeTaken = timeTaken;
        this.strOperators = operators;
    }

    public int getNumberLimit() {
        return this.intNumLim;
    }

    public int getNumberQuestions() {
        return this.intNumOfQ;
    }

    public int getTime() {
        return this.intTimeTaken;
    }

    public int getScore() {
        return this.intAccuracy;
    }

    public String getOperations() {
        return this.strOperators;
    }
}
