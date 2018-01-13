package com.challenge.ndrive.tmdbexplorer;

import com.challenge.ndrive.tmdbexplorer.model.Movie;
import com.challenge.ndrive.tmdbexplorer.mvp.main.MainPresenter;
import com.challenge.ndrive.tmdbexplorer.mvp.main.MainView;
import com.challenge.ndrive.tmdbexplorer.tmdb.TmdbClient;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.verify;

/**
 * Created by marcelo on 1/11/18.
 */

public class MainPresenterTest {

    @Mock
    MainView mView;
    @Mock
    TmdbClient mClient;

    private MainPresenter mPresenter;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        mPresenter = new MainPresenter(mClient);
        mPresenter.setView(mView);
    }

    @Test
    public void testSearchMoviesSuccess() {
        // Arrange
        final List<Movie> resultList = new ArrayList<>();
        resultList.add(new Movie(1, "test", "2018-01-10", ""));

        doAnswer(new Answer() {
            @Override
            public Void answer(InvocationOnMock invocation) throws Throwable {
                TmdbClient.MoviesCallback<List<Movie>> callback = invocation.getArgument(2);
                callback.onLoaded(resultList);
                return null;
            }
        }).when(mClient).searchMovies(anyString(), anyInt(), any(TmdbClient.MoviesCallback.class));

        // Act
        mPresenter.searchMovies("my_query");

        // Assert
        verify(mView).hideErrorMessage();
        verify(mView).clearSearchFocus();
        verify(mView).showLoading();
        verify(mView).showList(resultList);
    }

    @Test
    public void testSearchMoviesError() {
        // Arrange
        final String errorMessage = "error";

        doAnswer(new Answer() {
            @Override
            public Void answer(InvocationOnMock invocation) throws Throwable {
                TmdbClient.MoviesCallback<List<Movie>> callback = invocation.getArgument(2);
                callback.onError(errorMessage);
                return null;
            }
        }).when(mClient).searchMovies(anyString(), anyInt(), any(TmdbClient.MoviesCallback.class));

        // Act
        mPresenter.searchMovies("my_query");

        // Assert
        verify(mView).hideErrorMessage();
        verify(mView).showLoading();
        verify(mView).showErrorMessage(errorMessage);
    }

    @Test
    public void testShowMovieDetail() {
        // Arrange
        final List<Movie> resultList = new ArrayList<>();
        resultList.add(new Movie(123, "test", "2018-01-10", ""));

        doAnswer(new Answer() {
            @Override
            public Void answer(InvocationOnMock invocation) throws Throwable {
                TmdbClient.MoviesCallback<List<Movie>> callback = invocation.getArgument(2);
                callback.onLoaded(resultList);
                return null;
            }
        }).when(mClient).searchMovies(anyString(), anyInt(), any(TmdbClient.MoviesCallback.class));

        // Act
        mPresenter.searchMovies("my_query");
        mPresenter.showMovieDetail(0);

        // Assert
        verify(mView).showMovieDetails(resultList.get(0).getId());
    }

}
