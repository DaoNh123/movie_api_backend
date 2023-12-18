package com.example.backend_sem2.service.ServiceImpl;

import com.example.backend_sem2.entity.Movie;
import com.example.backend_sem2.entity.MovieLabelEnum;
import com.example.backend_sem2.exception.CustomErrorException;
import com.example.backend_sem2.repository.MovieRepo;
import com.example.backend_sem2.service.interfaceService.MovieService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@AllArgsConstructor
public class MovieServiceImpl implements MovieService {
    private MovieRepo movieRepo;

    @Override
    public Page<Movie> getMoviePageableByCondition(Pageable pageable, String partOfMovieName, String categoryName, String movieLabel) {
//        return movieRepo.getMoviePageableByCondition1(pageable, partOfMovieName, categoryName);
//        return movieRepo.getMoviePageableByCondition(pageable, partOfMovieName, categoryName, movieLabel);
        return movieRepo.getMoviePageableByCondition3(pageable, partOfMovieName, categoryName, getMovieLabelEnum(movieLabel));
//        return null;
    }


    @Override
    public Movie getMovieById(Long id) {
        return movieRepo.findById(id)
                .orElseThrow(() -> new CustomErrorException(HttpStatus.BAD_REQUEST, "Movie is not exist!"));
    }

    @Override
    public Page<Movie> getMoviePageable(Pageable pageable) {
        return movieRepo.findAll(pageable);
    }

    @Override
    public List<Movie> findMoviesByMovieLabel(String movieLabel) {
        MovieLabelEnum movieLabelEnum = getMovieLabelEnum(movieLabel);
        if(movieLabelEnum == null) throw new CustomErrorException(HttpStatus.BAD_REQUEST, "Your movie label is not exist!");
        return movieRepo.findMoviesByMovieLabel(movieLabelEnum);
    }

    private MovieLabelEnum getMovieLabelEnum(String movieLabel) {
        try {
            return MovieLabelEnum.valueOf(movieLabel);
        } catch (IllegalArgumentException e) {
            return null;
            //throw new CustomErrorException(HttpStatus.BAD_REQUEST, "Your movie label is not exist!");
        }
    }
}
