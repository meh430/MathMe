package com.example.mathme.scores.databases.time;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mathme.R;
import com.example.mathme.scores.TimeScore;

import java.util.List;

public class TimeScoreAdapter extends RecyclerView.Adapter<TimeScoreAdapter.TimeViewHolder> {
    private final LayoutInflater mInflater;
    private List<TimeScore> mTimeScores;

    public TimeScoreAdapter(Context context) {
        mInflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public TimeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = mInflater.inflate(R.layout.time_score_list_item, parent, false);
        return new TimeViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull TimeScoreAdapter.TimeViewHolder holder, int position) {
        if (mTimeScores != null) {
            TimeScore current = mTimeScores.get(position);
            holder.bindTo(current);
        }
    }

    public void setTimeScores(List<TimeScore> tScores) {
        mTimeScores = tScores;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        if (mTimeScores != null)
            return mTimeScores.size();
        else return 0;
    }

    public TimeScore getTimeScoreAtPosition(int position) {
        return mTimeScores.get(position);
    }

    class TimeViewHolder extends RecyclerView.ViewHolder {
        private TextView operatorTv, timeTv, numLimTv, timeScoreTv, dateTv;

        TimeViewHolder(View itemView) {
            super(itemView);

            operatorTv = itemView.findViewById(R.id.operators);
            numLimTv = itemView.findViewById(R.id.num_lim);
            timeTv = itemView.findViewById(R.id.time);
            timeScoreTv = itemView.findViewById(R.id.time_score);
            dateTv = itemView.findViewById(R.id.time_date);
        }

        void bindTo(TimeScore timeScore) {
            String date = timeScore.getDate();
            String operators = "Operators: " + timeScore.getStrOperators();
            String time = "Time: " + timeScore.getIntTime();
            String numLim = "Number Limit: " + timeScore.getIntNumLim();
            String score = "Time Score: " + timeScore.getIntScore();

            operatorTv.setText(operators);
            timeTv.setText(time);
            numLimTv.setText(numLim);
            timeScoreTv.setText(score);
            dateTv.setText(date);
        }
    }
}
