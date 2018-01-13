package com.challenge.ndrive.tmdbexplorer.mvp.main;

import android.os.Bundle;

import com.challenge.ndrive.tmdbexplorer.tmdb.TmdbClient;
import com.challenge.ndrive.tmdbexplorer.model.Movie;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by marcelo on 1/11/18.
 */

public class MainPresenter {

    private static final String MOVIE_LIST_PARAM = "MOVIE_LIST";
    private static final String ERROR_MESSAGE_PARAM = "ERROR_MESSAGE";

    private MainView mView;
    private TmdbClient mClient;

    private List<Movie> currentMovieList = null;
    private String currentErrorMessage = null;

    public MainPresenter(TmdbClient tmdbClient) {
        this.mClient = tmdbClient;
    }

    public void setView(MainView view) {
        this.mView = view;
    }

    public void onViewSaveState(Bundle outState) {
        outState.putParcelableArrayList(MOVIE_LIST_PARAM, currentMovieList != null ? new ArrayList<>(currentMovieList) : null);
        outState.putString(ERROR_MESSAGE_PARAM, currentErrorMessage);
    }

    public void onViewRestoreState(Bundle savedInstanceState) {
        if (savedInstanceState != null) {
            currentMovieList = savedInstanceState.getParcelableArrayList(MOVIE_LIST_PARAM);
            currentErrorMessage = savedInstanceState.getString(ERROR_MESSAGE_PARAM);

            if (currentMovieList != null) {
                loadList(savedInstanceState.<Movie>getParcelableArrayList(MOVIE_LIST_PARAM));
            } else if (currentErrorMessage != null) {
                loadError(currentErrorMessage);
            }
        }
    }

    public void onQueryChange(String query) {
        if (query.isEmpty()) {
            mView.hideErrorMessage();
            mView.clearList();
        }
    }

    public void searchMovies(String query) {
        mView.hideErrorMessage();
        mView.clearSearchFocus();
        mView.showLoading();
        mClient.searchMovies(query, 1, new TmdbClient.MoviesCallback<List<Movie>>() {
            @Override
            public void onLoaded(List<Movie> movies) {
                loadList(movies);
            }

            @Override
            public void onError(String message) {
                loadError(message);
            }
        });
    }

    public void showMovieDetail(int position) {
        long movieId = currentMovieList.get(position).getId();
        mView.showMovieDetails(movieId);
    }

    private void loadList(List<Movie> movies) {
        mView.hideLoading();
        currentMovieList = movies;
        mView.showList(movies);
    }

    private void loadError(String message) {
        mView.hideLoading();
        currentErrorMessage = message;
        mView.showErrorMessage(message);
    }

}