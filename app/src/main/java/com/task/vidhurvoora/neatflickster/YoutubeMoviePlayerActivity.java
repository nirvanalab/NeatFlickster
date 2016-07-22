package com.task.vidhurvoora.neatflickster;

import android.os.Bundle;
import android.view.View;

import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerView;
import com.task.vidhurvoora.neatflickster.Model.MovieTrailer;

import org.parceler.Parcels;

import java.util.ArrayList;

public class YoutubeMoviePlayerActivity extends YouTubeBaseActivity {

    private static final String Youtube_Api_Key = "AIzaSyDsgMR1XZNTfOoq4KOcG-4phFIcSdyU-PA";
    private ArrayList<String> movieKeys = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_youtube_movie_player);

        //hide status bar
        View decorView = getWindow().getDecorView();
        decorView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_FULLSCREEN);
        ArrayList<MovieTrailer> trailers = (ArrayList<MovieTrailer>) Parcels.unwrap(getIntent().getParcelableExtra("trailers"));

        for ( MovieTrailer trailer : trailers ) {
            movieKeys.add(trailer.key);
        }

        doPlayerInit();

    }

    private void doPlayerInit() {
        //get youtube player view
        YouTubePlayerView playerView = (YouTubePlayerView) findViewById(R.id.moviePlayer);

        playerView.initialize(Youtube_Api_Key, new YouTubePlayer.OnInitializedListener() {
            @Override
            public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer youTubePlayer, boolean b) {
                youTubePlayer.loadVideos(movieKeys);
            }

            @Override
            public void onInitializationFailure(YouTubePlayer.Provider provider, YouTubeInitializationResult youTubeInitializationResult) {
                YoutubeMoviePlayerActivity.this.finish();
            }
        });
    }
}
