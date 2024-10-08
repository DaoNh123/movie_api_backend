package com.example.backend_sem2.mapper;

import com.example.backend_sem2.api.HttpService;
import com.example.backend_sem2.api.KinoCheckApiService;
import com.example.backend_sem2.api.TheMovieDBApiService;
import com.example.backend_sem2.dto.CreateMovieRequest;
import com.example.backend_sem2.dto.dtoForMovie.MovieResponseInPage;
import com.example.backend_sem2.dto.dtoForMovie.MovieResponseOverview;
import com.example.backend_sem2.dto.dtoForMovie.MovieResponseWithComment;
import com.example.backend_sem2.dto.orderResponseInfo_InDetail.MovieInOrderRes;
import com.example.backend_sem2.entity.Category;
import com.example.backend_sem2.entity.Movie;
import com.example.backend_sem2.enums.MovieLabelEnum;
import com.example.backend_sem2.model.theMovieDB.*;
import com.example.backend_sem2.model.theMovieDB.findMovieByTheMovieDBId.MovieByTheMovieDBId;
import com.example.backend_sem2.repository.CategoryRepo;
import com.example.backend_sem2.service.interfaceService.AmazonService;
import com.example.backend_sem2.utils.EntityUtility;
import org.mapstruct.*;
import org.springframework.beans.factory.annotation.Autowired;
import reactor.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.function.Function;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring", uses = {ReferenceMapper.class, CommentMapper.class})

public abstract class MovieMapper {
    @Autowired
    EntityUtility entityUtility;
    @Autowired
    CategoryRepo categoryRepo;
    @Autowired
    AmazonService amazonService;
    @Autowired
    HttpService httpService;
    @Autowired
    TheMovieDBApiService theMovieDBApiService;
    @Autowired
    KinoCheckApiService kinoCheckApiService;

