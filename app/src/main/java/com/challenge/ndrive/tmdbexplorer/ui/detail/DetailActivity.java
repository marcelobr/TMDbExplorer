package com.challenge.ndrive.tmdbexplorer.ui.detail;

import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.challenge.ndrive.tmdbexplorer.R;
import com.challenge.ndrive.tmdbexplorer.TmdbApplication;
import com.challenge.ndrive.tmdbexplorer.model.Movie;
import com.challenge.ndrive.tmdbexplorer.mvp.detail.DetailPresenter;
import com.challenge.ndrive.tmdbexplorer.mvp.detail.DetailView;

import butterknife.BindView;
import butterknife.ButterKnife;


public class DetailActivity extends AppCompatActivity implements DetailView {

    private DetailPresenter mPresenter;

    @BindView(R.id.movie_detail_container)
    View mContainer;

    /**
     * TextView that is displayed when the list is empty
     */
    @BindView(R.id.movie_detail_empty_view)
    TextView mEmptyStateTextView;

    @BindView(R.id.movie_detail_loading_indicator)
    View mLoadingIndicator;

    @BindView(R.id.movie_detail_title)
    TextView titleTextView;

    @BindView(R.id.movie_detail_backdrop)
    ImageView backdropImageView;

    @BindView(R.id.movie_detail_vote_average)
    TextView voteAverageTextView;

    @BindView(R.id.movie_detail_vote_count)
    TextView voteCountTextView;

    @BindView(R.id.movie_detail_overview)
    TextView overviewTextView;

    @BindView(R.id.movie_detail_revenue)
    TextView revenueTextView;

    @BindView(R.id.movie_detail_runtime)
    TextView runtimeTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        ButterKnife.bind(this);

        TmdbApplication mApplication = ((TmdbApplication) getApplication());
        mPresenter = new DetailPresenter(mApplication.getClient());
        mPresenter.setView(this);

        Bundle extras = getIntent().getExtras();
        mPresenter.getMovieDetail(extras, savedInstanceState);

        //mPresenter.onViewRestoreState(savedInstanceState);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        mPresenter.onViewSaveState(outState);
        super.onSaveInstanceState(outState);
    }


    @Override
    public void showLoading() {
        mLoadingIndicator.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideLoading() {
        mLoadingIndicator.setVisibility(View.GONE);
    }

    @Override
    public void showErrorMessage(String message) {
        mLoadingIndicator.setVisibility(View.GONE);
        mEmptyStateTextView.setText(R.string.no_internet_connection);
    }

    @Override
    public void showDetailContainer() {
        mContainer.setVisibility(View.VISIBLE);
    }

    @Override
    public void showMovie(Movie movie) {
        titleTextView.setText(movie.getTitle());

        boolean isLandscape = getResources().getBoolean(R.bool.is_landscape);

        Uri movieImage = mPresenter.getImageUri(isLandscape);

        Glide.with(getApplicationContext())
                .load(movieImage)
                .placeholder(R.drawable.detail_image_placeholder)
                .crossFade()
                .error(R.drawable.image_error)
                .into(backdropImageView);

        voteAverageTextView.setText(String.valueOf(movie.getVoteAverage()));

        voteCountTextView.setText(String.valueOf(movie.getVoteCount()));

        overviewTextView.setText(movie.getOverview());

        revenueTextView.setText(String.valueOf(movie.getRevenue()));

        runtimeTextView.setText(String.valueOf(movie.getRuntime()));
    }
}
