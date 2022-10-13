package com.thiagosol.moviesbattle.core.exception;

public class GameInProgressException extends RuntimeException{

    private final static String message = "There is already a game in progress, list and continue the same in battle/current";

    public GameInProgressException() {
        super(message);
    }
}
