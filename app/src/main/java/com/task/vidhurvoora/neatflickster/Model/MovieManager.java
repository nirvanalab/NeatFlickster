package com.task.vidhurvoora.neatflickster.Model;

import android.util.Log;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;

public class MovieManager {

    private static AsyncHttpClient httpClient;
    private String apiKey = "a07e22bc18f5cb106bfe4cc1f83ad8ed";
    private String movieDbBaseUrl = "https://api.themoviedb.org/3/movie";
    private String nowPlayingUrl = " https://api.themoviedb.org/3/movie/now_playing?api_key=a07e22bc18f5cb106bfe4cc1f83ad8ed";
    private static MovieManager sInstance;

    public static synchronized MovieManager getsInstance()
    {
        if ( sInstance == null ) {
            sInstance = new MovieManager();
            httpClient = new AsyncHttpClient();
        }
        return sInstance;
    }

    public ArrayList<Movie> fetchMoviesFromJsonArray(JSONArray movieArr) {
        ArrayList<Movie> movies = new ArrayList<Movie>();
        for ( int i = 0 ; i < movieArr.length();i++) {
            try {
                JSONObject movieObj = movieArr.getJSONObject(i);
                Movie movie = new Movie(movieObj);
                movies.add(movie);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return movies;
    }

    public ArrayList<MovieTrailer>fetchMovieTrailersFromJsonArray(JSONArray trailerArr) throws JSONException {
        ArrayList<MovieTrailer> trailers = new ArrayList<MovieTrailer>();
        for ( int i = 0;i < trailerArr.length();i++ ) {
            JSONObject trailerObj = trailerArr.getJSONObject(i);
            MovieTrailer trailer = new MovieTrailer(trailerObj);
            trailers.add(trailer);
        }
        return trailers;
    }

    public ArrayList<MovieReview>fetchMovieReviewsFromJsonArray(JSONArray reviewArr) throws JSONException {
        ArrayList<MovieReview> reviews = new ArrayList<MovieReview>();
        for ( int i = 0;i < reviewArr.length();i++ ) {
            JSONObject reviewObj = reviewArr.getJSONObject(i);
            MovieReview review = new MovieReview(reviewObj);
            reviews.add(review);
        }
        return reviews;
    }


    public void fetchNowPlayingMovies(int page ,final MovieResponseCompletionHandler handler) {
        RequestParams params = new RequestParams();
        params.put("page",page);

        this.httpClient.get(nowPlayingUrl,params,new JsonHttpResponseHandler(){

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                super.onSuccess(statusCode, headers, response);
                JSONArray results = new JSONArray();

                try {
                   results =  response.getJSONArray("results");
                    ArrayList<Movie> movies = fetchMoviesFromJsonArray(results);
                    handler.movieResults(true,movies);
                    Log.d("Results",results.toString());
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                super.onFailure(statusCode, headers, throwable, errorResponse);
                handler.movieResults(false,null);
            }

        });
    }

    public void fetchTrailersForMovie (Movie movie, final MovieTrailerResponseCompletionHandler handler) {

        String trailerRequestUrl = String.format("%s/%d/videos?api_key=%s",movieDbBaseUrl,movie.getId(),apiKey);
        RequestParams params = new RequestParams();
        this.httpClient.get(trailerRequestUrl,params,new JsonHttpResponseHandler(){

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                super.onSuccess(statusCode, headers, response);
                JSONArray results = new JSONArray();
                try {
                    results = response.getJSONArray("results");
                    ArrayList<MovieTrailer> trailers = fetchMovieTrailersFromJsonArray(results);
                    handler.trailerResults(true,trailers);
                }catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, String responseString) {
                super.onSuccess(statusCode, headers, responseString);
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONArray response) {
                super.onSuccess(statusCode, headers, response);
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                super.onFailure(statusCode, headers, throwable, errorResponse);
                handler.trailerResults(false,null);
            }
        });
    }

    public void fetchReviewsForMovie (Movie movie, final MovieReviewsCompletionHandler handler ) {

        String reviewRequestUrl = String.format("%s/%d/reviews?api_key=%s",movieDbBaseUrl,movie.getId(),apiKey);
        RequestParams params = new RequestParams();
        this.httpClient.get(reviewRequestUrl,params,new JsonHttpResponseHandler(){

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                super.onSuccess(statusCode, headers, response);
                JSONArray results = new JSONArray();
                try {
                    results = response.getJSONArray("results");
                    ArrayList<MovieReview> reviews = fetchMovieReviewsFromJsonArray(results);
                    handler.movieReviews(true,reviews);
                }catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, String responseString) {
                super.onSuccess(statusCode, headers, responseString);
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONArray response) {
                super.onSuccess(statusCode, headers, response);
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                super.onFailure(statusCode, headers, throwable, errorResponse);
                handler.movieReviews(false,null);
            }
        });
    }

}
