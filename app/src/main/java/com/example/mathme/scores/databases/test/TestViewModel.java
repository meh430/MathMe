package com.example.mathme.scores.databases.test;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.mathme.scores.TestScore;

import java.util.List;

public class TestViewModel extends AndroidViewModel {
    private TestRepository mRepository;
    private LiveData<List<TestScore>> mAllTestScores;

    public TestViewModel(@NonNull Application application) {
        super(application);
        mRepository = new TestRepository(application.getApplicationContext());
        mAllTestScores = mRepository.getAllTestScores();
    }

    public LiveData<List<TestScore>> getAllTestScores() {
        return mAllTestScores;
    }

    public LiveData<List<TestScore>> getTestScoreD() {
        return mRepository.getTestScoreD();
    }

    public void deleteAll() {
        mRepository.deleteAll();
    }

    public void insert(TestScore tScore) {
        mRepository.insert(tScore);
    }

    public void deleteTestScore(TestScore tScore) {
        mRepository.deleteTestScore(tScore);
    }
}
