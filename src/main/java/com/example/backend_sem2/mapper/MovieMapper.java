package com.example.backend_sem2.mapper;

import com.example.backend_sem2.dto.CategoryDto;
import com.example.backend_sem2.dto.DtoForMovie.MovieResponseInPage;
import com.example.backend_sem2.dto.DtoForMovie.MovieResponseWithComment;
import com.example.backend_sem2.dto.OrderResponseInfo.MovieInOrderRes;
import com.example.backend_sem2.entity.Category;
import com.example.backend_sem2.entity.Movie;
import com.example.backend_sem2.model.rapidApi.MovieOverviewDetailIMDB;
import com.example.backend_sem2.model.theMovieDB.MovieWithIdRating;
import org.mapstruct.*;

@Mapper(componentModel = "spring", uses = {ReferenceMapper.class, CommentMapper.class})
public interface MovieMapper {
    Movie toEntity(Long id);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    MovieInOrderRes toMovieInOrderRes(Movie movie);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "categoryNameList", expression =
            "java(movie.getCategoryList().stream().map(com.example.backend_sem2.entity.Category::getCategoryName).toList())")
    MovieResponseInPage toMovieResponseInPage(Movie movie);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "categoryNameList", expression =
            "java(movie.getCategoryList().stream().map(com.example.backend_sem2.entity.Category::getCategoryName).toList())")
    MovieResponseWithComment toMovieResponseWithComment(Movie movie);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    Movie toEntity(MovieOverviewDetailIMDB movieInApi);

    @Mapping(target = "id", ignore = true)
    @Mapping(source = "voteAverage", target = "imdbRatings")
    @BeanMapping(nullValuePropertyMappingStrategy =  NullValuePropertyMappingStrategy.IGNORE)
    Movie updateMovieRating (MovieWithIdRating movieWithIdRating, @MappingTarget Movie movie);
}
