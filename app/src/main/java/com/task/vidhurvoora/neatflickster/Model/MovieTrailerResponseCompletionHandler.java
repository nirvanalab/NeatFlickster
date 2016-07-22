package com.task.vidhurvoora.neatflickster.Model;

import java.util.ArrayList;

public interface MovieTrailerResponseCompletionHandler {
    public void trailerResults (Boolean isSuccess, ArrayList<MovieTrailer> trailers);
}
