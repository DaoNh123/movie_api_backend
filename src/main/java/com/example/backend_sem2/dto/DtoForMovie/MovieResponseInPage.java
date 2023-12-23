package com.example.backend_sem2.dto.DtoForMovie;

import com.example.backend_sem2.entity.Movie;
import com.example.backend_sem2.Enum.MovieLabelEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.ZonedDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MovieResponseInPage {
    private Long id;
    private String movieName;
    private String posterUrl;
    private String director;
    private String description;
    private Long duration;          // calculate in seconds
    private String language;
    private ZonedDateTime openingTime;      // The time which customer can book a ticket
    private ZonedDateTime closingTime;      // The time which movie is no longer selling ticket
    private String iframe;
    private List<String> categoryNameList;      // need custom expression
    private MovieLabelEnum movieLabel;

    public MovieResponseInPage(Movie movie) {
        this.categoryNameList = movie.getCategoryList().stream()
                .map(com.example.backend_sem2.entity.Category::getCategoryName).toList();
    }
}