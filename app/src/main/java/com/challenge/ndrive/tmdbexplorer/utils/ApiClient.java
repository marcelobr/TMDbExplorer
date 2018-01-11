package com.challenge.ndrive.tmdbexplorer.utils;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by marcelo on 10/01/18.
 */

class ApiClient {
    private static final String API_BASE_URL = "https://api.themoviedb.org/3/";
    private static Retrofit retrofit = null;

    static Retrofit getClient() {
        if (retrofit==null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(API_BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }
}
