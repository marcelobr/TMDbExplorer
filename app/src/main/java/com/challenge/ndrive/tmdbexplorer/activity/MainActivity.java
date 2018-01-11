package com.challenge.ndrive.tmdbexplorer.activity;

import android.content.Intent;
import android.support.annotation.StringRes;
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
import com.challenge.ndrive.tmdbexplorer.TmdbApplication;
import com.challenge.ndrive.tmdbexplorer.adapter.MovieAdapter;
import com.challenge.ndrive.tmdbexplorer.interfaces.TmdbClient;
import com.challenge.ndrive.tmdbexplorer.model.Movie;
import com.challenge.ndrive.tmdbexplorer.listener.RecyclerItemClickListener;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


public class MainActivity extends AppCompatActivity {

    private static final String MOVIE_LIST_PARAM = "MOVIE_LIST";

    private MovieAdapter mAdapter;

    private TmdbClient mClient;

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

        mClient = ((TmdbApplication) getApplication()).getClient();

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

        List<Movie> savedMovieList = null;

        if (savedInstanceState != null) {
            savedMovieList = savedInstanceState.getParcelableArrayList(MOVIE_LIST_PARAM);
        }

        mAdapter = new MovieAdapter(this);
        mAdapter.addMovies(savedMovieList);

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

        if (!hasNetworkConnection()) {
            showNoInternetConnection();
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putParcelableArrayList(MOVIE_LIST_PARAM, new ArrayList<>(mAdapter.getMoviesList()));
        super.onSaveInstanceState(outState);
    }

    private void searchMovies(String query) {
        hideEmptyMessage();

        mClient.searchMovies(query, 1, new TmdbClient.MoviesCallback<List<Movie>>() {
            @Override
            public void onLoaded(List<Movie> movies) {
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
        });
    }

    private boolean hasNetworkConnection() {
        // Get a reference to the ConnectivityManager to check state of network connectivity
        ConnectivityManager connMgr = (ConnectivityManager)
                getSystemService(Context.CONNECTIVITY_SERVICE);

        // Get details on the currently active default data network
        //NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        NetworkInfo networkInfo = connMgr != null ? connMgr.getActiveNetworkInfo() : null;

        return networkInfo != null && networkInfo.isConnected();
    }

    private void showNoInternetConnection() {
        mLoadingIndicator.setVisibility(View.GONE);

        // Update empty state with no connection error message
        setEmptyMessage(R.string.no_internet_connection);
    }

    private void setEmptyMessage(@StringRes int message) {
        mEmptyStateTextView.setText(message);
        mEmptyStateTextView.setVisibility(View.VISIBLE);
    }

    private void hideEmptyMessage() {
        mEmptyStateTextView.setVisibility(View.GONE);
    }
}