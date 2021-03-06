package com.github.jsofteng.popularmovies.util;

import com.github.jsofteng.popularmovies.model.Movie;

import android.util.JsonReader;
import android.util.JsonToken;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;


/**
 * Parsing utility for handling themoviedb.org requests
 */
public final class JSONParser {
    static Movie[] movies = new Movie[20];
    static final String BASE_URL = "http://image.tmdb.org/t/p/";
    static final String FILE_SIZE = "w185";

    /**
     * Opens streams and initializes reader object
     *
     * @param json
     * @return
     * @throws IOException
     */
    public static Movie[] parseMovieJSON(String json) throws IOException{
        InputStream is = new ByteArrayInputStream(json.getBytes(StandardCharsets.UTF_8));
        JsonReader reader = new JsonReader(new InputStreamReader(is));

        readMovie(reader);

        return movies;
    }

    /**
     * Processes movie details from JSON
     *
     * @param reader
     * @throws IOException
     */
    private static void readMovie(JsonReader reader) throws IOException{
        String name;

        reader.beginObject();
        while(reader.hasNext()){
            name = reader.nextName();
            if(name.equals("results")){
                readArray(reader);
            }else{
                reader.skipValue();
            }
        }
        reader.endObject();
    }

    /**
     * Handles processing of array structures in JSON format
     *
     * @param reader
     * @throws IOException
     */
    private static void readArray(JsonReader reader) throws IOException{
        JsonToken checkForNull;
        String name;
        int index = 0;

        reader.beginArray();
        while (reader.hasNext()) {
            Movie movie = new Movie();
            reader.beginObject();
            while (reader.hasNext()) {
                name = reader.nextName();
                if (name.equals("poster_path")) {
                    checkForNull = reader.peek();
                    if (checkForNull != JsonToken.NULL) {
                        movie.setPoster(BASE_URL + FILE_SIZE + reader.nextString());
                    }else{
                        reader.skipValue();
                    }
                } else if (name.equals("title")) {
                    movie.setTitle(reader.nextString());
                } else if (name.equals("popularity")) {
                    movie.setPopularity(reader.nextDouble());
                } else if (name.equals("vote_average")) {
                    movie.setVoteAVG(reader.nextDouble());
                } else if (name.equals("release_date")) {
                    movie.setReleaseDate(reader.nextString());
                } else if (name.equals("overview")) {
                    movie.setOverview(reader.nextString());
                } else {
                    reader.skipValue();
                }
            }
            movies[index] = movie;
            index++;
            reader.endObject();
        }
        reader.endArray();
    }
}
