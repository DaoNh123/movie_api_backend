package com.example.backend_sem2.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class MovieOverviewDetailIMDB {
    private String id;              // can get ImdbId;
    private Title title;         // include field can convert to "movieName", "posterUrl" and "duration"
    private CertificateList certificates;       // include field can convert to "Movie label"
    private Ratings ratings;               // can get ImdbScore
    private List<String> genres;             // convert to "Category"
    private LocalDate releaseDate;
    private PlotOutline plotOutline;
    private PlotSummary plotSummary;        // include field can convert to "director" and "description"
}
