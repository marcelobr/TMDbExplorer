package com.challenge.ndrive.tmdbexplorer.tmdb;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.challenge.ndrive.tmdbexplorer.model.Movie;
import com.challenge.ndrive.tmdbexplorer.model.MoviesResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * TMDb mapping API interface.
 */
public interface ApiEndpoints {

    @NonNull
    @GET("search/movie")
    Call<MoviesResponse> searchMovies(@Query("api_key") String apiKey, @Query("page") int page, @Query("query") String query);

    @Nullable
    @GET("movie/{id}")
    Call<Movie> getMovieDetails(@Path("id") long id, @Query("api_key") String apiKey);

}
