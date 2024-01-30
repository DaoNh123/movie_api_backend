package com.example.backend_sem2.model.theMovieDB.findMovieByIMDBId;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class MovieResults {
//   @JsonProperty("adult")
//   boolean adult;                         // to "movieLabel"
//   @JsonProperty("backdrop_path")
//   String backdropPath;
   @JsonProperty("id")
   Long id;
//   @JsonProperty("title")
//   String title;
//   @JsonProperty("original_language")
//   String originalLanguage;
//   @JsonProperty("original_title")
//   String originalTitle;
//   @JsonProperty("overview")
//   String overview;
//   @JsonProperty("poster_path")
//   String posterPath;
//   @JsonProperty("media_type")
//   String mediaType;
//   @JsonProperty("genre_ids")
//   List<Integer> genreIds;
//   @JsonProperty("popularity")
//   double popularity;
//   @JsonProperty("release_date")
//   LocalDate releaseDate;
//   @JsonProperty("video")
//   boolean video;
//   @JsonProperty("vote_average")
//   Double voteAverage;
//   @JsonProperty("vote_count")
//   Integer voteCount;
}