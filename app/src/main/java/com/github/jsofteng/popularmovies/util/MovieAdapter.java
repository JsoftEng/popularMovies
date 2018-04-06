package com.github.jsofteng.popularmovies.util;

import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import com.github.jsofteng.popularmovies.util.MovieAdapterViewHolder;

/**
 *  Custom adapter class for handling movie objects
 */

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapterViewHolder> {

    public interface MovieAdapterOnClickHandler{
        void onClick(String movieTitle);
    }

    @Override
    public int getItemCount() {
        return 0;
    }

    @Override
    public MovieAdapterViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(MovieAdapterViewHolder holder, int position) {

    }
}
