package com.example.mathme.scores;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "death_table")
public class DeathScores {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "death")
    private int intNumLim, intScore;
    private String strOperators;

    public DeathScores(int numLim, int score, String operators) {
        this.intNumLim = numLim;
        this.intScore = score;
        this.strOperators = operators;
    }

    public int getNumberLimit() {
        return this.intNumLim;
    }

    public int getScore() {
        return this.intScore;
    }

    public String getOperations() {
        return this.strOperators;
    }
}
