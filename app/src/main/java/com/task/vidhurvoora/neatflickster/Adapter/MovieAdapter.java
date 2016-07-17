package com.task.vidhurvoora.neatflickster.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.task.vidhurvoora.neatflickster.Model.Movie;
import com.task.vidhurvoora.neatflickster.R;

import java.util.ArrayList;

/**
 * Created by vidhurvoora on 7/16/16.
 */
public class MovieAdapter extends ArrayAdapter<Movie>
{
    private static class MovieItemViewHolder
    {
        TextView tvTitle;
        TextView tvOverview;
        ImageView ivPoster;
    }

    public MovieAdapter(Context context, ArrayList<Movie> movies) {
        super(context, android.R.layout.simple_list_item_1,movies);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        Movie movie = getItem(position);
        MovieItemViewHolder viewHolder;
        if (convertView == null ) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.movie_list_item,parent,false);
            viewHolder = new MovieItemViewHolder();
            viewHolder.tvTitle = (TextView) convertView.findViewById(R.id.tvTitle);
            viewHolder.tvOverview = (TextView)convertView.findViewById(R.id.tvOverview);
            viewHolder.ivPoster = (ImageView) convertView.findViewById(R.id.ivPoster);
            convertView.setTag(viewHolder);
        }
        else {
            viewHolder = (MovieItemViewHolder) convertView.getTag();
        }

        viewHolder.tvTitle.setText(movie.getTitle());
        viewHolder.tvOverview.setText(movie.getOverview());
        Picasso.with(getContext()).load(movie.getPosterPath()).into(viewHolder.ivPoster);

        return convertView;
    }
}
