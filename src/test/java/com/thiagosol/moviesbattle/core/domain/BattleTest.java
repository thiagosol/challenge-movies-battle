package com.thiagosol.moviesbattle.core.domain;

import com.thiagosol.moviesbattle.core.domain.enums.BattleStatus;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class BattleTest {

    @Test
    void getBattleIdentifierTest() {
        var battleOrdered = getBattle("t001","t002");
        assertEquals("t001-t002", battleOrdered.getBattleIdentifier());

        var battleReverseOrder = getBattle("t002","t001");
        assertEquals("t001-t002", battleReverseOrder.getBattleIdentifier());
    }

    @Test
    void playBattleTest() {
        var battleWon = getBattle("t001","t002");
        assertEquals(BattleStatus.WAITING, battleWon.getStatus());

        battleWon.play("t001", new Movie("t001", "tittle", 2022L));
        assertEquals(BattleStatus.WON, battleWon.getStatus());

        var battleLost = getBattle("t001","t002");
        assertEquals(BattleStatus.WAITING, battleLost.getStatus());

        battleLost.play("t001", new Movie("t002", "tittle", 2022L));
        assertEquals(BattleStatus.LOST, battleLost.getStatus());
    }

    private Battle getBattle(String movieAIMDB, String movieBIMDB){
        var player = new Player("player1", "pass");
        return new Battle(new Game(player),
                new Movie(movieAIMDB, "tittle", 2022L),
                new Movie(movieBIMDB, "tittle", 2022L));
    }
}