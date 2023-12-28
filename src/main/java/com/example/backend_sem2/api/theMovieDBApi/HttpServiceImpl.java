package com.example.backend_sem2.api.theMovieDBApi;

import com.example.backend_sem2.mapper.MovieMapper;
import com.example.backend_sem2.model.theMovieDB.MovieInApi;
import com.example.backend_sem2.model.theMovieDB.MovieWithIdRating;
import com.example.backend_sem2.model.theMovieDB.TrendingMovieResponse;
import com.example.backend_sem2.repository.MovieRepo;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@AllArgsConstructor
public class HttpServiceImpl implements HttpService {
    private OkHttpClient okHttpClient;
    private ObjectMapper objectMapper;
    private MovieRepo movieRepo;
    private MovieMapper movieMapper;

    @Value("${the_movieDB_api.base_url}")
    private String theMovieDbBaseUrl;
    @Value("${the_movieDB_api.authorization_key}")
    private String authorizationKeyInTheMovieDB;
    Environment env;

    @Autowired
    public HttpServiceImpl(
            OkHttpClient okHttpClient,
            ObjectMapper objectMapper,
            MovieRepo movieRepo,
            MovieMapper movieMapper
    ) {
        this.okHttpClient = okHttpClient;
        this.objectMapper = objectMapper;
        this.movieRepo = movieRepo;
        this.movieMapper = movieMapper;
    }

    private Request createGetRequest(String getRequestUrl) {
        return new Request.Builder()
                .url(getRequestUrl)
                .get()
                .addHeader("accept", "application/json")
                .addHeader("Authorization", authorizationKeyInTheMovieDB)
                .build();
    }

    private String createUrlFromEndpointAndParams(String endpoint, Map<String, String> queryParamMap) {
        HttpUrl.Builder urlBuilder
//                = Objects.requireNonNull(HttpUrl.parse(theMovieDbBaseUrl + endpoint)).newBuilder();
//                = HttpUrl.parse(env.getProperty("the_movieDB_api.base_url") + endpoint).newBuilder();
                = HttpUrl.parse(theMovieDbBaseUrl + endpoint).newBuilder();

        for (Map.Entry<String, String> entry : queryParamMap.entrySet()) {
            urlBuilder.addQueryParameter(entry.getKey(), entry.getValue());
        }

        return urlBuilder.build().toString();
    }

    @SneakyThrows
    public <T> T getResponseEntity(String endpoint, Class<T> type, Map<String, String> queryParamMap) {
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
    public List<MovieInApi> getMovieInApiByPage(int pageNumber) {
        TrendingMovieResponse response = getResponseEntity("/trending/movie/day",
                TrendingMovieResponse.class, Map.ofEntries(
                        Map.entry("page", Integer.toString(pageNumber))
                )
        );
        return response.getMovieInApiList();
    }

    /*  Method to get "Configuration" in "theMovieDB" depend on pageId   */
//    public ConfigurationTheMovieDB getConfigurationTheMovieDB(){
//        return getResponseEntity("/configuration",
//                ConfigurationTheMovieDB.class, new HashMap<>());
//    }

    /*  Method get "Genre" in "theMovieDB"  */
//    public List<Genre> getGenreFromTheMovieDB(){
//        return getResponseEntity("/genre/movie/list",
//                GenreResponse.class, new HashMap<>()).getGenres();
//    }

    /*  Get info of a Movie from "theMovieDB" by Id, using in "@Schedule" to update rating for movie */
    public MovieWithIdRating getMovieWithRatingUsingTheMovieDBId(Long theMovieDBId) {
        String endpoint = "/movie/" + theMovieDBId;

        return getResponseEntity(endpoint, MovieWithIdRating.class, new HashMap<>());
    }

}
