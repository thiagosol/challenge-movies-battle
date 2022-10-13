package com.thiagosol.moviesbattle.core.exception;

public class CreateBattleErrorException extends RuntimeException{

    private final static String message = "Could not create battle try again later";

    public CreateBattleErrorException() {
        super(message);
    }
}
