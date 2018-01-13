package com.challenge.ndrive.tmdbexplorer.mvp.detail;

import com.challenge.ndrive.tmdbexplorer.model.Movie;

/**
 * Created by marcelo on 11/01/18.
 */

public interface DetailView {

    void showLoading();

    void hideLoading();

    void showErrorMessage(String message);

    void hideErrorMessage();

    void showMovie(Movie movie);

}
