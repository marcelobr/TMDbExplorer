package com.challenge.ndrive.tmdbexplorer.interfaces;

import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.challenge.ndrive.tmdbexplorer.model.Movie;
import com.challenge.ndrive.tmdbexplorer.model.MoviesResponse;
import com.challenge.ndrive.tmdbexplorer.utils.TmdbImageType;

import java.util.List;

/**
 * Created by marcelo on 1/8/18.
 */
public interface TmdbClient {

    interface MoviesCallback<T> {

        void onLoaded(T movies);
    }

    interface MovieCallback<T> {

        void onLoaded(T movie);
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
     * @return A list of movies match with the query or an empty list.
     */
    @NonNull
    void searchMovies(@NonNull String query, int page, MoviesCallback<List<Movie>> callback);

    /**
     * Get the details of a Movie on TMDb service.
     *
     * @param movieId The id of movie to load.
     * @return The {@link Movie} instance filled with detail data.
     */
    @Nullable
    void getMovie(long movieId, MovieCallback<Movie> callback);

}
