package com.example.backend_sem2.config;

import io.netty.channel.ChannelOption;
import io.netty.handler.timeout.ReadTimeoutHandler;
import io.netty.handler.timeout.WriteTimeoutHandler;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import reactor.netty.http.client.HttpClient;

import java.time.Duration;
import java.util.Collections;
import java.util.concurrent.TimeUnit;

@Configuration
//@EnableWebMvc
public class AppConfig implements WebMvcConfigurer {
    @Value("${frontend.endpoint}")
    private String frontendUrl;
    @Value("${connect_timeout}")
    private Long connectTimeout;
    @Value("${response_timeout}")
    private Long responseTimeout;
    @Value("${read_timeout}")
    private Long readTimeout;
    @Value("${write_timeout}")
    private Long writeTimeout;
    @Value("${base_url}")
    private String baseUrl;
    @Value("${x_rapid.api.key}")
    private String xRapidKey;
    @Value("${x_rapid.api.host}")
    private String xRapidHost;


    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins(frontendUrl)
                .allowedMethods("GET", "POST", "PUT", "DELETE");
    }

    @Bean
    public WebClient webClient(){
        HttpClient httpClient = HttpClient.create()
                .option(ChannelOption.CONNECT_TIMEOUT_MILLIS, 5000)
                .responseTimeout(Duration.ofMillis(5000))
                .doOnConnected(conn ->
                        conn.addHandlerLast(new ReadTimeoutHandler(5000, TimeUnit.MILLISECONDS))
                                .addHandlerLast(new WriteTimeoutHandler(5000, TimeUnit.MILLISECONDS)));

        WebClient webClient = WebClient.builder()
                .baseUrl(baseUrl)
                .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .defaultHeader("X-RapidAPI-Key", xRapidKey)
                .defaultHeader("X-RapidAPI-Host", xRapidHost)
                .defaultUriVariables(Collections.singletonMap("url", baseUrl))
                .clientConnector(new ReactorClientHttpConnector(httpClient))
                .build();

        return webClient;
    }
}
