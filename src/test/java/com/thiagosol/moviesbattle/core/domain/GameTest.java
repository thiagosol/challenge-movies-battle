package com.thiagosol.moviesbattle.core.domain;

import com.thiagosol.moviesbattle.core.domain.enums.GameStatus;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;

class GameTest {

    @Test
    void endGameTest() {
        var game = getGame();
        assertEquals(GameStatus.STARTED, game.getStatus());

        game.end();
        assertEquals(GameStatus.FINISHED, game.getStatus());
    }

    @Test
    void updateGameTest() {
        var game = getGame();
        var battleWinOne = getBattle("t001", "t002");
        game.addBattle(battleWinOne);
        assertEquals(GameStatus.STARTED, game.getStatus());
        assertEquals(0, game.getCountWon());
        assertEquals(0, game.getCountLost());
        assertEquals(BigDecimal.ZERO, game.getRating());

        battleWinOne.play("t001",new Movie("t001", "tittle", 2022L));
        game.update();
        assertEquals(GameStatus.STARTED, game.getStatus());
        assertEquals(1, game.getCountWon());
        assertEquals(0, game.getCountLost());
        assertEquals(new BigDecimal("100.00"), game.getRating());

        var battleLostOne = getBattle("t003", "t004");
        game.addBattle(battleLostOne);
        battleLostOne.play("t003",new Movie("t004", "tittle", 2022L));
        game.update();
        assertEquals(GameStatus.STARTED, game.getStatus());
        assertEquals(1, game.getCountWon());
        assertEquals(1, game.getCountLost());
        assertEquals(new BigDecimal("50.00"), game.getRating());

        var battleLostTwo = getBattle("t005", "t006");
        game.addBattle(battleLostTwo);
        battleLostTwo.play("t005",new Movie("t006", "tittle", 2022L));
        game.update();
        assertEquals(GameStatus.STARTED, game.getStatus());
        assertEquals(1, game.getCountWon());
        assertEquals(2, game.getCountLost());
        assertEquals(new BigDecimal("33.33"), game.getRating());

        var battleLostTree = getBattle("t007", "t008");
        game.addBattle(battleLostTree);
        battleLostTree.play("t007",new Movie("t008", "tittle", 2022L));
        game.update();
        assertEquals(GameStatus.FINISHED, game.getStatus());
        assertEquals(1, game.getCountWon());
        assertEquals(3, game.getCountLost());
        assertEquals(new BigDecimal("25.00"), game.getRating());
    }

    private Game getGame(){
        var player = new Player("player1", "pass");
        return new Game(player);
    }

    private Battle getBattle(String movieAIMDB, String movieBIMDB){
        var player = new Player("player1", "pass");
        return new Battle(new Game(player),
                new Movie(movieAIMDB, "tittle", 2022L),
                new Movie(movieBIMDB, "tittle", 2022L));
    }
}