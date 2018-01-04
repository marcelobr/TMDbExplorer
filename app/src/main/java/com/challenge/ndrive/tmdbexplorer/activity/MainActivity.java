package com.challenge.ndrive.tmdbexplorer.activity;

import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.os.Bundle;
import android.view.View;

import com.challenge.ndrive.tmdbexplorer.R;

public class MainActivity extends AppCompatActivity {

    private View mLoadingIndicator;

    private SearchView searchView;

    /**
     * URL for movie data from the TMDb
     */
    //private static final String USGS_REQUEST_URL = "https://api.themoviedb.org/3/movie/top_rated?api_key=&language=en-US&page=1";

    private String uriBuilder() {
        Uri.Builder builder = new Uri.Builder();
        builder.scheme("https")
                .authority("api.themoviedb.org")
                .appendPath("3")
                .appendPath("movie")
                .appendPath("top_rated")
                .appendQueryParameter("api_key", "83d01f18538cb7a275147492f84c3698")
                .appendQueryParameter("language", "en-US")
                .appendQueryParameter("page", "1");
        String myUrl = builder.build().toString();
        return myUrl;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mLoadingIndicator = findViewById(R.id.loading_indicator);
        mLoadingIndicator.setVisibility(View.GONE);

        searchView = (SearchView) findViewById(R.id.search);
        searchView.setIconifiedByDefault(false);
        uriBuilder().toString();
    }
}
