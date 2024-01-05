package com.example.backend_sem2;

import com.example.backend_sem2.api.HttpService;
import com.example.backend_sem2.api.KinoCheckApiService;
import com.example.backend_sem2.api.TheMovieDBApiService;
import com.example.backend_sem2.entity.*;
import com.example.backend_sem2.enums.MovieBookingStatusEnum;
import com.example.backend_sem2.enums.MovieShowingStatusEnum;
import com.example.backend_sem2.mapper.MovieMapper;
import com.example.backend_sem2.mapper.MovieMapper2;
import com.example.backend_sem2.model.theMovieDB.ConfigurationTheMovieDB;
import com.example.backend_sem2.model.theMovieDB.GenreResponse;
import com.example.backend_sem2.model.theMovieDB.MovieInApi;
import com.example.backend_sem2.repository.CommentRepo;
import com.example.backend_sem2.repository.MovieRepo;
import com.example.backend_sem2.repository.SlotRepo;
import lombok.AllArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.time.*;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.concurrent.ThreadLocalRandom;

@SpringBootApplication
@AllArgsConstructor
//@RequiredArgsConstructor
public class BackendSem2Application {

    private CommentRepo commentRepo;
    private SlotRepo slotRepo;
    private MovieMapper movieMapper;
    private HttpService httpService;
    private MovieMapper2 movieMapper2;
    @Qualifier("theMovieDBBaseUrl")
    private String theMovieDbBaseUrl;
    private MovieRepo movieRepo;
    private TheMovieDBApiService theMovieDBApiService;
    private KinoCheckApiService kinoCheckApiService;
    private final long rows = 12;
    private final long columns = 12;

    public static void main(String[] args) {
        SpringApplication.run(BackendSem2Application.class, args);
    }

    @Bean
    public CommandLineRunner commandLineRunner() {
        return runner -> {

            Long start = System.currentTimeMillis();
            if (!slotRepo.existsById(1L)) {
                /*  this method does not generate all generated Object in method    */
                generateData(4L);
            }
            Long end = System.currentTimeMillis();
            System.out.println("Running time: " + (end - start));
        };
    }

    private void testKinoCheck() {
        System.out.println(kinoCheckApiService.getYoutubeIdForMovieTrailerByIMDBId("tt15398776"));;
    }

    private void testUpdateStatusMovie() {
        ZoneId zoneId = ZoneId.of("UTC");
        ZonedDateTime startOfToday = LocalDate.now().atStartOfDay().atZone(zoneId);
        ZonedDateTime threeDaysAfterToday = LocalDate.now().atStartOfDay().atZone(zoneId);

        /*  If today is between "movie.openingDay" - 3 days and "movie.closingDay" ==> ALLOWED */
        movieRepo.updateAllowedBookingStatus(startOfToday, threeDaysAfterToday, MovieBookingStatusEnum.ALLOWED);
        /*  If today is after "movie.closingDay" ==> NOT_ALLOWED    */
        movieRepo.updateNotAllowedBookingStatus(startOfToday, MovieBookingStatusEnum.NOT_ALLOWED);

        /*  If today is between "movie.openingTime" and "movie.closingTime" ==> NOW_SHOWING */
        movieRepo.updateNowShowingStatus(startOfToday, MovieShowingStatusEnum.NOW_SHOWING);
        /*  If today is after "movie.closingTime" ==> ENDED */
        movieRepo.updateEndedShowingStatus(startOfToday, MovieShowingStatusEnum.ENDED);
    }

    @NotNull
    private List<Category> getCategoriesFromGenre() {
        GenreResponse genreResponse = theMovieDBApiService.getGenreOfMovieByTheMovieDB();
        genreResponse.getGenres().forEach(System.out::println);

        List<Category> categoryListFromGenre = genreResponse.getGenres().stream()
                .map(genre -> (Category) Category.builder().genreId(genre.getId()).categoryName(genre.getName()).build())
                .toList();

        categoryListFromGenre.forEach(System.out::println);
        return categoryListFromGenre;
    }
    

