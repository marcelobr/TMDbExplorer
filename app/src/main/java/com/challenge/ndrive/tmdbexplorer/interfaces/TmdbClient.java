package com.challenge.ndrive.tmdbexplorer.interfaces;

import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.challenge.ndrive.tmdbexplorer.model.Movie;
import com.challenge.ndrive.tmdbexplorer.model.MoviesResponse;
import com.challenge.ndrive.tmdbexplorer.utils.TmdbImageType;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by marcelo on 1/8/18.
 */
public interface TmdbClient {

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
    @GET("search/movie")
    Call<MoviesResponse> searchMovies(@Query("api_key") String apiKey, @Query("page") int page, @Query("query") String query);

    /**
     * Get the details of a Movie on TMDb service.
     *
     * @param id The id of movie to load.
     * @return The {@link Movie} instance filled with detail data.
     */
    @Nullable
    @GET("movie/{id}")
    Call<Movie> getMovieDetails(@Path("id") long id, @Query("api_key") String apiKey);

}
