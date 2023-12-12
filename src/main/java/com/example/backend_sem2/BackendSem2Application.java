package com.example.backend_sem2;

import com.example.backend_sem2.dto.CommentRequest;
import com.example.backend_sem2.entity.*;
import com.example.backend_sem2.mapper.CommentMapper;
import com.example.backend_sem2.repository.*;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@SpringBootApplication
@AllArgsConstructor
public class BackendSem2Application {
    private MovieRepo movieRepo;
    private CategoryRepo categoryRepo;
    private CommentRepo commentRepo;
    private SlotRepo slotRepo;
    private TheaterRoomRepo theaterRoomRepo;
    private SeatRepo seatRepo;
    private SeatClassRepo seatClassRepo;
    private OrderRepo orderRepo;
    private OrderDetailRepo orderDetailRepo;
    private CommentMapper commentMapper;

    public static void main(String[] args) {
        SpringApplication.run(BackendSem2Application.class, args);
    }

    @Bean
    public CommandLineRunner commandLineRunner() {
        return runner -> {
//            testCommentMapper();
//            testMovieLabelEnumInMovie();
//            if(!slotRepo.existsById(1L)){
//                generateData();
//            }
        };
    }

    public void generateData() {
        Random random = new Random();
/*  Generate Category   */
        List<Category> categories = new ArrayList<>();
        for (int i = 1; i <= 10; i++) {
            categories.add(Category.builder().categoryName("category +" + i).build());
        }

/*  Generate Movie   */
        List<Movie> movies = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            MovieLabelEnum[] movieLabelEnums = MovieLabelEnum.values();

            movies.add(Movie.builder()
                    .movieName("movieName " + (i + 1))
                    .director("director " + (i + 1))
                    .movieLabel(movieLabelEnums[random.nextInt(movieLabelEnums.length)])
                    .categoryList(List.of(
                            categories.get(random.nextInt(categories.size()))
                    ))
                    .build()
            );
        }

/*  Generate Comment   */
        List<Comment> comments = new ArrayList<>();
        for (int i = 1; i <= 100; i++) {

            comments.add(Comment.builder()
                    .commentUsername("user " + i)
                    .starRate(random.nextLong(5) + 1L)
                    .commentContent("comment content " + i)
                    .movie(movies.get(random.nextInt(movies.size())))
                    .build()
            );
        }
/*  Generate Theater Room   */


/*  Generate Slot   */
        ZonedDateTime currentDateTime = ZonedDateTime.now();
        List<Slot> slots = new ArrayList<>();
        for (int i = 0; i < 50; i++) {
            int randomDays = random.nextInt(11) - 5;
            Movie movie;
            if(i < 5) movie = movies.get(0);
            else {
                movie = movies.get(random.nextInt(movies.size()));
            }
            slots.add(Slot.builder()
                    .startTime(currentDateTime.plus(randomDays, ChronoUnit.DAYS))
                    .movie(movie)
                    .build()
            );
            commentRepo.saveAll(comments);

        }
    }

    private void testMovieLabelEnumInMovie() {
        createAnMovie();
        Movie movie = movieRepo.findById(1L).orElse(null);
        System.out.println(movie);
    }

    private void createAnMovie() {
        if (!movieRepo.existsById(1L)) {
            Movie movie = Movie.builder()
                    .id(1L)
                    .movieName("test 1")
                    .movieLabel(MovieLabelEnum.C16)
                    .build();
            movieRepo.save(movie);
        }
    }

    public void testCommentMapper() {
        createAnMovie();
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
