package com.github.jsofteng.popularmovies.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Custom movie class for storing details about movies returned by themoviedb.org api
 */

public class Movie implements Parcelable{
    private String title;
    private String poster;
    private String overview;
    private double voteAVG;
    private double popularity;
    private String releaseDate;

    public static final Parcelable.Creator<Movie> CREATOR = new Parcelable.Creator<Movie>(){
        public Movie createFromParcel(Parcel in){
            return new Movie(in);
        }

        @Override
        public Movie[] newArray(int size) {
            return new Movie[size];
        }
    };

    public Movie(){}

    public Movie(String title, String poster, String overview, double voteAVG,double popularity, String releaseDate) {
        this.title = title;
        this.poster = poster;
        this.overview = overview;
        this.voteAVG = voteAVG;
        this.popularity = popularity;
        this.releaseDate = releaseDate;
    }

    private Movie(Parcel in){
        title = in.readString();
        poster = in.readString();
        overview = in.readString();
        voteAVG = in.readDouble();
        popularity = in.readDouble();
        releaseDate = in.readString();
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPoster() {
        return poster;
    }

    public void setPoster(String poster) {
        this.poster = poster;
    }

    public String getOverview() {
        return overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public double getVoteAVG() {
        return voteAVG;
    }

    public void setVoteAVG(double voteAVG) {
        this.voteAVG = voteAVG;
    }

    public double getPopularity() {
        return popularity;
    }

    public void setPopularity(double popularity) {
        this.popularity = popularity;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel out, int flags) {
        out.writeString(title);
        out.writeString(poster);
        out.writeString(overview);
        out.writeDouble(voteAVG);
        out.writeDouble(popularity);
        out.writeString(releaseDate);
    }

}
