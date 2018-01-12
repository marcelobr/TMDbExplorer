package com.challenge.ndrive.tmdbexplorer.activity;

import com.challenge.ndrive.tmdbexplorer.model.Movie;

import java.util.List;

/**
 * Created by marcelo on 1/11/18.
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
