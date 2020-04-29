package com.example.quizapp;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> {

    private List<Score> mDataset;


    public MyAdapter(List<Score> dataset)
    {
        mDataset = dataset;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_score, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Score score = mDataset.get(position);
        holder.category.setText(score.category);
        holder.difficulty.setText(score.difficulty);
        holder.score.setText(score.score);
    }

    @Override
    public int getItemCount() {
        return mDataset.size();
    }


    public static class ViewHolder extends RecyclerView.ViewHolder {

        public TextView category;
        public TextView difficulty;
        public TextView score;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            category = itemView.findViewById(R.id.tvCategory);
            difficulty = itemView.findViewById(R.id.tvDifficulty);
            score = itemView.findViewById(R.id.tvScore);
        }
    }
}