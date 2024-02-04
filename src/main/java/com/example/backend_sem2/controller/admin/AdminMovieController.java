package com.example.backend_sem2.controller.admin;

import com.example.backend_sem2.dto.CreateMovieRequest;
import com.example.backend_sem2.dto.dtoForMovie.MovieResponseInPage;
import com.example.backend_sem2.dto.dtoForMovie.MovieResponseOverview;
import com.example.backend_sem2.service.interfaceService.MovieService;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/admin/movies")
@AllArgsConstructor
public class AdminMovieController {
    private MovieService movieService;

    @SneakyThrows
    @PostMapping({"", "/"})
    public MovieResponseInPage createAMovie(
            @RequestPart(value = "poster", required = false) MultipartFile poster,
            @RequestPart("createMovieRequest") CreateMovieRequest createMovieRequest
    ) {
        System.out.println("*** process ***: Type of createMovieRequest: " + createMovieRequest.getClass());
        return movieService.createMovie(poster, createMovieRequest);
    }

    @DeleteMapping("/{id}")
    public MovieResponseInPage deleteMovie(
            @PathVariable Long id
    ) {
        return movieService.deleteMovie(id);
    }

    @GetMapping("/autofill-data/{imdbId}")
    public CreateMovieRequest imdbIdToCreateMovieRequest(
            @PathVariable String imdbId
    ) {
        return movieService.imdbIdToCreateMovieRequest(imdbId);
    }

    /*  This method will return all Movie which have
    "closingTime" is after today  */
    @GetMapping("/listing")
    public List<MovieResponseOverview> listingMovieToCreateSlot() {
        return movieService.listingMovieToCreateSlot();
    }

    @GetMapping("/listing2")
    public List<MovieResponseOverview> listingMovieToCreateSlot2(
            @RequestParam(name = "date-of-slot") LocalDate dateOfSlot
    ) {
        return movieService.listingMovieToCreateSlot(dateOfSlot);
    }
}
