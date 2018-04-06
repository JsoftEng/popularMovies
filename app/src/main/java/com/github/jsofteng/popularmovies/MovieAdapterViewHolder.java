package com.github.jsofteng.popularmovies;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;

/**
 * Custom viewholder class for handling Movie objects
 */

public class MovieAdapterViewHolder extends RecyclerView.ViewHolder implements OnClickListener {

    public ImageView mPoster;

    public MovieAdapterViewHolder(View view){
        super(view);
        mPoster = (ImageView) findV
    }

    @Override
    public void onClick(View v) {
        //TODO
    }
}
