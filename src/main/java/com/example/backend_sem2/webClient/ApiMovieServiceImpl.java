package com.example.backend_sem2.webClient;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.springframework.web.util.UriBuilder;
import org.springframework.web.util.UriComponentsBuilder;
import reactor.core.publisher.Mono;

import java.net.URI;
import java.util.List;
import java.util.Map;

@Service
@NoArgsConstructor
public class ApiMovieServiceImpl implements ApiMovieService{
    private WebClient webClient;
    private final String mostPopularMovieURI = "title/get-most-popular-movies";
    @Value("${base_url}")
    private String baseUrl;

    @Autowired
    public ApiMovieServiceImpl(WebClient webClient) {
        this.webClient = webClient;
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

    public <T> List<T> getDataIsList(String uri) {
        List result = webClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path(uri)
                        .queryParam("q", "game of thr")
                        .build())
                .retrieve()
                .bodyToMono(List.class)
                .onErrorResume(e -> Mono.empty())
                .block();

        return result;
    }

    public List<String> getMostPopularMovieListCodeInIMDB (){
        List<String> data = getDataIsList("title/get-most-popular-movies");
        return data.stream().map(endpoint -> {
            List<String> split = List.of(endpoint.split("/"));
            return split.get(split.size() - 1);
        }).toList();
    }
}
