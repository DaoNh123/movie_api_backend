package com.example.backend_sem2.service.serviceImpl;

import com.example.backend_sem2.dto.CreateMovieRequest;
import com.example.backend_sem2.dto.DtoForMovie.MovieResponseInPage;
import com.example.backend_sem2.dto.DtoForMovie.MovieResponseWithComment;
import com.example.backend_sem2.entity.Movie;
import com.example.backend_sem2.enums.MovieLabelEnum;
import com.example.backend_sem2.enums.MovieShowingStatusEnum;
import com.example.backend_sem2.mapper.MovieMapper;
import com.example.backend_sem2.repository.MovieRepo;
import com.example.backend_sem2.service.interfaceService.AmazonService;
import com.example.backend_sem2.service.interfaceService.MovieService;
import lombok.AllArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.ZonedDateTime;

@Repository
@AllArgsConstructor
@EnableCaching
public class MovieServiceImpl implements MovieService {
    private MovieRepo movieRepo;
    private MovieMapper movieMapper;
    private AmazonService amazonService;


    @Override
    @Cacheable(value = "movieByCondition")
    public Page<MovieResponseInPage> getMoviePageableByCondition(Pageable pageable, String partOfMovieName, String categoryName, MovieLabelEnum movieLabel) {

        Page<Movie> moviePageableByCondition = movieRepo.getMoviePageableByCondition(pageable, partOfMovieName,
                categoryName, movieLabel, null, null, false);

        return moviePageableByCondition.map(movieMapper::toMovieResponseInPage);

    }

    @Override
    @Cacheable(value = "movieWithShowingStatus")
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

//    private String exactFileNameFrom

    @Override
    @CacheEvict(value = {"movieWithShowingStatus", "movieByCondition"}, allEntries = true)
    public MovieResponseInPage createMovie(CreateMovieRequest createMovieRequest) throws IOException {
        /*  Using S3 in next 2 lines*/
        if (createMovieRequest.getPoster() != null) createMovieRequest.setPosterUrl(amazonService.
                handleImageInCreateMovieRequest(createMovieRequest.getPoster()));

        Movie createdMovie = movieRepo.save(movieMapper.toEntity(createMovieRequest));

        return movieMapper.toMovieResponseInPage(createdMovie);
    }

    @Override
    public MovieResponseInPage createMovie2(MultipartFile poster, CreateMovieRequest createMovieRequest) throws IOException {
        if (poster != null) createMovieRequest.setPosterUrl(amazonService.
                handleImageInCreateMovieRequest(poster));

        Movie createdMovie = movieRepo.save(movieMapper.toEntity(createMovieRequest));

        return movieMapper.toMovieResponseInPage(createdMovie);
    }
}
