package com.example.backend_sem2.service;

import com.example.backend_sem2.entity.MovieLabel;
import com.example.backend_sem2.exception.CustomErrorException;
import com.example.backend_sem2.repository.MovieLabelRepo;
import com.example.backend_sem2.repository.MovieRepo;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class MovieLabelServiceImpl implements MovieLabelService{
    private MovieLabelRepo movieLabelRepo;
    @Override
    public List<MovieLabel> getAllMovieLabel() {
        return movieLabelRepo.findAll();
    }

    @Override
    public MovieLabel updateMovieLabel(MovieLabel movieLabel, Long id) {
        if(movieLabelRepo.existsById(id)){
            movieLabel.setId(id);
            return movieLabelRepo.save(movieLabel);
        }
        throw new CustomErrorException(HttpStatus.BAD_REQUEST, "Movie Label doesn't exist!");
    }

    @Override
    public MovieLabel saveMovieLabel(MovieLabel movieLabel) {
        if(!movieLabelRepo.existsByMovieLabelNameIgnoreCase(movieLabel.getMovieLabelName())){
            return movieLabelRepo.save(movieLabel);
        }
        throw new CustomErrorException(HttpStatus.BAD_REQUEST, "This movie label already exists!");
    }

    @Override
    public boolean deleteMovieLabelById(Long id) {
        if(movieLabelRepo.existsById(id)){
            movieLabelRepo.deleteById(id);
            return true;
        }
        throw new CustomErrorException(HttpStatus.BAD_REQUEST, "This movie label doesn't exist!");
    }
}
