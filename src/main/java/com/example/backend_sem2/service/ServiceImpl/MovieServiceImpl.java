package com.example.backend_sem2.service.ServiceImpl;

import com.example.backend_sem2.dto.DtoForMovie.MovieResponseInPage;
import com.example.backend_sem2.dto.DtoForMovie.MovieResponseWithComment;
import com.example.backend_sem2.entity.Movie;
import com.example.backend_sem2.Enum.MovieLabelEnum;
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

        Page<Movie> moviePageableByCondition3 = movieRepo.getMoviePageableByCondition(pageable, partOfMovieName, categoryName, movieLabel);

        return moviePageableByCondition3.map(movieMapper::toMovieResponseInPage);

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

}
