package com.example.mathme;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class ResultListAdapter extends RecyclerView.Adapter<ResultListAdapter.ResultViewHolder> {
    private final ArrayList<String> testResultList;
    private LayoutInflater inflater;

    public ResultListAdapter (Context context, ArrayList<String> list) {
        inflater = LayoutInflater.from(context);
        this.testResultList = list;
    }

    @NonNull
    @Override
    public ResultListAdapter.ResultViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = inflater.inflate(R.layout.result_list_item, parent, false);
        return new ResultViewHolder(itemView, this);
    }

    @Override
    public void onBindViewHolder(@NonNull ResultListAdapter.ResultViewHolder holder, int position) {
        String current = testResultList.get(position);
        holder.resultItemView.setText(current);
    }

    @Override
    public int getItemCount() {
        return testResultList.size();
    }

    //inner class that gets the view holder
    class ResultViewHolder extends RecyclerView.ViewHolder {
        public final TextView resultItemView;
        final ResultListAdapter adapter;

        public ResultViewHolder(View view, ResultListAdapter adapt) {
            super(view);

            resultItemView = view.findViewById(R.id.wrong_answer);
            this.adapter = adapt;
        }
    }
}
