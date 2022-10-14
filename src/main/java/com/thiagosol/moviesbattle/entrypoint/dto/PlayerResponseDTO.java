package com.thiagosol.moviesbattle.entrypoint.dto;

import com.thiagosol.moviesbattle.core.domain.Player;
import io.swagger.v3.oas.annotations.media.Schema;

public record PlayerResponseDTO(@Schema(description = "Player username", example = "player1")
                                String login) {

    public PlayerResponseDTO(Player player){
        this(player.getLogin());
    }
}
