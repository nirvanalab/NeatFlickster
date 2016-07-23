package com.task.vidhurvoora.neatflickster.Model;

import org.json.JSONException;
import org.json.JSONObject;
import org.parceler.Parcel;

/**
 * Created by vidhurvoora on 7/23/16.
 */

@Parcel
public class MovieReview
{

    public String author;
    public String content;
    public String url;

    public MovieReview() {

    }

    public MovieReview (JSONObject jsonObject) throws JSONException {
        this.author = jsonObject.getString("author");
        this.content = jsonObject.getString("content");
        this.url = jsonObject.getString("url");
    }
 }
