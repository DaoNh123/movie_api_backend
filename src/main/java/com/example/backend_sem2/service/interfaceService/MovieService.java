package com.example.backend_sem2.service.interfaceService;

import com.example.backend_sem2.entity.Movie;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface MovieService {
    Page<Movie> getMoviePageableByCondition(Pageable pageable, String partOfMovieName, String categoryName, String movieLabel);

    Movie getMovieById(Long id);
}
