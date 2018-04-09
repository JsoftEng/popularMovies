package com.github.jsofteng.popularmovies;

import android.content.Intent;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.github.jsofteng.popularmovies.model.Movie;
import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;

public class DetailActivity extends AppCompatActivity {

    private Movie mMovie;
    private ImageView mPoster;
    private TextView mTitle;
    private TextView mOverviewLBL;
    private TextView mOverview;
    private TextView mReleaseDateLBL;
    private TextView mReleaseDate;
    private TextView mRatingLBL;
    private TextView mRating;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        mPoster = (ImageView) findViewById(R.id.iv_poster_detail);
        mTitle = (TextView) findViewById(R.id.tv_movie_title);
        mOverviewLBL = (TextView) findViewById(R.id.tv_overview_lbl);
        mOverview = (TextView) findViewById(R.id.tv_overview);
        mReleaseDateLBL = (TextView) findViewById(R.id.tv_release_date_lbl);
        mReleaseDate = (TextView) findViewById(R.id.tv_release_date);
        mRatingLBL = (TextView) findViewById(R.id.tv_rating_lbl);
        mRating = (TextView) findViewById(R.id.tv_rating);

        Intent intent = getIntent();

        if (intent != null){
            if(intent.hasExtra("movie")){
                mMovie = intent.getParcelableExtra("movie");

                Picasso.with(this)
                        .load(mMovie.getPoster())
                        .placeholder(R.drawable.ic_image_not_available)
                        .into(mPoster);

                populateUI();
            }
        }
    }

    private void populateUI(){
        if(mMovie.getTitle() != null && !mMovie.getTitle().equals("")){
            mTitle.setText(mMovie.getTitle());
            mTitle.setVisibility(View.VISIBLE);
        }else{
            mTitle.setVisibility(View.GONE);
        }
        if(mMovie.getOverview() != null && !mMovie.getOverview().equals("")){
            mOverview.setText(mMovie.getOverview());
            mOverviewLBL.setVisibility(View.VISIBLE);
            mOverview.setVisibility(View.VISIBLE);
        }else{
            mOverviewLBL.setVisibility(View.GONE);
            mOverview.setVisibility(View.GONE);
        }
        if(mMovie.getReleaseDate() != null && !mMovie.getReleaseDate().equals("")){
            mReleaseDate.setText(mMovie.getReleaseDate());
            mReleaseDateLBL.setVisibility(View.VISIBLE);
            mReleaseDate.setVisibility(View.VISIBLE);
        }else{
            mReleaseDateLBL.setVisibility(View.GONE);
            mReleaseDate.setVisibility(View.GONE);
        }
        if(mMovie.getVoteAVG() != 0){
            mRating.setText(String.valueOf(mMovie.getVoteAVG()));
            mRatingLBL.setVisibility(View.VISIBLE);
            mRating.setVisibility(View.VISIBLE);
        }else{
            mRatingLBL.setVisibility(View.GONE);
            mRating.setVisibility(View.GONE);
        }
    }
}
