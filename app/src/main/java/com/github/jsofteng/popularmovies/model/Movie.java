package com.github.jsofteng.popularmovies.model;

/**
 * Custom movie class for storing details about movies returned by themoviedb.org api
 */

public class Movie {
    private String title;
    private String poster;
    private String overview;
    private double voteAVG;
    private double popularity;
    private String releaseDate;

    public Movie(){}

    public Movie(String title, String poster, String overview, double voteAVG,double popularity, String releaseDate) {
        this.title = title;
        this.poster = poster;
        this.overview = overview;
        this.voteAVG = voteAVG;
        this.popularity = popularity;
        this.releaseDate = releaseDate;
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
}
