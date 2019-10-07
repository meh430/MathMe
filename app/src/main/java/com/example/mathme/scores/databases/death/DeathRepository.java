package com.example.mathme.scores.databases.death;

import android.content.Context;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import com.example.mathme.scores.DeathScores;

import java.util.List;

public class DeathRepository {
    private DeathDao mDeathDao;
    private LiveData<List<DeathScores>> mAllDeathScores;

    public DeathRepository(Context application) {
        DeathRoomDatabase db = DeathRoomDatabase.getDatabase(application);
        mDeathDao = db.deathDao();
        mAllDeathScores = mDeathDao.getAllDeathScores();
    }

    LiveData<List<DeathScores>> getAllDeathScores() {
        return mAllDeathScores;
    }

    LiveData<List<DeathScores>> getTestScoreD() {
        return mDeathDao.getDeathScoreD();
    }

    void insert(DeathScores dScores) {
        new DeathRepository.insertAsyncTask(mDeathDao).execute(dScores);
    }

    void deleteAll() {
        new DeathRepository.deleteAllDeathScoresAsyncTask(mDeathDao).execute();
    }

    void deleteDeathScore(DeathScores dScore) {
        new DeathRepository.deleteDeathScoreAsyncTask(mDeathDao).execute(dScore);
    }

    private static class insertAsyncTask extends AsyncTask<DeathScores, Void, Void> {

        private DeathDao mAsyncTaskDao;

        insertAsyncTask(DeathDao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final DeathScores... params) {
            mAsyncTaskDao.insert(params[0]);
            return null;
        }
    }

    private static class deleteAllDeathScoresAsyncTask extends AsyncTask<Void, Void, Void> {
        private DeathDao mAsyncTaskDao;

        deleteAllDeathScoresAsyncTask(DeathDao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            mAsyncTaskDao.deleteAll();
            return null;
        }
    }

    private static class deleteDeathScoreAsyncTask extends AsyncTask<DeathScores, Void, Void> {
        private DeathDao mAsyncTaskDao;

        deleteDeathScoreAsyncTask(DeathDao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final DeathScores... params) {
            mAsyncTaskDao.deleteDeathScore(params[0]);
            return null;
        }
    }
}
