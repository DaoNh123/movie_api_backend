package com.example.backend_sem2.webClient;

import okhttp3.Request;

import java.util.Map;

public interface OkHttpService {
    public Request createGetRequest(String getRequestUrl);
    public String createUrlFromEndpointAndParams (String endpoint, Map<String, String> queryParamMap);
    public <T> T getResponseEntity (String endpoint, Class<T> type, Map<String, String> queryParamMap);
}
