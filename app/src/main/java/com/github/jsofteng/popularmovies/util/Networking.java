package com.github.jsofteng.popularmovies.util;

import android.content.Context;
import android.net.Uri;
import android.util.JsonReader;

import com.github.jsofteng.popularmovies.R;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.InetSocketAddress;
import java.net.MalformedURLException;
import java.net.Socket;
import java.net.SocketAddress;
import java.net.URL;
import java.util.Scanner;

/**
 * Custom utilities class for handling network activity for the Popular Movies app
 */

public final class Networking {

    /**
     * Creates url for moviedb api endpoint using android uri builder
     *
     * @param context
     * @param sortCriteria
     * @return moviedb api endpoint as url
     * @throws IOException
     */
    public static URL buildUrl(Context context, String sortCriteria) throws IOException {
        String apiKey = getKey(context);
        String baseURL = "";

        switch(sortCriteria){
            case "popularity":
                baseURL = "http://api.themoviedb.org/3/movie/popular";
                break;
            case "top_rated":
                baseURL = "http://api.themoviedb.org/3/movie/top_rated";
        }

        Uri builtUri = Uri.parse(baseURL).buildUpon()
                .appendQueryParameter("api_key",apiKey)
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
     * @return key value as string
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
     * @return http response as string
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

    /**
     * Determines if internet connection is available
     *
     * @return false if no connection available
     */
    public static boolean hasConnection(){
        try{
            int timeoutMS = 1500;
            Socket socket = new Socket();
            SocketAddress sockaddr = new InetSocketAddress("8.8.8.8",53);

            socket.connect(sockaddr,timeoutMS);
            socket.close();

            return true;
        } catch(IOException e){
            e.printStackTrace();
            return false;
        }
    }

}
