package com.example.backend_sem2.mapper;

import com.example.backend_sem2.dto.CategoryDto;
import com.example.backend_sem2.dto.CreateMovieRequest;
import com.example.backend_sem2.dto.DtoForMovie.MovieResponseInPage;
import com.example.backend_sem2.dto.DtoForMovie.MovieResponseWithComment;
import com.example.backend_sem2.dto.OrderResponseInfo.MovieInOrderRes;
import com.example.backend_sem2.entity.Category;
import com.example.backend_sem2.entity.Movie;
import com.example.backend_sem2.model.rapidApi.MovieOverviewDetailIMDB;
import com.example.backend_sem2.model.theMovieDB.MovieWithIdRating;
import org.mapstruct.*;

@Mapper(componentModel = "spring", uses = {ReferenceMapper.class, CommentMapper.class})
public abstract class MovieMapper {

    abstract Movie toEntity(Long id);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    abstract MovieInOrderRes toMovieInOrderRes(Movie movie);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "categoryNameList", expression =
            "java(movie.getCategoryList().stream().map(com.example.backend_sem2.entity.Category::getCategoryName).toList())")
    abstract MovieResponseInPage toMovieResponseInPage(Movie movie);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "categoryNameList", expression =
            "java(movie.getCategoryList().stream().map(com.example.backend_sem2.entity.Category::getCategoryName).toList())")
    abstract MovieResponseWithComment toMovieResponseWithComment(Movie movie);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    abstract Movie toEntity(MovieOverviewDetailIMDB movieInApi);

    @Mapping(target = "id", ignore = true)
    @Mapping(source = "voteAverage", target = "imdbRatings")
    @BeanMapping(nullValuePropertyMappingStrategy =  NullValuePropertyMappingStrategy.IGNORE)
    abstract Movie updateMovieRating (MovieWithIdRating movieWithIdRating, @MappingTarget Movie movie);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    abstract Movie toEntity(CreateMovieRequest createMovieRequest);
}
