package com.example.backend_sem2.controller;

import com.example.backend_sem2.dto.CreateMovieRequest;
import com.example.backend_sem2.dto.DtoForMovie.MovieResponseInPage;
import com.example.backend_sem2.dto.DtoForMovie.MovieResponseWithComment;
import com.example.backend_sem2.dto.SlotResponse;
import com.example.backend_sem2.enums.MovieLabelEnum;
import com.example.backend_sem2.enums.MovieShowingStatusEnum;
import com.example.backend_sem2.service.interfaceService.MovieService;
import com.example.backend_sem2.service.interfaceService.SeatService;
import com.example.backend_sem2.service.interfaceService.SlotService;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import org.jetbrains.annotations.NotNull;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.web.SortDefault;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/movies")
@AllArgsConstructor
public class MovieController {
    private MovieService movieService;
    private SlotService slotService;
    private SeatService seatService;

    @SneakyThrows
    @PostMapping({"/remove"})
    public MovieResponseInPage createAMovie(
            @ModelAttribute CreateMovieRequest createMovieRequest
    ){
        return movieService.createMovie(createMovieRequest);
    }

    @SneakyThrows
    @PostMapping({"", "/"})
    public MovieResponseInPage createAMovie2(
            @RequestPart("poster") MultipartFile poster,
            @RequestPart("createMovieRequest") CreateMovieRequest createMovieRequest
    ){
        System.out.println("*** process ***: Type of createMovieRequest: " + createMovieRequest.getClass());
        return movieService.createMovie2(poster, createMovieRequest);
    }

    @GetMapping({"", "/"})
    public Page<MovieResponseInPage> getMoviePageableByCondition(
            @SortDefault(sort = "openingTime", direction = Sort.Direction.DESC)
            @PageableDefault(size = 25) Pageable pageable,
            @RequestParam(name = "name", required = false) String partOfMovieName,
            @RequestParam(name = "category_name", required = false) String categoryName,
            @RequestParam(name = "movie_label", required = false) MovieLabelEnum movieLabel
    ) {
        return movieService.getMoviePageableByCondition(pageable, partOfMovieName, categoryName, movieLabel);
    }

    @GetMapping({"/now-showing"})
    public Page<MovieResponseInPage> getMovieWithShowingStatusPageableByCondition(
            @SortDefault(sort = "openingTime", direction = Sort.Direction.DESC)
            @PageableDefault(size = 25) Pageable pageable,
            @RequestParam(name = "name", required = false) String partOfMovieName,
            @RequestParam(name = "category_name", required = false) String categoryName,
            @RequestParam(name = "movie_label", required = false) MovieLabelEnum movieLabel
    ) {
        MovieShowingStatusEnum showingStatus = MovieShowingStatusEnum.NOW_SHOWING;
        Long start = System.currentTimeMillis();
        Page<MovieResponseInPage> movieWithShowingStatusPageableByCondition = movieService.getMovieWithShowingStatusPageableByCondition(pageable, partOfMovieName, categoryName, movieLabel, showingStatus);
        Long end = System.currentTimeMillis();
        System.out.println("Running time: " + (end - start));
        return movieWithShowingStatusPageableByCondition;
    }

    /*  Coming Soon Movie   */
    @GetMapping("/coming-soon")
    public Page<MovieResponseInPage> getComingSoonMoviesByCondition(
            @SortDefault(sort = "openingTime", direction = Sort.Direction.DESC)
            @PageableDefault(size = 15) Pageable pageable,
            @RequestParam(name = "name", required = false) String partOfMovieName,
            @RequestParam(name = "category_name", required = false) String categoryName,
            @RequestParam(name = "movie_label", required = false) MovieLabelEnum movieLabel
    ) {
        MovieShowingStatusEnum showingStatus = MovieShowingStatusEnum.UNRELEASED;
        return movieService.getMovieWithShowingStatusPageableByCondition(pageable, partOfMovieName, categoryName, movieLabel, showingStatus);
    }

    @GetMapping("/{id}")
    public MovieResponseWithComment getMovieWithCommentsById(@PathVariable Long id) {
        System.out.println("***" + id);
        return movieService.getMovieWithCommentsById(id);
    }

    /*  Prepare to remove because do not using in Frontend */
    @GetMapping("/{id}/slots")
    public Map<String, Object> getAllSlotOfAMovieByShowDate(
            @SortDefault(sort = "openingTime", direction = Sort.Direction.ASC)
//            @PageableDefault(value = 10, size = 10, page = 0)
            Pageable pageable,
            @DateTimeFormat(pattern = "dd.MM.yyyy")
            @RequestParam(name = "show_date", required = false) LocalDate showDate,
            @PathVariable Long id
    ) {
        System.out.println("showDate = " + showDate);
        ZoneId zoneId = ZoneId.of("UTC+7");
        ZonedDateTime startOfShowDate = (showDate == null) ? null : showDate.atStartOfDay().atZone(zoneId);
        ZonedDateTime endOfShowDate = (showDate == null) ? null : showDate.plusDays(1).atStartOfDay().atZone(zoneId);

        return getResponseMap(pageable, id, startOfShowDate, endOfShowDate);
    }


    @GetMapping("/{id}/slotsInNext7Days")
    public Map<String, Object> getAllSlotOfAMovieWithinSevenDaysFromNow(
            @SortDefault(sort = "startTime", direction = Sort.Direction.ASC)            // startTime is Slot property
//            @PageableDefault(value = 10, size = 10, page = 0)
            Pageable pageable,
            @PathVariable Long id
    ) {
        ZoneId zoneId = ZoneId.of("UTC+7");
        ZonedDateTime startOfShowDate = ZonedDateTime.now();
        ZonedDateTime endOfShowDate = ZonedDateTime.now().toLocalDate().atStartOfDay(zoneId).plusDays(7);
        System.out.println("endOfShowDate = " + endOfShowDate);

        return getResponseMap(pageable, id, startOfShowDate, endOfShowDate);
    }

    @NotNull
    private Map<String, Object> getResponseMap(Pageable pageable, Long id, ZonedDateTime startOfShowDate, ZonedDateTime endOfShowDate) {
        List<SlotResponse> slotResponses = slotService.getSlotsByMovie_IdBetweenTwoZonedDateTimes(pageable, id, startOfShowDate, endOfShowDate);

        Map<String, Object> response = new HashMap<>();
        response.put("resultSize", slotResponses.size());
        response.put("slotResponses", slotResponses);
        return response;
    }

}
