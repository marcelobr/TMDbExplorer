package com.challenge.ndrive.tmdbexplorer.loader;

import android.content.Context;
import android.support.v4.content.AsyncTaskLoader;

import com.challenge.ndrive.tmdbexplorer.TmdbApplication;
import com.challenge.ndrive.tmdbexplorer.interfaces.TmdbClient;
import com.challenge.ndrive.tmdbexplorer.model.Movie;


/**
 * Created by marcelo on 06/01/18.
 */

public class MovieDetailLoader extends AsyncTaskLoader<Movie> {
    /** Movie Id */
    private long mId;

    private TmdbClient client;

    /**
     * Constructs a new {@link MovieDetailLoader}.
     *
     * @param context of the activity
     * @param id the id of Movie to load.
     */
    public MovieDetailLoader(Context context, long id) {
        super(context);
        client = ((TmdbApplication) context.getApplicationContext()).getClient();
        mId = id;
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
        return client.getMovie(mId);
    }
}
