package com.task.vidhurvoora.neatflickster.Model;

import java.util.ArrayList;

/**
 * Created by vidhurvoora on 7/16/16.
 */

public interface MovieResponseCompletionHandler {
    public void movieResults (Boolean isSuccess, ArrayList<Movie> movies);
}

