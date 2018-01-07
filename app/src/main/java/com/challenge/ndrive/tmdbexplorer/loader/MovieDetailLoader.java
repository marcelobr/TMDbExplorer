package com.challenge.ndrive.tmdbexplorer.loader;

import android.content.Context;
import android.support.v4.content.AsyncTaskLoader;

import com.challenge.ndrive.tmdbexplorer.model.MovieDetail;
import com.challenge.ndrive.tmdbexplorer.utils.QueryUtils;


/**
 * Created by marcelo on 06/01/18.
 */

public class MovieDetailLoader extends AsyncTaskLoader<MovieDetail> {
    /** Query URL */
    private String mUrl;

    /**
     * Constructs a new {@link MovieLoader}.
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
    public MovieDetail loadInBackground() {
        if (mUrl == null) {
            return null;
        }

        // Perform the network request, parse the response, and extract a list of movie details.
        MovieDetail movieDetail = QueryUtils.fetchMovieDetailData(mUrl);
        return movieDetail;
    }
}
