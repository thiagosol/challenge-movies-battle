package com.thiagosol.moviesbattle.entrypoint.dto;

import com.thiagosol.moviesbattle.core.domain.Player;
import io.swagger.v3.oas.annotations.media.Schema;

public record PlayerLoginRequestDTO(@Schema(description = "Player username", example = "player1")
                                    String login,

                                    @Schema(description = "Password for login", example = "A14%as!")
                                    String password) {

    public Player toPlayer(){
        return new Player(login(), password());
    }
}
