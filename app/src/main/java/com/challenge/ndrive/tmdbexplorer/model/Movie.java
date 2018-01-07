package com.challenge.ndrive.tmdbexplorer.model;

/**
 * Created by marcelo on 03/01/18.
 */

public class Movie {

    private int mId;
    private String mTitle;
    private String mReleaseDate;
    private String mPoster​Image;

    public Movie(int id, String title, String releaseDate, String poster​Image) {
        this.mId = id;
        this.mTitle = title;
        this.mReleaseDate = releaseDate;
        this.mPoster​Image = poster​Image;
    }

    public int getId() {
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
}