    abstract Movie toEntity(Long id);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(source = "posterUrl", target = "posterUrl", qualifiedByName = "preSignedPosterUrl")
    public abstract MovieInOrderRes toMovieInOrderRes(Movie movie);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "categoryNameList", expression =
            "java(movie.getCategoryList() == null ? null : movie.getCategoryList().stream().map(com.example.backend_sem2.entity.Category::getCategoryName).toList())")
    @Mapping(target = "posterUrl", qualifiedByName = "preSignedPosterUrl")
    public abstract MovieResponseInPage toMovieResponseInPage(Movie movie);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "categoryNameList", expression =
            "java(movie.getCategoryList().stream().map(com.example.backend_sem2.entity.Category::getCategoryName).toList())")
    @Mapping(source = "posterUrl", target = "posterUrl", qualifiedByName = "preSignedPosterUrl")
    @Mapping(target = "trailerUrl", expression = "java(movie.getYoutubeEmbedLink())")
    public abstract MovieResponseWithComment toMovieResponseWithComment(Movie movie);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "categoryList", source = "categoryList", qualifiedByName = "categoryNameListToCategoryList")
    @Mapping(target = "youtubeId", expression = "java(createMovieRequest.getYoutubeId())")
    public abstract Movie toEntity(CreateMovieRequest createMovieRequest);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "id", ignore = true)
    @Mapping(source = "movieInApi.title", target = "movieName")
    @Mapping(source = "movieInApi.id", target = "theMovieDBId")
    @Mapping(source = "movieInApi.voteAverage", target = "imdbRatings")
    @Mapping(source = "movieInApi.posterPath", target = "posterUrl", qualifiedByName = "posterPathToPosterUrlInS3")      //   Need creating @Named Mapping method, convert to S3 Amazon
    @Mapping(source = "movieInApi.overview", target = "description")
    @Mapping(source = "movieInApi.originalLanguage", target = "language")
    @Mapping(source = "movieInApi.genreIds", target = "categoryList", qualifiedByName = "genreToCategory")        //    Need creating @Named Mapping method
    @Mapping(source = "movieInApi.adult", target = "movieLabel", qualifiedByName = "booleanAdultToMovieLabel")        // Need creating @Named Mapping method
    @Mapping(source = "movieInApi.id", target = "imdbId", qualifiedByName = "theMovieDBIdToIMDBId")                 // Need creating @Named Mapping method

    public abstract Movie toEntity(MovieInApi movieInApi, @Context ConfigurationTheMovieDB configurationTheMovieDB,
                                   @Context List<Category> existingCategoryList);
    @Named("theMovieDBIdToIMDBId")
    protected String theMovieDBIdToIMDBId (Long theMovieDBId){
        return theMovieDBApiService.getImdbIdByTheMovieDBId(theMovieDBId);
    }
    @Named("posterPathToPosterUrlInS3")
    protected String posterPathToPosterUrlInS3 (String posterPath, @Context ConfigurationTheMovieDB configurationTheMovieDB){
        ImageSizes imageSizes = configurationTheMovieDB.getImageSizes();
        String posterBaseUrl = imageSizes.getBaseUrl();
        String size = imageSizes.getPosterSizes().get(imageSizes.getPosterSizes().size() - 1);

        String posterUrlInTheMovieDB = String.join("",posterBaseUrl, size, posterPath);

        return amazonService.uploadImageInUrlToS3("images", posterUrlInTheMovieDB);
    }

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

    @Mapping(target = "id", ignore = true)
    @Mapping(source = "voteAverage", target = "imdbRatings")
    @BeanMapping(nullValuePropertyMappingStrategy =  NullValuePropertyMappingStrategy.IGNORE)
    public abstract Movie updateMovieRating (MovieWithIdRating movieWithIdRating, @MappingTarget Movie movie);

    @Named("categoryNameListToCategoryList")
    public List<Category> categoryNameListToCategoryList(List<String> categoryNameList){
        if(CollectionUtils.isEmpty(categoryNameList)) return null;
        return  categoryNameList.stream()
                .map(categoryName -> {
                    return categoryRepo.findByCategoryName(categoryName).orElse(
                            Category.builder().categoryName(categoryName).build()
                    );
                }).toList();
    }

    @Named("preSignedPosterUrl")
    public String preSignedPosterUrl(String posterUrl){
        return amazonService.createPreSignedPosterUrl(posterUrl);
    }

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(source = "title", target = "movieName")
    @Mapping(source = "imdbId", target = "imdbId")
    @Mapping(source = "posterPath", target = "posterUrlInMovieDB", qualifiedByName = "posterPathInTheMovieDBToPosterUrl")
    @Mapping(source = "overview", target = "description")
    @Mapping(source = "runtime", target = "duration")
    @Mapping(source = "originalLanguage", target = "language")
    @Mapping(target = "openingTime", expression = "java(movieByTheMovieDBId.getOpeningTime())")
    @Mapping(source = "imdbId", target = "youtubeLink", qualifiedByName = "imdbIdToYouTubeLink")
    @Mapping(source = "genres", target = "categoryList", qualifiedByName = "genresToCategoryList")
    @Mapping(source = "adult", target = "movieLabel", qualifiedByName = "adultBooleanToMovieLabel")
    public abstract CreateMovieRequest toCreateMovieRequest (MovieByTheMovieDBId movieByTheMovieDBId);

    @Named("posterPathInTheMovieDBToPosterUrl")
    public String posterPathInTheMovieDBToPosterUrl(String posterPath){
        ConfigurationTheMovieDB configurationTheMovieDB = theMovieDBApiService.getConfigurationInTheMovieDB();

        String posterBaseUrl = configurationTheMovieDB.getImageSizes().getBaseUrl();
        List<String> sizeList = configurationTheMovieDB.getImageSizes().getPosterSizes();
        String size = sizeList.get(sizeList.size() - 1);
        return String.join("",posterBaseUrl, size, posterPath);
    }

    @Named("imdbIdToYouTubeLink")
    public String imdbIdToYouTubeLink(String imdbId){
        String youtubeId = kinoCheckApiService.getYoutubeIdForMovieTrailerByIMDBId(imdbId);
        return youtubeId == null ? null : "https://www.youtube.com/watch?v=" + youtubeId;
    }

    @Named("genresToCategoryList")
    public List<String> genresToCategoryList (List<Genre> genres){
        return genres.stream().map(Genre::getName).toList();
    }
    @Named("adultBooleanToMovieLabel")
    public MovieLabelEnum movieLabelEnum(boolean adult){
        Random random = new Random();
        List<MovieLabelEnum> movieLabelEnums = List.of(
                MovieLabelEnum.P,
                MovieLabelEnum.C12
        );
        if(adult){
            return MovieLabelEnum.C18;
        }
        return movieLabelEnums.get(random.nextInt(2));
    }

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    public abstract MovieResponseOverview toMovieResponseOverview (Movie movie);
}
