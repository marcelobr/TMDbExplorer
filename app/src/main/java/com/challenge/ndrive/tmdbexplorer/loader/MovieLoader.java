package com.challenge.ndrive.tmdbexplorer.loader;

import android.support.v4.content.AsyncTaskLoader;
import android.content.Context;

import com.challenge.ndrive.tmdbexplorer.TmdbApplication;
import com.challenge.ndrive.tmdbexplorer.interfaces.TmdbClient;
import com.challenge.ndrive.tmdbexplorer.model.Movie;

import java.util.List;

/**
 * Created by marcelo on 04/01/18.
 */

public class MovieLoader extends AsyncTaskLoader<List<Movie>> {

    /** Query to perform */
    private String mQuery;

    /** Page to load */
    private int mPage;

    private TmdbClient client;

    /**
     * Constructs a new {@link MovieLoader}.
     *
     * @param context of the activity
     * @param query to load data from
     */
    public MovieLoader(Context context, String query, int page) {
        super(context);
        client = ((TmdbApplication) context.getApplicationContext()).getClient();
        mQuery = query;
        mPage = page;
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
        if (mQuery == null) {
            return null;
        }

        // Perform the network request, parse the response, and extract a list of earthquakes.
        return client.searchMovies(mQuery, mPage);
    }
}
