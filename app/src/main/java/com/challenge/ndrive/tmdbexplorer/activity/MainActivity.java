package com.challenge.ndrive.tmdbexplorer.activity;

import android.content.Intent;
import android.support.annotation.StringRes;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.challenge.ndrive.tmdbexplorer.R;
import com.challenge.ndrive.tmdbexplorer.adapter.MovieAdapter;
import com.challenge.ndrive.tmdbexplorer.loader.MovieLoader;
import com.challenge.ndrive.tmdbexplorer.model.Movie;
import com.challenge.ndrive.tmdbexplorer.listener.RecyclerItemClickListener;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<List<Movie>> {

    /**
     * Constant value for the earthquake loader ID.
     */
    private static final int MOVIE_LOADER_ID = 1;

    private MovieAdapter mAdapter;

    @BindView(R.id.loading_indicator)
    View mLoadingIndicator;

    @BindView(R.id.search)
    SearchView searchView;

    @BindView(R.id.empty_view)
    TextView mEmptyStateTextView;

    @BindView(R.id.recycler_view)
    RecyclerView mMovieRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);

        mLoadingIndicator.setVisibility(View.GONE);

        searchView.setIconifiedByDefault(false);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                mEmptyStateTextView.setText("");
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

        mAdapter = new MovieAdapter(this);
        mMovieRecyclerView.setHasFixedSize(true);
        mMovieRecyclerView.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));
        mMovieRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mMovieRecyclerView.setAdapter(mAdapter);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        mMovieRecyclerView.setLayoutManager(mLayoutManager);

        mMovieRecyclerView.addOnItemTouchListener(
                new RecyclerItemClickListener(this, new RecyclerItemClickListener.OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {
                        // Find the current movie that was clicked on
                        Movie currentMovie = mAdapter.getItem(position);

                        // Create a new intent to view the movie detail
                        Intent intent = new Intent(MainActivity.this, DetailActivity.class);

                        intent.putExtra("MovieId", currentMovie.getId());

                        // Send the intent to launch a new activity
                        startActivity(intent);
                    }
                })
        );

        if (hasNetworkConnection()) {
            // Initialize the loader. Pass in the int ID constant defined above and pass in null for
            // the bundle. Pass in this activity for the LoaderCallbacks parameter (which is valid
            // because this activity implements the LoaderCallbacks interface).
            if (getSupportLoaderManager().getLoader(MOVIE_LOADER_ID) != null) {
                getSupportLoaderManager().initLoader(MOVIE_LOADER_ID, null, this);
            }
        } else {
            showNoInternetConnection();
        }
    }

    private void searchMovies(String query) {
        hideEmptyMessage();

        Bundle queryBundle = new Bundle();
        queryBundle.putString("queryString", query);
        queryBundle.putInt("page", 1);
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
        mLoadingIndicator.setVisibility(View.GONE);

        // Update empty state with no connection error message
        setEmptyMessage(R.string.no_internet_connection);
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

        String query = bundle.getString("queryString");
        int page = bundle.getInt("page");

        return new MovieLoader(this, query, page);
    }

    @Override
    public void onLoadFinished(Loader<List<Movie>> loader, List<Movie> movies) {

        mLoadingIndicator.setVisibility(View.GONE);

        // Clear the adapter of previous movie data
        mAdapter.clear();

        // If there is a valid list of {@link Movie}s, then add them to the adapter's
        // data set. This will trigger the ListView to update.
        if (movies != null && !movies.isEmpty()) {
            hideEmptyMessage();
            mAdapter.addMovies(movies);
        } else {
            setEmptyMessage(R.string.no_movies);
        }

        searchView.clearFocus();
    }

    @Override
    public void onLoaderReset(Loader<List<Movie>> loader) {
        // Loader reset
        mAdapter.clear();
    }

    private void setEmptyMessage(@StringRes int message) {
        mEmptyStateTextView.setText(message);
        mEmptyStateTextView.setVisibility(View.VISIBLE);
    }

    private void hideEmptyMessage() {
        mEmptyStateTextView.setVisibility(View.GONE);
    }
}
