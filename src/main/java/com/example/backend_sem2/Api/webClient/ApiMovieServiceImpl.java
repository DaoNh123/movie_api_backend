package com.example.backend_sem2.Api.webClient;

import com.example.backend_sem2.Utility.ApiUtility;
import com.example.backend_sem2.entity.Category;
import com.example.backend_sem2.entity.Movie;
import com.example.backend_sem2.mapper.MovieMapper;
import com.example.backend_sem2.model.rapidApi.MovieOverviewDetailIMDB;
import com.example.backend_sem2.repository.CategoryRepo;
import com.example.backend_sem2.repository.MovieRepo;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.util.UriComponentsBuilder;
import reactor.core.publisher.Mono;

import java.net.URI;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class ApiMovieServiceImpl implements ApiMovieService {
    private WebClient webClient;
    private CategoryRepo categoryRepo;
    private MovieRepo movieRepo;
    private MovieMapper movieMapper;
    private final String mostPopularMovieURI = "title/get-most-popular-movies";
    private final String getOverviewDetailURI = "title/get-overview-details";
    @Value("${base_url}")
    private String baseUrl;

    @Autowired
    public ApiMovieServiceImpl(WebClient webClient, CategoryRepo categoryRepo, MovieRepo movieRepo, MovieMapper movieMapper) {
        this.webClient = webClient;
        this.categoryRepo = categoryRepo;
        this.movieRepo = movieRepo;
        this.movieMapper = movieMapper;
    }

    @Transactional
    public void saveMovieFromApi(List<String> movieCodeList) {
        Long start = System.currentTimeMillis();
        String firstIMDBCode = movieCodeList.get(0);
        System.out.println("firstIMDBCode = " + firstIMDBCode);
        if(!movieRepo.existsByImdbId(firstIMDBCode)){
            Set<Category> categoriesInDatabase = categoryRepo.getAllCategorySet();
            Map<String, Category> categoryMap = categoriesInDatabase.stream()
                    .collect(Collectors.toMap(Category::getCategoryName, category -> category));
            int i = 1;
            List<Movie> moviesFromApi = movieCodeList.stream()
                    .map(code -> getDataIsNotList(getOverviewDetailURI, MovieOverviewDetailIMDB.class, Map.ofEntries(
                            Map.entry("tconst", code)
                    ))).map(movieOverviewDetailIMDB -> {
                        if(movieOverviewDetailIMDB == null) return null;
                        Movie movie = movieOverviewDetailIMDB.toMovieEntity();
                        movie.setCategoryList(movieOverviewDetailIMDB.getCategoryOfMovieEntity(categoryMap));

                        System.out.println("*** " + movieMapper.toMovieInOrderRes(movie));
                        return movie;
                    }).toList();

//            moviesFromApi.forEach(movie -> System.out.println(movieMapper.toMovieInOrderRes(movie)));
            List<Movie> nonNullMovieList = moviesFromApi.stream().filter(Objects::nonNull).toList();
            movieRepo.saveAll(nonNullMovieList);
            System.out.println("moviesFromApi.size() = " + moviesFromApi.size());
            System.out.println("nonNullMovieList.size() = " + nonNullMovieList.size());
            Long end = System.currentTimeMillis();
            System.out.println("(end - start) = " + (end - start));
        }
    }

    public <T> T getDataIsNotList(String uri, Class<T> type, Map<String, String> queryParamMap) {
        UriComponentsBuilder uriBuilder = UriComponentsBuilder.fromUriString(baseUrl)
                .path(uri);

        for (Map.Entry<String, String> entry : queryParamMap.entrySet()) {
            uriBuilder.queryParam(entry.getKey(), entry.getValue());
        }

        URI customUri = uriBuilder.build().toUri();

        return webClient.get()
                .uri(customUri)
                .retrieve()
                .bodyToMono(type)
                .onErrorResume(e -> Mono.empty())
                .block();
    }
    /*  Other method without using "basePath" because it get basePath from "webClient"*/

//    public <T> T getDataIsNotList(String uri, Class<T> type, Map<String, String> queryParamMap) {
//        return webClient.get()
//                .uri(uriBuilder -> uriBuilder
//                        .path("title/get-overview-details")
//                        .queryParam("tconst", "tt4729430")
//                        .build())
//                .retrieve()
//                .bodyToMono(type)
//                .onErrorResume(e -> Mono.empty())
//                .block();
//    }

//    public <T> T getDataIsNotList(String uri, Class<T> type, Map<String, String> queryParamMap) {
//        return webClient.get()
//                .uri(uriBuilder -> uriBuilder
//                        .path("title/find")
//                        .queryParam("q", "game of thr")
//                        .build())
//                .retrieve()
//                .bodyToMono(type)
//                .onErrorResume(e -> Mono.empty())
//                .block();
//    }

    /*  End of other method */

    public <T> List<T> getDataIsList(String uri, Map<String, String> queryParamMap) {
        UriComponentsBuilder uriBuilder = UriComponentsBuilder.fromUriString(baseUrl)
                .path(uri);

        for (Map.Entry<String, String> entry : queryParamMap.entrySet()) {
            uriBuilder.queryParam(entry.getKey(), entry.getValue());
        }

        URI customUri = uriBuilder.build().toUri();

        List result = webClient.get()
                .uri(customUri)
                .retrieve()
                .bodyToMono(List.class)
                .onErrorResume(e -> Mono.empty())
                .block();

        return result;
    }

    public List<String> getMostPopularMovieListCodeInIMDB() {
        List<String> data = getDataIsList("title/get-most-popular-movies", new HashMap<>());
        System.out.println(data);
        return data.stream().map(endpoint -> {
            return ApiUtility.getIdFromEndpointString(endpoint);
        }).toList();
    }
}
