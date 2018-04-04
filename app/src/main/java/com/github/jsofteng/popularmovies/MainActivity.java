package com.github.jsofteng.popularmovies;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.github.jsofteng.popularmovies.model.Movie;
import com.github.jsofteng.popularmovies.util.JSONParser;
import com.github.jsofteng.popularmovies.util.Networking;

import java.io.IOException;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        new testNetwork().execute(Networking.SortBy.POPASC.toString());
    }

    private class testNetwork extends AsyncTask<String,Void,ArrayList<Movie>>{
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
