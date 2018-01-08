package com.challenge.ndrive.tmdbexplorer.loader;

import android.support.v4.content.AsyncTaskLoader;
import android.content.Context;

import com.challenge.ndrive.tmdbexplorer.model.Movie;
import com.challenge.ndrive.tmdbexplorer.utils.TmdbData;

import java.util.List;

/**
 * Created by marcelo on 04/01/18.
 */

public class MovieLoader extends AsyncTaskLoader<List<Movie>> {

    /** Query URL */
    private String mUrl;

    /**
     * Constructs a new {@link MovieLoader}.
     *
     * @param context of the activity
     * @param url to load data from
     */
    public MovieLoader(Context context, String url) {
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
    public List<Movie> loadInBackground() {
        if (mUrl == null) {
            return null;
        }

        // Perform the network request, parse the response, and extract a list of earthquakes.
        List<Movie> movies = TmdbData.fetchMovieData(mUrl);
        return movies;
    }
}
