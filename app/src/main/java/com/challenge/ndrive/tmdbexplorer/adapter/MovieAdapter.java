package com.challenge.ndrive.tmdbexplorer.adapter;

import android.content.Context;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.challenge.ndrive.tmdbexplorer.R;
import com.challenge.ndrive.tmdbexplorer.model.Movie;

import java.util.ArrayList;

/**
 * Created by marcelo on 04/01/18.
 */

public class MovieAdapter extends ArrayAdapter<Movie> {

    final String BASE_URL = "https://image.tmdb.org/t/p/w92";

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
                .appendPath(currentMovie.getPoster​Image())
                .build();

        String posterImage = builder.toString();

        ImageView imageView = (ImageView) listItemView.findViewById(R.id.list_item_image);

        Glide.with(getContext())
                .load(posterImage)
                .error(R.drawable.image_error)
                .into(imageView);



        return listItemView;
    }
}
