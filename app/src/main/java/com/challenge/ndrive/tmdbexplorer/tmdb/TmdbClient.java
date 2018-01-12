package com.challenge.ndrive.tmdbexplorer.tmdb;

import android.net.Uri;
import android.support.annotation.NonNull;

import com.challenge.ndrive.tmdbexplorer.model.Movie;

import java.util.List;

/**
 * Created by marcelo on 1/8/18.
 */
public interface TmdbClient {

    /**
     *
     * @param <T>
     */
    interface MoviesCallback<T> {
        void onLoaded(T movies);
        void onError(String message);
    }

    /**
     *
     * @param <T>
     */
    interface MovieCallback<T> {
        void onLoaded(T movie);
        void onError(String message);
    }

    /**
     * Return a TMDb image uri.
     *
     * @param imagePath the image path to apply.
     * @param imageType a {@link TmdbImageType}.
     * @return a TMDb image uri.
     */
    Uri getImageUri(String imagePath, TmdbImageType imageType);

    /**
     * Search on TMDb service.
     *
     * @param query The query to search for on TMDb service.
     * @param page The page to load.
     */
    void searchMovies(@NonNull String query, int page, MoviesCallback<List<Movie>> callback);

    /**
     * Get the details of a Movie on TMDb service.
     *
     * @param movieId The id of movie to load.
     */
    void getMovie(long movieId, MovieCallback<Movie> callback);

}
