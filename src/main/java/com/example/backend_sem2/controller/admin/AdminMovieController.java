package com.example.backend_sem2.controller.admin;

import com.example.backend_sem2.dto.CreateMovieRequest;
import com.example.backend_sem2.dto.DtoForMovie.MovieResponseInPage;
import com.example.backend_sem2.service.interfaceService.MovieService;
import com.example.backend_sem2.service.interfaceService.SeatService;
import com.example.backend_sem2.service.interfaceService.SlotService;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/admin/movies")
@AllArgsConstructor
public class AdminMovieController {
    private MovieService movieService;
    private SlotService slotService;
    private SeatService seatService;

    @SneakyThrows
    @PostMapping({"", "/"})
    public MovieResponseInPage createAMovie(
            @RequestPart("poster") MultipartFile poster,
            @RequestPart("createMovieRequest") CreateMovieRequest createMovieRequest
    ){
        System.out.println("*** process ***: Type of createMovieRequest: " + createMovieRequest.getClass());
        return movieService.createMovie(poster, createMovieRequest);
    }

    @DeleteMapping("/{id}")
    public MovieResponseInPage deleteMovie (
            @PathVariable Long id
    ){
        return movieService.deleteMovie(id);
    }
}
