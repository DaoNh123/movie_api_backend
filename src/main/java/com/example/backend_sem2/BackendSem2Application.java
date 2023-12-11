package com.example.backend_sem2;

import com.example.backend_sem2.dto.CommentRequest;
import com.example.backend_sem2.entity.Movie;
import com.example.backend_sem2.mapper.CommentMapper;
import com.example.backend_sem2.repository.MovieRepo;
import lombok.AllArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
@AllArgsConstructor
public class BackendSem2Application {
    private MovieRepo movieRepo;
    private CommentMapper commentMapper;
    public static void main(String[] args) {
        SpringApplication.run(BackendSem2Application.class, args);
    }
    @Bean
    public CommandLineRunner commandLineRunner() {
        return runner -> {
            testCommentMapper();

        };
    }
    public void testCommentMapper(){
        if (!movieRepo.existsById(1L)) {
            Movie movie = Movie.builder()
                    .id(1L)
                    .movieName("test 1")
                    .build();
            movieRepo.save(movie);
        }
        CommentRequest commentRequest = CommentRequest.builder()
                .commentUsername("Dao")
                .commentContent("good!")
                .starRate(4L)
                .movieId(1L)
                .build();
        System.out.println("*** Test Comment Mapper ***");
        System.out.println(commentMapper.toEntity(commentRequest));


    }
}
