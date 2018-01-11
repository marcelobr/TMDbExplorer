package com.challenge.ndrive.tmdbexplorer.utils;

import android.net.Uri;
import android.support.annotation.NonNull;
import android.util.Log;

import com.challenge.ndrive.tmdbexplorer.interfaces.ApiEndpoints;
import com.challenge.ndrive.tmdbexplorer.interfaces.TmdbClient;
import com.challenge.ndrive.tmdbexplorer.model.Movie;
import com.challenge.ndrive.tmdbexplorer.model.MoviesResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by marcelo on 1/7/18.
 */

public class TmdbClientImpl implements TmdbClient {

    /** Tag for the log messages */
    private static final String LOG_TAG = TmdbClientImpl.class.getSimpleName();

    private static final String IMAGE_BASE_URL = "https://image.tmdb.org/t/p/";

    private static final String API_KEY = "83d01f18538cb7a275147492f84c3698";

    private ApiEndpoints mApiEndpoints;

    public TmdbClientImpl() {
        mApiEndpoints = ApiClient.getClient().create(ApiEndpoints.class);
    }

    @Override
    public void searchMovies(@NonNull String query, int page, final MoviesCallback<List<Movie>> callback) {
        Call<MoviesResponse> callMovies = mApiEndpoints.searchMovies(API_KEY, page, query);
        callMovies.enqueue(new Callback<MoviesResponse>() {
            @Override
            public void onResponse(Call<MoviesResponse> call, Response<MoviesResponse> response) {
                List<Movie> movies = response.body().getResults();
                callback.onLoaded(movies);
            }

            @Override
            public void onFailure(Call<MoviesResponse> call, Throwable t) {
                Log.e(LOG_TAG, t.toString());
            }
        });
    }

    @Override
    public void getMovie(long movieId, final MovieCallback<Movie> callback) {
        Call<Movie> callMovie = mApiEndpoints.getMovieDetails(movieId, API_KEY);
        if (callMovie != null) {
            callMovie.enqueue(new Callback<Movie>() {
                @Override
                public void onResponse(Call<Movie> call, Response<Movie> response) {
                    Movie movieDetail = response.body();
                    callback.onLoaded(movieDetail);
                }

                @Override
                public void onFailure(Call<Movie> call, Throwable t) {
                    Log.e(LOG_TAG, t.toString());
                }
            });
        }
    }

    @Override
    public Uri getImageUri(String imagePath, TmdbImageType imageType) {
        return Uri.parse(IMAGE_BASE_URL)
                .buildUpon()
                .appendEncodedPath(imageType.getValue() + imagePath)
                .build();
    }

}
