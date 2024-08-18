package com.example.backend_sem2.model.theMovieDB.findMovieByTheMovieDBId;

import com.example.backend_sem2.model.theMovieDB.Genre;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class MovieByTheMovieDBId {

   @JsonProperty("adult")
   boolean adult;

   @JsonProperty("backdrop_path")
   String backdropPath;

   @JsonProperty("belongs_to_collection")
   BelongsToCollection belongsToCollection;

   @JsonProperty("budget")
   int budget;

   @JsonProperty("genres")
   List<Genre> genres;

   @JsonProperty("homepage")
   String homepage;

   @JsonProperty("id")
   int id;

   @JsonProperty("imdb_id")
   String imdbId;

   @JsonProperty("original_language")
   String originalLanguage;

   @JsonProperty("original_title")
   String originalTitle;

   @JsonProperty("overview")
   String overview;

   @JsonProperty("popularity")
   double popularity;

   @JsonProperty("poster_path")
   String posterPath;

   @JsonProperty("release_date")
   LocalDate releaseDate;

   @JsonProperty("revenue")
   int revenue;

   @JsonProperty("runtime")
   Long runtime;

   @JsonProperty("spoken_languages")
   List<SpokenLanguages> spokenLanguages;

   @JsonProperty("status")
   String status;

   @JsonProperty("tagline")
   String tagline;

   @JsonProperty("title")
   String title;

   @JsonProperty("video")
   boolean video;

   @JsonProperty("vote_average")
   double voteAverage;

   @JsonProperty("vote_count")
   int voteCount;

   public ZonedDateTime getOpeningTime(){
      ZoneId zoneId = ZoneId.of("UTC+7");
      return this.releaseDate.atStartOfDay().atZone(zoneId);
   }
}