package com.github.jsofteng.popularmovies;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

/**
 *  Custom adapter class for handling movie objects
 */

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapterViewHolder> {

    private final MovieAdapterOnClickHandler mClickHandler;

    public MovieAdapter(MovieAdapterOnClickHandler clickHandler){
        mClickHandler = clickHandler;
    }
    public interface MovieAdapterOnClickHandler{
        void onClick(String movieTitle);
    }

    @Override
    public int getItemCount() {
        return 0;
    }

    @Override
    public MovieAdapterViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        int posterLayoutID = R.layout.poster_item;
        LayoutInflater inflater = LayoutInflater.from(context);
        return null;
    }

    @Override
    public void onBindViewHolder(MovieAdapterViewHolder holder, int position) {

    }
}
