package com.task.vidhurvoora.neatflickster.Adapter;

import android.content.Context;
import android.content.res.Configuration;
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
        ImageView ivBackdrop;
    }

    public MovieAdapter(Context context, ArrayList<Movie> movies) {
        super(context, android.R.layout.simple_list_item_1,movies);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        Boolean isPortrait = getContext().getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT ? true : false;
        Movie movie = getItem(position);
        MovieItemViewHolder viewHolder;
        if (convertView == null ) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.movie_list_item,parent,false);
            viewHolder = new MovieItemViewHolder();
            viewHolder.tvTitle = (TextView) convertView.findViewById(R.id.tvTitle);
            viewHolder.tvOverview = (TextView)convertView.findViewById(R.id.tvOverview);
            if ( isPortrait ) {
                viewHolder.ivPoster = (ImageView) convertView.findViewById(R.id.ivPoster);
            }
            else {
                viewHolder.ivBackdrop = (ImageView)convertView.findViewById(R.id.ivBackdrop);
            }

            convertView.setTag(viewHolder);
        }
        else {
            viewHolder = (MovieItemViewHolder) convertView.getTag();
        }

        viewHolder.tvTitle.setText(movie.getTitle());
        viewHolder.tvOverview.setText(movie.getOverview());
        if ( isPortrait ) {
            Picasso.with(getContext()).load(movie.getPosterPath()).into(viewHolder.ivPoster);
        }
        else {
            Picasso.with(getContext()).load(movie.getBackdropPath()).into(viewHolder.ivBackdrop);
        }

        return convertView;
    }
}
