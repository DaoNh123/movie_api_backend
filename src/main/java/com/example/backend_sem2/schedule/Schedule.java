package com.example.backend_sem2.schedule;

import com.example.backend_sem2.api.HttpService;
import com.example.backend_sem2.api.TheMovieDBApiService;
import com.example.backend_sem2.entity.Movie;
import com.example.backend_sem2.enums.MovieBookingStatusEnum;
import com.example.backend_sem2.enums.MovieShowingStatusEnum;
import com.example.backend_sem2.mapper.MovieMapper;
import com.example.backend_sem2.model.theMovieDB.MovieWithIdRating;
import com.example.backend_sem2.repository.MovieRepo;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.List;

@Component
@AllArgsConstructor
public class Schedule {
    private MovieRepo movieRepo;
    private MovieMapper movieMapper;
    private HttpService httpService;
    private TheMovieDBApiService theMovieDBApiService;

    @Scheduled(cron = "0 0/20 * * * *")      // Run every 20 minutes
//    @Scheduled(cron =  "0/10 * * * * *")      // Run every 10 seconds
    @Transactional
    public void updateRatingAndMovieIMDBId() {
        System.out.println("---Start updating rating for Movie---");
        System.out.println("LocalTime.now() = " + LocalTime.now());

        List<Movie> movieList = movieRepo.findAll();

        movieList.stream().forEach(movie -> {
            if(movie.getTheMovieDBId() != null){
                MovieWithIdRating movieWithIdRating = theMovieDBApiService.getMovieWithRatingUsingTheMovieDBId(movie.getTheMovieDBId());
                movieMapper.updateMovieRating(movieWithIdRating, movie);
            }
        });

        movieRepo.saveAllAndFlush(movieList);

        System.out.println("---End updating rating---");
    }

    /*  Update "booking_status" and "showing_status"    */
    @Scheduled(cron = "0 0/20 * * * *")
    public void updateMovieStatus(){
        System.out.println("Start updating BookingStatus and ShowingStatus of Movie!");
        ZoneId zoneId = ZoneId.of("UTC");
        ZonedDateTime startOfToday = LocalDate.now().atStartOfDay().atZone(zoneId);
        ZonedDateTime threeDaysAfterToday = LocalDate.now().atStartOfDay().atZone(zoneId);

        /*  If today is between "movie.openingDay" - 3 days and "movie.closingDay" ==> ALLOWED */
        movieRepo.updateAllowedBookingStatus(startOfToday, threeDaysAfterToday, MovieBookingStatusEnum.ALLOWED);
        /*  If today is after "movie.closingDay" ==> NOT_ALLOWED    */
        movieRepo.updateNotAllowedBookingStatus(startOfToday, MovieBookingStatusEnum.NOT_ALLOWED);

        /*  If today is between "movie.openingTime" and "movie.closingTime" ==> NOW_SHOWING */
        movieRepo.updateNowShowingStatus(startOfToday, MovieShowingStatusEnum.NOW_SHOWING);
        /*  If today is after "movie.closingTime" ==> ENDED */
        movieRepo.updateEndedShowingStatus(startOfToday, MovieShowingStatusEnum.ENDED);


        System.out.println("End updating BookingStatus and ShowingStatus of Movie!");
    }

}
