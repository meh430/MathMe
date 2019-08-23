package com.example.mathme.result;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mathme.R;

import java.util.ArrayList;

//adapter class for RecyclerView that binds data to the views being recycled
public class ResultListAdapter extends RecyclerView.Adapter<ResultListAdapter.ResultViewHolder> {

    private final ArrayList<String> testResultList;
    private LayoutInflater inflater;

    //constructor passes in the list with test results
    ResultListAdapter(Context context, ArrayList<String> list) {
        inflater = LayoutInflater.from(context);
        this.testResultList = list;
    }

    //method that creates the viewHolder object
    //the parent is the viewGroup into which the new view is added after being bound to an adapter position
    //returns the viewHolder
    @NonNull
    @Override
    public ResultListAdapter.ResultViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = inflater.inflate(R.layout.result_list_item, parent, false);
        return new ResultViewHolder(itemView, this);
    }

    //binds data to viewHolder
    //the holder is the viewHolder that holds the data
    //the position is the current position of the adapter
    @Override
    public void onBindViewHolder(@NonNull ResultListAdapter.ResultViewHolder holder, int position) {
        String current = testResultList.get(position);
        holder.resultItemView.setText(current);
    }

    @Override
    public int getItemCount() {
        return testResultList.size();
    }

    //inner class that gets the view holder and represents each row of data in the RecyclerView
    class ResultViewHolder extends RecyclerView.ViewHolder {
        //the textView in the cardView
        final TextView resultItemView;
        final ResultListAdapter adapter;

        //constructor for the viewHolder that initializes the textViews
        ResultViewHolder(View view, ResultListAdapter adapt) {
            super(view);

            resultItemView = view.findViewById(R.id.wrong_answer);
            this.adapter = adapt;
        }
    }
}
