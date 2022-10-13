package com.thiagosol.moviesbattle.core.exception;

public class GameInProgressNotFoundException extends RuntimeException{

    private final static String message = "Game in progress not found, create a new one in games/start";

    public GameInProgressNotFoundException() {
        super(message);
    }
}
