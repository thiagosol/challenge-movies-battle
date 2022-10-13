package com.thiagosol.moviesbattle.entrypoint.dto;

import com.thiagosol.moviesbattle.core.domain.Player;

public record PlayerLoginRequestDTO(String login, String password) {

    public Player toPlayer(){
        return new Player(login(), password());
    }
}
