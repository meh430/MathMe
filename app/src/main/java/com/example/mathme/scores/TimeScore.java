package com.example.mathme.scores;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "time_table")
public class TimeScore {
    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    @ColumnInfo(name = "time_date")
    private String date;

    public int getIntNumLim() {
        return intNumLim;
    }

    public void setIntNumLim(int intNumLim) {
        this.intNumLim = intNumLim;
    }

    @ColumnInfo(name = "time_num_lim")
    private int intNumLim;

    public int getIntScore() {
        return intScore;
    }

    public void setIntScore(int intScore) {
        this.intScore = intScore;
    }

    @ColumnInfo(name = "time_score")
    private int intScore;

    public int getIntTime() {
        return intTime;
    }

    public void setIntTime(int intTime) {
        this.intTime = intTime;
    }

    @ColumnInfo(name = "time_time")
    private int intTime;

    public String getStrOperators() {
        return strOperators;
    }

    public void setStrOperators(String strOperators) {
        this.strOperators = strOperators;
    }

    @ColumnInfo(name = "time_operators")
    private String strOperators;

    public int getTestId() {
        return testId;
    }

    public void setTestId(int testId) {
        this.testId = testId;
    }

    @PrimaryKey(autoGenerate = true)
    private int testId;

    public TimeScore(int intNumLim, int intScore, int intTime, String strOperators, int testId, String date) {
        this.intNumLim = intNumLim;
        this.intScore = intScore;
        this.intTime = intTime;
        this.strOperators = strOperators;
        this.testId = testId;
        this.date = date;
    }
}
