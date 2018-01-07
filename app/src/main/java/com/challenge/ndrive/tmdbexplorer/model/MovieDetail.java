package com.challenge.ndrive.tmdbexplorer.model;

/**
 * Created by marcelo on 06/01/18.
 */

public class MovieDetail {

    private String mTitle;
    private String mBackdropPath;
    private double mVoteAverage;
    private double mVoteCount;
    private String mOverview;
    private int mRevenue;
    private int mRuntime;

    public MovieDetail(String title, String backdropPath, double voteAverage, double voteCount, String overview, int revenue, int runtime) {
        this.mTitle = title;
        this.mBackdropPath = backdropPath;
        this.mVoteAverage = voteAverage;
        this.mVoteCount = voteCount;
        this.mOverview = overview;
        this.mRevenue = revenue;
        this.mRuntime = runtime;
    }

    public String getTitle() {
        return mTitle;
    }

    public String getBackdropPath() {
        return mBackdropPath;
    }

    public double getVoteAverage() {
        return mVoteAverage;
    }

    public double getVoteCount() {
        return mVoteCount;
    }

    public String getOverview() {
        return mOverview;
    }

    public int getRevenue() {
        return mRevenue;
    }

    public int getRuntime() {
        return mRuntime;
    }
}
