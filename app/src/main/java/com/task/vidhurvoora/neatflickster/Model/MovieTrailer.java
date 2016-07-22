package com.task.vidhurvoora.neatflickster.Model;

import org.json.JSONException;
import org.json.JSONObject;
import org.parceler.Parcel;

/**
 * Created by vidhurvoora on 7/20/16.
 */

@Parcel
public class MovieTrailer
{
    public String id ;
    public String key;
    public String name;
    public String site;

    public MovieTrailer(){

    }

    public MovieTrailer (JSONObject jsonObject) throws JSONException {
        this.id = jsonObject.getString("id");
        this.key = jsonObject.getString("key");
        this.name = jsonObject.getString("name");
        this.site = jsonObject.getString("site");
    }
}
