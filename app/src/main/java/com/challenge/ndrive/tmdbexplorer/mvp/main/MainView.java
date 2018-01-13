package com.challenge.ndrive.tmdbexplorer.mvp.main;

import com.challenge.ndrive.tmdbexplorer.model.Movie;

import java.util.List;

/**
 * The View interface of the movie search screen
 */

public interface MainView {

    void showLoading();

    void hideLoading();

    void showErrorMessage(String message);

    void hideErrorMessage();

    void showList(List<Movie> moviesList);

    void clearList();

    void clearSearchFocus();

    void showMovieDetails(long movieId);

}
