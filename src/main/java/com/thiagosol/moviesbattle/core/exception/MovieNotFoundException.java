package com.thiagosol.moviesbattle.core.exception;

public class MovieNotFoundException extends RuntimeException{

    private final static String message = "Movie imdb not found in active battle";

    public MovieNotFoundException() {
        super(message);
    }
}
