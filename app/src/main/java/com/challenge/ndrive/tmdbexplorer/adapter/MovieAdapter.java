package com.challenge.ndrive.tmdbexplorer.adapter;

import android.content.Context;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
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

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by marcelo on 04/01/18.
 */

public class MovieAdapter extends ArrayAdapter<Movie> {

    /** Tag for the log messages */
    private static final String LOG_TAG = MovieAdapter.class.getSimpleName();

    private static final String BASE_URL = "https://image.tmdb.org/t/p/w92";

    public MovieAdapter(@NonNull Context context, ArrayList<Movie> movies) {
        super(context, 0, movies);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View listItemView = convertView;
        if (listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(R.layout.list_item, parent, false);
        }

        Movie currentMovie = getItem(position);

        Uri builder = Uri.parse(BASE_URL)
                .buildUpon()
                .appendEncodedPath(currentMovie.getPoster​Image())
                .build();

        String posterImage = builder.toString();

        ImageView posterImageView = listItemView.findViewById(R.id.poster_image);

        Glide.with(getContext())
                .load(posterImage)
                .error(R.drawable.image_error)
                .into(posterImageView);

        TextView titleTextView = listItemView.findViewById(R.id.movie_title);
        titleTextView.setText(currentMovie.getTitle());

        // Create a new Date object from the time of the web publication date
        Date dateObject = null;
        String year = null;
        try {
            DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
            dateObject = df.parse(currentMovie.getReleaseDate());
            Calendar cal = Calendar.getInstance();
            cal.setTime(dateObject);
            year = String.valueOf(cal.get(Calendar.YEAR));
        } catch(ParseException e) {
            Log.e(LOG_TAG, "Date parse error ", e);
        }

        TextView yearTextView = listItemView.findViewById(R.id.movie_year);
        yearTextView.setText(year);

        return listItemView;
    }
}
