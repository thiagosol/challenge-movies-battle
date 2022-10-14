package com.thiagosol.moviesbattle.entrypoint.dto;

import com.thiagosol.moviesbattle.core.domain.Game;
import com.thiagosol.moviesbattle.core.domain.enums.GameStatus;
import io.swagger.v3.oas.annotations.media.Schema;

import java.util.List;

public record GameResponseDTO(@Schema(description = "Game status", example = "STARTED")
                              GameStatus status,

                              @Schema(description = "List of battles")
                              List<BattleResponseDTO> battles) {

    public GameResponseDTO(Game game){
        this(game.getStatus(), game.getBattles().stream().map(BattleResponseDTO::new).toList());
    }

}
