package com.example.backend_sem2;

import com.example.backend_sem2.api.theMovieDBApi.HttpService;
import com.example.backend_sem2.entity.*;
import com.example.backend_sem2.mapper.MovieMapper;
import com.example.backend_sem2.mapper.MovieMapper2;
import com.example.backend_sem2.model.theMovieDB.ConfigurationTheMovieDB;
import com.example.backend_sem2.model.theMovieDB.GenreResponse;
import com.example.backend_sem2.model.theMovieDB.MovieInApi;
import com.example.backend_sem2.model.theMovieDB.TrendingMovieResponse;
import com.example.backend_sem2.repository.CommentRepo;
import com.example.backend_sem2.repository.SlotRepo;
import lombok.AllArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
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

    private final String image1 = "https://m.media-amazon.com/images/M/MV5BN2IzYzBiOTQtNGZmMi00NDI5LTgxMzMtN2EzZjA1NjhlOGMxXkEyXkFqcGdeQXVyNjAwNDUxODI@._V1_.jpg";
    private final String image2 = "https://m.media-amazon.com/images/M/MV5BMjk2NjgzMTEtYWViZS00NTMyLWFjMzctODczYmQzNzk2NjIwXkEyXkFqcGdeQXVyMTEyMjM2NDc2._V1_.jpg";

    private final String iframe1 = "https://www.youtube.com/embed/bjqEWgDVPe0?si=EkeFabnnr4-yWO46";
    private final String iframe2 = "https://www.youtube.com/embed/AlhHGUfCYw4?si=omo6TcypeCzNzPGu";
    private final String iframe3 = "https://www.youtube.com/embed/nblUgAMoOvU?si=rzo5bGWxf3zKdMUJ";

    private final long rows = 12;
    private final long columns = 12;

    public static void main(String[] args) {
        SpringApplication.run(BackendSem2Application.class, args);
    }

    @Bean
    public CommandLineRunner commandLineRunner() {
        return runner -> {
            testMovieWithIdRating();
//            String ImdbId = getIMDBIdFromTheMovieDBId(343611L);
//            System.out.println("ImdbId = " + ImdbId);

            System.out.println("Youtube Link: " + ("https://www.youtube.com/watch?v=" + httpService.getYoutubeIdForMovieTrailerByIMDBId("tt4154796")));
            Long start = System.currentTimeMillis();
            if (!slotRepo.existsById(1L)) {
                /*  this method does not generate all generated Object in method    */
                generateData(4L);
            }
            Long end = System.currentTimeMillis();
            System.out.println("Running time: " + (end - start));
        };
    }

    private String getIMDBIdFromTheMovieDBId(Long theMovieDBId) {
        String endpoint = "/movie/" + theMovieDBId;
        return httpService.getImdbIdByTheMovieDBId(theMovieDBId);
    }

    private void testMovieWithIdRating() {
        System.out.println("httpService.getMovieWithRatingUsingTheMovieDBId(695721) = "
                + httpService.getMovieWithRatingUsingTheMovieDBId(695721L));
    }

    private ConfigurationTheMovieDB getConfigurationTheMovieDB() {
        ConfigurationTheMovieDB configurationTheMovieDB = httpService.getResponseEntity(theMovieDbBaseUrl,"/configuration",
                ConfigurationTheMovieDB.class, new HashMap<>());
        return configurationTheMovieDB;
    }

    @NotNull
    private List<Category> getCategoriesFromGenre() {
        GenreResponse genreResponse = httpService.getResponseEntity(theMovieDbBaseUrl,"/genre/movie/list",
                GenreResponse.class, new HashMap<>());
        genreResponse.getGenres().forEach(System.out::println);

        List<Category> categoryListFromGenre = genreResponse.getGenres().stream()
                .map(genre -> (Category) Category.builder().genreId(genre.getId()).categoryName(genre.getName()).build())
                .toList();

        categoryListFromGenre.forEach(System.out::println);
        return categoryListFromGenre;
    }

    @NotNull
    private List<MovieInApi> getMovieInApiList(Long numberOfPages) {
        List<MovieInApi> movieInApiList = new ArrayList<>();
        Set<MovieInApi> movieInApiSet = new HashSet<>();

        for (int i = 0; i < numberOfPages; i++) {
            TrendingMovieResponse response = httpService.getResponseEntity(theMovieDbBaseUrl,"/trending/movie/day",
                    TrendingMovieResponse.class, Map.ofEntries(
                            Map.entry("page", Integer.toString(i + 1))
                    )
            );
            movieInApiList.addAll(response.getMovieInApiList());
            movieInApiSet.addAll(response.getMovieInApiList());
        }
//        Set<String>

        System.out.println("*** movieInApiList.size():" + movieInApiList.size());
        for (int i = 0; i < movieInApiList.size(); i++) {
            MovieInApi movieInApi = movieInApiList.get(i);
            System.out.println((i + 1) + " " + movieInApi.getId() + " " + movieInApi.getOriginalTitle());
        }
        System.out.println("*** movieInApiSet.size():" + movieInApiSet.size());
//        Iterator<MovieInApi> movieInApiIterator = movieInApiSet.iterator();
//        int i = 1;
//        while (movieInApiIterator.hasNext()) {
//            if(movieInApiIterator.next() == null) i++;
//            System.out.println((i++) + " " + movieInApiIterator.next().getId() + " " + movieInApiIterator.next().getOriginalTitle());
//        }
        System.out.println("*** End");
        return movieInApiList;
    }

    public void generateData(Long numberOfPage) {
        Random random = new Random();
        /*  Try to get Category and Movie from TheMovieDB     */
        List<MovieInApi> movieInApiList = getMovieInApiList(numberOfPage);
        List<Category> categories = getCategoriesFromGenre();
        ConfigurationTheMovieDB configurationTheMovieDB = getConfigurationTheMovieDB();

        List<String> iframeList = List.of(iframe1, iframe2, iframe3);

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
                    movie.setIframe(httpService.getYoutubeIdForMovieTrailerByIMDBId(movie.getImdbId()));
//                    movie.setIframe(iframeList.get(random.nextInt(3)));
                    return movie;
                }).filter(movie -> movie.getIframe() != null)
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
