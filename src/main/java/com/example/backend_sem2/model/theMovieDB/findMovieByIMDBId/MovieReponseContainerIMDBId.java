package com.example.backend_sem2.model.theMovieDB.findMovieByIMDBId;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class MovieReponseContainerIMDBId {

   @JsonProperty("movie_results")
   List<MovieResults> movieResults;

//   @JsonProperty("person_results")
//   List<String> personResults;
//
//   @JsonProperty("tv_results")
//   List<String> tvResults;
//
//   @JsonProperty("tv_episode_results")
//   List<String> tvEpisodeResults;
//
//   @JsonProperty("tv_season_results")
//   List<String> tvSeasonResults;
}