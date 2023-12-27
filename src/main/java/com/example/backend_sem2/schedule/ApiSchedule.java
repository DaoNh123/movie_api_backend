package com.example.backend_sem2.schedule;

import com.example.backend_sem2.api.theMovieDBApi.HttpService;
import com.example.backend_sem2.entity.Movie;
import com.example.backend_sem2.mapper.MovieMapper;
import com.example.backend_sem2.model.theMovieDB.MovieWithIdRating;
import com.example.backend_sem2.repository.MovieRepo;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalTime;
import java.util.List;

@Component
@AllArgsConstructor
public class ApiSchedule {
    private MovieRepo movieRepo;
    private MovieMapper movieMapper;
    private HttpService httpService;

//    @Scheduled(cron = "0 0/5 * * * *")      // Run every 5 minutes
//    @Scheduled(cron =  "0/10 * * * * *")      // Run every 10 seconds
    @Transactional
    public void updateRatingAndMovieIMDBId() {
        System.out.println("---Start updating rating---");
        System.out.println("LocalTime.now() = " + LocalTime.now());

        List<Movie> movieList = movieRepo.findAll();

        movieList.stream().forEach(movie -> {
            if(movie.getTheMovieDBId() != null){
                MovieWithIdRating movieWithIdRating = httpService.getMovieWithRatingUsingTheMovieDBId(movie.getTheMovieDBId());
                movieMapper.updateMovieRating(movieWithIdRating, movie);
            }
        });

        movieRepo.saveAllAndFlush(movieList);

        System.out.println("---End updating rating---");
    }
}
