package com.task.vidhurvoora.neatflickster;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie);

        lvMovies = (ListView) findViewById(R.id.lvMovies);
        movieList = new ArrayList<>();
        movieAdapter = new MovieAdapter(this,movieList);
        lvMovies.setAdapter(movieAdapter);

        loadMovies();
    }

    private void loadMovies()
    {
        MovieManager.getsInstance().fetchNowPlayingMovies(new MovieResponseCompletionHandler() {
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
