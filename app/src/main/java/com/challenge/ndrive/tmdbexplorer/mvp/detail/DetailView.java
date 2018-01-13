package com.challenge.ndrive.tmdbexplorer.mvp.detail;

import com.challenge.ndrive.tmdbexplorer.model.Movie;

/**
 * The View interface of the movie details screen
 */

public interface DetailView {

    void showLoading();

    void hideLoading();

    void showErrorMessage(String message);

    void hideErrorMessage();

    void showMovie(Movie movie);

}
