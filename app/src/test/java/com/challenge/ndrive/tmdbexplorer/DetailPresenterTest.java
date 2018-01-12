package com.challenge.ndrive.tmdbexplorer;

import android.net.Uri;
import android.os.Bundle;

import com.challenge.ndrive.tmdbexplorer.model.Movie;
import com.challenge.ndrive.tmdbexplorer.mvp.detail.DetailPresenter;
import com.challenge.ndrive.tmdbexplorer.mvp.detail.DetailView;
import com.challenge.ndrive.tmdbexplorer.tmdb.TmdbClient;
import com.challenge.ndrive.tmdbexplorer.tmdb.TmdbImageType;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.verify;

/**
 * Created by marcelo on 12/01/18.
 */

public class DetailPresenterTest {

    @Mock
    DetailView mView;
    @Mock
    TmdbClient mClient;

    private DetailPresenter mPresenter;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        mPresenter = new DetailPresenter(mClient);
        mPresenter.setView(mView);
    }

    @Test
    public void testGetMovieDetailSuccess() {
        // Arrange
        final String MOVIE_ID_PARAM = "MovieId";

        final Movie movie = new Movie(315635, "Spider-Man: Homecoming", "2017-07-05", "/ApYhuwBWzl29Oxe9JJsgL7qILbD.jpg", "/vc8bCGjdVp0UbMNLzHnHSLRbBWQ.jpg", 7.3, 5681, "Following the events of Captain America: Civil War, Peter Parker, with the help of his mentor Tony Stark, tries to balance his life as an ordinary high school student in Queens, New York City, with fighting crime as his superhero alter ego Spider-Man as a new threat, the Vulture, emerges.", 880024498, 133);

        doAnswer(new Answer() {
            @Override
            public Object answer(InvocationOnMock invocation) throws Throwable {
                TmdbClient.MovieCallback<Movie> callback = invocation.getArgument(1);
                callback.onLoaded(movie);
                return null;
            }
        }).when(mClient).getMovie(anyLong(), any(TmdbClient.MovieCallback.class));

        Bundle movieIdBundle = new Bundle();
        movieIdBundle.putLong(MOVIE_ID_PARAM, 315635);

        // Act
        mPresenter.getMovieDetail(movieIdBundle, null);

        // Assert
        verify(mView).hideLoading();
        verify(mView).showDetailContainer();
        verify(mView).showMovie(movie);
    }

    @Test
    public void testRestoreMovieDetailSuccess() {
        // Arrange
        final String MOVIE_PARAM = "MOVIE";
        final String ERROR_MESSAGE_PARAM = "ERROR_MESSAGE";

        final Movie movie = new Movie(315635, "Spider-Man: Homecoming", "2017-07-05", "/ApYhuwBWzl29Oxe9JJsgL7qILbD.jpg", "/vc8bCGjdVp0UbMNLzHnHSLRbBWQ.jpg", 7.3, 5681, "Following the events of Captain America: Civil War, Peter Parker, with the help of his mentor Tony Stark, tries to balance his life as an ordinary high school student in Queens, New York City, with fighting crime as his superhero alter ego Spider-Man as a new threat, the Vulture, emerges.", 880024498, 133);

        Bundle movieBundle = new Bundle();
        movieBundle.putParcelable(MOVIE_PARAM, movie);
        movieBundle.putString(ERROR_MESSAGE_PARAM, null);

        // Act
        mPresenter.getMovieDetail(null, movieBundle);

        // Assert
//        verify(mView).hideLoading();
//        verify(mView).showDetailContainer();
//        verify(mView).showMovie(movie);
    }

    @Test
    public void testGetMovieDetailError() {
        // Arrange
        final String MOVIE_ID_PARAM = "MovieId";
        final String errorMessage = "error";

        doAnswer(new Answer() {
            @Override
            public Object answer(InvocationOnMock invocation) throws Throwable {
                TmdbClient.MovieCallback<Movie> callback = invocation.getArgument(1);
                callback.onError(errorMessage);
                return null;
            }
        }).when(mClient).getMovie(anyLong(), any(TmdbClient.MovieCallback.class));

        Bundle movieIdBundle = new Bundle();
        movieIdBundle.putLong(MOVIE_ID_PARAM, 315635);

        // Act
        mPresenter.getMovieDetail(movieIdBundle, null);

        // Assert
        verify(mView).showErrorMessage(errorMessage);
    }

    @Test
    public void testGetImageUriPortraitSuccess() {
        //Arrange
        final String MOVIE_ID_PARAM = "MovieId";
        final String IMAGE_BASE_URL = "https://image.tmdb.org/t/p/";

        final Movie movie = new Movie(315635, "Spider-Man: Homecoming", "2017-07-05", "/ApYhuwBWzl29Oxe9JJsgL7qILbD.jpg", "/vc8bCGjdVp0UbMNLzHnHSLRbBWQ.jpg", 7.3, 5681, "Following the events of Captain America: Civil War, Peter Parker, with the help of his mentor Tony Stark, tries to balance his life as an ordinary high school student in Queens, New York City, with fighting crime as his superhero alter ego Spider-Man as a new threat, the Vulture, emerges.", 880024498, 133);

//        Uri expectedImageUri = Uri.parse(IMAGE_BASE_URL)
//                .buildUpon()
//                .appendEncodedPath(TmdbImageType.LARGE.getValue() + movie.getBackdropPath())
//                .build();

        Uri expectedImageUri = Uri.parse(IMAGE_BASE_URL + TmdbImageType.LARGE.getValue() + movie.getPoster​Image());

        doAnswer(new Answer() {
            @Override
            public Object answer(InvocationOnMock invocation) throws Throwable {
                TmdbClient.MovieCallback<Movie> callback = invocation.getArgument(1);
                callback.onLoaded(movie);
                return null;
            }
        }).when(mClient).getMovie(anyLong(), any(TmdbClient.MovieCallback.class));

        Bundle movieIdBundle = new Bundle();
        movieIdBundle.putLong(MOVIE_ID_PARAM, 315635);

        // Act
        mPresenter.getMovieDetail(movieIdBundle, null);
        Uri actualImageUri = mPresenter.getImageUri(false);

        //Assert
        Assert.assertEquals(expectedImageUri, actualImageUri);
    }

    @Test
    public void testGetImageUriLandscapeSuccess() {
        //Arrange
        final String MOVIE_ID_PARAM = "MovieId";
        final String IMAGE_BASE_URL = "https://image.tmdb.org/t/p/";

        final Movie movie = new Movie(315635, "Spider-Man: Homecoming", "2017-07-05", "/ApYhuwBWzl29Oxe9JJsgL7qILbD.jpg", "/vc8bCGjdVp0UbMNLzHnHSLRbBWQ.jpg", 7.3, 5681, "Following the events of Captain America: Civil War, Peter Parker, with the help of his mentor Tony Stark, tries to balance his life as an ordinary high school student in Queens, New York City, with fighting crime as his superhero alter ego Spider-Man as a new threat, the Vulture, emerges.", 880024498, 133);

//        Uri expectedImageUri = Uri.parse(IMAGE_BASE_URL)
//                .buildUpon()
//                .appendEncodedPath(TmdbImageType.THUMB.getValue() + movie.getPoster​Image())
//                .build();

        Uri expectedImageUri = Uri.parse(IMAGE_BASE_URL + TmdbImageType.THUMB.getValue() + movie.getPoster​Image());

        doAnswer(new Answer() {
            @Override
            public Object answer(InvocationOnMock invocation) throws Throwable {
                TmdbClient.MovieCallback<Movie> callback = invocation.getArgument(1);
                callback.onLoaded(movie);
                return null;
            }
        }).when(mClient).getMovie(anyLong(), any(TmdbClient.MovieCallback.class));

        Bundle movieIdBundle = new Bundle();
        movieIdBundle.putLong(MOVIE_ID_PARAM, 315635);

        // Act
        mPresenter.getMovieDetail(movieIdBundle, null);
        Uri actualImageUri = mPresenter.getImageUri(true);

        //Assert
        Assert.assertEquals(expectedImageUri, actualImageUri);
    }
}
