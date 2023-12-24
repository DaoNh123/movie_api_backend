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
public class ConfigurationTheMovieDB {
    @JsonProperty("images")
    ImageSizes imageSizes;

    @JsonProperty("change_keys")
    List<String> changeKeys;

    @Override
    public String toString() {
        return "ConfigurationTheMovieDB{" +
                "imageSizes=" + imageSizes.toString() +
                ", changeKeys=" + changeKeys +
                '}';
    }
}
