package com.task.vidhurvoora.neatflickster.Model;

import org.json.JSONException;
import org.json.JSONObject;
import org.parceler.Parcel;

@Parcel
public class Movie
{
    public String posterPath;
    public String overview;
    public String title;
    public String backdropPath;
    public MovieCategory category;
    public String releaseDate;
    public int id;

    public Movie() {

    }

    public Movie (JSONObject jsonObject) throws JSONException {
        this.posterPath = jsonObject.getString("poster_path");
        this.overview = jsonObject.getString("overview");
        this.title = jsonObject.getString("title");
        this.backdropPath = jsonObject.getString("backdrop_path");
        int voteAverage = jsonObject.getInt("vote_average");
        this.category = voteAverage > 5 ? MovieCategory.MovieCategoryPopular : MovieCategory.MovieCategoryNotPopular;
        this.id = jsonObject.getInt("id");
        this.releaseDate = jsonObject.getString("release_date");
//
//        String releaseDateStr = jsonObject.getString("release_date");
//        DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
//        try {
//            this.releaseDate = df.parse(releaseDateStr);
//        } catch (ParseException e) {
//            e.printStackTrace();
//        }
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

    public String getLargeBackdropPath() {
        return String.format("https://image.tmdb.org/t/p/w1280/%s",backdropPath);
    }

    public int getId() {
        return id;
    }

    public MovieCategory getCategory() {
        return category;
    }
}
