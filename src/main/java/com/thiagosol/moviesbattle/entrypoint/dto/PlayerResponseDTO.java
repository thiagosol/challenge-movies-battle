package com.thiagosol.moviesbattle.entrypoint.dto;

import com.thiagosol.moviesbattle.core.domain.Player;

public record PlayerResponseDTO(String login) {

    public PlayerResponseDTO(Player player){
        this(player.getLogin());
    }
}
