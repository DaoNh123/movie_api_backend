package com.example.backend_sem2.controller;

import com.example.backend_sem2.dto.SeatResponse;
import com.example.backend_sem2.entity.Movie;
import com.example.backend_sem2.entity.Slot;
import com.example.backend_sem2.service.interfaceService.MovieService;
import com.example.backend_sem2.service.interfaceService.SeatService;
import com.example.backend_sem2.service.interfaceService.SlotService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.web.SortDefault;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.ZoneId;
import java.time.ZonedDateTime;
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
            @SortDefault(sort = "id", direction = Sort.Direction.DESC)
            @PageableDefault(size = 20) Pageable pageable,
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
    public List<Slot> getAllSlotOfAMovieByShowDate(
            @SortDefault(sort = "startTime", direction = Sort.Direction.ASC)
//            @PageableDefault(value = 10, size = 10, page = 0)
            Pageable pageable,
            @DateTimeFormat(pattern = "dd.MM.yyyy")
            @RequestParam(name = "show_date", required = false) LocalDate showDate,
            @PathVariable Long id
    ){
        System.out.println("showDate = " + showDate);
        ZoneId zoneId = ZoneId.of("UTC+7");
        ZonedDateTime startOfShowDate = (showDate == null) ? null : showDate.atStartOfDay().atZone(zoneId);
        ZonedDateTime endOfShowDate = (showDate == null) ? null : showDate.plusDays(1).atStartOfDay().atZone(zoneId);
        System.out.println("endOfShowDate = " + endOfShowDate);
//        return slotService.getSlotsByMovie_Id(id);
        return slotService.getSlotsByMovie_IdAndShowDate(pageable, id, startOfShowDate, endOfShowDate);
    }


    @GetMapping("/{id}/slots/{slot_id}")
    public List<SeatResponse> getAllSeatOfASlotWithStatus(
            @PathVariable(name = "id") Long movieId,
            @PathVariable(name = "slot_id") Long slotId
    ){
        System.out.println("*** Movie Id is: " + movieId);
        return seatService.getAllSeatOfASlotWithStatus(slotId);
    }

    @GetMapping("/host")
    public String getHostName(HttpServletRequest request) {

        System.out.println(request.getLocalName());  // it will return the hostname of the machine where server is running.

        System.out.println(request.getLocalAddr());  // it will return the ip address of the machine where server is running.

        String requestURI = request.getRequestURI();
        String requestURL = request.getRequestURL().toString();

        System.out.println("Local path: " + requestURL.replace(requestURI, ""));
        return request.getHeader("host");

    }
}
