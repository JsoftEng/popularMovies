package com.github.jsofteng.popularmovies;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Debug;
import android.preference.Preference;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.github.jsofteng.popularmovies.model.Movie;
import com.github.jsofteng.popularmovies.util.JSONParser;
import com.github.jsofteng.popularmovies.util.Networking;

import java.io.IOException;
import java.net.URL;

public class MainActivity extends AppCompatActivity implements MovieAdapter.MovieAdapterOnClickHandler{

    private RecyclerView mRecyclerView;
    private MovieAdapter mMovieAdapter;
    private ProgressBar mProgress;
    private String sortBy;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (savedInstanceState != null){
            sortBy = savedInstanceState.getString("sortBy");
        }else{
            sortBy = Networking.SortBy.POPASC.toString();
        }

        setContentView(R.layout.activity_main);

        mRecyclerView = (RecyclerView) findViewById(R.id.rv_posters);

        GridLayoutManager layoutManager
                = new GridLayoutManager(this,2);
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setHasFixedSize(true);

        mMovieAdapter = new MovieAdapter(MainActivity.this,this);
        mRecyclerView.setAdapter(mMovieAdapter);

        mProgress = (ProgressBar) findViewById(R.id.pb_progress);

        new FetchMoviesTask().execute(sortBy);
    }



    @Override
    public void onClick(Movie movie) {
        launchDetailActivity(movie);
    }

    private class FetchMoviesTask extends AsyncTask<String,Void,Movie[]>{

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            mProgress.setVisibility(View.VISIBLE);
        }

        @Override
        protected Movie[] doInBackground(String... params) {
            try {
                String sortKey = params[0];
                URL movieRequestURL = Networking.buildUrl(MainActivity.this,sortKey);

                Movie[] movieArray = JSONParser.parseMovieJSON
                        (Networking.getResponse(movieRequestURL));
                return movieArray;
            }catch(IOException e){
                e.printStackTrace();
                return null;
            }
        }

        @Override
        protected void onPostExecute(Movie[] movieArray) {
            mProgress.setVisibility(View.INVISIBLE);
            mMovieAdapter.setMovieData(movieArray);
        }
    }


    @Override
    protected void onSaveInstanceState(Bundle savedInstanceState) {
        savedInstanceState.putString("sortBy",sortBy);

        super.onSaveInstanceState(savedInstanceState);
    }

    public void launchDetailActivity(Movie movie){
        Intent intentLaunchDetailActivity = new Intent(this,DetailActivity.class);

        intentLaunchDetailActivity.putExtra("movie",movie);
        startActivity(intentLaunchDetailActivity);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu,menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        switch (id){
            case R.id.menu_sort:
                Toast.makeText(this,"Sort button pressed",Toast.LENGTH_SHORT)
                        .show();
                break;
            case R.id.sort_pop_asc:
                sortBy = Networking.SortBy.POPASC.toString();
                new FetchMoviesTask().execute(sortBy);
                Toast.makeText(this,"Sorted by popularity (ascending)",Toast.LENGTH_SHORT)
                        .show();
                break;
            case R.id.sort_pop_desc:
                sortBy = Networking.SortBy.POPDESC.toString();
                new FetchMoviesTask().execute(sortBy);
                Toast.makeText(this,"Sorted by popularity (descending)",Toast.LENGTH_SHORT)
                        .show();
                break;
            case R.id.sort_rate_asc:
                sortBy = Networking.SortBy.RATEASC.toString();
                new FetchMoviesTask().execute(sortBy);
                Toast.makeText(this,"Sorted by rating (ascending)",Toast.LENGTH_SHORT)
                        .show();
                break;
            case R.id.sort_rate_desc:
                sortBy = Networking.SortBy.RATEDESC.toString();
                new FetchMoviesTask().execute(sortBy);
                Toast.makeText(this,"Sorted by rating (descending)",Toast.LENGTH_SHORT)
                        .show();
                break;
        }

        return super.onOptionsItemSelected(item);
    }
}
