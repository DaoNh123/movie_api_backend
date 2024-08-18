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
public class SpokenLanguages {
   @JsonProperty("english_name")
   String englishName;
   @JsonProperty("iso_639_1")
   String iso6391;
   @JsonProperty("name")
   String name;
}