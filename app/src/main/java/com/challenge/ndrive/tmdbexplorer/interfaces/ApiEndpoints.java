package com.challenge.ndrive.tmdbexplorer.interfaces;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.challenge.ndrive.tmdbexplorer.model.Movie;
import com.challenge.ndrive.tmdbexplorer.model.MoviesResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by marcelo on 10/01/18.
 */

public interface ApiEndpoints {

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
