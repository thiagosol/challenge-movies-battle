package com.thiagosol.moviesbattle.dataprovider.interceptor;

import feign.RequestInterceptor;
import feign.RequestTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class ClientRequestInterceptor implements RequestInterceptor {

    private final String apiMovieUrl;
    private final String apiMovieKey;
    private final String apiMovieHost;

    public ClientRequestInterceptor(@Value("${api.movie.url}") String apiMovieUrl,
                                    @Value("${api.movie.key}") String apiMovieKey,
                                    @Value("${api.movie.host}") String apiMovieHost) {
        this.apiMovieUrl = apiMovieUrl;
        this.apiMovieKey = apiMovieKey;
        this.apiMovieHost = apiMovieHost;
    }

    @Override
    public void apply(RequestTemplate requestTemplate) {
        if(requestTemplate.feignTarget().url().equals(apiMovieUrl)){
            requestTemplate.header("x-rapidapi-host",apiMovieHost);
            requestTemplate.header("x-rapidapi-key", apiMovieKey);
        }
    }
}
