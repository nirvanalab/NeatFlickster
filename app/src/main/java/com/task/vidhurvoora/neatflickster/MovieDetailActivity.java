package com.task.vidhurvoora.neatflickster;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerView;
import com.squareup.picasso.Picasso;
import com.task.vidhurvoora.neatflickster.Adapter.MovieReviewAdapter;
import com.task.vidhurvoora.neatflickster.Model.Movie;
import com.task.vidhurvoora.neatflickster.Model.MovieManager;
import com.task.vidhurvoora.neatflickster.Model.MovieReview;
import com.task.vidhurvoora.neatflickster.Model.MovieReviewsCompletionHandler;
import com.task.vidhurvoora.neatflickster.Model.MovieTrailer;

import org.parceler.Parcels;

import java.util.ArrayList;

public class MovieDetailActivity extends YouTubeBaseActivity {

    private static final String Youtube_Api_Key = "AIzaSyDsgMR1XZNTfOoq4KOcG-4phFIcSdyU-PA";

    private Movie movie;
    private ImageView ivPosterBackground;
    private ImageView ivBackdropBackground;
    private TextView tvReleaseDate;
    private TextView tvTitleDetail;
    private TextView tvOverviewDetail;
    private ListView lvReviews;
    private MovieReviewAdapter reviewAdapter;
    private ArrayList<String> movieKeys = new ArrayList<>();
    private ArrayList<MovieReview>movieReviews = new ArrayList<MovieReview>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_detail);
        ivPosterBackground = (ImageView) findViewById(R.id.ivPosterBackground);
        ivBackdropBackground = (ImageView) findViewById(R.id.ivBackdropBackground);
        tvTitleDetail = (TextView)findViewById(R.id.tvTitleDetail);
        tvOverviewDetail = (TextView)findViewById(R.id.tvOverviewDetail);
        tvReleaseDate = (TextView)findViewById(R.id.tvReleaseDate);


        ArrayList<MovieTrailer> trailers = (ArrayList<MovieTrailer>) Parcels.unwrap(getIntent().getParcelableExtra("trailers"));
        for ( MovieTrailer trailer : trailers ) {
            movieKeys.add(trailer.key);
        }

        Movie movie = (Movie) Parcels.unwrap(getIntent().getParcelableExtra("movie"));
        String posterPath = movie.getPosterPath();
        if (ivPosterBackground != null) {
            Picasso.with(getApplicationContext()).load(posterPath).fit().into(ivPosterBackground);
        }
        String backdropPath = movie.getBackdropPath();
        if (ivBackdropBackground != null) {
            Picasso.with(getApplicationContext()).load(backdropPath).fit().into(ivBackdropBackground);
        }
        tvTitleDetail.setText(movie.getTitle());
        tvOverviewDetail.setText(movie.getOverview());
        tvReleaseDate.setText(movie.releaseDate);

        //fetch movie reviews
        lvReviews = (ListView)findViewById(R.id.lvReviews);
        if ( lvReviews != null ) {
            reviewAdapter = new MovieReviewAdapter(this,movieReviews);
            lvReviews.setAdapter(reviewAdapter);
        }

        doPlayerInit();
        if ( movie != null ) {
            fetchMovieReviews(movie);
        }
    }

    private void doPlayerInit() {
        //get youtube player view
        YouTubePlayerView playerView = (YouTubePlayerView) findViewById(R.id.movieDetailPlayer);

        playerView.initialize(Youtube_Api_Key, new YouTubePlayer.OnInitializedListener() {
            @Override
            public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer youTubePlayer, boolean b) {
                youTubePlayer.cueVideos(movieKeys);
            }

            @Override
            public void onInitializationFailure(YouTubePlayer.Provider provider, YouTubeInitializationResult youTubeInitializationResult) {
                //load an image instead ?
            }
        });
    }

    private void fetchMovieReviews(Movie movie){
        MovieManager.getsInstance().fetchReviewsForMovie(movie,new MovieReviewsCompletionHandler(){
            @Override
            public void movieReviews(Boolean isSuccess, ArrayList<MovieReview> reviews) {
                if ( isSuccess) {
                    movieReviews.addAll(reviews);
                    if ( reviewAdapter != null ) {
                        reviewAdapter.notifyDataSetChanged();
                    }

                }
            }
        });
    }
}
