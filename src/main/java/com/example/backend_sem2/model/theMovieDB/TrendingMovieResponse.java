package com.example.backend_sem2.model.theMovieDB;

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
public class TrendingMovieResponse {
    @JsonProperty("page")
    Long page;

    @JsonProperty("results")
    List<MovieInApi> movieInApiList;

    @JsonProperty("total_pages")
    Long totalPages;

    @JsonProperty("total_results")
    Long totalResults;
}
