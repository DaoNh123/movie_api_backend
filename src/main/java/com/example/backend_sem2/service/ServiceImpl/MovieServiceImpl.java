package com.example.backend_sem2.service.ServiceImpl;

import com.example.backend_sem2.entity.Movie;
import com.example.backend_sem2.repository.MovieRepo;
import com.example.backend_sem2.service.interfaceService.MovieService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

@Repository
@AllArgsConstructor
public class MovieServiceImpl implements MovieService {
    private MovieRepo movieRepo;

    @Override
    public Page<Movie> getMoviePageableByCondition(Pageable pageable, String partOfMovieName, String categoryName, String movieLabel) {
        return movieRepo.getMoviePageableByCondition(pageable, partOfMovieName, categoryName, movieLabel);
    }

    @Override
    public Movie getMovieById(Long id) {
        return movieRepo.getById(id);
    }
}
