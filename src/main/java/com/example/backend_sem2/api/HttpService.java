package com.example.backend_sem2.api;

import com.example.backend_sem2.model.theMovieDB.MovieWithIdRating;

import java.util.Map;

public interface HttpService {
    //    public Request createGetRequest(String getRequestUrl);
//    public String createUrlFromEndpointAndParams (String endpoint, Map<String, String> queryParamMap);
//    <T> T getResponseEntity(String baseUrl, String endpoint, Class<T> type, Map<String, String> queryParamMap);
//
//    MovieWithIdRating getMovieWithRatingUsingTheMovieDBId(Long theMovieDBId);
//
//    Object getSpecificProperties(String baseUrl, String endpoint, Map<String, String> queryParamMap, String propertyName);
//    String getYoutubeIdForMovieTrailerByIMDBId(String theIMDBId);
//    String getImdbIdByTheMovieDBId (Long theMovieDbId);

    public <T> T getResponseEntity(String baseUrl, String endpoint, Class<T> type, Map<String, String> queryParamMap, String authorizationKey);
}
