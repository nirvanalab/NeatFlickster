package com.task.vidhurvoora.neatflickster.Adapter;

import android.content.Context;
import android.content.res.Configuration;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.LayerDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.task.vidhurvoora.neatflickster.Model.Movie;
import com.task.vidhurvoora.neatflickster.Model.MovieCategory;
import com.task.vidhurvoora.neatflickster.R;

import java.util.ArrayList;

import jp.wasabeef.picasso.transformations.RoundedCornersTransformation;

/**
 * Created by vidhurvoora on 7/16/16.
 */
public class MovieAdapter extends ArrayAdapter<Movie>
{
    private int lastPosition = -1;

    private static class MovieItemViewHolder
    {
        TextView tvTitle;
        TextView tvOverview;
        ImageView ivPoster;
        ImageView ivBackdrop;
        RatingBar rbMovieRating;
    }

    private static class PopularMovieItemViewHolder
    {
        ImageView ivBackdrop;
        TextView tvTitle;
        RatingBar rbMovieRating;
    }

    public MovieAdapter(Context context, ArrayList<Movie> movies)
    {
        super(context, android.R.layout.simple_list_item_1,movies);
    }

    @Override
    public int getViewTypeCount() {
        return MovieCategory.values().length;
    }

    @Override
    public int getItemViewType(int position)
    {
        Movie movie = (Movie)getItem(position);
        MovieCategory category = movie.getCategory();
        return category.ordinal();
    }

    private View getInflatedViewForType(int categoryType )
    {
        if ( categoryType == MovieCategory.MovieCategoryPopular.ordinal() ) {
            //popular movie
            return LayoutInflater.from(getContext()).inflate(R.layout.popular_movie_list_item,null);
        }

        return LayoutInflater.from(getContext()).inflate(R.layout.movie_list_item,null);

    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {

        Boolean isPortrait = getContext().getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT ? true : false;
        int movieCategoryType = getItemViewType(position);
        Movie movie = getItem(position);

        if ( movieCategoryType == MovieCategory.MovieCategoryNotPopular.ordinal()) {

            MovieItemViewHolder movieHolder;
            if (convertView == null ) {

                convertView = getInflatedViewForType(movieCategoryType);


                movieHolder = new MovieItemViewHolder();

                movieHolder.tvTitle = (TextView) convertView.findViewById(R.id.tvTitle);
                movieHolder.tvOverview = (TextView)convertView.findViewById(R.id.tvOverview);
                movieHolder.rbMovieRating = (RatingBar)convertView.findViewById(R.id.rbMovieRating);

                if ( isPortrait ) {
                    movieHolder.ivPoster = (ImageView) convertView.findViewById(R.id.ivPoster);
                }
                else {
                    movieHolder.ivBackdrop = (ImageView)convertView.findViewById(R.id.ivBackdrop);
                }

                convertView.setTag(movieHolder);
            }
            else {
                movieHolder = (MovieItemViewHolder) convertView.getTag();
            }
            if (movieHolder.tvTitle != null) {
                movieHolder.tvTitle.setText(movie.getTitle());
            }
            if (movieHolder.tvOverview != null ) {
                movieHolder.tvOverview.setText(movie.getOverview());
            }
            if (movieHolder.rbMovieRating !=null ) {
                movieHolder.rbMovieRating.setMax(10);
                movieHolder.rbMovieRating.setRating(movie.getVoteAverage()/2);
                LayerDrawable stars = (LayerDrawable) movieHolder.rbMovieRating.getProgressDrawable();
                stars.getDrawable(2).setColorFilter(Color.YELLOW, PorterDuff.Mode.SRC_ATOP);
            }

            if ( isPortrait ) {
                if (movieHolder.ivPoster != null ) {
                    Picasso.with(getContext()).load(movie.getPosterPath()).transform(new RoundedCornersTransformation(10,10)).placeholder(R.drawable.placeholder_240).into(movieHolder.ivPoster);}
                }
            else {
                if (movieHolder.ivBackdrop != null ) {

                    Picasso.with(getContext()).load(movie.getBackdropPath()).transform(new RoundedCornersTransformation(20,20)).placeholder(R.drawable.placeholder_240).into(movieHolder.ivBackdrop);}
                }
        }
        else {
            //popular movie
            PopularMovieItemViewHolder popularMovieHolder;
            if ( convertView == null ) {
                convertView = getInflatedViewForType(movieCategoryType);
                popularMovieHolder = new PopularMovieItemViewHolder();
                popularMovieHolder.ivBackdrop = (ImageView)convertView.findViewById(R.id.ivBackdrop);
                popularMovieHolder.tvTitle = (TextView)convertView.findViewById(R.id.tvTitle);
                popularMovieHolder.rbMovieRating = (RatingBar)convertView.findViewById(R.id.rbMovieRating);
                convertView.setTag(popularMovieHolder);
            }
            else {
                popularMovieHolder = (PopularMovieItemViewHolder)convertView.getTag();
            }
            if ( popularMovieHolder.ivBackdrop != null ) {
                Picasso.with(getContext()).load(movie.getBackdropPath()).transform(new RoundedCornersTransformation(20,20)).placeholder(R.drawable.placeholder_240).into(popularMovieHolder.ivBackdrop);
            }
            popularMovieHolder.tvTitle.setText(movie.title);
            if (popularMovieHolder.rbMovieRating !=null ) {
                popularMovieHolder.rbMovieRating.setMax(10);
                popularMovieHolder.rbMovieRating.setRating(movie.getVoteAverage()/2);
                LayerDrawable stars = (LayerDrawable) popularMovieHolder.rbMovieRating.getProgressDrawable();
                stars.getDrawable(2).setColorFilter(Color.YELLOW, PorterDuff.Mode.SRC_ATOP);
            }

        }

//        lastPosition = position;
//        Animation animation = new TranslateAnimation(0, 0, (position > lastPosition) ? 100 : -100, 0);
//        animation.setDuration(400);
//        convertView.startAnimation(animation);
        return convertView;
    }
}
