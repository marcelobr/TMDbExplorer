package com.challenge.ndrive.tmdbexplorer.loader;

import android.content.Context;
import android.support.v4.content.AsyncTaskLoader;

import com.challenge.ndrive.tmdbexplorer.model.Movie;
import com.challenge.ndrive.tmdbexplorer.utils.TmdbData;


/**
 * Created by marcelo on 06/01/18.
 */

public class MovieDetailLoader extends AsyncTaskLoader<Movie> {
    /** Query URL */
    private String mUrl;

    /**
     * Constructs a new {@link Movie}.
     *
     * @param context of the activity
     * @param url to load data from
     */
    public MovieDetailLoader(Context context, String url) {
        super(context);
        mUrl = url;
    }

    @Override
    protected void onStartLoading() {
        forceLoad();
    }

    /**
     * This is on a background thread.
     */
    @Override
    public Movie loadInBackground() {
        if (mUrl == null) {
            return null;
        }

        // Perform the network request, parse the response, and extract a list of movie details.
        Movie movie = TmdbData.fetchMovieDetailData(mUrl);
        return movie;
    }
}
