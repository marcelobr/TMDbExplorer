package com.challenge.ndrive.tmdbexplorer.model;

import android.support.annotation.Nullable;
import android.util.Log;

import com.google.gson.annotations.SerializedName;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 * Created by marcelo on 03/01/18.
 */

public class Movie {

    /** Tag for the log messages */
    private static final String LOG_TAG = Movie.class.getSimpleName();

    @SerializedName("id")
    private long mId;
    @SerializedName("title")
    private String mTitle;
    @SerializedName("release_date")
    private String mReleaseDate;
    @SerializedName("poster_path")
    private String mPoster​Image;
    @SerializedName("backdrop_path")
    private String mBackdropPath;
    @SerializedName("vote_average")
    private double mVoteAverage;
    @SerializedName("vote_count")
    private double mVoteCount;
    @SerializedName("overview")
    private String mOverview;
    @SerializedName("revenue")
    private int mRevenue;
    @SerializedName("runtime")
    private int mRuntime;

    public Movie(long id, String title, String releaseDate, String poster​Image) {
        this.mId = id;
        this.mTitle = title;
        this.mReleaseDate = releaseDate;
        this.mPoster​Image = poster​Image;
    }

    public Movie(long id, String title, String releaseDate, String posterImage, String backdropPath, double voteAverage, double voteCount, String overview, int revenue, int runtime) {
        this(id, title, releaseDate, posterImage);
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

    @Nullable
    public Calendar getReleaseDate() {
        Calendar calendar = Calendar.getInstance();

        if (this.mReleaseDate != null) {
            try {
                DateFormat df = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
                calendar.setTime(df.parse(this.mReleaseDate));
            } catch (ParseException e) {
                Log.e(LOG_TAG, "Unknown date format", e);
            }
        }

        return calendar;
    }

    /**
     * @return The year of release date or 0 when unknown.
     */
    public int getYear() {
        Calendar date = getReleaseDate();

        if (date == null) {
            return 0;
        }

        return date.get(Calendar.YEAR);
    }
}
