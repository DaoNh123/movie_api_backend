package com.example.backend_sem2.service;

import com.example.backend_sem2.entity.Movie;
import com.example.backend_sem2.repository.MovieRepo;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

@Repository
@AllArgsConstructor
public class MovieServiceImpl implements MovieService{
    private MovieRepo movieRepo;

    @Override
    public Page<Movie> getMoviePageableByCondition(Pageable pageable, String partOfMovieName, String categoryName, String movieLabel) {
        return movieRepo.getMoviePageableByCondition(pageable, partOfMovieName, categoryName, movieLabel);
    }
}
