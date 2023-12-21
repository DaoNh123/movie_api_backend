package com.example.backend_sem2.webClient;

import java.util.List;
import java.util.Map;

public interface ApiMovieService {
    public <T> T getDataIsNotList(String Uri, Class<T> type, Map<String, String> queryParamMap);
    public <T> List<T> getDataIsList(String uri);
    public List<String> getMostPopularMovieListCodeInIMDB ();
}
