package com.github.jsofteng.popularmovies;

import android.content.Intent;
import android.os.AsyncTask;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mRecyclerView = (RecyclerView) findViewById(R.id.rv_posters);

        GridLayoutManager layoutManager
                = new GridLayoutManager(this,2);
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setHasFixedSize(true);

        mMovieAdapter = new MovieAdapter(MainActivity.this,this);
        mRecyclerView.setAdapter(mMovieAdapter);

        mProgress = (ProgressBar) findViewById(R.id.pb_progress);

        new FetchMoviesTask().execute(Networking.SortBy.POPASC.toString());
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

        if(id == R.id.menu_sort){
            Toast.makeText(this,"Sort button pressed",Toast.LENGTH_SHORT)
                    .show();
        }

        return super.onOptionsItemSelected(item);
    }
}
