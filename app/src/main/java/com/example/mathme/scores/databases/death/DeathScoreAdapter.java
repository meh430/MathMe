package com.example.mathme.scores.databases.death;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mathme.R;
import com.example.mathme.scores.DeathScores;

import java.util.List;

public class DeathScoreAdapter extends RecyclerView.Adapter<DeathScoreAdapter.DeathViewHolder> {
    private final LayoutInflater mInflater;
    private List<DeathScores> mDeathScores;

    public DeathScoreAdapter(Context context) {
        mInflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public DeathScoreAdapter.DeathViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = mInflater.inflate(R.layout.death_score_list_item, parent, false);
        return new DeathScoreAdapter.DeathViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull DeathScoreAdapter.DeathViewHolder holder, int position) {
        if (mDeathScores != null) {
            DeathScores current = mDeathScores.get(position);
            holder.bindTo(current);
        }
    }

    public void setTimeScores(List<DeathScores> dScores) {
        mDeathScores = dScores;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        if (mDeathScores != null)
            return mDeathScores.size();
        else return 0;
    }

    public DeathScores getDeathScoreAtPosition(int position) {
        return mDeathScores.get(position);
    }

    class DeathViewHolder extends RecyclerView.ViewHolder {
        private TextView operatorTv, numLimTv, deathScoreTv, dateTv;

        DeathViewHolder(View itemView) {
            super(itemView);

            operatorTv = itemView.findViewById(R.id.operators);
            numLimTv = itemView.findViewById(R.id.num_lim);
            deathScoreTv = itemView.findViewById(R.id.death_score);
            dateTv = itemView.findViewById(R.id.death_date);
        }

        void bindTo(DeathScores deathScore) {
            String date = deathScore.getDate();
            String operators = "Operators: " + deathScore.getStrOperators();
            String numLim = "Number Limit: " + deathScore.getIntNumLim();
            String score = "Score: " + deathScore.getIntScore();

            operatorTv.setText(operators);
            numLimTv.setText(numLim);
            deathScoreTv.setText(score);
            dateTv.setText(date);
        }
    }
}
