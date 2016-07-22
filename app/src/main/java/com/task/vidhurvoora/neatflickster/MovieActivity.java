package com.task.vidhurvoora.neatflickster;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ListView;

import com.task.vidhurvoora.neatflickster.Adapter.MovieAdapter;
import com.task.vidhurvoora.neatflickster.Model.Movie;
import com.task.vidhurvoora.neatflickster.Model.MovieManager;
import com.task.vidhurvoora.neatflickster.Model.MovieResponseCompletionHandler;
import com.task.vidhurvoora.neatflickster.Model.MovieTrailer;
import com.task.vidhurvoora.neatflickster.Model.MovieTrailerResponseCompletionHandler;

import org.parceler.Parcels;

import java.util.ArrayList;

public class MovieActivity extends AppCompatActivity {

    ListView lvMovies;
    MovieAdapter movieAdapter;
    ArrayList<Movie> movieList;
    int page = 0;

    private SwipeRefreshLayout movieContainer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie);
        //hide the action bar
        getSupportActionBar().hide();

        lvMovies = (ListView) findViewById(R.id.lvMovies);
        movieList = new ArrayList<>();
        movieAdapter = new MovieAdapter(this,movieList);
        lvMovies.setAdapter(movieAdapter);
        movieContainer =  (SwipeRefreshLayout) findViewById(R.id.movieContainer);

        //setup listeners
        setupRefreshListener();
        setupScrollListener();
        setupMovieClickListener();

        //load movies
        loadMovies();
    }

    //refresh listener
    private void setupRefreshListener()
    {
        movieContainer.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                //set the page = 0
                //clear the movieList ?
                page = 0;
                movieList.clear();
                loadMovies();
            }
        });
        movieContainer.setColorSchemeResources(android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);
    }

    //scroll listener
    private void setupScrollListener()
    {
        lvMovies.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView absListView, int i) {

            }

            @Override
            public void onScroll(AbsListView absListView, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                Log.d("First Visible Item",Integer.toString(firstVisibleItem));
                Log.d("Visible Item Count",Integer.toString(visibleItemCount));
                Log.d("Total Item Count",Integer.toString(totalItemCount));

                if ( firstVisibleItem + visibleItemCount == totalItemCount
                        && totalItemCount == movieList.size()
                        && totalItemCount > 0 ) {
                    //Load next page
                    Log.d("Calling Load Movies","");
                    loadMovies();
                }
            }
        });
    }

    private void setupMovieClickListener() {

        lvMovies.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {

                Movie movie = movieList.get(position);

                MovieManager.getsInstance().fetchTrailersForMovie(movie, new MovieTrailerResponseCompletionHandler() {
                    @Override
                    public void trailerResults(Boolean isSuccess, ArrayList<MovieTrailer> trailers) {
                        if ( isSuccess && trailers.size() > 0) {
                            Intent intent = new Intent(MovieActivity.this,YoutubeMoviePlayerActivity.class);
                            intent.putExtra("trailers", Parcels.wrap(trailers));
                            startActivity(intent);
                        }
                    }


                });
            }
        });

    }


    private void loadMovies()
    {
        MovieManager.getsInstance().fetchNowPlayingMovies(++page,new MovieResponseCompletionHandler() {
            @Override
            public void movieResults(Boolean isSuccess, ArrayList<Movie> movies) {
                if (isSuccess) {
                    movieList.addAll(movies);
                    movieAdapter.notifyDataSetChanged();
                    movieContainer.setRefreshing(false);
                }
            }
        });
    }
}
