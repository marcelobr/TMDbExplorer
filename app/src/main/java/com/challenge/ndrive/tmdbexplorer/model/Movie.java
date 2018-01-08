package com.challenge.ndrive.tmdbexplorer.model;

/**
 * Created by marcelo on 03/01/18.
 */

public class Movie {

    private long mId;
    private String mTitle;
    private String mReleaseDate;
    private String mPoster​Image;
    private String mBackdropPath;
    private double mVoteAverage;
    private double mVoteCount;
    private String mOverview;
    private int mRevenue;
    private int mRuntime;

    public Movie(long id, String title, String releaseDate, String poster​Image) {
        this.mId = id;
        this.mTitle = title;
        this.mReleaseDate = releaseDate;
        this.mPoster​Image = poster​Image;
    }

    public Movie(String title, String posterImage, String backdropPath, double voteAverage, double voteCount, String overview, int revenue, int runtime) {
        this.mTitle = title;
        this.mPoster​Image = posterImage;
        this.mBackdropPath = backdropPath;
        this.mVoteAverage = voteAverage;
        this.mVoteCount = voteCount;
        this.mOverview = overview;
        this.mRevenue = revenue;
        this.mRuntime = runtime;
    }

    public long getId() {
        return mId;
    }

    public String getTitle() {
        return mTitle;
    }

    public String getReleaseDate() {
        return mReleaseDate;
    }

    public String getPoster​Image() {
        return mPoster​Image;
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
