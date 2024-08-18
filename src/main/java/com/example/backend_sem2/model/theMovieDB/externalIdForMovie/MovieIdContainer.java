package com.example.backend_sem2.model.theMovieDB.externalIdForMovie;


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
public class MovieIdContainer {
   @JsonProperty("id")
   Long id;
   @JsonProperty("imdb_id")
   String imdbId;
   @JsonProperty("wikidata_id")
   String wikidataId;
   @JsonProperty("facebook_id")
   String facebookId;
   @JsonProperty("instagram_id")
   String instagramId;
   @JsonProperty("twitter_id")
   String twitterId;
}