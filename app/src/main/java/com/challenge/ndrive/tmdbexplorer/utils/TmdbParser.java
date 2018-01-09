package com.challenge.ndrive.tmdbexplorer.utils;

import android.util.Log;

import com.challenge.ndrive.tmdbexplorer.model.Movie;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Helper methods related to requesting and receiving movie data from TMDb.
 */
public final class TmdbParser {

    /**
     * Tag for the log messages
     */
    private static final String LOG_TAG = TmdbParser.class.getSimpleName();

    /**
     * Return a list of {@link Movie} objects that has been built up from
     * parsing a JSON response.
     */
    public List<Movie> extractMovies(JSONObject movieJSON) {

        // Create an empty ArrayList that we can start adding movies to
        List<Movie> movies = new ArrayList<>();

        // Try to parse the SAMPLE_JSON_RESPONSE. If there's a problem with the way the JSON
        // is formatted, a JSONException exception object will be thrown.
        // Catch the exception so the app doesn't crash, and print the error message to the logs.
        try {
            // Extract the JSONArray associated with the key called "results",
            // which represents a list of features (or movies).
            JSONArray movieArray = movieJSON.getJSONArray("results");

            // For each movie in the movieArray, create an {@link Movie} object
            for (int i = 0; i < movieArray.length(); i++) {

                // Get a single movie at position i within the list of movies
                JSONObject currentMovie = movieArray.getJSONObject(i);

                // Extract the value for the key called "id"
                long id = currentMovie.getLong("id");

                // Extract the value for the key called "title"
                String title = currentMovie.getString("title");

                // Extract the value for the key called "release_date"
                String releaseDate = currentMovie.getString("release_date");

                // Extract the value for the key called "poster_path"
                String poster = currentMovie.getString("poster_path");

                // Create a new {@link Movie} object with the id, title, release date
                // and poster from the JSON response.
                Movie movie = new Movie(id, title, releaseDate, poster);

                // Add the new {@link Movie} to the list of movies.
                movies.add(movie);
            }

        } catch (JSONException e) {
            // If an error is thrown when executing any of the above statements in the "try" block,
            // catch the exception here, so the app doesn't crash. Print a log message
            // with the message from the exception.
            Log.e(LOG_TAG, "Problem parsing the movie JSON results", e);
        }

        // Return the list of movies
        return movies;
    }

    public Movie extractMovieDetail(JSONObject movieDetailJSON) {
        Movie movieDetail = null;

        try {
            // Extract the value for the key called "title"
            String title = movieDetailJSON.getString("title");

            // Extract the value for the key called "poster_path"
            String poster = movieDetailJSON.getString("poster_path");

            // Extract the value for the key called "backdrop_path"
            String backdrop = movieDetailJSON.getString("backdrop_path");

            // Extract the value for the key called "vote_average"
            Double voteAverage = movieDetailJSON.getDouble("vote_average");

            // Extract the value for the key called "vote_count"
            int voteCount = movieDetailJSON.getInt("vote_count");

            // Extract the value for the key called "overview"
            String overview = movieDetailJSON.getString("overview");

            // Extract the value for the key called "revenue"
            int revenue = movieDetailJSON.getInt("revenue");

            // Extract the value for the key called "runtime"
            int runtime = movieDetailJSON.getInt("runtime");

            movieDetail = new Movie(title, poster, backdrop, voteAverage, voteCount, overview, revenue, runtime);
        } catch (JSONException e) {
            // If an error is thrown when executing any of the above statements in the "try" block,
            // catch the exception here, so the app doesn't crash. Print a log message
            // with the message from the exception.
            Log.e(LOG_TAG, "Problem parsing the movie detail JSON results", e);
        }

        return movieDetail;
    }
}
