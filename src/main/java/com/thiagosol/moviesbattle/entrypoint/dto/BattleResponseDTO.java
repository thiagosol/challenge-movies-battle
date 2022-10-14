package com.thiagosol.moviesbattle.entrypoint.dto;

import com.thiagosol.moviesbattle.core.domain.Battle;
import com.thiagosol.moviesbattle.core.domain.Movie;
import com.thiagosol.moviesbattle.core.domain.enums.BattleStatus;
import io.swagger.v3.oas.annotations.media.Schema;

public record BattleResponseDTO(@Schema(description = "Battle Status", example = "WON")
                                BattleStatus status,

                                @Schema(description = "First battle movie")
                                MovieDTO movieA,

                                @Schema(description = "Second battle movie")
                                MovieDTO movieB) {

    public BattleResponseDTO(Battle battle){
        this(battle.getStatus(), new MovieDTO(battle.getMovieA()), new MovieDTO(battle.getMovieB()));
    }
    public record MovieDTO(@Schema(description = "IMDb is the world's most popular and authoritative source for movie", example = "tt0325980")
                           String imdb,

                           @Schema(description = "Movie title", example = "Pirates of the Caribbean: The Curse of the Black Pearl")
                           String title,

                           @Schema(description = "Movie release year", example = "2003")
                           Long releaseYear) {
        public MovieDTO(Movie movie) {
            this(movie.getImdb(), movie.getTitle(), movie.getReleaseYear());
        }
    }

}
