package com.thiagosol.moviesbattle.dataprovider.exception;

public class PlayerNotFoundException extends RuntimeException{

    private final static String message = "Player not found";

    public PlayerNotFoundException() {
        super(message);
    }
}
