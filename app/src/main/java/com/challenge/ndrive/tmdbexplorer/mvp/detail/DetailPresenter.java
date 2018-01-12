package com.challenge.ndrive.tmdbexplorer.mvp.detail;

import android.net.Uri;
import android.os.Bundle;

import com.challenge.ndrive.tmdbexplorer.tmdb.TmdbClient;
import com.challenge.ndrive.tmdbexplorer.model.Movie;
import com.challenge.ndrive.tmdbexplorer.tmdb.TmdbImageType;

/**
 * Created by marcelo on 11/01/18.
 */

public class DetailPresenter {

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
        //outState.putParcelable(MOVIE_PARAM, currentMovie != null ? currentMovie : null);
        outState.putParcelable(MOVIE_PARAM, currentMovie);
        outState.putString(ERROR_MESSAGE_PARAM, currentErrorMessage);
    }

//    public void onViewRestoreState(Bundle savedInstanceState) {
//        if (savedInstanceState != null) {
//            currentMovie = savedInstanceState.getParcelable(MOVIE_PARAM);
//            currentErrorMessage = savedInstanceState.getString(ERROR_MESSAGE_PARAM);
//
//            if (currentMovie != null) {
//                loadMovie(savedInstanceState.<Movie>getParcelable(MOVIE_PARAM));
//            } else if (currentErrorMessage != null) {
//                loadError(currentErrorMessage);
//            }
//        }
//    }

    public void getMovieDetail(Bundle extras, Bundle savedInstanceState) {
        if (savedInstanceState != null) {
            currentMovie = savedInstanceState.getParcelable(MOVIE_PARAM);
            currentErrorMessage = savedInstanceState.getString(ERROR_MESSAGE_PARAM);

            if (currentMovie != null) {
                loadMovie(currentMovie);
            } else if (currentErrorMessage != null) {
                loadError(currentErrorMessage);
            }
        } else if (extras != null) {
            long movieId = extras.getLong("MovieId");

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
        }
    }

    public Uri getImageUri(boolean isLandscape) {
        String moviePathImage = isLandscape ? currentMovie.getPoster​Image() : currentMovie.getBackdropPath();

        return mClient.getImageUri(moviePathImage, TmdbImageType.LARGE);
    }

    private void loadMovie(Movie movie) {
        mView.hideLoading();
        mView.showDetailContainer();
        currentMovie = movie;
        mView.showMovie(movie);
    }

    private void loadError(String message) {
        currentErrorMessage = message;
        mView.showErrorMessage(message);
    }
}
