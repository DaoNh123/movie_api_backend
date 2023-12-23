package com.example.backend_sem2.controller;

import com.example.backend_sem2.Enum.MovieLabelEnum;
import com.example.backend_sem2.dto.DtoForMovie.MovieResponseInPage;
import com.example.backend_sem2.dto.DtoForMovie.MovieResponseWithComment;
import com.example.backend_sem2.dto.OrderResponseInfo.MovieInOrderRes;
import com.example.backend_sem2.dto.SeatResponse;
import com.example.backend_sem2.dto.SlotResponse;
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

    @GetMapping({"", "/"})
    public Page<MovieResponseInPage> getMoviePageableByCondition(
            @SortDefault(sort = "id", direction = Sort.Direction.DESC)
            @PageableDefault(size = 20) Pageable pageable,
            @RequestParam(name = "name", required = false) String partOfMovieName,
            @RequestParam(name = "category_name", required = false) String categoryName,
            @RequestParam(name = "movie_label", required = false) MovieLabelEnum movieLabel
    ){
        return movieService.getMoviePageableByCondition(pageable, partOfMovieName, categoryName, movieLabel);
    }

    @GetMapping("/{id}")
    public MovieResponseWithComment getMovieWithCommentsById(@PathVariable Long id){
        System.out.println("***" + id);
        return movieService.getMovieWithCommentsById(id);
    }

    /*  Prepare to remove because do not using in Frontend */
    @GetMapping("/{id}/slots")
    public Map<String, Object> getAllSlotOfAMovieByShowDate(
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

        List<SlotResponse> slotResponses = slotService.getSlotsByMovie_IdBetweenTwoZonedDateTimes(pageable, id, startOfShowDate, endOfShowDate);

        Map<String, Object> response = new HashMap<>();
        response.put("resultSize", slotResponses.size());
        response.put("slotResponses", slotResponses);
        return response;
    }

    @GetMapping("/{id}/slotsInNext7Days")
    public Map<String, Object> getAllSlotOfAMovieWithinSevenDaysFromNow(
            @SortDefault(sort = "startTime", direction = Sort.Direction.ASC)
//            @PageableDefault(value = 10, size = 10, page = 0)
            Pageable pageable,
            @PathVariable Long id
    ){
        ZoneId zoneId = ZoneId.of("UTC+7");
        ZonedDateTime startOfShowDate = ZonedDateTime.now();
        ZonedDateTime endOfShowDate = ZonedDateTime.now().toLocalDate().atStartOfDay(zoneId).plusDays(7);
        System.out.println("endOfShowDate = " + endOfShowDate);

        List<SlotResponse> slotResponses = slotService.getSlotsByMovie_IdBetweenTwoZonedDateTimes(pageable, id, startOfShowDate, endOfShowDate);

        Map<String, Object> response = new HashMap<>();
        response.put("resultSize", slotResponses.size());
        response.put("slotResponses", slotResponses);
        return response;
    }


    @GetMapping("/{id}/slots/{slot_id}")
    public List<SeatResponse> getAllSeatOfASlotWithStatus(
            @PathVariable(name = "id") Long movieId,
            @PathVariable(name = "slot_id") Long slotId
    ){
        System.out.println("*** Movie Id is: " + movieId);
        return seatService.getAllSeatOfASlotWithStatus(slotId);
    }

    
    /*  Removing        */
//    @GetMapping("/host")
//    public String getHostName(HttpServletRequest request) {
//
//        System.out.println(request.getLocalName());  // it will return the hostname of the machine where server is running.
//
//        System.out.println(request.getLocalAddr());  // it will return the ip address of the machine where server is running.
//
//        String requestURI = request.getRequestURI();
//        String requestURL = request.getRequestURL().toString();
//
//        System.out.println("Local path: " + requestURL.replace(requestURI, ""));
//        return request.getHeader("host");
//    }
}
