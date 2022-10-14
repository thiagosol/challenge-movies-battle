package com.thiagosol.moviesbattle.mocks;

import com.github.tomakehurst.wiremock.WireMockServer;
import org.springframework.http.HttpStatus;

import java.io.IOException;
import java.util.HashMap;

public class MovieMocks {

    private static final String URL_MOVIES = "/moviedatabase/titles\\?info=mini_info&list=most_pop_movies&titleType=movie&page=(.*)&limit=(.*)";
    private static final String URL_RATING = "/moviedatabase/titles/x/titles-by-ids\\?info=rating&idsList%5B0%5D=(.*)&idsList%5B1%5D=(.*)";

    public static void setupMockMoviesSuccessResponse(WireMockServer mockService) throws IOException {
        SetupMocks.configGetMock(mockService, URL_MOVIES, HttpStatus.OK, "payloads/get-movies-response.json");
    }

    public static void setupMockMoviesEmptyResponse(WireMockServer mockService) throws IOException {
        SetupMocks.configGetMock(mockService, URL_MOVIES, HttpStatus.OK, "payloads/get-movies-empty-response.json");
    }

    public static void setupMockRatingSuccessResponse(WireMockServer mockService, HashMap<String, String> mapReplace) throws IOException {
        SetupMocks.configGetMockWithReplaceResponse(mockService, URL_RATING, HttpStatus.OK, "payloads/get-rating-response.json", mapReplace);
    }

}