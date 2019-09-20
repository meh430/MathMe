package com.example.mathme.scores;


import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "test_table")
public class TestScore {
    public int getIntNumLim() {
        return intNumLim;
    }

    public void setIntNumLim(int intNumLim) {
        this.intNumLim = intNumLim;
    }

    @ColumnInfo(name = "test_num_limit")
    private int intNumLim;

    public int getIntNumOfQ() {
        return intNumOfQ;
    }

    public void setIntNumOfQ(int intNumOfQ) {
        this.intNumOfQ = intNumOfQ;
    }

    @ColumnInfo(name = "test_num_q")
    private int intNumOfQ;

    public double getDblTestScore() {
        return dblTestScore;
    }

    public void setDblTestScore(double dblTestScore) {
        this.dblTestScore = dblTestScore;
    }

    @ColumnInfo(name = "test_Score")
    private double dblTestScore;

    public String getStrOperators() {
        return strOperators;
    }

    public void setStrOperators(String strOperators) {
        this.strOperators = strOperators;
    }

    @ColumnInfo(name = "test_operators")
    private String strOperators;

    public int getTestId() {
        return testId;
    }

    public void setTestId(int testId) {
        this.testId = testId;
    }

    @PrimaryKey(autoGenerate = true)
    private int testId;

    public TestScore(int intNumLim, int intNumOfQ, double dblTestScore, String strOperators, int testId) {
        this.intNumLim = intNumLim;
        this.intNumOfQ = intNumOfQ;
        this.dblTestScore = dblTestScore;
        this.strOperators = strOperators;
        this.testId = testId;
    }
}
