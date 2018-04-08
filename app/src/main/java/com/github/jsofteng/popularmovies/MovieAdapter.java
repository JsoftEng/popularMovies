package com.github.jsofteng.popularmovies;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.github.jsofteng.popularmovies.model.Movie;
import com.squareup.picasso.Picasso;

/**
 *  Custom adapter class for handling movie objects
 */

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.MovieAdapterViewHolder> {

    private Movie[] mMovieData;
    private final MovieAdapterOnClickHandler mClickHandler;
    private Context mContext;

    public MovieAdapter(Context context, MovieAdapterOnClickHandler clickHandler){
        mClickHandler = clickHandler;
        mContext = context;
    }

    public interface MovieAdapterOnClickHandler{
        void onClick(Movie movie);
    }

    @Override
    public int getItemCount() {
        if(mMovieData == null){
            return 0;
        }
        return mMovieData.length;
    }

    @Override
    public MovieAdapterViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        int posterLayoutID = R.layout.poster_item;
        LayoutInflater inflater = LayoutInflater.from(context);

        View view = inflater.inflate(posterLayoutID,parent,false);
        return new MovieAdapterViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MovieAdapterViewHolder holder, int position) {
        Movie movieAtThisPosition = mMovieData[position];
        String posterURL = movieAtThisPosition.getPoster();
        Picasso.with(mContext)
                .load(posterURL)
                .placeholder(R.drawable.ic_image_not_available)
                .into(holder.mPoster);
    }

    public void setMovieData(Movie[] movieData){
        mMovieData = movieData;
        notifyDataSetChanged();
    }

    /**
     * Custom nested viewholder class for handling Movie objects
     */

    public class MovieAdapterViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public ImageView mPoster;

        public MovieAdapterViewHolder(View view){
            super(view);
            mPoster = (ImageView) view.findViewById(R.id.iv_poster_main);
            view.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            int adapterPos = getAdapterPosition();
            Movie movie = mMovieData[adapterPos];
            mClickHandler.onClick(movie);
        }
    }
}
