package com.example.backend_sem2.model.theMovieDB.findMovieByTheMovieDBId;

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
public class BelongsToCollection {
   @JsonProperty("id")
   int id;
   @JsonProperty("name")
   String name;
   @JsonProperty("poster_path")
   String posterPath;
   @JsonProperty("backdrop_path")
   String backdropPath;
}