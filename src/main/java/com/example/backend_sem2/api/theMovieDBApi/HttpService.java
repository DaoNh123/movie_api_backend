package com.example.backend_sem2.api.theMovieDBApi;

import com.example.backend_sem2.model.theMovieDB.MovieWithIdRating;

import java.util.Map;

public interface HttpService {
//    public Request createGetRequest(String getRequestUrl);
//    public String createUrlFromEndpointAndParams (String endpoint, Map<String, String> queryParamMap);
    public <T> T getResponseEntity (String endpoint, Class<T> type, Map<String, String> queryParamMap);

    public MovieWithIdRating getMovieWithRatingUsingTheMovieDBId (Long theMovieDBId);
}
