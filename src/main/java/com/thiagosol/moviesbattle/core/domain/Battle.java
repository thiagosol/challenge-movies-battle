package com.thiagosol.moviesbattle.core.domain;

import com.thiagosol.moviesbattle.core.domain.enums.BattleStatus;

import java.util.List;

public class Battle {

    private Long id;
    private Game game;

    private Movie movieA;

    private Movie movieB;

    private BattleStatus status;

    public Battle(Game game, Movie movieA, Movie movieB) {
        this.game = game;
        this.movieA = movieA;
        this.movieB = movieB;
        this.status = BattleStatus.WAITING;
    }

    public Battle(Long id, Game game, Movie movieA, Movie movieB, BattleStatus status) {
        this.id = id;
        this.game = game;
        this.movieA = movieA;
        this.movieB = movieB;
        this.status = status;
    }

    public Long getId() {
        return id;
    }

    public Game getGame() {
        return game;
    }

    public Movie getMovieA() {
        return movieA;
    }

    public Movie getMovieB() {
        return movieB;
    }

    public BattleStatus getStatus() {
        return status;
    }

    public String getBattleIdentifier(){
        var listMovie = List.of(movieA, movieB);
        return listMovie.stream()
                .map(Movie::getImdb)
                .sorted()
                .reduce("", (acc, imdb) -> acc + (acc.equals("") ? "" : "-") + imdb);
    }

    public void play(String playImdbId, Movie betterMovie) {
        if(playImdbId.equals(betterMovie.getImdb())){
            this.status = BattleStatus.WON;
        } else {
            this.status = BattleStatus.LOST;
        }
    }
}
