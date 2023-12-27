package com.example.backend_sem2.service.serviceImpl;

import com.example.backend_sem2.dto.CreateMovieRequest;
import com.example.backend_sem2.dto.DtoForMovie.MovieResponseInPage;
import com.example.backend_sem2.dto.DtoForMovie.MovieResponseWithComment;
import com.example.backend_sem2.entity.Movie;
import com.example.backend_sem2.enums.MovieLabelEnum;
import com.example.backend_sem2.enums.MovieShowingStatusEnum;
import com.example.backend_sem2.mapper.MovieMapper;
import com.example.backend_sem2.repository.MovieRepo;
import com.example.backend_sem2.service.interfaceService.MovieService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.time.ZonedDateTime;

@Repository
@AllArgsConstructor
public class MovieServiceImpl implements MovieService {
    private MovieRepo movieRepo;
    private MovieMapper movieMapper;

    @Override
    public Page<MovieResponseInPage> getMoviePageableByCondition(Pageable pageable, String partOfMovieName, String categoryName, MovieLabelEnum movieLabel) {

        Page<Movie> moviePageableByCondition = movieRepo.getMoviePageableByCondition(pageable, partOfMovieName,
                categoryName, movieLabel, null, null, false);

        return moviePageableByCondition.map(movieMapper::toMovieResponseInPage);

    }

    @Override
    public Page<MovieResponseInPage> getMovieWithShowingStatusPageableByCondition(Pageable pageable, String partOfMovieName, String categoryName, MovieLabelEnum movieLabel, MovieShowingStatusEnum showingStatus) {

        Page<Movie> nowShowingMoviePageableByCondition = movieRepo.getMoviePageableByCondition(pageable,
                partOfMovieName, categoryName, movieLabel, showingStatus, null, false);

        return nowShowingMoviePageableByCondition.map(movieMapper::toMovieResponseInPage);
    }

    @Override
    public MovieResponseWithComment getMovieWithCommentsById(Long id) {
        return movieMapper.toMovieResponseWithComment(movieRepo.getMovieWithComments(id));
    }

    @Override
    public Page<MovieResponseInPage> getMoviesByOpeningTimeAfter(
            Pageable pageable,
            ZonedDateTime zonedDateTime
    ) {
        return movieRepo.getMoviesByOpeningTimeAfter(pageable, zonedDateTime)
                .map(movieMapper::toMovieResponseInPage);
    }

    @Override
    public MovieResponseInPage createMovie(CreateMovieRequest createMovieRequest) {

        return null;
    }
}
