package com.task.vidhurvoora.neatflickster.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.task.vidhurvoora.neatflickster.Model.MovieReview;
import com.task.vidhurvoora.neatflickster.R;

import java.util.ArrayList;

/**
 * Created by vidhurvoora on 7/23/16.
 */
public class MovieReviewAdapter extends ArrayAdapter<MovieReview>
{
    public MovieReviewAdapter(Context context, ArrayList<MovieReview> reviews)
    {
        super(context, android.R.layout.simple_list_item_1,reviews);
    }

    private static class MovieReviewHolder {
        TextView tvAuthor;
        TextView tvReview;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        MovieReview review = getItem(position);
        MovieReviewHolder reviewHolder;
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.movie_review_item,parent,false);
            reviewHolder = new MovieReviewHolder();
            reviewHolder.tvAuthor = (TextView) convertView.findViewById(R.id.tvAuthor);
            reviewHolder.tvReview = (TextView) convertView.findViewById(R.id.tvReview);
            convertView.setTag(reviewHolder);
        }
        else {
            reviewHolder = (MovieReviewHolder) convertView.getTag();
        }

        reviewHolder.tvAuthor.setText(review.author);
        reviewHolder.tvReview.setText(review.content);
        return convertView;
    }
}
