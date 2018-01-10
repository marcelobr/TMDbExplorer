package com.challenge.ndrive.tmdbexplorer.utils;

import android.net.Uri;
import android.support.annotation.NonNull;

import com.challenge.ndrive.tmdbexplorer.interfaces.TmdbClient;
import com.challenge.ndrive.tmdbexplorer.model.Movie;
import com.challenge.ndrive.tmdbexplorer.model.MoviesResponse;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by marcelo on 1/7/18.
 */

public class TmdbClientImpl implements TmdbClient {

    private static final String API_BASE_URL = "https://api.themoviedb.org/3/";
    private static final String IMAGE_BASE_URL = "https://image.tmdb.org/t/p/";

    private static final String API_KEY = "83d01f18538cb7a275147492f84c3698";

    private static final String SEARCH_URL = API_BASE_URL + "/search/movie?";
    private static final String DETAIL_URL = API_BASE_URL + "/movie/";

    private static final String API_KEY_PARAM = "api_key";
    private static final String LANGUAGE_PARAM = "language";
    private static final String PAGE_PARAM = "page";
    private static final String INCLUDE_ADULT_PARAM = "include_adult";
    private static final String QUERY_PARAM = "query";

    private static Retrofit retrofit = null;

    @NonNull
    @Override
    public Call<MoviesResponse> searchMovies(String apiKey, int page, String query) {
        TmdbClient apiService = getClient().create(TmdbClient.class);

        return apiService.searchMovies(apiKey, page, query);
    }

    @Override
    public Call<Movie> getMovieDetails(long id, String apiKey) {
        TmdbClient apiService = getClient().create(TmdbClient.class);

        return apiService.getMovieDetails(id, apiKey);
    }

    @Override
    public Uri getImageUri(String imagePath, TmdbImageType imageType) {
        return Uri.parse(IMAGE_BASE_URL)
                .buildUpon()
                .appendEncodedPath(imageType.getValue() + imagePath)
                .build();
    }

    public static Retrofit getClient() {
        if (retrofit==null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(API_BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }

}
