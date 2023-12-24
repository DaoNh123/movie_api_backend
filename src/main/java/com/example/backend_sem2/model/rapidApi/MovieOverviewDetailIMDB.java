package com.example.backend_sem2.model.rapidApi;

import com.example.backend_sem2.Enum.MovieLabelEnum;
import com.example.backend_sem2.Utility.ApiUtility;
import com.example.backend_sem2.entity.Category;
import com.example.backend_sem2.entity.Movie;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

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

    public Movie toMovieEntity() {

//        return Movie.builder()
//                .imdbId(this.id)
//                .movieName(this.title.getTitle())
//                .movieLabel(this.certificates.certificateListForUS.get(0).getMovieLabelEnum())                  // convert from "certificate"
//                .posterUrl(this.title.getImage().getUrl())
//                .duration(title.getRunningTimeInMinutes() * 60)
//                .imdbRatings(ratings.getRatings())
////                .categoryList()               // convert from "genres"
//                .director(this.plotSummary.getAuthor())
//                .description(this.plotSummary.getText())
//                .build();
        return Movie.builder()
                .imdbId(ApiUtility.getIdFromEndpointString(this.id))
                .movieName(this.title != null ? this.title.getTitle() : null)
                // convert from "certificate", and it will be "C16" if null
                .movieLabel(this.certificates != null && this.certificates.certificateListForUS != null
                        && this.certificates.certificateListForUS.get(0) != null
                        ? this.certificates.certificateListForUS.get(0).getMovieLabelEnum() : MovieLabelEnum.C16)
                .posterUrl(this.title != null && this.title.getImage() != null ? this.title.getImage().getUrl() : null)
                .duration(this.title != null && this.title.getRunningTimeInMinutes() != null
                        ? this.title.getRunningTimeInMinutes() * 60 : null)
                .imdbRatings(ratings.getRatings())
//                .categoryList()               // convert from "genres"
                .director(this.plotSummary != null ? this.plotSummary.getAuthor() : null)
                .description(this.plotSummary != null ? this.plotSummary.getText() : null)
                .build();
    }

    public List<Category> getCategoryOfMovieEntity(Map<String, Category> savedCategoryMap) {
        List<Category> categoryList = this.genres.stream()
                .map(genre -> {
                    if (savedCategoryMap.containsKey(genre)) return savedCategoryMap.get("genre");
                    Category newCategory = Category.builder()
                            .categoryName(genre).build();
                    savedCategoryMap.put(genre, newCategory);
                    return newCategory;
                }).toList();
        return categoryList;
    }
}
