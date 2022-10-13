package com.thiagosol.moviesbattle.core.exception;

public class BattleNotFoundException extends RuntimeException{

    private final static String message = "There are no battles, create a new game in games/start";

    public BattleNotFoundException() {
        super(message);
    }
}
