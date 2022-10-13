package com.thiagosol.moviesbattle.entrypoint.dto;

import com.thiagosol.moviesbattle.core.domain.Game;
import com.thiagosol.moviesbattle.core.domain.enums.GameStatus;

import java.util.List;

public record GameResponseDTO(GameStatus status, List<BattleResponseDTO> battles) {

    public GameResponseDTO(Game game){
        this(game.getStatus(), game.getBattles().stream().map(BattleResponseDTO::new).toList());
    }

}