    public void generateData(Long numberOfPage) {
        Random random = new Random();
        /*  Try to get Category and Movie from TheMovieDB     */
        List<MovieInApi> movieInApiList = theMovieDBApiService.getTrendingMovieInTheMovieDBApiWithNPage(numberOfPage);
        List<Category> categories = getCategoriesFromGenre();
        ConfigurationTheMovieDB configurationTheMovieDB = theMovieDBApiService.getConfigurationInTheMovieDB();

        List<Movie> movies = movieInApiList.stream()
//                .map(movieInApi -> movieInApi.toMovieEntity(categories, configurationTheMovieDB))
                .map(movieInApi -> movieMapper.toEntity(movieInApi, configurationTheMovieDB, categories))
                /*  add some data for "Movie" entity    */
                .map(movie -> {
                    ZonedDateTime openingTime = getRandomZonedDateTime(7);
                    movie.setDuration(60L + random.nextInt(30));
                    movie.setOpeningTime(openingTime);
                    movie.setClosingTime(openingTime.plusDays(random.nextInt(10) + 20));

                    System.out.println("*** IMDB_ID: " + movie.getImdbId());
                    movie.setYoutubeVideoId(kinoCheckApiService.getYoutubeIdForMovieTrailerByIMDBId(movie.getImdbId()));
//                    movie.setYoutubeVideoId(youtubeVideoIdList.get(random.nextInt(3)));
                    return movie;
                }).filter(movie -> movie.getYoutubeVideoId() != null)
                .toList();
        System.out.println("*** Start checking Movie ***");
        movies.forEach(movie -> {
            System.out.println("*** " + movieMapper.toMovieResponseInPage(movie));
        });

        /*  Generate Comment   */
        List<Comment> comments = new ArrayList<>();
        for (int i = 0; i < movies.size(); i++) {
            int numberOfComment = random.nextInt(5) + 3;
            for (int j = 0; j < numberOfComment; j++) {
                comments.add(Comment.builder()
                        .commentUsername("user " + i)
                        .starRate(random.nextLong(5) + 1L)
                        .commentContent("comment content " + i)
                        .movie(movies.get(i))
                        .build()
                );
            }
        }

        /*  Generate Theater Room   */
        List<TheaterRoom> theaterRooms = new ArrayList<>();
        for (int i = 1; i <= 5; i++) {
            theaterRooms.add(TheaterRoom.builder()
                    .theaterRoomName("A00" + i)
                    .build()
            );
        }

        /*  Generate Slot   */
        ZonedDateTime currentDateTime = ZonedDateTime.now();
        List<Slot> slots = new ArrayList<>();
        for (int i = 0; i < movies.size(); i++) {
            int numberOfSlot = random.nextInt(3) + 2;
            Movie movie = movies.get(i);
            for (int j = 0; j < numberOfSlot; j++) {
                int randomDays = random.nextInt(11) - 5;
                slots.add(Slot.builder()
                        .startTime(currentDateTime.plus(randomDays, ChronoUnit.DAYS))
                        .movie(movie)
                        .theaterRoom(theaterRooms.get(random.nextInt(theaterRooms.size())))
                        .build());
            }
        }

        /*  Generate Seat Class */
        List<SeatClass> seatClasses = List.of(
                new SeatClass("VIP", 200_000D),
                new SeatClass("NOR", 160_000D)
        );


        /*  Generate Seat   */
        List<Seat> seats = new ArrayList<>();
        for (int i = 0; i < theaterRooms.size(); i++) {
            /*  j represent for "row" and k represent for "column"  */
            for (Character j = 'A'; j < ('A' + rows); j++) {
                for (int k = 1; k <= columns; k++) {
                    SeatClass seatClass;
                    if ((j <= 'A' + 1 || j >= 'A' + rows - 2) || (k <= 2 || k >= columns - 1)) {
                        seatClass = seatClasses.get(1);
                    } else {
                        seatClass = seatClasses.get(0);
                    }

                    seats.add(Seat.builder()
                            .seatName(Character.toString(j) + k)
                            .theaterRoom(theaterRooms.get(i))
                            .seatClass(seatClass)
                            .build()
                    );
                }
            }
        }

        /*  Generate Order and OrderDetail  */
        List<Order> orders = new ArrayList<>();
        for (int i = 0; i < 500; i++) {
            List<OrderDetail> orderDetailsInOrder = List.of(
                    OrderDetail.builder().seat(seats.get(random.nextInt(seats.size()))).build(),
                    OrderDetail.builder().seat(seats.get(random.nextInt(seats.size()))).build()
            );

            orders.add(Order.builder()
                    .customerName("Customer " + i)
                    .customerAddress("Address of customer " + i)
                    .customerAge(random.nextLong(50) + 18)
                    .orderDetailList(orderDetailsInOrder)
                    .slot(slots.get(random.nextInt(slots.size())))
                    .build()
            );
        }
        commentRepo.saveAll(comments);
    }

    private static ZonedDateTime getRandomZonedDateTime(Integer dayRange) {
        ZoneId zoneId = ZoneId.systemDefault();
        LocalDate today = LocalDate.now();

        ZonedDateTime zdtStart = today.minusDays(dayRange).atStartOfDay(zoneId)
                .with(LocalTime.of(16, 0));

        ZonedDateTime zdtEnd = today.plusDays(dayRange)
                .atStartOfDay(zoneId)
                .with(LocalTime.of(2, 0));

        ZonedDateTime zdtResult =
                Instant.ofEpochMilli(
                        ThreadLocalRandom
                                .current()
                                .nextLong(
                                        zdtStart.toInstant().toEpochMilli(),
                                        zdtEnd.toInstant().toEpochMilli()
                                ) / (1000 * 600) * (1000 * 600)            // make time is multiple of 10 minutes
                ).atZone(zoneId);
        return zdtResult;
    }
}
