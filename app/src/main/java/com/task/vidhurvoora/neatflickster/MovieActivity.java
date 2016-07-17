package com.task.vidhurvoora.neatflickster;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.AbsListView;
import android.widget.ListView;

import com.task.vidhurvoora.neatflickster.Adapter.MovieAdapter;
import com.task.vidhurvoora.neatflickster.Model.Movie;
import com.task.vidhurvoora.neatflickster.Model.MovieManager;
import com.task.vidhurvoora.neatflickster.Model.MovieResponseCompletionHandler;

import java.util.ArrayList;

public class MovieActivity extends AppCompatActivity {

    ListView lvMovies;
    MovieAdapter movieAdapter;
    ArrayList<Movie> movieList;
    int page = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie);

        lvMovies = (ListView) findViewById(R.id.lvMovies);
        movieList = new ArrayList<>();

        movieAdapter = new MovieAdapter(this,movieList);
        lvMovies.setAdapter(movieAdapter);

        loadMovies();

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

    private void loadMovies()
    {
        MovieManager.getsInstance().fetchNowPlayingMovies(++page,new MovieResponseCompletionHandler() {
            @Override
            public void movieResults(Boolean isSuccess, ArrayList<Movie> movies) {
                if (isSuccess) {
                    movieList.addAll(movies);
                    movieAdapter.notifyDataSetChanged();
                }
            }
        });
    }
}
