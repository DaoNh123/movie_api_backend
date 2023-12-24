package com.example.backend_sem2.model.theMovieDB;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class MovieWithIdRating {
    @JsonProperty("id")
    Long id;                // convert to "theMovieDBId" in Entity "Movie
    @JsonProperty("imdb_id")
    String imdbId;
    @JsonProperty("vote_average")
    Double voteAverage;     // convert to "imdbRatings"
}
