package com.challenge.ndrive.tmdbexplorer.activity;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.challenge.ndrive.tmdbexplorer.R;
import com.challenge.ndrive.tmdbexplorer.TmdbApplication;
import com.challenge.ndrive.tmdbexplorer.interfaces.TmdbClient;
import com.challenge.ndrive.tmdbexplorer.loader.MovieDetailLoader;
import com.challenge.ndrive.tmdbexplorer.model.Movie;
import com.challenge.ndrive.tmdbexplorer.utils.TmdbImageType;

import butterknife.BindView;
import butterknife.ButterKnife;


public class DetailActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Movie> {

    /**
     * Constant value for the movie detail loader ID.
     */
    private static final int MOVIE_DETAIL_LOADER_ID = 2;

    private TmdbClient mClient;

    /**
     * TextView that is displayed when the list is empty
     */
    @BindView(R.id.movie_detail_empty_view)
    TextView mEmptyStateTextView;

    @BindView(R.id.movie_detail_loading_indicator)
    View loadingIndicator;

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

        mClient = ((TmdbApplication) getApplication()).getClient();

        loadingIndicator.setVisibility(View.VISIBLE);

        mEmptyStateTextView.setText("");

        // Get a reference to the ConnectivityManager to check state of network connectivity
        ConnectivityManager connMgr = (ConnectivityManager)
                getSystemService(Context.CONNECTIVITY_SERVICE);

        // Get details on the currently active default data network
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();

        Bundle extras = getIntent().getExtras();

        // If there is a network connection, fetch data
        if (networkInfo != null && networkInfo.isConnected()) {
            if (extras != null) {
                long movieId = extras.getLong("MovieId");
                Bundle movieIdBundle = new Bundle();
                movieIdBundle.putLong("movieId", movieId);
                getSupportLoaderManager().initLoader(MOVIE_DETAIL_LOADER_ID, movieIdBundle, this);
            }
        } else {
            // Otherwise, display error
            // First, hide loading indicator so error message will be visible
            loadingIndicator.setVisibility(View.GONE);

            // Update empty state with no connection error message
            mEmptyStateTextView.setText(R.string.no_internet_connection);
        }
    }

    @Override
    public Loader<Movie> onCreateLoader(int i, Bundle bundle) {
        return new MovieDetailLoader(this, bundle.getLong("movieId"));
    }

    @Override
    public void onLoadFinished(Loader<Movie> loader, Movie movieDetail) {

        loadingIndicator.setVisibility(View.GONE);

        titleTextView.setText(movieDetail.getTitle());

        boolean isLandscape = getResources().getBoolean(R.bool.is_landscape);

        String moviePathImage = isLandscape ? movieDetail.getPoster​Image() : movieDetail.getBackdropPath();

        Uri movieImage = mClient.getImageUri(moviePathImage, TmdbImageType.LARGE);

        Glide.with(this)
                .load(movieImage)
                .placeholder(R.drawable.detail_image_placeholder)
                .crossFade()
                .error(R.drawable.image_error)
                .into(backdropImageView);

        voteAverageTextView.setText(String.valueOf(movieDetail.getVoteAverage()));

        voteCountTextView.setText(String.valueOf(movieDetail.getVoteCount()));

        overviewTextView.setText(movieDetail.getOverview());

        revenueTextView.setText(String.valueOf(movieDetail.getRevenue()));

        runtimeTextView.setText(String.valueOf(movieDetail.getRuntime()));
    }

    @Override
    public void onLoaderReset(Loader<Movie> loader) {
    }
}
