package com.example.mathme.scores.databases.test;

import android.content.Context;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import com.example.mathme.scores.TestScore;

import java.util.List;

public class TestRepository {
    private TestDao mTestDao;
    private LiveData<List<TestScore>> mAllTestScores;

    public TestRepository(Context application) {
        TestRoomDatabase db = TestRoomDatabase.getDatabase(application);
        mTestDao = db.testDao();
        mAllTestScores = mTestDao.getAllTestScores();
    }

    LiveData<List<TestScore>> getAllTestScores() {
        return mAllTestScores;
    }

    LiveData<List<TestScore>> getTestScoreD() {
        return mTestDao.getTestScoreD();
    }

    void insert(TestScore tScore) {
        new insertAsyncTask(mTestDao).execute(tScore);
    }

    void deleteAll() {
        new deleteAllTestScoresAsyncTask(mTestDao).execute();
    }

    void deleteTestScore(TestScore tScore) {
        new deleteTestScoreAsyncTask(mTestDao).execute(tScore);
    }

    private static class insertAsyncTask extends AsyncTask<TestScore, Void, Void> {

        private TestDao mAsyncTaskDao;

        insertAsyncTask(TestDao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final TestScore... params) {
            mAsyncTaskDao.insert(params[0]);
            return null;
        }
    }

    private static class deleteAllTestScoresAsyncTask extends AsyncTask<Void, Void, Void> {
        private TestDao mAsyncTaskDao;

        deleteAllTestScoresAsyncTask(TestDao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            mAsyncTaskDao.deleteAll();
            return null;
        }
    }

    private static class deleteTestScoreAsyncTask extends AsyncTask<TestScore, Void, Void> {
        private TestDao mAsyncTaskDao;

        deleteTestScoreAsyncTask(TestDao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final TestScore... params) {
            mAsyncTaskDao.deleteTestScore(params[0]);
            return null;
        }
    }
}
