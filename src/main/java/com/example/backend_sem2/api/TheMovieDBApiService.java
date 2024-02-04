package com.example.backend_sem2.api;

import com.example.backend_sem2.model.theMovieDB.ConfigurationTheMovieDB;
import com.example.backend_sem2.model.theMovieDB.GenreResponse;
import com.example.backend_sem2.model.theMovieDB.MovieInApi;
import com.example.backend_sem2.model.theMovieDB.MovieWithIdRating;
import com.example.backend_sem2.model.theMovieDB.findMovieByTheMovieDBId.MovieByTheMovieDBId;

import java.util.List;

public interface TheMovieDBApiService {
    Long getTheMovieDBIdByImdbId(String imdbId);

    String getImdbIdByTheMovieDBId(Long theMovieDbId);
    MovieByTheMovieDBId findMovieByTheMovieDBId (Long theMovieDbId);

    GenreResponse getGenreOfMovieByTheMovieDB();

    ConfigurationTheMovieDB getConfigurationInTheMovieDB();

    MovieWithIdRating getMovieWithRatingUsingTheMovieDBId(Long theMovieDBId);
    List<MovieInApi> getTrendingMovieInTheMovieDBApiWithNPage (Long numberOfPage);
}
