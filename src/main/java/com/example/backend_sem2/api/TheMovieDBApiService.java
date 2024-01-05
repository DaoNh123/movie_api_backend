package com.example.backend_sem2.api;

import com.example.backend_sem2.model.theMovieDB.ConfigurationTheMovieDB;
import com.example.backend_sem2.model.theMovieDB.GenreResponse;
import com.example.backend_sem2.model.theMovieDB.MovieInApi;
import com.example.backend_sem2.model.theMovieDB.MovieWithIdRating;

import java.util.List;

public interface TheMovieDBApiService {

    public String getImdbIdByTheMovieDBId(Long theMovieDbId);

    public GenreResponse getGenreOfMovieByTheMovieDB();

    public ConfigurationTheMovieDB getConfigurationInTheMovieDB();

    public MovieWithIdRating getMovieWithRatingUsingTheMovieDBId(Long theMovieDBId);
    public List<MovieInApi> getTrendingMovieInTheMovieDBApiWithNPage (Long numberOfPage);
}
