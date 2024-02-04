package com.example.backend_sem2.service.interfaceService;

import com.example.backend_sem2.dto.CreateMovieRequest;
import com.example.backend_sem2.dto.dtoForMovie.MovieResponseOverview;
import com.example.backend_sem2.enums.MovieLabelEnum;
import com.example.backend_sem2.dto.dtoForMovie.MovieResponseInPage;
import com.example.backend_sem2.dto.dtoForMovie.MovieResponseWithComment;
import com.example.backend_sem2.enums.MovieShowingStatusEnum;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDate;
import java.time.ZonedDateTime;
import java.util.List;

public interface MovieService {
    Page<MovieResponseInPage> getMoviePageableByCondition(Pageable pageable, String partOfMovieName, String categoryName, MovieLabelEnum movieLabel);

    MovieResponseWithComment getMovieWithCommentsById(Long id);

    Page<MovieResponseInPage> getMoviesByOpeningTimeAfter(Pageable pageable, ZonedDateTime zonedDateTime);

    Page<MovieResponseInPage> getMovieWithShowingStatusPageableByCondition(Pageable pageable, String partOfMovieName, String categoryName, MovieLabelEnum movieLabel, MovieShowingStatusEnum showingStatus);

//    MovieResponseInPage createMovie(CreateMovieRequest createMovieRequest) throws IOException;

    MovieResponseInPage createMovie(MultipartFile poster, CreateMovieRequest createMovieRequest) throws IOException;

    MovieResponseInPage deleteMovie(Long id);

    CreateMovieRequest imdbIdToCreateMovieRequest(String imdbId);

    List<MovieResponseOverview> listingMovieToCreateSlot();

    List<MovieResponseOverview> listingMovieToCreateSlot(LocalDate dateOfSlot);

    String getMovieNameById(Long id);
    /*  "getMoviePageableByCondition" already cover this method */
//    List<MovieResponseInPage> findMoviesByMovieLabel(String movieLabel);
}
