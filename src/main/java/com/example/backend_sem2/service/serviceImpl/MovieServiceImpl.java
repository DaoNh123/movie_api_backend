package com.example.backend_sem2.service.serviceImpl;

import com.example.backend_sem2.api.TheMovieDBApiService;
import com.example.backend_sem2.dto.CreateMovieRequest;
import com.example.backend_sem2.dto.dtoForMovie.MovieResponseInPage;
import com.example.backend_sem2.dto.dtoForMovie.MovieResponseOverview;
import com.example.backend_sem2.dto.dtoForMovie.MovieResponseWithComment;
import com.example.backend_sem2.entity.Movie;
import com.example.backend_sem2.enums.MovieLabelEnum;
import com.example.backend_sem2.enums.MovieShowingStatusEnum;
import com.example.backend_sem2.exception.CustomErrorException;
import com.example.backend_sem2.mapper.MovieMapper;
import com.example.backend_sem2.model.theMovieDB.findMovieByTheMovieDBId.MovieByTheMovieDBId;
import com.example.backend_sem2.repository.MovieRepo;
import com.example.backend_sem2.service.interfaceService.AmazonService;
import com.example.backend_sem2.service.interfaceService.MovieService;
import lombok.AllArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Repository;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.List;

@Repository
@AllArgsConstructor
@EnableCaching
public class MovieServiceImpl implements MovieService {
    private MovieRepo movieRepo;
    private MovieMapper movieMapper;
    private AmazonService amazonService;
    private TheMovieDBApiService theMovieDBApiService;


    @Override
    @Cacheable(value = "movieByCondition")
    public Page<MovieResponseInPage> getMoviePageableByCondition(
            Pageable pageable, String partOfMovieName,
            String categoryName, MovieLabelEnum movieLabel
    ) {
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

    @Override
    @CacheEvict(value = {"movieWithShowingStatus", "movieByCondition"}, allEntries = true)
    public MovieResponseInPage createMovie(MultipartFile poster, CreateMovieRequest createMovieRequest) throws IOException {
        if (poster != null) {
            createMovieRequest.setPosterUrl(amazonService.handleImageInCreateMovieRequest(poster));
        } else if (createMovieRequest.getPosterUrlInMovieDB() != null && createMovieRequest.getPosterUrl() == null){
            createMovieRequest.setPosterUrl(amazonService.uploadImageInUrlToS3("images", createMovieRequest.getPosterUrlInMovieDB()));
        }

        Movie createdMovie = movieRepo.save(movieMapper.toEntity(createMovieRequest));

        return movieMapper.toMovieResponseInPage(createdMovie);
    }

    @Override
    public MovieResponseInPage deleteMovie(Long id) {
        Movie deletedMovie = movieRepo.findById(id).orElseThrow(
                () -> new CustomErrorException(HttpStatus.BAD_REQUEST, "The movie which you want to delete doesn't exist!")
        );

        if(deletedMovie.getDeleted()) throw new CustomErrorException(HttpStatus.BAD_REQUEST, "The movie which you want to delete doesn't exist!");
        else deletedMovie.setDeleted(true);

        movieRepo.save(deletedMovie);
        return movieMapper.toMovieResponseInPage(deletedMovie);
    }

    @Override
    public CreateMovieRequest imdbIdToCreateMovieRequest(String imdbId) {
        MovieByTheMovieDBId movieByTheMovieDBId = theMovieDBApiService.findMovieByTheMovieDBId(
                theMovieDBApiService.getTheMovieDBIdByImdbId(imdbId)
        );

        if(movieByTheMovieDBId.getImdbId() == null) throw new CustomErrorException(HttpStatus.NOT_FOUND, "Can not find movie with your IMDB_Id. Please try another imdb_id or enter all data about your movie!");

        return movieMapper.toCreateMovieRequest(movieByTheMovieDBId);
    }

    @Override
    public List<MovieResponseOverview> listingMovieToCreateSlot() {
        ZoneId zoneId = ZoneId.of("UTC+7");
        ZonedDateTime startOfTomorrow = LocalDate.now().atStartOfDay(zoneId).plusDays(1);
        List<Movie> movieList = movieRepo.findByClosingTimeAfter(startOfTomorrow);

        return movieList.stream().map(movieMapper::toMovieResponseOverview).toList();
    }

    @Override
    public List<MovieResponseOverview> listingMovieToCreateSlot(LocalDate dateOfSlot) {
        ZoneId zoneId = ZoneId.of("UTC+7");
        ZonedDateTime startOfSlotDate = dateOfSlot.atStartOfDay(zoneId).plusSeconds(1);     //
        ZonedDateTime endOfSlotDate = dateOfSlot.atStartOfDay(zoneId).plusDays(1);

        List<Movie> movieList = movieRepo.findByOpeningTimeBeforeAndClosingTimeAfter(startOfSlotDate, endOfSlotDate);
        return movieList.stream().map(movieMapper::toMovieResponseOverview).toList();
    }

    @Override
    public String getMovieNameById(Long id) {
        return movieRepo.getMovieNameById(id);
    }
}
