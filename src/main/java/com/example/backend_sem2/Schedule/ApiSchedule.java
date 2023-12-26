package com.example.backend_sem2.Schedule;

import com.example.backend_sem2.Api.OkHttp.OkHttpService;
import com.example.backend_sem2.entity.Movie;
import com.example.backend_sem2.mapper.MovieMapper;
import com.example.backend_sem2.model.theMovieDB.MovieWithIdRating;
import com.example.backend_sem2.repository.MovieRepo;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import okhttp3.OkHttpClient;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalTime;
import java.util.List;

@Component
@AllArgsConstructor
public class ApiSchedule {
    private MovieRepo movieRepo;
    private MovieMapper movieMapper;
    private OkHttpService okHttpService;

//    @Scheduled(cron = "0 0/5 * * * *")      // Run every 5 minutes
//    @Scheduled(cron =  "0/10 * * * * *")      // Run every 10 seconds
    @Transactional
    public void updateRatingAndMovieIMDBId() {
        System.out.println("---Start updating rating---");
        System.out.println("LocalTime.now() = " + LocalTime.now());

        List<Movie> movieList = movieRepo.findAll();

        movieList.stream().forEach(movie -> {
            MovieWithIdRating movieWithIdRating = okHttpService.getMovieWithRatingUsingTheMovieDBId(movie.getTheMovieDBId());
            movieMapper.updateMovieRating(movieWithIdRating, movie);
        });

        movieRepo.saveAllAndFlush(movieList);

        System.out.println("---End updating rating---");
    }
}
