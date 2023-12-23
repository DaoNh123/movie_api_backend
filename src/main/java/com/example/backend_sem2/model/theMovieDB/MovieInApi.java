package com.example.backend_sem2.model.theMovieDB;

import com.example.backend_sem2.Enum.MovieLabelEnum;
import com.example.backend_sem2.entity.Category;
import com.example.backend_sem2.entity.Movie;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.function.Function;
import java.util.stream.Collectors;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class MovieInApi {
    @JsonProperty("adult")
    boolean adult;              // ==> convert to MovieLabel

    @JsonProperty("backdrop_path")
    String backdropPath;

    @JsonProperty("id")
    Long id;

    @JsonProperty("title")
    String title;               //  movieName

    @JsonProperty("original_language")
    String originalLanguage;        // language

    @JsonProperty("original_title")
    String originalTitle;

    @JsonProperty("overview")
    String overview;                // description

    @JsonProperty("poster_path")
    String posterPath;              // ==> getConfiguration and create a path for poterUrl

    @JsonProperty("media_type")
    String mediaType;

    @JsonProperty("genre_ids")
    List<Long> genreIds;            // convert to Category

    @JsonProperty("popularity")
    Double popularity;

    @JsonProperty("release_date")
    Date releaseDate;

    @JsonProperty("video")
    boolean video;

    @JsonProperty("vote_average")
    Double voteAverage;                 // convert to IMDB Rating

    @JsonProperty("vote_count")
    Long voteCount;

    public Movie toMovieEntity(List<Category> categoryList, ConfigurationTheMovieDB configurationTheMovieDB){
        ImageSizes imageSizes = configurationTheMovieDB.getImageSizes();
        String posterBaseUrl = imageSizes.baseUrl;
        String size = imageSizes.getPosterSizes().get(imageSizes.getPosterSizes().size() - 1);

        MovieLabelEnum movieLabelEnum = null;
        Random random = new Random();
        if(this.adult == true) {
            List<MovieLabelEnum> movieLabelEnumsForAdult = List.of(
                    MovieLabelEnum.C18, MovieLabelEnum.C16
            );
            movieLabelEnum = movieLabelEnumsForAdult.get(random.nextInt(2));
        }else {
            List<MovieLabelEnum> movieLabelEnumsForAdult = List.of(
                    MovieLabelEnum.C12, MovieLabelEnum.P
            );
            movieLabelEnum = movieLabelEnumsForAdult.get(random.nextInt(2));
        }

        Map<Long, Category> categoryMap = categoryList.stream()
                .collect(Collectors.toMap(Category::getGenreId, Function.identity()));
        List<Category> categoryListOfMovie = this.genreIds.stream()
                .map(categoryMap::get).toList();

        return Movie.builder()
                .movieName(this.title)
                .imdbId(Long.toString(this.id))
                .imdbRatings(this.voteAverage)
                .posterUrl(String.join("",posterBaseUrl, size, this.posterPath))
                .description(this.overview)
                .language(this.originalLanguage)
                .categoryList(categoryListOfMovie)
                .movieLabel(movieLabelEnum)
                .build();
    }
}
