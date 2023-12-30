package com.example.backend_sem2.mapper;

import com.example.backend_sem2.entity.Category;
import com.example.backend_sem2.entity.Movie;
import com.example.backend_sem2.enums.MovieLabelEnum;
import com.example.backend_sem2.model.theMovieDB.ConfigurationTheMovieDB;
import com.example.backend_sem2.model.theMovieDB.ImageSizes;
import com.example.backend_sem2.model.theMovieDB.MovieInApi;
import org.mapstruct.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.function.Function;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public abstract class MovieMapper2 {

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "id", ignore = true)
    @Mapping(source = "movieInApi.title", target = "movieName")
    @Mapping(source = "movieInApi.id", target = "theMovieDBId")
    @Mapping(source = "movieInApi.voteAverage", target = "imdbRatings")
//    @Mapping(source = "movieInApi.posterPath", target = "posterUrl", qualifiedByName = "posterPathToPosterUrlInS3")      //   Need creating @Named Mapping method, convert to S3 Amazon
    @Mapping(source = "movieInApi.overview", target = "description")
    @Mapping(source = "movieInApi.originalLanguage", target = "language")
//    @Mapping(source = "movieInApi.genreIds", target = "categoryList", qualifiedByName = "genreToCategory")        //    Need creating @Named Mapping method
//    @Mapping(source = "movieInApi.adult", target = "movieLabel", qualifiedByName = "booleanAdultToMovieLabel")        // Need creating @Named Mapping method
    public abstract Movie toEntity(MovieInApi movieInApi);

//    @Named("posterPathToPosterUrlInS3")
//    protected String posterPathToPosterUrlInS3 (String posterPath, @Context ConfigurationTheMovieDB configurationTheMovieDB){
//        ImageSizes imageSizes = configurationTheMovieDB.getImageSizes();
//        String posterBaseUrl = imageSizes.getBaseUrl();
//        String size = imageSizes.getPosterSizes().get(imageSizes.getPosterSizes().size() - 1);
//
//        String posterUrlInTheMovieDB = String.join("",posterBaseUrl, size, posterPath);
//
//        return amazonService.uploadImageInUrlToS3("thMovieDb/images", posterUrlInTheMovieDB);
//    }

    @Named("genreToCategory")
    protected List<Category> genreToCategory(List <Long> genreIds, @Context List<Category> existingCategoryList)
    {
        if (existingCategoryList == null) existingCategoryList = new ArrayList<>();
        Map<Long, Category> categoryMap = existingCategoryList.stream().collect(Collectors.toMap(Category::getGenreId, Function.identity()));
        return genreIds.stream().map(categoryMap::get).toList();
    }

    @Named("booleanAdultToMovieLabel")
    protected MovieLabelEnum booleanAdultToMovieLabelEnum (boolean adult){
        MovieLabelEnum movieLabelEnum = null;
        Random random = new Random();
        if(adult) {
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
        return movieLabelEnum;
    }
}
