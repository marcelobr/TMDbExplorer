package com.challenge.ndrive.tmdbexplorer.mvp.detail;

import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.challenge.ndrive.tmdbexplorer.model.Movie;
import com.challenge.ndrive.tmdbexplorer.tmdb.TmdbClient;
import com.challenge.ndrive.tmdbexplorer.tmdb.TmdbImageType;

/**
 * Represents the presenter class of the movie details screen
 */

public class DetailPresenter {

    private static final String MOVIE_ID_PARAM = "MovieId";
    private static final String MOVIE_PARAM = "MOVIE";
    private static final String ERROR_MESSAGE_PARAM = "ERROR_MESSAGE";

    private DetailView mView;
    private TmdbClient mClient;

    private Movie currentMovie = null;
    private String currentErrorMessage = null;

    public DetailPresenter(TmdbClient tmdbClient) {
        this.mClient = tmdbClient;
    }

    public void setView(DetailView view) {
        this.mView = view;
    }

    public void onViewSaveState(Bundle outState) {
        outState.putParcelable(MOVIE_PARAM, currentMovie);
        outState.putString(ERROR_MESSAGE_PARAM, currentErrorMessage);
    }

    public void onViewRestoreState(Bundle savedInstanceState) {
        if (savedInstanceState != null) {
            currentMovie = savedInstanceState.getParcelable(MOVIE_PARAM);
            currentErrorMessage = savedInstanceState.getString(ERROR_MESSAGE_PARAM);

            if (currentMovie != null) {
                loadMovie(savedInstanceState.<Movie>getParcelable(MOVIE_PARAM));
            } else if (currentErrorMessage != null) {
                loadError(currentErrorMessage);
            }
        }
    }

    public void getMovieDetail(@Nullable Bundle extras) {
        long movieId = 0;

        if (extras != null) {
            movieId = extras.getLong(MOVIE_ID_PARAM, 0);
        }

        if (movieId != 0) {
            mView.hideErrorMessage();
            mView.showLoading();
            mClient.getMovie(movieId, new TmdbClient.MovieCallback<Movie>() {
                @Override
                public void onLoaded(Movie movie) {
                    loadMovie(movie);
                }

                @Override
                public void onError(String message) {
                    loadError(message);
                }
            });
        } else {
            loadError("No Movie id !!!");
        }
    }

    public Uri getImageUri(boolean isLandscape) {
        String moviePathImage = isLandscape ? currentMovie.getPoster​Image() : currentMovie.getBackdropPath();

        return mClient.getImageUri(moviePathImage, TmdbImageType.LARGE);
    }

    private void loadMovie(Movie movie) {
        mView.hideLoading();
        currentMovie = movie;
        mView.showMovie(movie);
    }

    private void loadError(String message) {
        mView.hideLoading();
        currentErrorMessage = message;
        mView.showErrorMessage(message);
    }
}