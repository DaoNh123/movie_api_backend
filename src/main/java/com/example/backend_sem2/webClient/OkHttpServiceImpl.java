package com.example.backend_sem2.webClient;

import com.example.backend_sem2.model.theMovieDB.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@Service
@AllArgsConstructor
public class OkHttpServiceImpl implements OkHttpService{
    private OkHttpClient okHttpClient;
    private ObjectMapper objectMapper;
    private final String theMovieDbBaseUrl = "https://api.themoviedb.org/3";

    public Request createGetRequest(String getRequestUrl){
        return new Request.Builder()
                .url(getRequestUrl)
                .get()
                .addHeader("accept", "application/json")
                .addHeader("Authorization", "Bearer eyJhbGciOiJIUzI1NiJ9.eyJhdWQiOiI0NWYyNGI0MWQ1NTgwMzY1NzkwY2YxZWU5M2NhNTYwZiIsInN1YiI6IjY1ODQzY2E0MDgzNTQ3NDU1NTNlYmIzZSIsInNjb3BlcyI6WyJhcGlfcmVhZCJdLCJ2ZXJzaW9uIjoxfQ.YD3DkJdnGbLfa0-R3vQiKmZz8PHLE6SjSzdubv3B_rc")
                .build();
    }

    public String createUrlFromEndpointAndParams (String endpoint, Map<String, String> queryParamMap){
        HttpUrl.Builder urlBuilder
                = Objects.requireNonNull(HttpUrl.parse(theMovieDbBaseUrl + endpoint)).newBuilder();

        for (Map.Entry<String, String> entry : queryParamMap.entrySet()) {
            urlBuilder.addQueryParameter(entry.getKey(), entry.getValue());
        }

        return urlBuilder.build().toString();
    }

    @SneakyThrows
    public <T> T getResponseEntity (String endpoint, Class<T> type, Map<String, String> queryParamMap){
        Request getRequest = createGetRequest(createUrlFromEndpointAndParams(endpoint, queryParamMap));

        Response response = okHttpClient.newCall(getRequest).execute();

        String string = response.body().string();
        T entityResponse = objectMapper.readValue(string, type);
        return entityResponse;
    }

    /*  Method to get Trending "MovieInApi" depend on pageId   */
    public List<MovieInApi> getMovieInApiByPage (int pageNumber){
        TrendingMovieResponse response = getResponseEntity("/trending/movie/day",
                TrendingMovieResponse.class, Map.ofEntries(
                        Map.entry("page", Integer.toString(pageNumber))
                )
        );
        return response.getMovieInApiList();
    }

    /*  Method to get "Configuration" in "theMovieDB" depend on pageId   */
    public ConfigurationTheMovieDB getConfigurationTheMovieDB(){
        return getResponseEntity("/configuration",
                ConfigurationTheMovieDB.class, new HashMap<>());
    }

    /*  Method get "Genre" in "theMovieDB"  */
    public List<Genre> getGenreFromTheMovieDB(){
        return getResponseEntity("/genre/movie/list",
                GenreResponse.class, new HashMap<>()).getGenres();
    }
}
