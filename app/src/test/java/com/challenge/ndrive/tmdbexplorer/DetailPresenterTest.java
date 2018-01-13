package com.challenge.ndrive.tmdbexplorer;

import android.os.Bundle;

import com.challenge.ndrive.tmdbexplorer.model.Movie;
import com.challenge.ndrive.tmdbexplorer.mvp.detail.DetailPresenter;
import com.challenge.ndrive.tmdbexplorer.mvp.detail.DetailView;
import com.challenge.ndrive.tmdbexplorer.tmdb.TmdbClient;
import com.challenge.ndrive.tmdbexplorer.tmdb.TmdbImageType;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Created by marcelo on 12/01/18.
 */

public class DetailPresenterTest {

    @Mock
    DetailView mView;
    @Mock
    TmdbClient mClient;

    private DetailPresenter mPresenter;

    private static final String MOVIE_ID_PARAM = "MovieId";

    private void stubMovieIdParam(Bundle extras, long value) {
        when(extras.getLong(MOVIE_ID_PARAM, 0)).thenReturn(value);
    }

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        mPresenter = new DetailPresenter(mClient);
        mPresenter.setView(mView);
    }

    @Test
    public void testGetMovieDetailNoData() {
        // Act
        mPresenter.getMovieDetail(null);

        // Assert
        verify(mView).showErrorMessage(anyString());
    }

    @Test
    public void testGetMovieDetailNoId() {
        // Act
        Bundle extras = mock(Bundle.class);
        mPresenter.getMovieDetail(extras);

        // Assert
        verify(mView).showErrorMessage(anyString());
    }

    @Test
    public void testGetMovieDetailSuccess() {
        // Arrange
        final Movie movie = new Movie(315635, "Spider-Man: Homecoming", "2017-07-05", "/ApYhuwBWzl29Oxe9JJsgL7qILbD.jpg", "/vc8bCGjdVp0UbMNLzHnHSLRbBWQ.jpg", 7.3, 5681, "Following the events of Captain America: Civil War, Peter Parker, with the help of his mentor Tony Stark, tries to balance his life as an ordinary high school student in Queens, New York City, with fighting crime as his superhero alter ego Spider-Man as a new threat, the Vulture, emerges.", 880024498, 133);

        doAnswer(new Answer() {
            @Override
            public Object answer(InvocationOnMock invocation) throws Throwable {
                TmdbClient.MovieCallback<Movie> callback = invocation.getArgument(1);
                callback.onLoaded(movie);
                return null;
            }
        }).when(mClient).getMovie(anyLong(), any(TmdbClient.MovieCallback.class));

        // Act
        Bundle extras = mock(Bundle.class);
        stubMovieIdParam(extras, movie.getId());
        mPresenter.getMovieDetail(extras);

        // Assert
        verify(mView).hideErrorMessage();
        verify(mView).showLoading();
        verify(mView).hideLoading();
        verify(mView).showMovie(movie);
    }

//    @Test
//    public void testRestoreMovieDetailSuccess() {
//        // Arrange
//        final Movie movie = new Movie(315635, "Spider-Man: Homecoming", "2017-07-05", "/ApYhuwBWzl29Oxe9JJsgL7qILbD.jpg", "/vc8bCGjdVp0UbMNLzHnHSLRbBWQ.jpg", 7.3, 5681, "Following the events of Captain America: Civil War, Peter Parker, with the help of his mentor Tony Stark, tries to balance his life as an ordinary high school student in Queens, New York City, with fighting crime as his superhero alter ego Spider-Man as a new threat, the Vulture, emerges.", 880024498, 133);
//
//        // Act
//        Bundle extras = new Bundle();
//        extras.putLong(MOVIE_ID_PARAM, movie.getId());
//        mPresenter.getMovieDetail(extras);
//
//        // Assert
//        verify(mView).hideLoading();
//        verify(mView).showDetailContainer();
//        verify(mView).showMovie(movie);
//    }

    @Test
    public void testGetMovieDetailError() {
        // Arrange
        final String errorMessage = "error";

        doAnswer(new Answer() {
            @Override
            public Object answer(InvocationOnMock invocation) throws Throwable {
                TmdbClient.MovieCallback<Movie> callback = invocation.getArgument(1);
                callback.onError(errorMessage);
                return null;
            }
        }).when(mClient).getMovie(anyLong(), any(TmdbClient.MovieCallback.class));

        // Act
        Bundle extras = mock(Bundle.class);
        stubMovieIdParam(extras, 123);
        mPresenter.getMovieDetail(extras);

        // Assert
        verify(mView).showErrorMessage(errorMessage);
    }

    @Test
    public void testGetImageUriPortrait() {
        //Arrange
        final Movie movie = new Movie(315635, "Spider-Man: Homecoming", "2017-07-05", "/ApYhuwBWzl29Oxe9JJsgL7qILbD.jpg", "/vc8bCGjdVp0UbMNLzHnHSLRbBWQ.jpg", 7.3, 5681, "Following the events of Captain America: Civil War, Peter Parker, with the help of his mentor Tony Stark, tries to balance his life as an ordinary high school student in Queens, New York City, with fighting crime as his superhero alter ego Spider-Man as a new threat, the Vulture, emerges.", 880024498, 133);

        doAnswer(new Answer() {
            @Override
            public Object answer(InvocationOnMock invocation) throws Throwable {
                TmdbClient.MovieCallback<Movie> callback = invocation.getArgument(1);
                callback.onLoaded(movie);
                return null;
            }
        }).when(mClient).getMovie(anyLong(), any(TmdbClient.MovieCallback.class));


        // Act
        Bundle extras = mock(Bundle.class);
        stubMovieIdParam(extras, movie.getId());
        mPresenter.getMovieDetail(extras);
        mPresenter.getImageUri(false);

        //Assert
        verify(mClient).getImageUri(movie.getBackdropPath(), TmdbImageType.LARGE);
    }

    @Test
    public void testGetImageUriLandscape() {
        //Arrange
        final Movie movie = new Movie(315635, "Spider-Man: Homecoming", "2017-07-05", "/ApYhuwBWzl29Oxe9JJsgL7qILbD.jpg", "/vc8bCGjdVp0UbMNLzHnHSLRbBWQ.jpg", 7.3, 5681, "Following the events of Captain America: Civil War, Peter Parker, with the help of his mentor Tony Stark, tries to balance his life as an ordinary high school student in Queens, New York City, with fighting crime as his superhero alter ego Spider-Man as a new threat, the Vulture, emerges.", 880024498, 133);

        doAnswer(new Answer() {
            @Override
            public Object answer(InvocationOnMock invocation) throws Throwable {
                TmdbClient.MovieCallback<Movie> callback = invocation.getArgument(1);
                callback.onLoaded(movie);
                return null;
            }
        }).when(mClient).getMovie(anyLong(), any(TmdbClient.MovieCallback.class));

        // Act
        Bundle extras = mock(Bundle.class);
        stubMovieIdParam(extras, movie.getId());
        mPresenter.getMovieDetail(extras);
        mPresenter.getImageUri(true);

        //Assert
        verify(mClient).getImageUri(movie.getPoster​Image(), TmdbImageType.LARGE);
    }
}