package com.challenge.ndrive.tmdbexplorer.activity;

import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import com.challenge.ndrive.tmdbexplorer.R;
import com.challenge.ndrive.tmdbexplorer.adapter.MovieAdapter;
import com.challenge.ndrive.tmdbexplorer.model.Movie;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private View mLoadingIndicator;

    private SearchView searchView;

    /** TextView that is displayed when the list is empty */
    private TextView mEmptyStateTextView;

    private MovieAdapter mAdapter;

    /**
     * URL for movie data from the TMDb
     */
    //private static final String USGS_REQUEST_URL = "https://api.themoviedb.org/3/movie/top_rated?api_key=&language=en-US&page=1";

    private String uriBuilder() {
        Uri.Builder builder = new Uri.Builder();
        builder.scheme("https")
                .authority("api.themoviedb.org")
                .appendPath("3")
                .appendPath("search")
                .appendPath("movie")
                .appendQueryParameter("api_key", "83d01f18538cb7a275147492f84c3698")
                .appendQueryParameter("language", "en-US")
                .appendQueryParameter("page", "1")
                .appendQueryParameter("include_adult", "false")
                .appendQueryParameter("query", "");
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

        searchView.setOnQueryTextListener(new android.support.v7.widget.SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                mLoadingIndicator = findViewById(R.id.loading_indicator);
                mLoadingIndicator.setVisibility(View.VISIBLE);

                //BookAsyncTask task = new BookAsyncTask();
                //task.execute(query.trim());
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                mEmptyStateTextView.setText("");

                if (newText.isEmpty()) {
                    mAdapter.clear();
                }
                return false;
            }
        });

        ListView movieListView = (ListView) findViewById(R.id.list_view);

        mEmptyStateTextView = (TextView) findViewById(R.id.empty_view);
        movieListView.setEmptyView(mEmptyStateTextView);

        mAdapter = new MovieAdapter(this, new ArrayList<Movie>());

        movieListView.setAdapter(mAdapter);

        String teste = uriBuilder().toString() + "teste";
    }
}
