package com.task.vidhurvoora.neatflickster.Model;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by vidhurvoora on 7/16/16.
 */
public class Movie
{
    private String posterPath;
    private String overview;
    private String title;

    public Movie (JSONObject jsonObject) throws JSONException {
        this.posterPath = jsonObject.getString("poster_path");
        this.overview = jsonObject.getString("overview");
        this.title = jsonObject.getString("title");
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



}
