package com.challenge.ndrive.tmdbexplorer.tmdb;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.util.Log;

import com.challenge.ndrive.tmdbexplorer.R;
import com.challenge.ndrive.tmdbexplorer.model.Movie;
import com.challenge.ndrive.tmdbexplorer.model.MoviesResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Represents a client for TMDb API
 */
public class TmdbClientImpl implements TmdbClient {

    /**
     * Tag for the log messages
     */
    private static final String LOG_TAG = TmdbClientImpl.class.getSimpleName();
    private static final String API_BASE_URL = "https://api.themoviedb.org/3/";
    private static final String IMAGE_BASE_URL = "https://image.tmdb.org/t/p/";
    private static final String API_KEY = "83d01f18538cb7a275147492f84c3698";

    private Context mApplicationContext;
    private ApiEndpoints mApiEndpoints;

    public TmdbClientImpl(Context applicationContext) {
        this.mApplicationContext = applicationContext;

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(API_BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        mApiEndpoints = retrofit.create(ApiEndpoints.class);
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
                Log.e(LOG_TAG, t.getMessage(), t);
                callback.onError(getErrorMessage(t));
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
                    Log.e(LOG_TAG, t.getMessage(), t);
                    callback.onError(getErrorMessage(t));
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

    private boolean hasNetworkConnection() {
        // Get a reference to the ConnectivityManager to check state of network connectivity
        ConnectivityManager connMgr = (ConnectivityManager)
                mApplicationContext.getSystemService(Context.CONNECTIVITY_SERVICE);

        // Get details on the currently active default data network
        NetworkInfo networkInfo = connMgr != null ? connMgr.getActiveNetworkInfo() : null;

        return networkInfo != null && networkInfo.isConnected();
    }

    private String getErrorMessage(Throwable t) {
        String message = t.getMessage();

        if (!hasNetworkConnection()) {
            message = mApplicationContext.getString(R.string.no_internet_connection);
        }

        return message;
    }

}
