package com.challenge.ndrive.tmdbexplorer.adapter;

import android.content.Context;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.challenge.ndrive.tmdbexplorer.R;
import com.challenge.ndrive.tmdbexplorer.model.Movie;
import com.challenge.ndrive.tmdbexplorer.utils.TmdbClient;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 * Created by marcelo on 04/01/18.
 */

public class MovieAdapter extends ArrayAdapter<Movie> {

    /** Tag for the log messages */
    private static final String LOG_TAG = MovieAdapter.class.getSimpleName();

    private static final String BASE_URL = "https://image.tmdb.org/t/p/w92";

    private final int colorDefault;
    private final int colorRed;

    public MovieAdapter(@NonNull Context context, ArrayList<Movie> movies) {
        super(context, 0, movies);
        this.colorDefault = ContextCompat.getColor(context, android.R.color.black);
        this.colorRed = ContextCompat.getColor(context, R.color.red);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View listItemView = convertView;
        if (listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(R.layout.list_item, parent, false);
        }

        Movie currentMovie = getItem(position);

        Uri builder = TmdbClient.getImageUri(currentMovie.getPoster​Image(), false);

        String posterImage = builder.toString();

        ImageView posterImageView = listItemView.findViewById(R.id.poster_image);

        Glide.with(getContext())
                .load(posterImage)
                .placeholder(R.drawable.image_placeholder)
                .error(R.drawable.image_error)
                .crossFade()
                .into(posterImageView);

        TextView titleTextView = listItemView.findViewById(R.id.movie_title);
        titleTextView.setText(currentMovie.getTitle());

        TextView yearTextView = listItemView.findViewById(R.id.movie_year);

        // Create a new Date object from the time of the web publication date
        Date dateObject;
        try {
            DateFormat df = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
            dateObject = df.parse(currentMovie.getReleaseDate());
            Calendar movieDate = Calendar.getInstance();
            movieDate.setTime(dateObject);
            int movieYear = movieDate.get(Calendar.YEAR);
            yearTextView.setText(String.valueOf(movieYear));
            Calendar currentDate = Calendar.getInstance();
            int currentYear = currentDate.get(Calendar.YEAR);
            yearTextView.setTextColor(movieYear == currentYear ? colorRed : colorDefault);
        } catch(ParseException e) {
            Log.e(LOG_TAG, "Date parse error ", e);
            yearTextView.setText(R.string.date_error_message);
        }

        return listItemView;
    }
}
