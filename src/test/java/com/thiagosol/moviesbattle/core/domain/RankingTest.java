package com.thiagosol.moviesbattle.core.domain;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class RankingTest {

    @Test
    void getRankingTest() {
        var mapPlayerPoint = new HashMap<Player, BigDecimal>();
        mapPlayerPoint.put( new Player("player1", ""), new BigDecimal("10"));
        mapPlayerPoint.put( new Player("player2", ""), new BigDecimal("5"));
        mapPlayerPoint.put( new Player("player3", ""), new BigDecimal("58"));
        mapPlayerPoint.put( new Player("player4", ""), new BigDecimal("42"));
        mapPlayerPoint.put( new Player("player5", ""), new BigDecimal("51"));

        var ranking = Ranking.getRanking(mapPlayerPoint);
        assertFalse(ranking.isEmpty());
        assertEquals(1, getRankingByPlayerLogin(ranking, "player3").getPosition());
        assertEquals(2, getRankingByPlayerLogin(ranking, "player5").getPosition());
        assertEquals(3, getRankingByPlayerLogin(ranking, "player4").getPosition());
        assertEquals(4, getRankingByPlayerLogin(ranking, "player1").getPosition());
        assertEquals(5, getRankingByPlayerLogin(ranking, "player2").getPosition());
    }

    private Ranking getRankingByPlayerLogin(List<Ranking> rankingList, String playerLogin){
        return rankingList.stream()
                .filter(ranking -> ranking.getPlayer().getLogin().equals(playerLogin))
                .findFirst()
                .orElseThrow();
    }
}