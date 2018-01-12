package com.challenge.ndrive.tmdbexplorer;

import android.app.Application;

import com.challenge.ndrive.tmdbexplorer.tmdb.TmdbClient;
import com.challenge.ndrive.tmdbexplorer.tmdb.TmdbClientImpl;

/**
 * Created by marcelo on 1/8/18.
 */

public class TmdbApplication extends Application {

    private TmdbClient client;

    @Override
    public void onCreate() {
        super.onCreate();

        client = new TmdbClientImpl(this);
    }

    public TmdbClient getClient() {
        return  client;
    }

}
