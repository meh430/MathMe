package com.example.mathme.scores.databases.death;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.mathme.scores.DeathScores;

import java.util.List;

public class DeathViewModel extends AndroidViewModel {
    private DeathRepository mRepository;
    private LiveData<List<DeathScores>> mAllDeathScores;

    public DeathViewModel(@NonNull Application application) {
        super(application);
        mRepository = new DeathRepository(application.getApplicationContext());
        mAllDeathScores = mRepository.getAllDeathScores();
    }

    public LiveData<List<DeathScores>> getAllDeathScores() {
        return mAllDeathScores;
    }

    public LiveData<List<DeathScores>> getDeathScoreD() {
        return mRepository.getTestScoreD();
    }

    public void deleteAll() {
        mRepository.deleteAll();
    }

    public void insert(DeathScores dScore) {
        mRepository.insert(dScore);
    }

    public void deleteDeathScore(DeathScores dScore) {
        mRepository.deleteDeathScore(dScore);
    }
}
