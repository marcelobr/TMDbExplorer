package com.challenge.ndrive.tmdbexplorer.ui.main;

import android.content.Context;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.challenge.ndrive.tmdbexplorer.R;
import com.challenge.ndrive.tmdbexplorer.TmdbApplication;
import com.challenge.ndrive.tmdbexplorer.tmdb.TmdbClient;
import com.challenge.ndrive.tmdbexplorer.model.Movie;
import com.challenge.ndrive.tmdbexplorer.tmdb.TmdbImageType;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * RecyclerView Adapter for Movies list
 */

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.ViewHolder> {

    private List<Movie> mMoviesList = null;
    private Context mContext;

    private final int colorDefault;
    private final int colorRed;

    private TmdbClient mClient;

    // Provide a reference to the views for each data item
    static class ViewHolder extends RecyclerView.ViewHolder {

        ImageView mPosterImageView;
        TextView mTitleTextView;
        TextView mYearTextView;

        ViewHolder(View view) {
            super(view);
            mPosterImageView = view.findViewById(R.id.poster_image);
            mTitleTextView = view.findViewById(R.id.movie_title);
            mYearTextView = view.findViewById(R.id.movie_year);
        }
    }

    // Class constructor
    public MovieAdapter(Context context) {
        this.mContext = context;
        this.mClient = ((TmdbApplication) context.getApplicationContext()).getClient();
        this.colorDefault = ContextCompat.getColor(context, android.R.color.black);
        this.colorRed = ContextCompat.getColor(context, R.color.red);
    }

    // Create new views
    @Override
    public MovieAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // create a new view
        View listItem = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_item, parent, false);

        return new ViewHolder(listItem);
    }

    // Replace the contents of a view
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

        int movieYear = currentMovie.getYear();
        if (movieYear != 0) {
            holder.mYearTextView.setText(String.valueOf(movieYear));
            holder.mYearTextView.setTextColor(movieYear == getCurrentYear() ? colorRed : colorDefault);
        } else {
            holder.mYearTextView.setText(R.string.date_error_message);
        }
    }

    // Return the size of dataset
    @Override
    public int getItemCount() {
        return this.mMoviesList == null ? 0 : this.mMoviesList.size();
    }

//    public Movie getItem(int position) {
//        return this.mMoviesList == null ? null : this.mMoviesList.get(position);
//    }

    public void addMovies(List<Movie> movies) {
        this.mMoviesList = movies;
        notifyDataSetChanged();
    }

//    @NonNull
//    public List<Movie> getMoviesList() {
//        return this.mMoviesList == null ? new ArrayList<Movie>() : this.mMoviesList;
//    }

    public void clear() {
        if (getItemCount() > 0) {
            this.mMoviesList.clear();
            notifyDataSetChanged();
        }
    }

    private int getCurrentYear() {
        Calendar currentDate = Calendar.getInstance();
        return currentDate.get(Calendar.YEAR);
    }
}
