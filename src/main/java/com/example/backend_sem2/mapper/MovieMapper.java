package com.example.backend_sem2.mapper;

import com.example.backend_sem2.dto.CreateMovieRequest;
import com.example.backend_sem2.dto.DtoForMovie.MovieResponseInPage;
import com.example.backend_sem2.dto.DtoForMovie.MovieResponseWithComment;
import com.example.backend_sem2.dto.OrderResponseInfo.MovieInOrderRes;
import com.example.backend_sem2.entity.Category;
import com.example.backend_sem2.entity.Movie;
import com.example.backend_sem2.model.rapidApi.MovieOverviewDetailIMDB;
import com.example.backend_sem2.model.theMovieDB.MovieWithIdRating;
import com.example.backend_sem2.repository.CategoryRepo;
import com.example.backend_sem2.utility.EntityUtility;
import org.mapstruct.*;
import org.springframework.beans.factory.annotation.Autowired;
import reactor.util.CollectionUtils;

import java.util.List;

@Mapper(componentModel = "spring", uses = {ReferenceMapper.class, CommentMapper.class})

public abstract class MovieMapper {
    @Autowired
    EntityUtility entityUtility;
    @Autowired
    CategoryRepo categoryRepo;
    abstract Movie toEntity(Long id);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    public abstract MovieInOrderRes toMovieInOrderRes(Movie movie);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "categoryNameList", expression =
            "java(movie.getCategoryList().stream().map(com.example.backend_sem2.entity.Category::getCategoryName).toList())")
    public abstract MovieResponseInPage toMovieResponseInPage(Movie movie);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "categoryNameList", expression =
            "java(movie.getCategoryList().stream().map(com.example.backend_sem2.entity.Category::getCategoryName).toList())")
    public abstract MovieResponseWithComment toMovieResponseWithComment(Movie movie);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    public abstract Movie toEntity(MovieOverviewDetailIMDB movieInApi);

    @Mapping(target = "id", ignore = true)
    @Mapping(source = "voteAverage", target = "imdbRatings")
    @BeanMapping(nullValuePropertyMappingStrategy =  NullValuePropertyMappingStrategy.IGNORE)
    public abstract Movie updateMovieRating (MovieWithIdRating movieWithIdRating, @MappingTarget Movie movie);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "categoryList", source = "categoryList", qualifiedByName = "categoryNameListToCategoryList")
    public abstract Movie toEntity(CreateMovieRequest createMovieRequest);

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
}
