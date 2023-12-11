package com.example.backend_sem2.service;

import com.example.backend_sem2.entity.MovieLabel;

import java.util.List;

public interface MovieLabelService {
    List<MovieLabel> getAllMovieLabel();

    MovieLabel saveMovieLabel(MovieLabel movieLabel);

    MovieLabel updateMovieLabel(MovieLabel movieLabel, Long id);

    boolean deleteMovieLabelById(Long id);
}
