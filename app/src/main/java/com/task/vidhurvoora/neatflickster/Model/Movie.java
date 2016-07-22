package com.task.vidhurvoora.neatflickster.Model;

import org.json.JSONException;
import org.json.JSONObject;

public class Movie
{
    private String posterPath;
    private String overview;
    private String title;
    private String backdropPath;
    private MovieCategory category;
    private int id;

    public Movie (JSONObject jsonObject) throws JSONException {
        this.posterPath = jsonObject.getString("poster_path");
        this.overview = jsonObject.getString("overview");
        this.title = jsonObject.getString("title");
        this.backdropPath = jsonObject.getString("backdrop_path");
        int voteAverage = jsonObject.getInt("vote_average");
        this.category = voteAverage > 5 ? MovieCategory.MovieCategoryPopular : MovieCategory.MovieCategoryNotPopular;
        this.id = jsonObject.getInt("id");
    }

    public String getPosterPath() {
        return String.format("https://image.tmdb.org/t/p/w342/%s",posterPath);
    }

    public String getOverview() {
        return overview;
    }

    public String getTitle() {
        return title;
    }

    public String getBackdropPath() {
        return String.format("https://image.tmdb.org/t/p/w780/%s",backdropPath);
    }

    public int getId() {
        return id;
    }

    public MovieCategory getCategory() {
        return category;
    }
}
