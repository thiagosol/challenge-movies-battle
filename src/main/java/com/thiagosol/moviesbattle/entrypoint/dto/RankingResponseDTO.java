package com.thiagosol.moviesbattle.entrypoint.dto;

import com.thiagosol.moviesbattle.core.domain.Ranking;

import java.math.BigDecimal;

public record RankingResponseDTO(int position, PlayerResponseDTO player, BigDecimal points) {

    public RankingResponseDTO(Ranking ranking){
        this(ranking.getPosition(), new PlayerResponseDTO(ranking.getPlayer()), ranking.getPoints());
    }
}
