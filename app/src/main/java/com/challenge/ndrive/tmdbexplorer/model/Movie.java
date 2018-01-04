package com.challenge.ndrive.tmdbexplorer.model;

/**
 * Created by marcelo on 03/01/18.
 */

public class Movie {

    private String title;
    private int year;
    private String poster​Image;
    private String backdropPath;
    private double voteAverage;
    private double voteCount;
    private String overview;
    private int revenue;
    private int runtime;

    public Movie(String title, int year, String poster​Image, String backdropPath, double voteAverage, double voteCount, String overview, int revenue, int runtime) {
        this.title = title;
        this.year = year;
        this.poster​Image = poster​Image;
        this.backdropPath = backdropPath;
        this.voteAverage = voteAverage;
        this.voteCount = voteCount;
        this.overview = overview;
        this.revenue = revenue;
        this.runtime = runtime;
    }

    public String getTitle() {
        return title;
    }

    public int getYear() {
        return year;
    }

    public String getPoster​Image() {
        return poster​Image;
    }

    public String getBackdropPath() {
        return backdropPath;
    }

    public double getVoteAverage() {
        return voteAverage;
    }

    public double getVoteCount() {
        return voteCount;
    }

    public String getOverview() {
        return overview;
    }

    public int getRevenue() {
        return revenue;
    }

    public int getRuntime() {
        return runtime;
    }
}
