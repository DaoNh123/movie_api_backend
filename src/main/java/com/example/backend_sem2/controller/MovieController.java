package com.example.backend_sem2.controller;

import com.example.backend_sem2.entity.Movie;
import com.example.backend_sem2.entity.Seat;
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

    @GetMapping
    public Page<Movie> getMoviePageableByCondition(
            Pageable pageable,
            @RequestParam(name = "name", required = false) String partOfMovieName,
            @RequestParam(name = "category_name", required = false) String categoryName,
            @RequestParam(name = "movie_label", required = false) String movieLabel
    ){
        return movieService.getMoviePageableByCondition(pageable, partOfMovieName, categoryName, movieLabel);
    }

    @GetMapping("/{id}")
    public Movie getMovieById(Long id){
        return movieService.getMovieById(id);
    }

    @GetMapping("/{id}/slots")
    public List<Slot> getAllSlotOfAMovie(
            @PathVariable Long id
    ){
        return slotService.getSlotsByMovie_Id(id);
    }

    @GetMapping("/{id}/slots/{slot_id}")
    public List<Seat> getAllSeatOfASlotWithStatus(
            @PathVariable Long id,
            @PathVariable(name = "slots_id") Long slotId
    ){
        return seatService.getAllSeatOfASlotWithStatus(id, slotId);
    }
}
