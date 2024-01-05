package com.example.backend_sem2.api;

import com.example.backend_sem2.model.kinoCheckApi.TrailerContainer;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class KinoCheckApiServiceImpl implements KinoCheckApiService {
    private static final String theKinocheckApiBaseUrl = "https://api.kinocheck.de";      // Using to get YoutubeId for "trailer" of Movie
    private static final String prefixYoutubeVideoIdYoutube = "https://www.youtube.com/embed/";
    private HttpService httpService;

    public KinoCheckApiServiceImpl(HttpService httpService) {
        this.httpService = httpService;
    }

        /*  Example for "kinocheck" API link: https://api.kinocheck.de/movies?imdb_id=tt4154796 */
    public String getYoutubeIdForMovieTrailerByIMDBId(String theIMDBId){
        TrailerContainer trailerContainer = httpService.getResponseEntity(theKinocheckApiBaseUrl, "/movies",
                TrailerContainer.class,
                Map.ofEntries(
                      Map.entry("imdb_id", theIMDBId)
                ), "");
        return trailerContainer.getYoutubeVideoId();
    }


}
