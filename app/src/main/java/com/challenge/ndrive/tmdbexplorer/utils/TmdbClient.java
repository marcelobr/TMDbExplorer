package com.challenge.ndrive.tmdbexplorer.utils;

import android.net.Uri;

/**
 * Created by marcelo on 1/7/18.
 */

public class TmdbClient {

    private static final String API_BASE_URL = "https://api.themoviedb.org/3";
    private static final String IMAGE_BASE_URL = "https://image.tmdb.org/t/p/";

    private static final String API_KEY = "83d01f18538cb7a275147492f84c3698";

    private static final String SEARCH_URL = API_BASE_URL + "/search/movie?";
    private static final String DETAIL_URL = API_BASE_URL + "/movie/";

    private static final String API_KEY_PARAM = "api_key";
    private static final String LANGUAGE_PARAM = "language";
    private static final String PAGE_PARAM = "page";
    private static final String INCLUDE_ADULT_PARAM = "include_adult";
    private static final String QUERY_PARAM = "query";

    public static Uri getSearchMoviesUri(String query, int page) {
        return Uri.parse(SEARCH_URL).buildUpon()
                .appendQueryParameter(API_KEY_PARAM, API_KEY)
                .appendQueryParameter(LANGUAGE_PARAM, "en-US")
                .appendQueryParameter(PAGE_PARAM, String.valueOf(page))
                .appendQueryParameter(INCLUDE_ADULT_PARAM, "false")
                .appendQueryParameter(QUERY_PARAM, query)
                .build();
    }

    public static Uri getMovieDetailUri(long movieId) {
        return Uri.parse(DETAIL_URL).buildUpon()
                .appendEncodedPath(String.valueOf(movieId))
                .appendQueryParameter(API_KEY_PARAM, API_KEY)
                .appendQueryParameter(LANGUAGE_PARAM, "en-US")
                .build();
    }

    public static Uri getImageUri(String imagePath, boolean large) {
        return Uri.parse(IMAGE_BASE_URL)
                .buildUpon()
                .appendEncodedPath((large ? "w500" : "w92") + imagePath)
                .build();
    }

}
