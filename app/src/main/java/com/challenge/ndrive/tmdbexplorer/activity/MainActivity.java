package com.challenge.ndrive.tmdbexplorer.activity;

import android.content.Intent;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.content.Context;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.challenge.ndrive.tmdbexplorer.R;
import com.challenge.ndrive.tmdbexplorer.adapter.MovieAdapter;
import com.challenge.ndrive.tmdbexplorer.loader.MovieLoader;
import com.challenge.ndrive.tmdbexplorer.model.Movie;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<List<Movie>> {

    private static final String MOVIE_SEARCH_BASE_URL = "https://api.themoviedb.org/3/search/movie?";
    private static final String API_KEY_PARAM = "api_key";
    private static final String LANGUAGE_PARAM = "language";
    private static final String PAGE_PARAM = "page";
    private static final String INCLUDE_ADULT_PARAM = "include_adult";
    private static final String QUERY_PARAM = "query";

    /**
     * Constant value for the earthquake loader ID.
     */
    private static final int MOVIE_LOADER_ID = 1;

    private View mLoadingIndicator;

    private SearchView searchView;

    /** TextView that is displayed when the list is empty */
    private TextView mEmptyStateTextView;

    private MovieAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mLoadingIndicator = findViewById(R.id.loading_indicator);
        mLoadingIndicator.setVisibility(View.GONE);

        searchView = findViewById(R.id.search);
        searchView.setIconifiedByDefault(false);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                mEmptyStateTextView.setText("");
                mLoadingIndicator = findViewById(R.id.loading_indicator);
                mLoadingIndicator.setVisibility(View.VISIBLE);

                if (hasNetworkConnection()) {
                    searchMovies(query);
                } else {
                    showNoInternetConnection();
                }

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

        ListView movieListView = findViewById(R.id.list_view);
        mEmptyStateTextView = findViewById(R.id.empty_view);
        movieListView.setEmptyView(mEmptyStateTextView);

        mAdapter = new MovieAdapter(this, new ArrayList<Movie>());
        movieListView.setAdapter(mAdapter);

        movieListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                // Find the current movie that was clicked on
                Movie currentMovie = mAdapter.getItem(position);

                // Create a new intent to view the movie detail
                Intent intent = new Intent(MainActivity.this, DetailActivity.class);

                intent.putExtra("MovieId", currentMovie.getId());

                // Send the intent to launch a new activity
                startActivity(intent);
            }
        });

        if (hasNetworkConnection()) {
            // Initialize the loader. Pass in the int ID constant defined above and pass in null for
            // the bundle. Pass in this activity for the LoaderCallbacks parameter (which is valid
            // because this activity implements the LoaderCallbacks interface).
            if(getSupportLoaderManager().getLoader(MOVIE_LOADER_ID)!=null){
                getSupportLoaderManager().initLoader(MOVIE_LOADER_ID, null, this);
            }
        } else {
            showNoInternetConnection();
        }
    }

    private void searchMovies(String query) {
        Uri builtURI = Uri.parse(MOVIE_SEARCH_BASE_URL).buildUpon()
                .appendQueryParameter(API_KEY_PARAM, "83d01f18538cb7a275147492f84c3698")
                .appendQueryParameter(LANGUAGE_PARAM, "en-US")
                .appendQueryParameter(PAGE_PARAM, "1")
                .appendQueryParameter(INCLUDE_ADULT_PARAM, "false")
                .appendQueryParameter(QUERY_PARAM, query)
                .build();

        Bundle queryBundle = new Bundle();
        queryBundle.putString("queryString", builtURI.toString());
        getSupportLoaderManager().restartLoader(MOVIE_LOADER_ID, queryBundle, this);
    }

    private boolean hasNetworkConnection() {
        // Get a reference to the ConnectivityManager to check state of network connectivity
        ConnectivityManager connMgr = (ConnectivityManager)
                getSystemService(Context.CONNECTIVITY_SERVICE);

        // Get details on the currently active default data network
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();

        if (networkInfo != null && networkInfo.isConnected()) {
            return true;
        } else {
            return false;
        }
    }

    private void showNoInternetConnection() {
        View loadingIndicator = findViewById(R.id.loading_indicator);
        loadingIndicator.setVisibility(View.GONE);

        // Update empty state with no connection error message
        mEmptyStateTextView.setText(R.string.no_internet_connection);
    }

    @Override
    public Loader<List<Movie>> onCreateLoader(int i, Bundle bundle) {
//        SharedPreferences sharedPrefs = PreferenceManager.getDefaultSharedPreferences(this);
//        String minMagnitude = sharedPrefs.getString(
//                getString(R.string.settings_min_magnitude_key),
//                getString(R.string.settings_min_magnitude_default));
//
//        String orderBy = sharedPrefs.getString(
//                getString(R.string.settings_order_by_key),
//                getString(R.string.settings_order_by_default)
//        );

        return new MovieLoader(this, bundle.getString("queryString"));
    }

    @Override
    public void onLoadFinished(Loader<List<Movie>> loader, List<Movie> movies) {
        View loadingIndicator = findViewById(R.id.loading_indicator);
        loadingIndicator.setVisibility(View.GONE);

        mEmptyStateTextView.setText(R.string.no_movies);

        // Clear the adapter of previous movie data
        mAdapter.clear();

        // If there is a valid list of {@link Movie}s, then add them to the adapter's
        // data set. This will trigger the ListView to update.
        if (movies != null && !movies.isEmpty()) {
            mAdapter.addAll(movies);
        }
    }

    @Override
    public void onLoaderReset(Loader<List<Movie>> loader) {
        // Loader reset
        mAdapter.clear();
    }
}
