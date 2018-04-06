package com.github.jsofteng.popularmovies;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.github.jsofteng.popularmovies.model.Movie;
import com.github.jsofteng.popularmovies.util.JSONParser;
import com.github.jsofteng.popularmovies.util.MovieAdapter;
import com.github.jsofteng.popularmovies.util.Networking;

import java.io.IOException;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private RecyclerView mRecyclerView;
    private MovieAdapter mMovieAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mRecyclerView = (RecyclerView) findViewById(R.id.rv_posters);

        GridLayoutManager layoutManager
                = new GridLayoutManager(this,GridLayoutManager.DEFAULT_SPAN_COUNT);
        mRecyclerView.setHasFixedSize(true);

        mMovieAdapter = new MovieAdapter();

        new FetchMoviesTask().execute(Networking.SortBy.POPASC.toString());
    }

    private class FetchMoviesTask extends AsyncTask<String,Void,ArrayList<Movie>>{
        @Override
        protected void onPostExecute(ArrayList<Movie> moviesList) {
            Log.d("TAG",moviesList.get(0).getPoster());
        }

        @Override
        protected ArrayList<Movie> doInBackground(String... params) {
            try {
                ArrayList<Movie> movieArray = JSONParser.parseMovieJSON
                        (Networking.getResponse(Networking.buildUrl(MainActivity.this,params[0])));
                return movieArray;
            }catch(IOException e){
                e.printStackTrace();
                return null;
            }
        }
    }

}
