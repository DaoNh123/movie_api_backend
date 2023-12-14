package com.example.backend_sem2.controller;

import com.example.backend_sem2.dto.SeatResponse;
import com.example.backend_sem2.entity.Movie;
import com.example.backend_sem2.entity.Slot;
import com.example.backend_sem2.service.interfaceService.MovieService;
import com.example.backend_sem2.service.interfaceService.SeatService;
import com.example.backend_sem2.service.interfaceService.SlotService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/movies")
@AllArgsConstructor
public class MovieController {
    private MovieService movieService;
    private SlotService slotService;
    private SeatService seatService;

    @GetMapping("/byCondition")
    public Page<Movie> getMoviePageableByCondition(
            Pageable pageable,
            @RequestParam(name = "name", required = false) String partOfMovieName,
            @RequestParam(name = "category_name", required = false) String categoryName,
            @RequestParam(name = "movie_label", required = false) String movieLabel
    ){
        return movieService.getMoviePageableByCondition(pageable, partOfMovieName, categoryName, movieLabel);
    }

    @GetMapping
    public Page<Movie> getMoviePageable(
            Pageable pageable
    ){
        return movieService.getMoviePageable(pageable);
    }

    @GetMapping("/movie-label/{movie-label}")
    public List<Movie> findMoviesByMovieLabel (
            @PathVariable(name = "movie-label") String movieLabel
            ){
        return movieService.findMoviesByMovieLabel(movieLabel);
    }

    @GetMapping("/{id}")
    public Movie getMovieById(@PathVariable Long id){
        System.out.println("***" + id);
        return movieService.getMovieById(id);
    }

    @GetMapping("/{id}/slots")
    public List<Slot> getAllSlotOfAMovie(
            @PathVariable Long id
    ){
        return slotService.getSlotsByMovie_Id(id);
    }


    @GetMapping("/{id}/slots/{slot_id}")
    public List<SeatResponse> getAllSeatOfASlotWithStatus(
            @PathVariable(name = "id") Long movieId,
            @PathVariable(name = "slot_id") Long slotId
    ){
        return seatService.getAllSeatOfASlotWithStatus(slotId);
    }
}
