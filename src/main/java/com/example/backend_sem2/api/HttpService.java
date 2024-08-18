package com.example.backend_sem2.api;

import com.example.backend_sem2.model.theMovieDB.MovieWithIdRating;

import java.util.Map;

public interface HttpService {
    public <T> T getResponseEntity(String baseUrl, String endpoint, Class<T> type, Map<String, String> queryParamMap, String authorizationKey);
}
