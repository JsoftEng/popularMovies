package com.github.jsofteng.popularmovies.util;

import android.content.Context;
import android.net.Uri;
import android.util.JsonReader;

import com.github.jsofteng.popularmovies.R;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;

/**
 * Custom utilities class for handling network activity for the Popular Movies app
 */

public final class Networking {
    private static final String BASE_URL = "http://api.themoviedb.org/3/discover/movie";

    /**
     * Enumeration of sort parameters
     */
    public enum SortBy {
        POPDESC("popularity.desc"),
        POPASC("popularity.asc"),
        RATEDESC("vote_average.desc"),
        RATEASC("vote_average.asc");

        private final String text;

        SortBy(final String text){
            this.text = text;
        }

        @Override
        public String toString() {
            return text;
        }
    }

    public static URL buildUrl(Context context, String sortCriteria) throws IOException {
        String apiKey = getKey(context);

        Uri builtUri = Uri.parse(BASE_URL).buildUpon()
                .appendQueryParameter("api_key",apiKey)
                .appendQueryParameter("sort_by", sortCriteria)
                .build();

        URL url = null;
        try {
            url = new URL(builtUri.toString());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        return url;
    }

    /**
     * Used to get the api key value (stored in json format).
     *
     * @param context
     * @return
     * @throws IOException
     */
    private static String getKey(Context context) throws IOException {
        String key = "";
        InputStream is = context.getResources().openRawResource(R.raw.keyconfig);
        JsonReader reader = new JsonReader(new InputStreamReader(is,"UTF-8"));

        reader.beginObject();
        while (reader.hasNext()){
            String name = reader.nextName();
            if (name.equals("key")){
                key = reader.nextString();
            }
        }
        reader.endObject();
        return key;
    }

    /**
     * Used to get the http response from supplied moviedb api endpoint
     *
     * @param url
     * @return
     * @throws IOException
     */
    public static String getResponse(URL url) throws IOException {
        HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
        try {
            InputStream in = urlConnection.getInputStream();

            Scanner scanner = new Scanner(in);
            scanner.useDelimiter("\\A");

            boolean hasInput = scanner.hasNext();
            if (hasInput) {
                return scanner.next();
            } else {
                return null;
            }
        } finally {
            urlConnection.disconnect();
        }
    }

}
