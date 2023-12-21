package com.example.backend_sem2.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.awt.*;


@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class Title {
    @JsonProperty("@type")
    private String type;
    private String id;
    private ImageInApi image;
    private Long runningTimeInMinutes;
    private String title;
    private String titleType;
    private String year;        // year of publication
}
