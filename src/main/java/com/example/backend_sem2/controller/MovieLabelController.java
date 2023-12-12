//package com.example.backend_sem2.controller;
//
//import com.example.backend_sem2.entity.MovieLabel;
//import com.example.backend_sem2.service.MovieLabelService;
//import lombok.AllArgsConstructor;
//import org.springframework.web.bind.annotation.*;
//
//import java.util.List;
//
//@RestController
//@RequestMapping("/movie-labels")
//@AllArgsConstructor
//public class MovieLabelController {
//    private MovieLabelService movieLabelService;
//
//    @GetMapping(value = {"", "/"})
//    public List<MovieLabel> getAllMovieLabel(){
//        return movieLabelService.getAllMovieLabel();
//    }
//
//    @PostMapping
//    public MovieLabel saveMovieLabel (@RequestBody MovieLabel movieLabel)
//    {
//        return movieLabelService.saveMovieLabel(movieLabel);
//    }
//
//    @PutMapping ("/{id}")
//    public MovieLabel updateMovieLabel (@RequestBody MovieLabel movieLabel, @PathVariable Long id)
//    {
//        return movieLabelService.updateMovieLabel(movieLabel, id);
//    }
//
//    @DeleteMapping("/{id}")
//    public String deleteMovieLabel(@PathVariable Long id)
//    {
//        if(movieLabelService.deleteMovieLabelById(id)){
//            return "Delete Successful!";
//        }
//        return "Delete Fail!";
//    }
//}
