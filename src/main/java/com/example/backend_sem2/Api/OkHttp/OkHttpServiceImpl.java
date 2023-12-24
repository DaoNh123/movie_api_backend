package com.example.backend_sem2.Api.OkHttp;

import com.example.backend_sem2.entity.Movie;
import com.example.backend_sem2.mapper.MovieMapper;
import com.example.backend_sem2.model.theMovieDB.*;
import com.example.backend_sem2.repository.MovieRepo;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@Service
@AllArgsConstructor
public class OkHttpServiceImpl implements OkHttpService{
    private OkHttpClient okHttpClient;
    private ObjectMapper objectMapper;
    private MovieRepo movieRepo;
    private MovieMapper movieMapper;
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

        String string = null;
        try {
            assert response.body() != null;
            string = response.body().string();
        } catch (IOException e) {
            System.out.println("No data received!");
        }
        return objectMapper.readValue(string, type);
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

    /*  Get info of a Movie from "theMovieDB" by Id, using in "@Schedule" to update rating for movie */
    public MovieWithIdRating getMovieWithRatingUsingTheMovieDBId (Long theMovieDBId){
        String endpoint = "/movie/" + theMovieDBId;

        return getResponseEntity(endpoint, MovieWithIdRating.class, new HashMap<>());
    }

//    @Scheduled(cron = "0 0/5 0 ? * * *")
////    @Scheduled(cron =  "0/10 * * * * *")
//    @Transactional
//    public void updateRatingAndMovieIMDBId(){
//        System.out.println("---Start updating rating---");
//
//        List<Movie> movieList = movieRepo.findAll();
//
//        movieList.stream().forEach(movie -> {
//            MovieWithIdRating movieWithIdRating = getMovieWithRatingUsingTheMovieDBId(movie.getTheMovieDBId());
//            movieMapper.updateMovieRating(movieWithIdRating, movie);
//        });
//
//        movieRepo.saveAllAndFlush(movieList);
//
//        System.out.println("---End updating rating---");
//    }
}
