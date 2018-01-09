package com.challenge.ndrive.tmdbexplorer.adapter;

import android.content.Context;
import android.net.Uri;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.challenge.ndrive.tmdbexplorer.R;
import com.challenge.ndrive.tmdbexplorer.TmdbApplication;
import com.challenge.ndrive.tmdbexplorer.interfaces.TmdbClient;
import com.challenge.ndrive.tmdbexplorer.model.Movie;
import com.challenge.ndrive.tmdbexplorer.utils.TmdbImageType;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

/**
 * Created by marcelo on 04/01/18.
 */

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.ViewHolder> {

    /** Tag for the log messages */
    private static final String LOG_TAG = MovieAdapter.class.getSimpleName();

    private List<Movie> mMoviesList = null;
    private Context mContext;

    private final int colorDefault;
    private final int colorRed;

    private TmdbClient mClient;

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public static class ViewHolder extends RecyclerView.ViewHolder {

        public ImageView mPosterImageView;
        public TextView mTitleTextView;
        public TextView mYearTextView;

        public ViewHolder(View view) {
            super(view);
            mPosterImageView = view.findViewById(R.id.poster_image);
            mTitleTextView = view.findViewById(R.id.movie_title);
            mYearTextView = view.findViewById(R.id.movie_year);
        }
    }

    public MovieAdapter(Context context) {
        this.mContext = context;
        this.mClient = ((TmdbApplication) context.getApplicationContext()).getClient();
        this.colorDefault = ContextCompat.getColor(context, android.R.color.black);
        this.colorRed = ContextCompat.getColor(context, R.color.red);
    }

    // Create new views (invoked by the layout manager)
    @Override
    public MovieAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // create a new view
        View listItem = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_item, parent, false);

        return new ViewHolder(listItem);
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Movie currentMovie = this.mMoviesList.get(position);

        Uri posterImage = this.mClient.getImageUri(currentMovie.getPoster​Image(), TmdbImageType.THUMB);

        Glide.with(mContext)
                .load(posterImage)
                .placeholder(R.drawable.image_placeholder)
                .error(R.drawable.image_error)
                .crossFade()
                .into(holder.mPosterImageView);

        holder.mTitleTextView.setText(currentMovie.getTitle());

        // Create a new Date object from the time of the web publication date
        Date dateObject;
        try {
            DateFormat df = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
            dateObject = df.parse(currentMovie.getReleaseDate());
            Calendar movieDate = Calendar.getInstance();
            movieDate.setTime(dateObject);
            int movieYear = movieDate.get(Calendar.YEAR);
            holder.mYearTextView.setText(String.valueOf(movieYear));
            Calendar currentDate = Calendar.getInstance();
            int currentYear = currentDate.get(Calendar.YEAR);
            holder.mYearTextView.setTextColor(movieYear == currentYear ? colorRed : colorDefault);
        } catch(ParseException e) {
            Log.e(LOG_TAG, "Date parse error ", e);
            holder.mYearTextView.setText(R.string.date_error_message);
        }
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return this.mMoviesList == null ? 0 : this.mMoviesList.size();
    }

    public Movie getItem(int position) {
        return this.mMoviesList == null ? null : this.mMoviesList.get(position);
    }

    public void addMovies(List<Movie> movies) {
        this.mMoviesList = movies;
        notifyDataSetChanged();
    }

    public void clear() {
        if (getItemCount() > 0) {
            this.mMoviesList.clear();
            notifyDataSetChanged();
        }
    }
}
