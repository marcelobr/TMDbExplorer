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
import com.challenge.ndrive.tmdbexplorer.model.Movie;
import com.challenge.ndrive.tmdbexplorer.utils.TmdbClient;

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

    private List<Movie> moviesList;
    private Context context;

    private final int colorDefault;
    private final int colorRed;

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
        this.context = context;
        this.colorDefault = ContextCompat.getColor(context, android.R.color.black);
        this.colorRed = ContextCompat.getColor(context, R.color.red);
    }

    @Override
    public MovieAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View listItem = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_item, parent, false);

        return new ViewHolder(listItem);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Movie movie = this.moviesList.get(position);

        Uri builder = TmdbClient.getImageUri(movie.getPoster​Image(), false);

        String posterImage = builder.toString();

        Glide.with(context)
                .load(posterImage)
                .placeholder(R.drawable.image_placeholder)
                .error(R.drawable.image_error)
                .crossFade()
                .into(holder.mPosterImageView);

        holder.mTitleTextView.setText(movie.getTitle());

        // Create a new Date object from the time of the web publication date
        Date dateObject;
        try {
            DateFormat df = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
            dateObject = df.parse(movie.getReleaseDate());
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

    @Override
    public int getItemCount() {
        return this.moviesList == null ? 0 : this.moviesList.size();
    }

    public Movie getItem(int position) {
        return this.moviesList == null ? null : this.moviesList.get(position);
    }

    public void addMovies(List<Movie> movies) {
        this.moviesList = movies;
        notifyDataSetChanged();
    }

    public void clear() {
        if (getItemCount() > 0) {
            this.moviesList.clear();
            notifyDataSetChanged();
        }
    }
}
