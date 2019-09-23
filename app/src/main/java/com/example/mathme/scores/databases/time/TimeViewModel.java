package com.example.mathme.scores.databases.time;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.mathme.scores.TimeScore;

import java.util.List;

public class TimeViewModel extends AndroidViewModel {
    private TimeRepositiory mRepository;
    private LiveData<List<TimeScore>> mAllTimeScores;

    public TimeViewModel(@NonNull Application application) {
        super(application);
        mRepository = new TimeRepositiory(application.getApplicationContext());
        mAllTimeScores = mRepository.getAllTimeScores();
    }

    public LiveData<List<TimeScore>> getAllTimeScores() {
        return mAllTimeScores;
    }

    public LiveData<List<TimeScore>> getTimeScoresD() {
        return mRepository.getTimeScoresD();
    }

    public void deleteAll() {
        mRepository.deleteAll();
    }

    public void insert(TimeScore tScore) {
        mRepository.insert(tScore);
    }

    public void deleteTimeScore(TimeScore tScore) {
        mRepository.deleteTimeScore(tScore);
    }
}
