package com.example.backend_sem2.api;

import com.example.backend_sem2.model.theMovieDB.*;
import com.example.backend_sem2.model.theMovieDB.externalIdForMovie.MovieIdContainer;
import com.example.backend_sem2.model.theMovieDB.findMovieByIMDBId.MovieReponseContainerIMDBId;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class TheMovieDBApiServiceImpl implements TheMovieDBApiService {
    @Value("${the_movieDB_api.base_url}")
    private String theMovieDbBaseUrl;
    @Value("${the_movieDB_api.authorization_key}")
    private String authorizationKeyInTheMovieDB;

    private HttpService httpService;

    public TheMovieDBApiServiceImpl(HttpService httpService) {
        this.httpService = httpService;
    }

    public Long getTheMovieDBIdByImdbId(String imdbId) {
//        return httpService.getResponseEntity(theMovieDbBaseUrl, "/find/" + imdbId + "?external_source=imdb_id", MovieReponseContainerIMDBId.class,
//                new HashMap<>(), authorizationKeyInTheMovieDB).getMovieResults().get(0).getId();

        return httpService.getResponseEntity(theMovieDbBaseUrl, "/movie/" + imdbId + "/external_ids",
                MovieIdContainer.class,
                new HashMap<>(), authorizationKeyInTheMovieDB).getId();
    }

    /*  OK  */
    public String getImdbIdByTheMovieDBId(Long theMovieDbId) {
//        return httpService.getResponseEntity(theMovieDbBaseUrl, "/movie/" + theMovieDbId, ImdbIdObject.class,
//                new HashMap<>(), authorizationKeyInTheMovieDB).getImdbId();
    /*  "https://api.themoviedb.org/3/movie/{movie_id}/external_ids"    */
        return httpService.getResponseEntity(theMovieDbBaseUrl, "/movie/" + theMovieDbId + "/external_ids",
                MovieIdContainer.class,
                new HashMap<>(), authorizationKeyInTheMovieDB).getImdbId();
    }

    public GenreResponse getGenreOfMovieByTheMovieDB() {
        return httpService.getResponseEntity(theMovieDbBaseUrl, "/genre/movie/list",
                GenreResponse.class, new HashMap<>(), authorizationKeyInTheMovieDB);
    }

    public ConfigurationTheMovieDB getConfigurationInTheMovieDB() {
        return httpService.getResponseEntity(theMovieDbBaseUrl, "/configuration",
                ConfigurationTheMovieDB.class, new HashMap<>(), authorizationKeyInTheMovieDB);
    }

    public MovieWithIdRating getMovieWithRatingUsingTheMovieDBId(Long theMovieDBId) {
        String endpoint = "/movie/" + theMovieDBId;

        return httpService.getResponseEntity(theMovieDbBaseUrl, endpoint, MovieWithIdRating.class, new HashMap<>(), authorizationKeyInTheMovieDB);
    }

    public List<MovieInApi> getTrendingMovieInTheMovieDBApiWithNPage (Long numberOfPage)
    {
        List<MovieInApi> movieInApiList = new ArrayList<>();

        for (int i = 0; i < numberOfPage; i++) {
            TrendingMovieResponse response = httpService.getResponseEntity(theMovieDbBaseUrl,"/trending/movie/day",
                    TrendingMovieResponse.class, Map.ofEntries(
                            Map.entry("page", Integer.toString(i + 1))
                    ), authorizationKeyInTheMovieDB
            );
            movieInApiList.addAll(response.getMovieInApiList());
        }

        for (int i = 0; i < movieInApiList.size(); i++) {
            MovieInApi movieInApi = movieInApiList.get(i);
            System.out.println((i + 1) + " " + movieInApi.getId() + " " + movieInApi.getOriginalTitle());
        }
        return movieInApiList;
    }
}
