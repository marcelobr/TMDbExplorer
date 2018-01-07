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
import com.challenge.ndrive.tmdbexplorer.loader.MovieDetailLoader;
import com.challenge.ndrive.tmdbexplorer.model.MovieDetail;


public class DetailActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<MovieDetail> {

    private static final String MOVIE_SEARCH_BASE_URL = "https://api.themoviedb.org/3/movie/";
    private static final String API_KEY_PARAM = "api_key";
    private static final String LANGUAGE_PARAM = "language";

    private static final String BASE_URL = "https://image.tmdb.org/t/p/w500";

    /**
     * Constant value for the movie detail loader ID.
     */
    private static final int MOVIE_DETAIL_LOADER_ID = 2;

    /** TextView that is displayed when the list is empty */
    private TextView mEmptyStateTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        mEmptyStateTextView = (TextView) findViewById(R.id.movie_detail_empty_view);
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
                int movieId = extras.getInt("MovieId");
                Bundle movieIdBundle = new Bundle();
                movieIdBundle.putString("movieId", String.valueOf(movieId));
                getSupportLoaderManager().initLoader(MOVIE_DETAIL_LOADER_ID, movieIdBundle, this);
            }
        } else {
            // Otherwise, display error
            // First, hide loading indicator so error message will be visible
            View loadingIndicator = findViewById(R.id.movie_detail_loading_indicator);
            loadingIndicator.setVisibility(View.GONE);

            // Update empty state with no connection error message
            mEmptyStateTextView.setText(R.string.no_internet_connection);
        }
    }

    @Override
    public Loader<MovieDetail> onCreateLoader(int i, Bundle bundle) {
        Uri builtURI = Uri.parse(MOVIE_SEARCH_BASE_URL).buildUpon()
                .appendEncodedPath(bundle.getString("movieId"))
                .appendQueryParameter(API_KEY_PARAM, "83d01f18538cb7a275147492f84c3698")
                .appendQueryParameter(LANGUAGE_PARAM, "en-US")
                .build();

        return new MovieDetailLoader(this, builtURI.toString());
    }

    @Override
    public void onLoadFinished(Loader<MovieDetail> loader, MovieDetail movieDetail) {
        View loadingIndicator = findViewById(R.id.movie_detail_loading_indicator);
        loadingIndicator.setVisibility(View.GONE);

        TextView titleTextView = (TextView) findViewById(R.id.movie_detail_title);
        titleTextView.setText(movieDetail.getTitle());

        Uri builder = Uri.parse(BASE_URL)
                .buildUpon()
                .appendEncodedPath(movieDetail.getBackdropPath())
                .build();

        String backdropImage = builder.toString();

        ImageView backdropImageView = (ImageView) findViewById(R.id.movie_detail_backdrop);
        Glide.with(this)
                .load(backdropImage)
                .error(R.drawable.image_error)
                .into(backdropImageView);

        TextView voteAverageTextView = (TextView) findViewById(R.id.movie_detail_vote_average);
        voteAverageTextView.setText(String.valueOf(movieDetail.getVoteAverage()));

        TextView voteCountTextView = (TextView) findViewById(R.id.movie_detail_vote_count);
        voteCountTextView.setText(String.valueOf(movieDetail.getVoteCount()));

        TextView overviewTextView = (TextView) findViewById(R.id.movie_detail_overview);
        overviewTextView.setText(movieDetail.getOverview());

        TextView revenueTextView = (TextView) findViewById(R.id.movie_detail_revenue);
        revenueTextView.setText(String.valueOf(movieDetail.getRevenue()));

        TextView runtimeTextView = (TextView) findViewById(R.id.movie_detail_runtime);
        runtimeTextView.setText(String.valueOf(movieDetail.getRuntime()));
    }

    @Override
    public void onLoaderReset(Loader<MovieDetail> loader) {}
}
