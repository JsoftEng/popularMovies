package com.github.jsofteng.popularmovies;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.github.jsofteng.popularmovies.model.Movie;
import com.squareup.picasso.Picasso;

public class DetailActivity extends AppCompatActivity {

    private Movie mMovie;
    private ImageView mPoster;
    private TextView mTitle;
    private TextView mOverview;
    private TextView mReleaseDate;
    private TextView mRating;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        mPoster = (ImageView) findViewById(R.id.iv_poster_detail);
        mTitle = (TextView) findViewById(R.id.tv_movie_title);
        mOverview = (TextView) findViewById(R.id.tv_overview);
        mReleaseDate = (TextView) findViewById(R.id.tv_release_date);
        mRating = (TextView) findViewById(R.id.tv_rating);

        Intent intent = getIntent();

        if (intent != null){
            if(intent.hasExtra("movie")){
                mMovie = intent.getParcelableExtra("movie");

                Picasso.with(this)
                        .load(mMovie.getPoster())
                        .placeholder(R.drawable.ic_image_not_available)
                        .into(mPoster);
            }
        }
    }
}
