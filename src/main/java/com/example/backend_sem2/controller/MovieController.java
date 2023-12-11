package com.example.backend_sem2.controller;

import com.example.backend_sem2.entity.Movie;
import com.example.backend_sem2.service.MovieService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/movies")
@AllArgsConstructor
public class MovieController {
    private MovieService movieService;

    @GetMapping
    public Page<Movie> getMoviePageableByCondition(
            Pageable pageable,
            @RequestParam(name = "name", required = false) String partOfMovieName,
            @RequestParam(name = "category_name", required = false) String categoryName,
            @RequestParam(name = "movie_label", required = false) String movieLabel
    ){
        return movieService.getMoviePageableByCondition(pageable, partOfMovieName, categoryName, movieLabel);
    }
}
