package com.example.backend_sem2.service.interfaceService;

import com.example.backend_sem2.dto.DtoForMovie.MovieResponseInPage;
import com.example.backend_sem2.dto.DtoForMovie.MovieResponseWithComment;
import com.example.backend_sem2.entity.Movie;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface MovieService {
    Page<MovieResponseInPage> getMoviePageableByCondition(Pageable pageable, String partOfMovieName, String categoryName, String movieLabel);

    MovieResponseWithComment getMovieWithCommentsById(Long id);

    /*  "getMoviePageableByCondition" already cover this method */
//    List<MovieResponseInPage> findMoviesByMovieLabel(String movieLabel);
}
