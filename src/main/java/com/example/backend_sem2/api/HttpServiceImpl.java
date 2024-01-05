package com.example.backend_sem2.api;

import com.example.backend_sem2.model.theMovieDB.MovieInApi;
import com.example.backend_sem2.model.theMovieDB.MovieWithIdRating;
import com.example.backend_sem2.model.theMovieDB.TrendingMovieResponse;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@Service
public class HttpServiceImpl implements HttpService {
    private OkHttpClient okHttpClient;
    private ObjectMapper objectMapper;


    @Autowired
    public HttpServiceImpl(
            OkHttpClient okHttpClient,
            ObjectMapper objectMapper
    ) {
        this.okHttpClient = okHttpClient;
        this.objectMapper = objectMapper;
    }

    private Request createGetRequest(String getRequestUrl, String authorizationKey) {
        return new Request.Builder()
                .url(getRequestUrl)
                .get()
                .addHeader("accept", "application/json")
                .addHeader("Authorization", authorizationKey)
                .build();
    }

    private String createUrlFromEndpointAndParams(String baseUrl, String endpoint, Map<String, String> queryParamMap) {
        HttpUrl.Builder urlBuilder
                = Objects.requireNonNull(HttpUrl.parse(baseUrl + endpoint)).newBuilder();

        for (Map.Entry<String, String> entry : queryParamMap.entrySet()) {
            urlBuilder.addQueryParameter(entry.getKey(), entry.getValue());
        }

        return urlBuilder.build().toString();
    }

    @SneakyThrows
    public <T> T getResponseEntity(String baseUrl, String endpoint, Class<T> type, Map<String, String> queryParamMap, String authorizationKey) {
        Request getRequest = createGetRequest(createUrlFromEndpointAndParams(baseUrl, endpoint, queryParamMap), authorizationKey);
        String string = null;
        Response response = null;
        try {
            response = okHttpClient.newCall(getRequest).execute();
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            assert response.body() != null;
            string = response.body().string();
        } catch (IOException e) {
            System.out.println("No data received!");
        }
        return objectMapper.readValue(string, type);
    }

//    @SneakyThrows
//    public Object getSpecificProperties(String baseUrl, String endpoint, Map<String, String> queryParamMap, String propertyName, String authorizationKey) {
//        Request getRequest = createGetRequest(createUrlFromEndpointAndParams(baseUrl, endpoint, queryParamMap), authorizationKey);
//
//        Response response = null;
//        try {
//            response = okHttpClient.newCall(getRequest).execute();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//
//        Object extractedProperty = null;
//        try {
//            assert response.body() != null;
//            String jsonString = response.body().string();
//
//            // Use Jackson ObjectMapper to parse JSON into a Map
//            Map<String, Object> resultMap = objectMapper.readValue(jsonString, new TypeReference<>() {});
//
//            // Extract Property from the map
//            extractedProperty = resultMap.get(propertyName);
//        } catch (IOException e) {
//            System.out.println("No data received!");
//        }
//
//        return extractedProperty;
//    }
//
//    /*  Example for "kinocheck" API link: https://api.kinocheck.de/movies?imdb_id=tt4154796 */
//    public String getYoutubeIdForMovieTrailerByIMDBId(String theIMDBId){
//        Map<String, Object> trailerMap = (Map<String, Object>) getSpecificProperties(theKinocheckApiBaseUrl, "/movies",
//                Map.ofEntries(
//                      Map.entry("imdb_id", theIMDBId)
//                ), "trailer");
//        if(trailerMap == null) return null;
//        return prefixYoutubeVideoIdYoutube + trailerMap.get("youtube_video_id");
//    }
//
//    public String getImdbIdByTheMovieDBId (Long theMovieDbId){
//        return (String) getSpecificProperties(theMovieDbBaseUrl, "/movie/" + theMovieDbId,
//new HashMap<>(), "imdb_id");
//    }
//
//    /*  Method to get Trending "MovieInApi" depend on pageId   */
//    public List<MovieInApi> getMovieInApiByPage(int pageNumber) {
//        TrendingMovieResponse response = getResponseEntity(theMovieDbBaseUrl ,  "/trending/movie/day",
//                TrendingMovieResponse.class, Map.ofEntries(
//                        Map.entry("page", Integer.toString(pageNumber))
//                )
//        );
//        return response.getMovieInApiList();
//    }

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
//    public MovieWithIdRating getMovieWithRatingUsingTheMovieDBId(Long theMovieDBId) {
//        String endpoint = "/movie/" + theMovieDBId;
//
//        return getResponseEntity(theMovieDbBaseUrl, endpoint, MovieWithIdRating.class, new HashMap<>());
//    }

}
