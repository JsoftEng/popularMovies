package com.github.jsofteng.popularmovies;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
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
            sortBy = "popularity";
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
                if(Networking.hasConnection()) {
                    String sortKey = params[0];
                    URL movieRequestURL = Networking.buildUrl(MainActivity.this, sortKey);

                    Movie[] movieArray = JSONParser.parseMovieJSON
                            (Networking.getResponse(movieRequestURL));
                    return movieArray;
                }else{
                    launchNoConnectionActivity();
                    return null;
                }
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

    public void launchNoConnectionActivity(){
        Intent intentLaunchDetailActivity = new Intent(this,NoConnectionActivity.class);

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
            case R.id.sort_popularity:
                sortBy = "popularity";
                new FetchMoviesTask().execute(sortBy);
                Toast.makeText(this,"Sorted by popularity",Toast.LENGTH_SHORT)
                        .show();
                break;
            case R.id.sort_top_rated:
                sortBy = "top_rated";
                new FetchMoviesTask().execute(sortBy);
                Toast.makeText(this,"Sorted by top rated",Toast.LENGTH_SHORT)
                        .show();
                break;
        }

        return super.onOptionsItemSelected(item);
    }
}
