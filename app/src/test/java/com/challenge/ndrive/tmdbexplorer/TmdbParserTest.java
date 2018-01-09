package com.challenge.ndrive.tmdbexplorer;

import com.challenge.ndrive.tmdbexplorer.model.Movie;
import com.challenge.ndrive.tmdbexplorer.utils.TmdbParser;

import org.json.JSONObject;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;

import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import static junit.framework.Assert.assertEquals;
import static org.junit.Assert.assertNull;

/**
 * Created by marcelo on 1/9/18.
 */

@RunWith(RobolectricTestRunner.class)
public class TmdbParserTest {

    private TmdbParser parser;

    @Before
    public void setup() {
        parser = new TmdbParser();
    }

    @Test
    public void testExtractMoviesSuccess() throws Exception {
        // Arrange
        String response = readResource("search_success.json");
        JSONObject json = new JSONObject(response);

        // Act
        List<Movie> movies = parser.extractMovies(json);

        // Assert
        assertEquals(20, movies.size());
    }

    @Test
    public void testExtractMoviesEmpty() throws Exception {
        // Arrange
        JSONObject json = new JSONObject("{}");

        // Act
        List<Movie> movies = parser.extractMovies(json);

        // Assert
        assertEquals(0, movies.size());
    }

    @Test
    public void testExtractMovieDetailSuccess() throws Exception {
        // Arrange
        String response = readResource("detail_success.json");
        JSONObject json = new JSONObject(response);

        // Act
        Movie movie = parser.extractMovieDetail(json);

        // Assert
        assertEquals("Madagascar", movie.getTitle());
        assertEquals(6.6, movie.getVoteAverage());

//        Calendar calendar = Calendar.getInstance();
//        calendar.set(2005, 5, 25);
//        Date expectedDate = calendar.getTime();
    }

    @Test
    public void testExtractMovieDetailNull() throws Exception {
        // Arrange
        JSONObject json = new JSONObject("{}");

        // Act
        Movie movie = parser.extractMovieDetail(json);

        // Assert
        assertNull(movie);
    }

    private String readResource(String filename) {
        ClassLoader classLoader = getClass().getClassLoader();
        URL resource = classLoader.getResource(filename);

        String content = "";

        try {
            content = new String(Files.readAllBytes(Paths.get(resource.getPath())));
        } catch (IOException e) {
            e.printStackTrace();
        }

        return content;
    }

}
