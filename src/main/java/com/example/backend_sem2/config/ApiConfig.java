package com.example.backend_sem2.config;

import okhttp3.OkHttpClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Properties;
import java.util.concurrent.TimeUnit;

@Configuration
public class ApiConfig {

    @Bean
    public OkHttpClient okHttpClient(){
        return new OkHttpClient.Builder()
                .readTimeout(2000, TimeUnit.MILLISECONDS)
                .build();
    }

    @Bean
    public String theMovieDBBaseUrl(){
        return "https://api.themoviedb.org/3";
//        return properties.getProperty("the_movieDB_api.base_url");
    }
}
