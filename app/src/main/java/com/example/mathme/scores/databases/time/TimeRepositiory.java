package com.example.mathme.scores.databases.time;

import android.content.Context;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import com.example.mathme.scores.TimeScore;

import java.util.List;

class TimeRepositiory {
    private TimeDao mTimeDao;
    private LiveData<List<TimeScore>> mAllTimeScores;

    TimeRepositiory(Context application) {
        TimeRoomDatabase db = TimeRoomDatabase.getDatabase(application);
        mTimeDao = db.timeDao();
        mAllTimeScores = mTimeDao.getAllTimeScores();
    }

    LiveData<List<TimeScore>> getAllTimeScores() {
        return mAllTimeScores;
    }

    LiveData<List<TimeScore>> getTimeScoresD() {
        return mTimeDao.getTimeScoresD();
    }

    void insert(TimeScore tScore) {
        new insertAsyncTask(mTimeDao).execute(tScore);
    }

    void deleteAll() {
        new deleteAllTimeScoresAsyncTask(mTimeDao).execute();
    }

    void deleteTimeScore(TimeScore tScore) {
        new deleteTimeScoreAsyncTask(mTimeDao).execute(tScore);
    }

    private static class insertAsyncTask extends AsyncTask<TimeScore, Void, Void> {

        private TimeDao mAsyncTaskDao;

        insertAsyncTask(TimeDao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final TimeScore... params) {
            mAsyncTaskDao.insert(params[0]);
            return null;
        }
    }

    private static class deleteAllTimeScoresAsyncTask extends AsyncTask<Void, Void, Void> {
        private TimeDao mAsyncTaskDao;

        deleteAllTimeScoresAsyncTask(TimeDao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            mAsyncTaskDao.deleteAll();
            return null;
        }
    }

    private static class deleteTimeScoreAsyncTask extends AsyncTask<TimeScore, Void, Void> {
        private TimeDao mAsyncTaskDao;

        deleteTimeScoreAsyncTask(TimeDao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final TimeScore... params) {
            mAsyncTaskDao.deleteTimeScore(params[0]);
            return null;
        }
    }
}
