package com.thiagosol.moviesbattle.entrypoint.dto;

import com.thiagosol.moviesbattle.core.domain.Ranking;
import io.swagger.v3.oas.annotations.media.Schema;

import java.math.BigDecimal;

public record RankingResponseDTO(@Schema(description = "Player position", example = "1")
                                 int position,

                                 @Schema(description = "Player")
                                 PlayerResponseDTO player,

                                 @Schema(description = "Player points", example = "178.67")
                                 BigDecimal points) {

    public RankingResponseDTO(Ranking ranking){
        this(ranking.getPosition(), new PlayerResponseDTO(ranking.getPlayer()), ranking.getPoints());
    }
}
