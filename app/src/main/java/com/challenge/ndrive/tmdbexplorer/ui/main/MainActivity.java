package com.challenge.ndrive.tmdbexplorer.ui.main;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.view.View;
import android.widget.TextView;

import com.challenge.ndrive.tmdbexplorer.R;
import com.challenge.ndrive.tmdbexplorer.TmdbApplication;
import com.challenge.ndrive.tmdbexplorer.ui.detail.DetailActivity;
import com.challenge.ndrive.tmdbexplorer.model.Movie;
import com.challenge.ndrive.tmdbexplorer.mvp.main.MainPresenter;
import com.challenge.ndrive.tmdbexplorer.mvp.main.MainView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements MainView {

    private MovieAdapter mAdapter;
    private MainPresenter mPresenter;

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

        TmdbApplication mApplication = ((TmdbApplication) getApplication());
        mPresenter = new MainPresenter(mApplication.getClient());
        mPresenter.setView(this);

        searchView.setIconifiedByDefault(false);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                mPresenter.searchMovies(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                mPresenter.onQueryChange(newText);
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
                        mPresenter.showMovieDetail(position);
                    }
                })
        );

        // Last step restore the State
        mPresenter.onViewRestoreState(savedInstanceState);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        mPresenter.onViewSaveState(outState);
        super.onSaveInstanceState(outState);
    }

    @Override
    public void showList(List<Movie> moviesList) {
        // Clear the adapter of previous movie data
        mAdapter.clear();

        if (moviesList != null && !moviesList.isEmpty()) {
            mAdapter.addMovies(moviesList);
        } else {
            showErrorMessage(getString(R.string.no_movies));
        }

        mMovieRecyclerView.setVisibility(View.VISIBLE);
    }

    @Override
    public void clearList() {
        mAdapter.clear();
    }

    @Override
    public void clearSearchFocus() {
        searchView.clearFocus();
    }

    @Override
    public void showLoading() {
        mLoadingIndicator.setVisibility(View.VISIBLE);
        mMovieRecyclerView.setVisibility(View.GONE);
    }

    @Override
    public void hideLoading() {
        mLoadingIndicator.setVisibility(View.GONE);
    }

    @Override
    public void showErrorMessage(String message) {
        mLoadingIndicator.setVisibility(View.GONE);
        mMovieRecyclerView.setVisibility(View.GONE);
        mEmptyStateTextView.setText(message);
        mEmptyStateTextView.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideErrorMessage() {
        mEmptyStateTextView.setVisibility(View.GONE);
    }

    @Override
    public void showMovieDetails(long movieId) {
        // Create a new intent to view the movie detail
        Intent intent = new Intent(MainActivity.this, DetailActivity.class);
        intent.putExtra("MovieId", movieId);

        // Send the intent to launch a new activity
        startActivity(intent);
    }
}