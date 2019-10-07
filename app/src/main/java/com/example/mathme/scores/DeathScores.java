package com.example.mathme.scores;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "death_table")
public class DeathScores {
    @PrimaryKey(autoGenerate = true)
    private int id;
    @ColumnInfo(name = "death_num_limit")
    private int intNumLim;
    @ColumnInfo(name = "death_score")
    private int intScore;
    @ColumnInfo(name = "death_operators")
    private String strOperators;
    @ColumnInfo(name = "death_date")
    private String date;

    public DeathScores(int intNumLim, int intScore, String strOperators, int id, String date) {
        this.intNumLim = intNumLim;
        this.intScore = intScore;
        this.strOperators = strOperators;
        this.id = id;
        this.date = date;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIntNumLim() {
        return intNumLim;
    }

    public void setIntNumLim(int intNumLim) {
        this.intNumLim = intNumLim;
    }

    public int getIntScore() {
        return intScore;
    }

    public void setIntScore(int intScore) {
        this.intScore = intScore;
    }

    public String getStrOperators() {
        return strOperators;
    }

    public void setStrOperators(String strOperators) {
        this.strOperators = strOperators;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
