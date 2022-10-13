package com.thiagosol.moviesbattle.entrypoint.dto;

import com.thiagosol.moviesbattle.core.domain.Battle;
import com.thiagosol.moviesbattle.core.domain.Movie;
import com.thiagosol.moviesbattle.core.domain.enums.BattleStatus;

public record BattleResponseDTO(BattleStatus status, MovieDTO movieA, MovieDTO movieB) {

    public BattleResponseDTO(Battle battle){
        this(battle.getStatus(), new MovieDTO(battle.getMovieA()), new MovieDTO(battle.getMovieB()));
    }
    public record MovieDTO(String imdb, String title, Long releaseYear) {
        public MovieDTO(Movie movie) {
            this(movie.getImdb(), movie.getTitle(), movie.getReleaseYear());
        }
    }

}
