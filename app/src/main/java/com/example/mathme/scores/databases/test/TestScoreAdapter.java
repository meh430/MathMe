package com.example.mathme.scores.databases.test;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mathme.R;
import com.example.mathme.scores.TestScore;

import java.util.List;

public class TestScoreAdapter extends RecyclerView.Adapter<TestScoreAdapter.TestViewHolder> {
    private final LayoutInflater mInflater;
    private List<TestScore> mTestScores;

    public TestScoreAdapter(Context context) {
        mInflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public TestViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = mInflater.inflate(R.layout.test_score_list_item, parent, false);
        return new TestViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull TestViewHolder holder, int position) {
        if (mTestScores != null) {
            TestScore current = mTestScores.get(position);
            holder.bindTo(current);
        }
    }

    public void setTestScores(List<TestScore> tScores) {
        mTestScores = tScores;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        if (mTestScores != null)
            return mTestScores.size();
        else return 0;
    }

    public TestScore getTestScoreAtPosition(int position) {
        return mTestScores.get(position);
    }

    class TestViewHolder extends RecyclerView.ViewHolder {
        private TextView operatorTv, numLimTv, numQTv, testScoreTv, dateTv;

        TestViewHolder(View itemView) {
            super(itemView);

            operatorTv = itemView.findViewById(R.id.operators);
            numLimTv = itemView.findViewById(R.id.num_lim);
            numQTv = itemView.findViewById(R.id.num_questions);
            testScoreTv = itemView.findViewById(R.id.test_score);
            dateTv = itemView.findViewById(R.id.test_date);
        }

        void bindTo(TestScore testScore) {
            String date = testScore.getDate();
            String operators = "Operators: " + testScore.getStrOperators();
            String numQ = "Number of Questions: " + testScore.getIntNumOfQ();
            String numLim = "Number Limit: " + testScore.getIntNumLim();
            String score = "Test Score: " + testScore.getDblTestScore();

            operatorTv.setText(operators);
            numQTv.setText(numQ);
            numLimTv.setText(numLim);
            testScoreTv.setText(score);
            dateTv.setText(date);
        }
    }
}
