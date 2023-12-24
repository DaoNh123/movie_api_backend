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
public class ImageSizes {
    @JsonProperty("base_url")
    String baseUrl;

    @JsonProperty("secure_base_url")
    String secureBaseUrl;

    @JsonProperty("backdrop_sizes")
    List<String> backdropSizes;

    @JsonProperty("logo_sizes")
    List<String> logoSizes;

    @JsonProperty("poster_sizes")
    List<String> posterSizes;

    @JsonProperty("profile_sizes")
    List<String> profileSizes;

    @JsonProperty("still_sizes")
    List<String> stillSizes;
}
